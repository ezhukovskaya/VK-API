package framework.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesRead {
    private static Properties prop = new Properties();
    private static String path = "src/main/resources/config.properties";
    private static final Logger LOG = Logger.getLogger(PropertiesRead.class);


    public static String readFromFrameworkConfig(String key) {
        try {
            InputStream input = new FileInputStream(path);
            prop.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOG.info(e.getMessage());
        }
        return prop.getProperty(key);
    }
}