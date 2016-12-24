import com.reviselabs.apex.config.ApexConfiguration;

public class MyConfig extends ApexConfiguration {
    @Override
    protected void configure() {
        getServerOptions().setPort(3001);
    }
}
