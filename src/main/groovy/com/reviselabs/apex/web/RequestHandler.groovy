package com.reviselabs.apex.web

/**
 * Created by ksheppard on 22/12/2016.
 */
interface RequestHandler {
    void handle(RoutingContext context);
}