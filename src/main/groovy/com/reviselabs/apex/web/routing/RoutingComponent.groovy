package com.reviselabs.apex.web.routing
import com.reviselabs.apex.web.RequestHandler
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

trait RoutingComponent {
    Router router

//    Be careful. This method won't be available to implementing classes because
//    private methods will not appear in the trait contract interface
    private RoutingComponent doRoute(HttpMethod method, String url, RequestHandler handler) {
        router.route(method, url)
                .handler({context -> handler.handle(new ApexRoutingContext(context))});
        return this
    }

    public RoutingComponent get(String url, RequestHandler handler) {
        doRoute(HttpMethod.GET, url, handler);
    }

    public RoutingComponent get(String url, RequestHandler... handlers) {
        handlers.each { get(url, it) }
        return this
    }

    public RoutingComponent post(String url, RequestHandler handler) {
        doRoute(HttpMethod.POST, url, handler);
        return this
    }

    public RoutingComponent post(String url, RequestHandler... handlers) {
        handlers.each { post(url, it) }
        return this
    }

    public RoutingComponent put(String url, RequestHandler handler) {
        doRoute(HttpMethod.PUT, url, handler);
    }

    public RoutingComponent put(String url, RequestHandler... handlers) {
        handlers.each { put(url, it) }
        return this
    }

    public RoutingComponent delete(String url, RequestHandler handler) {
        doRoute(HttpMethod.DELETE, url, handler);
    }

    public RoutingComponent delete(String url, RequestHandler... handlers) {
        handlers.each { delete(url, it) }
        return this
    }

    public RoutingComponent before(String url, RequestHandler handler) {
        router.route(url).handler({context -> handler.handle(new ApexRoutingContext(context))})
        return this
    }

    public RoutingComponent before(RequestHandler handler) {
        router.route().handler({context -> handler.handle(new ApexRoutingContext(context))})
        return this
    }

}