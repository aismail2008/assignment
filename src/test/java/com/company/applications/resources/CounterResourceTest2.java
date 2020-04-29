package com.company.applications.resources;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.jersey.DropwizardResourceConfig;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class CounterResourceTest2 extends JerseyTest {

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected javax.ws.rs.core.Application configure() {
        Environment environment = Mockito.mock(Environment.class);
        Mockito.when(environment.metrics()).thenReturn(new MetricRegistry());
        return DropwizardResourceConfig.forTesting().register(new CounterResource(environment));
        // You can remove the mockito for environment and inject it using abstract binder
    }

    @Test
    void logResourceWithHK2Binder() throws IOException {
        final javax.ws.rs.core.Response response = target("/counter").request().get();
        assertTrue(response.readEntity(String.class).startsWith("The current count is:"));
    }
}
