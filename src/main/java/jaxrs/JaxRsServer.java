package jaxrs;

import java.net.URI;
import java.util.Properties;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import jakarta.ws.rs.core.UriBuilder;

public final class JaxRsServer {
    private JaxRsServer() {}

    public static void main(String[] args) {
        Properties properties = Configuration.loadProperties();
        String serverUri = properties.getProperty("serverUri", "http://localhost:8080/");

        URI baseUri = UriBuilder.fromUri(serverUri).build();
        ResourceConfig config = ResourceConfig.forApplicationClass(BeverageBoxApi.class);

        final HttpServer server;
        try {
            server = JdkHttpServerFactory.createHttpServer(baseUri, config);
        } catch (RuntimeException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println("Server started at " + serverUri);

        final Object lock = new Object();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            try {
                server.stop(1);
            } catch (IllegalStateException ise) {
                System.err.println("ServiceLocator already shut down, ignoring stop(): " + ise.getMessage());
            } catch (Throwable t) {
                System.err.println("Error stopping server: " + t.getMessage());
                t.printStackTrace();
            }
            synchronized (lock) {
                lock.notifyAll();
            }
            System.out.println("Server stopped.");
        }));

        // Wait until shutdown hook notifies
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
