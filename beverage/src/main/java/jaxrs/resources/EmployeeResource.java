package jaxrs.resources;

import jaxrs.error.ErrorMessage;
import jaxrs.error.ErrorType;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@Singleton
@Path("employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    private static final Logger logger = Logger.getLogger(EmployeeResource.class.getName());
    private final Map<Integer, Employee> store = new ConcurrentHashMap<>();
    private final AtomicInteger idSeq = new AtomicInteger(1);

    public EmployeeResource() {
        // seed sample data
        Employee e1 = new Employee(idSeq.getAndIncrement(), "Alice", "manager");
        Employee e2 = new Employee(idSeq.getAndIncrement(), "Bob", "clerk");
        store.put(e1.getId(), e1);
        store.put(e2.getId(), e2);
    }

    @GET
    public Response listEmployees() {
        List<Employee> list = new ArrayList<>(store.values());
        return Response.ok(list).build();
    }

    @GET
    @Path("{id}")
    public Response getEmployee(@PathParam("id") int id) {
        Employee e = store.get(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(ErrorType.NOT_FOUND, "Employee not found"))
                    .build();
        }
        return Response.ok(e).build();
    }

    @POST
    public Response createEmployee(Employee employee, @Context UriInfo uriInfo) {
        if (employee == null || employee.getName() == null || employee.getName().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Employee name is required"))
                    .build();
        }
        int id = idSeq.getAndIncrement();
        employee.setId(id);
        store.put(id, employee);
        URI location = uriInfo.getAbsolutePathBuilder().path(Integer.toString(id)).build();
        logger.info("Created employee " + id);
        return Response.created(location).entity(employee).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteEmployee(@PathParam("id") int id) {
        Employee removed = store.remove(id);
        if (removed == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(ErrorType.NOT_FOUND, "Employee not found"))
                    .build();
        }
        return Response.noContent().build();
    }

    // simple DTO / model used by this resource
    public static class Employee {
        private int id;
        private String name;
        private String role;

        public Employee() {}

        public Employee(int id, String name, String role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}