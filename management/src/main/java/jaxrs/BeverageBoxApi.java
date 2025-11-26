package jaxrs;

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

        // load resources by FQCN at runtime to avoid compile-time dependency
        addIfPresent(resources, "jaxrs.resources.BeverageResource");
        addIfPresent(resources, "jaxrs.resources.EmployeeResource");
        addIfPresent(resources, "jaxrs.resources.OrderResource");
        addIfPresent(resources, "jaxrs.resources.HelloResource");

        return resources;
    }

    private void addIfPresent(Set<Class<?>> resources, String fqcn) {
        try {
            Class<?> c = Class.forName(fqcn);
            resources.add(c);
        } catch (ClassNotFoundException ignored) {
            // skip if not present
        }
    }
}
