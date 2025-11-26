package jaxrs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Configuration {
    private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());

    private Configuration() {
        // utility
    }

    public static Properties loadProperties() {
        final Properties properties = new Properties();
        try (InputStream stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("config.properties")) {
            if (stream == null) {
                LOGGER.warning("config.properties not found on classpath; returning empty Properties.");
                return properties;
            }
            properties.load(stream);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load configuration file.", e);
        }
        return properties;
    }
}
