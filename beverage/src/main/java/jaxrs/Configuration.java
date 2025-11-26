package jaxrs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Objects;

public final class Configuration {
    private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());

    private Configuration() {
        // utility
    }

    /**
     * Load application properties from classpath resource "config.properties".
     * Returns an empty Properties instance on error (never null).
     */
    public static Properties loadProperties() {
        final Properties properties = new Properties();
        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try (InputStream stream = cl == null ? null : cl.getResourceAsStream("config.properties")) {
            if (stream == null) {
                LOGGER.log(Level.WARNING, "config.properties not found on classpath; using defaults.");
                return properties;
            }
            properties.load(stream);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load configuration file 'config.properties'. Using defaults.", e);
        }
        return properties;
    }
}
