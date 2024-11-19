package io.github.summer.boot.gateway.controller;

import io.github.summer.boot.gateway.rewriter.*;
import io.github.summer.boot.gateway.service.*;
import io.github.summer.boot.gateway.util.RateLimiter;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 查询列表
 *
 * @author changebooks@qq.com
 */
@RequestMapping("select-list")
@RestController
public class SelectListController {

    @Resource
    private RouterService routerService;

    @Resource
    private IpBlacklistService ipBlacklistService;

    @Resource
    private IpLimiterService ipLimiterService;

    @Resource
    private PathLimiterService pathLimiterService;

    @Resource
    private PathReplaceService pathReplaceService;

    @Resource
    private SchemeRewriterService schemeRewriterService;

    @Resource
    private HostRewriterService hostRewriterService;

    @Resource
    private PortRewriterService portRewriterService;

    @Resource
    private PathRewriterService pathRewriterService;

    @Resource
    private QueryRewriterService queryRewriterService;

    @Resource
    private RequestHeaderRewriterService requestHeaderRewriterService;

    @Resource
    private RequestBodyRewriterService requestBodyRewriterService;

    @Resource
    private ResponseBodyRewriterService responseBodyRewriterService;

    /**
     * 路由
     *
     * @return [ RouteDefinition ]
     */
    @GetMapping(value = "/router-list")
    public List<RouteDefinition> routerList() {
        return routerService.selectList();
    }

    /**
     * ip黑名单
     *
     * @param routeId 路由id
     * @return [ IpAddr ]
     */
    @GetMapping(value = "/ip-blacklist-list")
    public Set<String> ipBlacklistList(@RequestParam("routeId") String routeId) {
        return ipBlacklistService.selectList(routeId);
    }

    /**
     * ip限流
     *
     * @return [ RouteId : RateLimiter.Parameter ]
     */
    @GetMapping(value = "/ip-limiter-list")
    public Map<String, RateLimiter.Parameter> ipLimiterList() {
        return ipLimiterService.selectList();
    }

    /**
     * 路径限流
     *
     * @param routeId 路由id
     * @return [ UriPath : RateLimiter.Parameter ]
     */
    @GetMapping(value = "/path-limiter-list")
    public Map<String, RateLimiter.Parameter> pathLimiterList(@RequestParam("routeId") String routeId) {
        return pathLimiterService.selectList(routeId);
    }

    /**
     * 路径替换
     *
     * @param routeId 路由id
     * @return [ RawPath : ReplacePath ]
     */
    @GetMapping(value = "/path-replace-list")
    public Map<String, String> pathReplaceList(@RequestParam("routeId") String routeId) {
        return pathReplaceService.selectList(routeId);
    }

    /**
     * 重写协议
     *
     * @param routeId 路由id
     * @return [ SchemeRewriter ]
     */
    @GetMapping(value = "/scheme-rewriter-list")
    public List<SchemeRewriter> schemeRewriterList(@RequestParam("routeId") String routeId) {
        return schemeRewriterService.selectList(routeId);
    }

    /**
     * 重写域名
     *
     * @param routeId 路由id
     * @return [ HostRewriter ]
     */
    @GetMapping(value = "/host-rewriter-list")
    public List<HostRewriter> hostRewriterList(@RequestParam("routeId") String routeId) {
        return hostRewriterService.selectList(routeId);
    }

    /**
     * 重写端口
     *
     * @param routeId 路由id
     * @return [ PortRewriter ]
     */
    @GetMapping(value = "/port-rewriter-list")
    public List<PortRewriter> portRewriterList(@RequestParam("routeId") String routeId) {
        return portRewriterService.selectList(routeId);
    }

    /**
     * 重写路径
     *
     * @param routeId 路由id
     * @return [ PathRewriter ]
     */
    @GetMapping(value = "/path-rewriter-list")
    public List<PathRewriter> pathRewriterList(@RequestParam("routeId") String routeId) {
        return pathRewriterService.selectList(routeId);
    }

    /**
     * 重写请求参数
     *
     * @param routeId 路由id
     * @return [ QueryRewriter ]
     */
    @GetMapping(value = "/query-rewriter-list")
    public List<QueryRewriter> queryRewriterList(@RequestParam("routeId") String routeId) {
        return queryRewriterService.selectList(routeId);
    }

    /**
     * 重写请求头
     *
     * @param routeId 路由id
     * @return [ RequestHeaderRewriter ]
     */
    @GetMapping(value = "/request-header-rewriter-list")
    public List<RequestHeaderRewriter> requestHeaderRewriterList(@RequestParam("routeId") String routeId) {
        return requestHeaderRewriterService.selectList(routeId);
    }

    /**
     * 重写请求体
     *
     * @param routeId 路由id
     * @return [ RequestBodyRewriter ]
     */
    @GetMapping(value = "/request-body-rewriter-list")
    public List<RequestBodyRewriter> requestBodyRewriterList(@RequestParam("routeId") String routeId) {
        return requestBodyRewriterService.selectList(routeId);
    }

    /**
     * 重写响应体
     *
     * @param routeId 路由id
     * @return [ ResponseBodyRewriter ]
     */
    @GetMapping(value = "/response-body-rewriter-list")
    public List<ResponseBodyRewriter> responseBodyRewriterList(@RequestParam("routeId") String routeId) {
        return responseBodyRewriterService.selectList(routeId);
    }

}
