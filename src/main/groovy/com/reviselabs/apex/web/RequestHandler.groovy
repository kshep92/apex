package com.reviselabs.apex.web

import com.reviselabs.apex.web.routing.ApexRoutingContext

/**
 * Created by ksheppard on 22/12/2016.
 */
interface RequestHandler {
    void handle(ApexRoutingContext context);
}