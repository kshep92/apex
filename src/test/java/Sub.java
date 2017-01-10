import com.reviselabs.apex.routing.SubRouter;

class Sub extends SubRouter {
    @Override
    public void configure() {
        get("/", ctx -> ctx.ok().end("You're in the sub router!"));
    }
}