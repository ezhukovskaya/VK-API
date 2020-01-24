package application.constants;

import framework.utils.PropertiesRead;

public class ApiInfo {
    public static final String API_VERSION = PropertiesRead.readFromFrameworkConfig("apiVersion", Paths.TEST_PROPERTY);
}
