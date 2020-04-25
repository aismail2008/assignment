package com.company.applications.resources;

import com.company.applications.api.Employee;
import com.company.applications.db.EmployeeDB;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Path("/client/")
@Api("/client/")
public class EmployeeClientController {

    @GET
    @Path("/employees/")
    @ApiOperation("Get Employees")
    public Response getEmployees() {
        //Do not hard code in your application
        List<Employee> employees = EmployeeDB.getEmployees();
        return Response.ok(employees).build();
    }

    @GET
    @Path("/employees/{id}")
    @ApiOperation("Get Employees by Id")
    public Response getEmployeeById(@PathParam("id") int id) {
        //You can validate here if user is watching his record
    	/*if(id != user.getId()){
    			//Not allowed
    	}*/
        Employee employee = EmployeeDB.getEmployee(id);
        if (employee != null)
            return Response.ok(employee).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }
}