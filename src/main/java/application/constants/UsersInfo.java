package application.constants;

import framework.utils.PropertiesRead;

public class UsersInfo {
    public static final String FIRST_USER_USERNAME = PropertiesRead.readFromFrameworkConfig("firstUsername", Paths.TEST_PROPERTY);
    public static final String FIRST_USER_PASSWORD = PropertiesRead.readFromFrameworkConfig("firstPassword", Paths.TEST_PROPERTY);
    public static final String SECOND_USER_USERNAME = PropertiesRead.readFromFrameworkConfig("secondUsername", Paths.TEST_PROPERTY);
    public static final String SECOND_USER_PASSWORD = PropertiesRead.readFromFrameworkConfig("secondPassword", Paths.TEST_PROPERTY);
    public static final String ACCESS_TOKEN_USER1 = PropertiesRead.readFromFrameworkConfig("accessToken", Paths.TEST_PROPERTY);
    public static final String ACCESS_TOKEN_USER2 = PropertiesRead.readFromFrameworkConfig("accessTokenUser2", Paths.TEST_PROPERTY);
}