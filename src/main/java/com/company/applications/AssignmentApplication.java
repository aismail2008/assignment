package com.company.applications;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import com.company.applications.api.User;
import com.company.applications.auth.ExampleAuthenticator;
import com.company.applications.auth.ExampleAuthorizer;
import com.company.applications.filter.FiltersBundle;
import com.company.applications.health.Healthcheck;
import com.company.applications.resources.CounterResource;
import com.company.applications.resources.EmployeeAdminController;
import com.company.applications.resources.EmployeeClientController;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class AssignmentApplication extends Application<AssignmentConfiguration> {

    public static void main(final String[] args) throws Exception {
        new AssignmentApplication().run(args);
    }

    @Override
    public String getName() {
        return "com.company.applications";
    }

    @Override
    public void initialize(final Bootstrap<AssignmentConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<AssignmentConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AssignmentConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });

        bootstrap.addBundle(new FiltersBundle());
    }

    @Override
    public void run(final AssignmentConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(environment);
        environment.jersey().packages(getName());
        // Set the application health checks
        environment.healthChecks().register("health", new Healthcheck());

        environment.jersey().register(CounterResource.class);

        //This is Basic Authntication part
        environment.jersey().register(environment.getValidator());
        environment.jersey().register(new EmployeeAdminController(environment.getValidator()));
        environment.jersey().register(EmployeeClientController.class);
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new ExampleAuthenticator())
                .setAuthorizer(new ExampleAuthorizer())
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
    }

}
