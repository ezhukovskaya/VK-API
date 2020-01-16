package tests;

import constants.ApiInfo;
import constants.UsersInfo;
import framework.browser.Browser;
import models.VkUser;
import org.testng.annotations.Test;
import pageObjects.pages.MyPage;
import steps.Steps;

public class TC2 extends BaseTest {
    @Test
    public void vkTest() throws InterruptedException {
        VkUser firstUser = Steps.getVkUser(UsersInfo.FIRST_USER_USERNAME, UsersInfo.FIRST_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER1);
        Steps.authorization(firstUser);
        MyPage myPageFirstUser = new MyPage();
        String userPageLink = Steps.getUserPageAddress();
        String firstUserId = Steps.getUserId(userPageLink);
        Steps.getWallPostId(firstUser, firstUserId);
        int postId = Steps.getWallPostId(firstUser, firstUserId);
        Steps.getPostText(firstUser, postId, myPageFirstUser);
        Steps.like(myPageFirstUser);
        Steps.logOut();
        VkUser secondUser = Steps.getVkUser(UsersInfo.SECOND_USER_USERNAME, UsersInfo.SECOND_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER2);
        Steps.authorization(secondUser);
        MyPage myPageSecondUser = new MyPage();
        Browser.goToUrl(userPageLink);
        Steps.like(myPageSecondUser);
    }
}
