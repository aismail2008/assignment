package com.company.applications;

import com.company.applications.health.Healthcheck;
import com.company.applications.resources.CounterResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class AssignmentApplication extends Application<AssignmentConfiguration> {

    public static void main(final String[] args) throws Exception {
        new AssignmentApplication().run(args);
    }

    @Override
    public String getName() {
        return "assignment";
    }

    @Override
    public void initialize(final Bootstrap<AssignmentConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<AssignmentConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AssignmentConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(final AssignmentConfiguration configuration,
                    final Environment environment) {
        environment.jersey().packages(getName());

        // Set the application health checks
        environment.healthChecks().register("health", new Healthcheck());

        environment.jersey().register(new CounterResource(environment));
    }

}
