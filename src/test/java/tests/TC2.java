package tests;

import application.constants.ApiInfo;
import application.constants.UsersInfo;
import application.enums.LikeStatus;
import application.models.VkUser;
import application.pageObjects.pages.MyPage;
import application.steps.Steps;
import framework.browser.Browser;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class TC2 extends BaseTest {
    private static final Logger LOG = Logger.getLogger(TC2.class);
    private String randomText = UUID.randomUUID().toString();
    @Test
    public void vkTest() {
        VkUser firstUser = Steps.getVkUser(UsersInfo.FIRST_USER_USERNAME, UsersInfo.FIRST_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER1);
        Steps.authorization(firstUser);
        MyPage myPage = new MyPage();
        String userPageLink = Steps.getUserPageAddress(myPage);
        String firstUserId = Steps.getUserId(userPageLink);
        int postId = Steps.getWallPostId(firstUser, firstUserId, randomText);
        Steps.getPostText(firstUserId, postId, myPage);
        myPage.getPost().likePost(firstUserId, postId);
        Steps.logOut();
        VkUser secondUser = Steps.getVkUser(UsersInfo.SECOND_USER_USERNAME, UsersInfo.SECOND_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER2);
        Steps.authorization(secondUser);
        String secondUserPageLink = Steps.getUserPageAddress(myPage);
        String secondUserId = Steps.getUserId(secondUserPageLink);
        Browser.goToUrl(userPageLink);
        String postText = Steps.getPostText(firstUserId, postId, myPage);
        LOG.info("Checks if Comment is from right user");
        Assert.assertTrue(myPage.getPost().wallPostIsFromRightUser(firstUserId, postId), String.format("Post is not from %s", firstUser.getUsername()));
        LOG.info(String.format("Checks if Post message matches %s", randomText));
        Assert.assertTrue(myPage.getPost().getWallPostText(firstUserId, postId).contains(postText), "Texts are different");
        myPage.getPost().likePost(firstUserId, postId);
        int firstUserLikedFirst = Steps.getLikeStatus(firstUserId, firstUserId, postId, firstUser);
        int secondUserLikedFirst = Steps.getLikeStatus(firstUserId, secondUserId, postId, secondUser);
        LOG.info("Checks if like status is 'LIKED'");
        Assert.assertEquals(firstUserLikedFirst, LikeStatus.LIKED.getValue(), String.format("User id=%s did not like item id=%d", firstUserId, postId));
        LOG.info("Checks if like status is 'LIKED'");
        Assert.assertEquals(secondUserLikedFirst, LikeStatus.LIKED.getValue(), String.format("User id=%s did not like item id=%d", secondUserId, postId));
    }
}
