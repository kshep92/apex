package routes
import com.reviselabs.apex.web.RequestHandler
import com.reviselabs.apex.web.routing.SubRouter
import filters.Filters

class SubRoutes extends SubRouter {

    @Override
    void configure() {
        before({
            it.put("user", "kevin@mail.com");
            it.put("permissions", ['manage_posts', 'manage_users'])
            it.next()
        });

        get("/", { context ->
            context.ok().close(context.request().path())
        })

        get("/user_data", { context ->
            context.ok().close(context.get("user") as String)
        })

        get("/allowed", [ Filters.checkPermissions('manage_posts'), { it.ok().close() } ] as RequestHandler[])

        get("/forbidden", [ Filters.checkPermissions('view_finances'), { it.ok().close() } ] as RequestHandler[])

        get("/me",
                { context -> context.bodyAsJson } as RequestHandler,
                { it.ok().close() } as RequestHandler)
    }
}