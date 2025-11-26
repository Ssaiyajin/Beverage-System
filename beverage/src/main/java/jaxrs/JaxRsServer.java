package jaxrs;

import jakarta.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public final class JaxRsServer {
    private static final Logger LOGGER = Logger.getLogger(JaxRsServer.class.getName());
    private static final Properties PROPERTIES = Configuration.loadProperties();
    private JaxRsServer() { /* utility */ }

    public static void main(String[] args) throws Exception {
        final String serverUri = PROPERTIES.getProperty("serverUri", "http://localhost:9999/");
        URI baseUri;
        try {
            baseUri = UriBuilder.fromUri(serverUri).build();
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Invalid serverUri: {0}, falling back to http://localhost:9999/", serverUri);
            baseUri = UriBuilder.fromUri("http://localhost:9999/").build();
        }

        final ResourceConfig rc = ResourceConfig.forApplicationClass(BeverageBoxApi.class);
        HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, rc);

        LOGGER.info("Server started at " + baseUri + " - press CTRL+C to stop.");

        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean stopping = new AtomicBoolean(false);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (stopping.compareAndSet(false, true)) {
                    LOGGER.info("Shutdown hook: stopping server...");
                    try {
                        server.stop(0);
                    } catch (IllegalStateException ise) {
                        // Jersey/HK2 may already be shutting down — ignore
                        LOGGER.log(Level.FINE, "ServiceLocator already shut down, ignoring stop()", ise);
                    } catch (Throwable t) {
                        LOGGER.log(Level.WARNING, "Error while stopping server in shutdown hook", t);
                    }
                }
            } finally {
                latch.countDown();
            }
        }));

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (stopping.compareAndSet(false, true)) {
            LOGGER.info("Stopping server");
            try {
                server.stop(0);
            } catch (IllegalStateException ise) {
                LOGGER.log(Level.FINE, "ServiceLocator already shut down at final stop(), ignoring", ise);
            } catch (Throwable t) {
                LOGGER.log(Level.WARNING, "Error while stopping server", t);
            }
        }
    }
}
