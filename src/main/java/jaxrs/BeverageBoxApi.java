package jaxrs;

import jaxrs.resources.*;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BeverageBoxApi extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();

        resources.add(BeverageResource.class);
        resources.add(OrderResource.class);
        resources.add(EmployeeResource.class);
        // swagger-ui
        resources.add(SwaggerUI.class);

        return resources;
    }
}
