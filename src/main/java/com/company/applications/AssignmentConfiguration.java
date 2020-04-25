package com.company.applications;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.validation.constraints.NotEmpty;

public class AssignmentConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty("ApplicationName")
    private String appName;


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    // TODO: implement service configuration
    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
}
