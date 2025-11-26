package jaxrs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public final class Configuration {
    private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());

    private Configuration() {}

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream stream = Configuration.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (stream == null) {
                LOGGER.warning("config.properties not found on classpath; using empty properties");
                return properties;
            }
            properties.load(stream);
        } catch (IOException e) {
            LOGGER.severe("Cannot load configuration file: " + e.getMessage());
        }
        return properties;
    }
}
