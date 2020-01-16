package tests;

import com.fasterxml.jackson.databind.JsonNode;
import constants.ApiInfo;
import constants.Fields;
import constants.UsersInfo;
import enums.LikeStatus;
import models.VkUser;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.pages.LoginPage;
import pageObjects.pages.MyPage;
import pageObjects.pages.UserFeed;
import utils.VkApiUtils;

import java.util.UUID;

public class TC1 extends BaseTest{
    private static final Logger LOG = Logger.getLogger(TC1.class);
    private String randomText = UUID.randomUUID().toString();
    private String imagePath = System.getProperty("user.dir") + "/src/test/java/resources/photo.jpg";

    @Test
    public void vkTest() {
        LoginPage loginPage = new LoginPage();
        LOG.info("Checks if login page is open");
        Assert.assertTrue(loginPage.isPageDisplayed(), "Page is not open");
        VkUser vkUser = new VkUser(UsersInfo.FIRST_USER_USERNAME, UsersInfo.FIRST_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER1);
        LOG.info("Authorizing User1");
        loginPage.getAuthorization().logOn(vkUser);
        UserFeed userFeed = new UserFeed();
        LOG.info("Checks if user feed page is open");
        Assert.assertTrue(userFeed.isPageDisplayed(), "Wrong user creds");
        LOG.info("My page click");
        userFeed.getMenu().getGoToMyPage().click();
        MyPage myPage = new MyPage();
        LOG.info("Checks if My page is open");
        Assert.assertTrue(myPage.isPageDisplayed(), "My page is not open");
        LOG.info("Gets response from posting wall post");
        JsonNode jsonNode = VkApiUtils.createWallPost(vkUser.getId(), randomText, vkUser);
        LOG.info("Gets post id");
        int postId = jsonNode.get(Fields.RESPONSE).get(Fields.POST_ID).asInt();
        LOG.info("Checks if Post is from right user");
        Assert.assertTrue(myPage.wallPostIsFromRightUser(vkUser, postId), String.format("Post is not from %s", vkUser.getUsername()));
        LOG.info(String.format("Checks if Post message matches %s", randomText));
        Assert.assertTrue(myPage.getWallPostText(vkUser, postId).contains(randomText), "Texts are different");
        //jsonNode = VkApiUtils.createWallPostEdit(vkUser.getId(), postId, "qwerty", PhotoUploadBuilder.getPhotoFromSavePhotoRequest(vkUser.getId(), imagePath));
        LOG.info("Gets response from posting wall post comment");
        jsonNode = VkApiUtils.createWallPostComment(postId, randomText, vkUser);
        LOG.info("Gets comment id");
        int commentId = jsonNode.get(Fields.RESPONSE).get(Fields.COMMENT_ID).asInt();
        LOG.info("Checks if Comment is from right user");
        Assert.assertTrue(myPage.commentIsFromRightUser(vkUser, commentId), String.format("Comment is not from %s", vkUser.getUsername()));
        LOG.info("Like post");
        myPage.likePost();
        LOG.info(String.format("Gets response from checking like status for post id=%d", postId));
        jsonNode = VkApiUtils.createIsLikedRequest(vkUser.getId(), vkUser.getId(), postId, vkUser);
        int liked = jsonNode.get(Fields.RESPONSE).get(Fields.LIKED).asInt();
        LOG.info("Checks if like status is 'LIKED'");
        Assert.assertEquals(liked, LikeStatus.LIKED.getValue(), String.format("User id=%s did not like item id=%d", vkUser.getId(), postId));
        LOG.info("Gets response from deleting wall post");
        VkApiUtils.createWallPostDeleteRequest(vkUser.getId(), postId, vkUser);
        Assert.assertFalse(myPage.wallPostIsFromRightUser(vkUser, postId), String.format("Post id=%d is not deleted", postId));
    }
}
