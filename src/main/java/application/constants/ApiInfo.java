package application.constants;

import framework.utils.PropertiesRead;

public class ApiInfo {
    public static final String API_VERSION = PropertiesRead.readFromFrameworkConfig("apiVersion");
    public static final String ACCESS_TOKEN_USER1 = PropertiesRead.readFromFrameworkConfig("accessToken");
    public static final String ACCESS_TOKEN_USER2 = PropertiesRead.readFromFrameworkConfig("accessTokenUser2");
}
