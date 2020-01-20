package tests;

import application.constants.ApiInfo;
import application.constants.UsersInfo;
import application.enums.LikeStatus;
import framework.browser.Browser;
import application.models.VkUser;
import org.testng.Assert;
import org.testng.annotations.Test;
import application.pageObjects.pages.MyPage;
import application.steps.Steps;

import java.util.UUID;

public class TC2 extends BaseTest {
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
        String secondUserPageLink = Steps.getUserPageAddress(myPageSecondUser);
        String secondUserId = Steps.getUserId(secondUserPageLink);
        Browser.goToUrl(userPageLink);
        String postText = Steps.getPostText(firstUserId, postId, myPageFirstUser);
        Assert.assertTrue(myPageSecondUser.getPost().wallPostIsFromRightUser(firstUserId, postId), String.format("Post is not from %s", firstUser.getUsername()));
        Assert.assertTrue(myPageSecondUser.getPost().getWallPostText(firstUserId, postId).contains(postText), "Texts are different");
        myPageSecondUser.getPost().likePost(firstUserId, postId);
        int firstUserLikedFirst = Steps.getLikeStatus(firstUserId, firstUserId, postId, firstUser);
        int secondUserLikedFirst = Steps.getLikeStatus(firstUserId, secondUserId, postId, secondUser);
        Assert.assertEquals(firstUserLikedFirst, LikeStatus.LIKED.getValue(), String.format("User id=%s did not like item id=%d", firstUserId, postId));
        Assert.assertEquals(secondUserLikedFirst, LikeStatus.LIKED.getValue(), String.format("User id=%s did not like item id=%d", secondUserId, postId));
    }
}
