package tests;

import application.constants.ApiInfo;
import application.constants.UsersInfo;
import application.models.VkUser;
import application.pageObjects.pages.MyPage;
import application.steps.Steps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TC5 extends BaseTest {
    @DataProvider(name = "users")
    public Object[][] getData() {
        VkUser firstUser = Steps.getVkUser(UsersInfo.FIRST_USER_USERNAME, UsersInfo.FIRST_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER1);
        VkUser secondUser = Steps.getVkUser(UsersInfo.SECOND_USER_USERNAME, UsersInfo.SECOND_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER2);
        return new Object[][]{{firstUser}, {secondUser}};
    }

    @Test(dataProvider = "users")
    public void vkTest(VkUser vkUser){
        Steps.authorization(vkUser);
        MyPage myPage = new MyPage();
        String userPageLink = Steps.getUserPageAddress(myPage);
    }
}
