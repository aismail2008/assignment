package com.company.applications.resources;

import com.company.applications.HttpHelper;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertTrue;

@ExtendWith(DropwizardExtensionsSupport.class)
public class CounterResourceTest3 {

    final static DropwizardAppExtension<TestConfiguration> RULE = new DropwizardAppExtension<>(TestApplication.class);

    @Test
    public void testDevLogic() throws Exception {
        try (Response response = HttpHelper.doGet("http://localhost:" + RULE.getLocalPort() + "/counter")) {
            assertTrue(response.body().string().startsWith("The current count is:"));
        }
    }

    static class TestApplication extends Application<TestConfiguration> {
        @Override
        public void initialize(Bootstrap<TestConfiguration> bootstrap) {
            System.out.println("INIT");
        }

        @Override
        public void run(TestConfiguration configuration, Environment environment) throws Exception {
            environment.jersey().register(new CounterResource(environment));
        }
    }

    static public class TestConfiguration extends Configuration {
    }
}
