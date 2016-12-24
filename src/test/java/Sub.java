import com.reviselabs.apex.web.routing.SubRouter;

class Sub extends SubRouter {
    @Override
    public void configure() {
        get("/", ctx -> ctx.ok().close("You're in the sub router!"));
    }
}