package constants;

import framework.utils.PropertiesRead;

public class UsersInfo {
    public static final String FIRST_USER_USERNAME = PropertiesRead.readFromFrameworkConfig("firstUsername");
    public static final String FIRST_USER_PASSWORD = PropertiesRead.readFromFrameworkConfig("firstPassword");
    public static final String SECOND_USER_USERNAME = PropertiesRead.readFromFrameworkConfig("secondUsername");
    public static final String SECOND_USER_PASSWORD = PropertiesRead.readFromFrameworkConfig("secondPassword");
}
