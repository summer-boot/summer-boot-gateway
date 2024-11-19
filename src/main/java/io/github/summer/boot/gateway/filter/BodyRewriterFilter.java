package io.github.summer.boot.gateway.filter;

import io.github.summer.boot.gateway.scheduler.CompletionListener;
import io.github.summer.boot.gateway.service.RewriterService;
import io.github.summer.boot.gateway.util.AttributeHolder;
import io.github.summer.boot.gateway.util.FilterOrder;
import jakarta.annotation.Resource;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * 重写请求体和响应体
 *
 * @author changebooks@qq.com
 */
@Service
public class BodyRewriterFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyRewriterFilter.class);

    @Resource
    private ServerCodecConfigurer serverCodecConfigurer;

    @Resource
    private RewriterService rewriterService;

    @Resource
    private CompletionListener completionListener;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = bodyInserter(exchange);
        CachedBodyOutputMessage outputMessage = outputMessage(exchange);

        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> chain.filter(exchange.mutate()
                        .request(new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @NonNull
                            @Override
                            public HttpHeaders getHeaders() {
                                return super.getHeaders();
                            }

                            @NonNull
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return outputMessage.getBody();
                            }
                        })
                        .response(new ServerHttpResponseDecorator(exchange.getResponse()) {
                            @NonNull
                            @Override
                            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                                if (body instanceof Flux) {
                                    return Flux.from(body)
                                            .collectList()
                                            .flatMap(list -> {
                                                DataBuffer value = new DefaultDataBufferFactory().join(list);
                                                Mono<DataBuffer> result = rewriteResponse(exchange, value);
                                                return super.writeWith(result);
                                            });
                                }

                                if (body instanceof Mono) {
                                    return Mono.from(body)
                                            .flatMap(value -> {
                                                Mono<DataBuffer> result = rewriteResponse(exchange, value);
                                                return super.writeWith(result);
                                            });
                                }

                                String routeId = AttributeHolder.getRouteId(exchange);
                                LOGGER.error("write response failed, routeId: {}, body must be [Flux, Mono]", routeId);
                                return super.writeWith(body);
                            }
                        })
                        .build()
                )));
    }

    /**
     * Body OutputMessage
     *
     * @param exchange Web Exchange
     * @return CachedBodyOutputMessage
     */
    private CachedBodyOutputMessage outputMessage(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders requestHeaders = newRequestHeaders(request);
        return new CachedBodyOutputMessage(exchange, requestHeaders);
    }

    /**
     * Body Inserter
     *
     * @param exchange Web Exchange
     * @return BodyInserter
     */
    private BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String requestBody = defaultRequestBody(request);

        List<HttpMessageReader<?>> messageReaders = serverCodecConfigurer.getReaders();
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);

        return BodyInserters.fromPublisher(
                serverRequest
                        .bodyToMono(String.class)
                        .defaultIfEmpty(requestBody)
                        .flatMap(body -> rewriteRequest(exchange, body)),
                String.class
        );
    }

    /**
     * Rewrite Request
     *
     * @param exchange Web Exchange
     * @param value    Request body
     * @return Rewritten Request body
     */
    private Mono<String> rewriteRequest(ServerWebExchange exchange, String value) {
        String body = rewriterService.rewriteRequestBody(exchange, value);
        return Mono.justOrEmpty(body);
    }

    /**
     * Rewrite Response
     *
     * @param exchange Web Exchange
     * @param value    Response body
     * @return Rewritten Response body
     */
    private Mono<DataBuffer> rewriteResponse(ServerWebExchange exchange, DataBuffer value) {
        DataBuffer body = rewriterService.rewriteResponseBody(exchange, value);

        completionListener.afterComplete(exchange);
        return Mono.justOrEmpty(body);
    }

    /**
     * 默认请求体
     *
     * @param request Http Request
     * @return Default Request body
     */
    private String defaultRequestBody(ServerHttpRequest request) {
        URI uri = request.getURI();
        String rawQuery = uri.getRawQuery();
        return rawQuery != null ? rawQuery : "";
    }

    /**
     * 克隆请求头
     *
     * @param request Http Request
     * @return Http Headers
     */
    private HttpHeaders newRequestHeaders(ServerHttpRequest request) {
        HttpHeaders rawHeaders = request.getHeaders();

        HttpHeaders result = new HttpHeaders();
        result.putAll(rawHeaders);
        return result;
    }

    @Override
    public int getOrder() {
        return FilterOrder.BODY_REWRITER;
    }

}
