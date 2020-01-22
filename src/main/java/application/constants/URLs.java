package application.constants;

import framework.utils.PropertiesRead;

public class URLs {
    public static final String URL = PropertiesRead.readFromFrameworkConfig("url", Paths.TEST_PROPERTY);
    public static final String DEV_VK_API = PropertiesRead.readFromFrameworkConfig("devUrl", Paths.TEST_PROPERTY);
}