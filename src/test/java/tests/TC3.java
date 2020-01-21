package tests;

import application.constants.ApiInfo;
import application.constants.UsersInfo;
import application.models.VkUser;
import application.pageObjects.pages.MyPage;
import application.steps.Steps;
import framework.browser.Browser;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class TC3 extends BaseTest{
    private static final Logger LOG = Logger.getLogger(TC3.class);
    private String randomText = UUID.randomUUID().toString();
    @Test
    public void vkTest() {
        VkUser firstUser = Steps.getVkUser(UsersInfo.FIRST_USER_USERNAME, UsersInfo.FIRST_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER1);
        Steps.authorization(firstUser);
        MyPage myPageFirstUser = new MyPage();
        String userPageLink = Steps.getUserPageAddress(myPageFirstUser);
        String firstUserId = Steps.getUserId(userPageLink);
        int postId = Steps.getWallPostId(firstUser, firstUserId, randomText);
        Steps.getPostText(firstUserId, postId, myPageFirstUser);
        myPageFirstUser.getPost().likePost(firstUserId, postId);
        Steps.logOut();
        VkUser secondUser = Steps.getVkUser(UsersInfo.SECOND_USER_USERNAME, UsersInfo.SECOND_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER2);
        Steps.authorization(secondUser);
        MyPage myPageSecondUser = new MyPage();
        Steps.getUserPageAddress(myPageSecondUser);
        Browser.goToUrl(userPageLink);
        String postText = Steps.getPostText(firstUserId, postId, myPageFirstUser);
        LOG.info("Checks if Comment is from right user");
        Assert.assertTrue(myPageSecondUser.getPost().wallPostIsFromRightUser(firstUserId, postId), String.format("Post is not from %s", firstUser.getUsername()));
        LOG.info(String.format("Checks if Post message matches %s", randomText));
        Assert.assertTrue(myPageSecondUser.getPost().getWallPostText(firstUserId, postId).contains(postText), "Texts are different");
    }
}
