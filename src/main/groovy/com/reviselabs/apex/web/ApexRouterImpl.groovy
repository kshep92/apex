package com.reviselabs.apex.web

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.impl.RouterImpl

/**
 * Created by ksheppard on 22/12/2016.
 */
class ApexRouterImpl implements Router {

    @Delegate RouterImpl delegate

    ApexRouterImpl(Vertx vertx) {
        this.delegate = new RouterImpl(vertx);
    }
}
