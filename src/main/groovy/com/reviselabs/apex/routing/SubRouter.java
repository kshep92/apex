package com.reviselabs.apex.routing;

import com.google.inject.Inject;
import com.reviselabs.apex.di.ApplicationContextContainer;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public abstract class SubRouter extends RoutingComponent implements ApplicationContextContainer, ISubRouter {
    private Router parent;

    @Inject
    private void _setRouter(Vertx vertx) {
        assert vertx != null;
        setRouter(Router.router(vertx));
    }

    public Router getParent() {
        return parent;
    }

    public void setParent(Router parent) {
        this.parent = parent;
    }
}
