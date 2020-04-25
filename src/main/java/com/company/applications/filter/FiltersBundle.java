package com.company.applications.filter;

import com.company.applications.AssignmentConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class FiltersBundle implements ConfiguredBundle<AssignmentConfiguration> {

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
    }

    @Override
    public void run(AssignmentConfiguration configuration, Environment environment) throws Exception {
        environment.servlets().addFilter("AllowedMethodFilter", new AllowedMethodFilter())
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        // Adds PenTestJerseyFilter to the Admin Environment
        environment.admin().addFilter("AdminAllowedMethodFilter", new AllowedMethodFilter())
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        // Adds PenTestJerseyFilter to the Servlet Environment
        environment.jersey().register(new RuntimeFilter());
    }
}