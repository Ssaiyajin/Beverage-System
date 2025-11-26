package jaxrs;

import jakarta.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public final class JaxRsServer {
    private static final Logger LOGGER = Logger.getLogger(JaxRsServer.class.getName());
    private static final Properties PROPERTIES = Configuration.loadProperties();
    private JaxRsServer() { /* utility */ }

    public static void main(String[] args) {
        final String serverUri = PROPERTIES.getProperty("serverUri", "http://localhost:8080/");
        URI baseUri;
        try {
            baseUri = UriBuilder.fromUri(serverUri).build();
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Invalid serverUri: {0}, falling back to http://localhost:8080/", serverUri);
            baseUri = UriBuilder.fromUri("http://localhost:8080/").build();
        }

        final URI finalBaseUri = baseUri;
        final ResourceConfig config = ResourceConfig.forApplicationClass(BeverageBoxApi.class);
        final AtomicReference<HttpServer> serverRef = new AtomicReference<>();
        final AtomicBoolean stopping = new AtomicBoolean(false);

        try {
            HttpServer server = JdkHttpServerFactory.createHttpServer(finalBaseUri, config);
            serverRef.set(server);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                HttpServer s = serverRef.get();
                if (s != null && stopping.compareAndSet(false, true)) {
                    LOGGER.info("Shutdown hook: stopping server...");
                    try {
                        s.stop(0);
                    } catch (IllegalStateException ise) {
                        LOGGER.log(Level.FINE, "ServiceLocator already shut down, ignoring stop()", ise);
                    } catch (Throwable t) {
                        LOGGER.log(Level.WARNING, "Error while stopping server in shutdown hook", t);
                    }
                }
            }));

            LOGGER.info(() -> "Server started at " + finalBaseUri + " - press ENTER to stop.");
            try {
                System.in.read();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Error waiting for input to stop server", e);
            }
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Failed to start HTTP server", e);
        } finally {
            HttpServer s = serverRef.get();
            if (s != null && stopping.compareAndSet(false, true)) {
                LOGGER.info("Stopping server");
                try {
                    s.stop(0);
                } catch (IllegalStateException ise) {
                    LOGGER.log(Level.FINE, "ServiceLocator already shut down at final stop(), ignoring", ise);
                } catch (Throwable t) {
                    LOGGER.log(Level.WARNING, "Error while stopping server", t);
                }
            }
        }
    }
}
