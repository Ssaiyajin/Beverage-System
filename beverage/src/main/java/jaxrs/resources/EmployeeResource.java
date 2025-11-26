package jaxrs.resources;

import jaxrs.controller.EmployeeService;
import jaxrs.dto.EmployeeDTO;
import jaxrs.dto.ErrorMessage;
import jaxrs.dto.ErrorType;
import jaxrs.model.Employee;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("employees")
public class EmployeeResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(@Context UriInfo uriInfo) {
        List<Employee> models = EmployeeService.instance.getEmployees();
        return Response.ok(new GenericEntity<List<EmployeeDTO>>(EmployeeDTO.marshall(models, uriInfo.getBaseUri())){}).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") int id, @Context UriInfo uriInfo) {
        Employee employee = EmployeeService.instance.getEmployee(id);
        if (employee == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(ErrorType.NOT_FOUND, "Employee not found"))
                    .build();
        }
        return Response.ok(new EmployeeDTO(employee, uriInfo.getBaseUri())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(EmployeeDTO employeeDto, @Context UriInfo uriInfo) {
        Employee created = EmployeeService.instance.createEmployee(employeeDto);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(location).entity(new EmployeeDTO(created, uriInfo.getBaseUri())).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") int id, Employee employee, @Context UriInfo uriInfo) {
        Employee updated = EmployeeService.instance.updateEmployee(id, employee);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(ErrorType.NOT_FOUND, "Employee not found"))
                    .build();
        }
        return Response.ok(new EmployeeDTO(updated, uriInfo.getBaseUri())).build();
    }
}
