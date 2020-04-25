package com.company.applications.resources;

import com.company.applications.api.CompanyArtifact;
import com.company.applications.service.MavenDeprecationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/deprecate")
@Api("/deprecate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class ArtifactDeprecator {
    private static final Logger logger = LoggerFactory.getLogger(ArtifactDeprecator.class);

    public ArtifactDeprecator() {
    }

    @PUT
    @ApiOperation("Deprecate Target")
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/target")
    public Response depricatTarget(@QueryParam("artifactPath") @Valid final String artifactPath) {
        logger.info("PUT /deprecate/target request received.");
        Response response;
        if (StringUtils.isEmpty(artifactPath)) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            response = Response.ok(MavenDeprecationService.getMessage()).build();
        } catch (Exception e) {
            logger.error("Failed to deprecate", e);
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
        return response;
    }

    @POST
    @Path("/artifact")
    @ApiOperation("Deprecate Artifact")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deprecateArtifact(@Valid CompanyArtifact artifact) {
        logger.info("POST /deprecate/artifact request received.");
        Response response;
        try {
            Map<String, String> inner = new HashMap();
            inner.put("String_key", "String_value");

            Map<String, Map> allResult = new HashMap<>();
            allResult.put("hello", inner);

            response = Response.ok(allResult).build();
        } catch (Exception e) {
            logger.error("Failed to deprecate: ", e);
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
        return response;
    }
}