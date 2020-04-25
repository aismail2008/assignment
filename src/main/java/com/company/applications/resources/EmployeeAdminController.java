package com.company.applications.resources;


import com.company.applications.api.Employee;
import com.company.applications.api.User;
import com.company.applications.db.EmployeeDB;
import io.dropwizard.auth.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

@Path("/employees")
@Api("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeAdminController {

    private final Validator validator;

    public EmployeeAdminController(Validator validator) {
        this.validator = validator;
    }

    @PermitAll
    @GET
    public Response getEmployees(@Auth User user) {
        return Response.ok(EmployeeDB.getEmployees()).build();
    }

    @RolesAllowed("USER")
    @GET
    @Path("/{id}")
    @ApiOperation("Get Employee By Id")
    public Response getEmployeeById(@PathParam("id") Integer id, @Auth User user) {
        //You can validate here if user is watching his record
    	/*if(id != user.getId()){
    			//Not allowed
    	}*/
        Employee employee = EmployeeDB.getEmployee(id);
        if (employee != null)
            return Response.ok(employee).build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }

    @RolesAllowed("ADMIN")
    @POST
    @ApiOperation("Create Employee")
    public Response createEmployee(Employee employee, @Auth User user) throws URISyntaxException {
        // validation
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        Employee e = EmployeeDB.getEmployee(employee.getId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Employee> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            EmployeeDB.updateEmployee(employee.getId(), employee);
            return Response.created(new URI("/employees/" + employee.getId()))
                    .build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    @ApiOperation("Update Employee")
    public Response updateEmployeeById(@PathParam("id") Integer id, Employee employee) {
        // validation
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        Employee e = EmployeeDB.getEmployee(employee.getId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Employee> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            employee.setId(id);
            EmployeeDB.updateEmployee(id, employee);
            return Response.ok(employee).build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation("Delete Employee")
    public Response removeEmployeeById(@PathParam("id") Integer id) {
        Employee employee = EmployeeDB.getEmployee(id);
        if (employee != null) {
            EmployeeDB.removeEmployee(id);
            return Response.ok().build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }
}