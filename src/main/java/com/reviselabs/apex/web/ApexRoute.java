package com.reviselabs.apex.web;

import io.vertx.core.Handler;
import io.vertx.ext.web.Route;

/**
 * Created by ksheppard on 22/12/2016.
 */
public interface ApexRoute {

    Route handler(Handler<RoutingContext> requestHandler);

    Route blockingHandler(Handler<RoutingContext> requestHandler);

    Route blockingHandler(Handler<RoutingContext> requestHandler, boolean ordered);

    Route failureHandler(Handler<RoutingContext> failureHandler);

}
