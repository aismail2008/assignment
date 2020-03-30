package com.company.applications;

import com.company.applications.health.Healthcheck;
import com.company.applications.resources.CounterResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
        // TODO: application initialization
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
