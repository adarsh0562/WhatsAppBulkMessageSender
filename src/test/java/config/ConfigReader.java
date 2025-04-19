package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\config.properties");
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration file: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
