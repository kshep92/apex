package config

import com.google.inject.name.Names
import com.reviselabs.apex.config.ApexConfiguration

/**
 * Created by ksheppard on 23/12/2016.
 */
class TestConfig extends ApexConfiguration {

    {
        serverConfig().port = 8000
    }

    void configure() {
        super
        bind(String.class).annotatedWith(Names.named("key")).toInstance('123');
    }
}
