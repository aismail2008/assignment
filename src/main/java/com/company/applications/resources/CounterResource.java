package com.company.applications.resources;

import com.codahale.metrics.Counter;
import io.dropwizard.setup.Environment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/counter")
@Api("/counter")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class CounterResource {
    private static final Logger logger = LoggerFactory.getLogger(CounterResource.class);

    private final Counter helloCounter;

    @Inject
    public CounterResource(final Environment environment) {
        this.helloCounter = new Counter();
        environment.metrics().register("test_counter", this.helloCounter);
    }

    @GET
    @ApiOperation("Counter Increment")
    @Produces(MediaType.TEXT_PLAIN)
    public String updateCounter() {
        logger.info("GET /counter request received.");
        helloCounter.inc();
        logger.info("Sending info at Counter +100 : " + helloCounter.getCount());
        return "The current count is: " + helloCounter.getCount();
    }
}