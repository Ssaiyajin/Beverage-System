package jaxrs;

import jaxrs.resources.BeverageResource;
import jaxrs.resources.OrderResource;
import jaxrs.resources.EmployeeResource;
import jaxrs.resources.HelloResource;
import jaxrs.resources.BottleResource;
import jaxrs.resources.CrateResource;
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

        // register resources directly so endpoints (e.g. /order) are available
        resources.add(BeverageResource.class);
        resources.add(OrderResource.class);
        resources.add(EmployeeResource.class);
        resources.add(HelloResource.class);
        resources.add(BottleResource.class);
        resources.add(CrateResource.class);

        return resources;
    }
}
