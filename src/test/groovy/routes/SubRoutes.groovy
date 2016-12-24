package routes

import com.reviselabs.apex.web.RouteGroup
import com.reviselabs.apex.web.RoutingContext
import com.reviselabs.apex.web.RequestHandler

class SubRoutes extends RouteGroup {

    SubRoutes() {
        before("/user_data", { it.put("user", "kevin@mail.com"); it.next() })

        get("/", { context ->
            context.ok().close(context.request().path())
        })

        get("/user_data", { context ->
            context.ok().close(context.get("user") as String)
        })

        get("/someroute", { it.next() }, { it.ok().close('Hooray!') })
    }

    @SuppressWarnings("GrMethodMayBeStatic")
    def RequestHandler checkPermissions(String[] permissions) {

        return new RequestHandler() {
            @Override
            void handle(RoutingContext context) {
                if (permissions.length == 0) context.next()
                else context.forbidden().close("You do not have permission to view this area.")
            }
        }

    }


}