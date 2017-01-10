package config
import com.google.inject.Provides
import com.reviselabs.apex.config.ApexConfiguration
import io.vertx.ext.web.templ.MVELTemplateEngine
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import test.data.Database

class TestConfig extends ApexConfiguration {
    Logger logger = LoggerFactory.getLogger(getClass());
    TestConfig() {
        logger.debug("Using custom configuration")
    }

    @Override
    protected void configure() {
        setTemplateEngine(MVELTemplateEngine.create())
        setTemplatesDirectory("src/test/files/templates")
//        (getTemplateEngine() as MVELTemplateEngine).setMaxCacheSize(...)
    }

    @SuppressWarnings("GrMethodMayBeStatic")
    @Provides
    Database getDatabase() {
        logger.debug("Building database instance.")
        return new Database(url: 'mysql.com', user: 'admin', password: 'admin')
    }
}
