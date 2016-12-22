package com.reviselabs.apex.web;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 * Created by ksheppard on 22/12/2016.
 */
interface ApexRouter extends Router {

    static Router router(Vertx vertx) {
        return new ApexRouterImpl(vertx);
    }
}
