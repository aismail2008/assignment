package com.company.applications.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.ResponseMetered;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/example")
@Api("/example")
@Produces(MediaType.TEXT_PLAIN)
public class ExampleResource {

    @ApiOperation("Show")
    @GET
    @Timed
    public String show() {
        return "yay";
    }

    @GET
    @Metered(name = "fancyName") // If name isn't specified, the meter will given the name of the method it decorates.
    @Path("/metered")
    @ApiOperation("metered")
    public String metered() {
        return "woo";
    }

    @GET
    @ExceptionMetered(cause = IOException.class) // Default cause is Exception.class
    @Path("/exception-metered")
    @ApiOperation("exception-metered")
    public String exceptionMetered(@QueryParam("splode") @DefaultValue("false") boolean splode) throws IOException {
        if (splode) {
            throw new IOException("AUGH");
        }
        return "fuh";
    }

    @GET
    @ResponseMetered
    @Path("/response-metered")
    @ApiOperation("response-metered")
    public Response responseMetered(@QueryParam("invalid") @DefaultValue("false") boolean invalid) {
        if (invalid) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }
}