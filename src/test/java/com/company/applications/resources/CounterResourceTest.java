package com.company.applications.resources;

import com.company.applications.AssignmentConfiguration;
import com.company.applications.HttpHelper;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertEquals;

@ExtendWith(DropwizardExtensionsSupport.class)
public class CounterResourceTest {
    final static String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-config.yml");
    final static DropwizardAppExtension<AssignmentConfiguration> RULE = new DropwizardAppExtension<>(TestApplication.class, CONFIG_PATH);
    @Test
    public void testDevLogic() throws Exception {
        try (Response response = HttpHelper.doGet("http://localhost:" + RULE.getLocalPort() + "/counter")) {
            assertEquals(true, response.body().string().startsWith("The current count is:"));
        }
    }

    static public class TestApplication extends Application<AssignmentConfiguration> {

        @Override
        public void initialize(Bootstrap<AssignmentConfiguration> bootstrap) {
            System.out.println("INIT");
        }

        @Override
        public void run(AssignmentConfiguration configuration, Environment environment) throws Exception {
            environment.jersey().register(new CounterResource(environment));
        }
    }
}