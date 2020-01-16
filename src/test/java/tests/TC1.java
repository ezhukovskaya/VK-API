package tests;

import builders.PhotoUploadBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import constants.*;
import enums.LikeStatus;
import framework.browser.Browser;
import framework.utils.PropertiesRead;
import models.VkUser;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.pages.LoginPage;
import pageObjects.pages.MyPage;
import pageObjects.pages.UserFeed;
import utils.VkApiUtils;

import java.util.UUID;

public class TC1 {
    private static final Logger LOG = Logger.getLogger(TC1.class);
    private String randomText = UUID.randomUUID().toString();
    private String imagePath = System.getProperty("user.dir") + "/src/test/java/resources/photo.jpg";

    @BeforeTest
    public void init() {
        PropertyConfigurator.configure(PropertiesRead.readFromFrameworkConfig("logfile"));
        Browser.getBrowser();
        Browser.setImplicitlyWait();
        Browser.goToUrl(URLs.VK_COM);
        Browser.maximize();
    }

    @Test
    public void vkTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage();
        LOG.info("Checks if login page is open");
        Assert.assertTrue(loginPage.isPageDisplayed(), "Page is not open");
        VkUser vkUser = new VkUser(UsersInfo.FIRST_USER_USERNAME, UsersInfo.FIRST_USER_PASSWORD, UsersInfo.FIRST_USER_ID);
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
        JsonNode jsonNode = VkApiUtils.createWallPost(vkUser.getId(), randomText);
        LOG.info("Gets post id");
        int postId = jsonNode.get(Fields.RESPONSE).get(Fields.POST_ID).asInt();
        LOG.info("Checks if Post is from right user");
        Assert.assertTrue(myPage.wallPostIsFromRightUser(vkUser, postId), String.format("Post is not from %s", vkUser.getUsername()));
        LOG.info(String.format("Checks if Post message matches %s", randomText));
        Assert.assertTrue(myPage.getWallPostText(vkUser, postId).contains(randomText), "Texts are different");
        //jsonNode = VkApiUtils.createWallPostEdit(vkUser.getId(), postId, "qwerty", PhotoUploadBuilder.getPhotoFromSavePhotoRequest(vkUser.getId(), imagePath));
        LOG.info("Gets response from posting wall post comment");
        jsonNode = VkApiUtils.createWallPostComment(postId, randomText);
        LOG.info("Gets comment id");
        int commentId = jsonNode.get(Fields.RESPONSE).get(Fields.COMMENT_ID).asInt();
        LOG.info("Checks if Comment is from right user");
        Assert.assertTrue(myPage.commentIsFromRightUser(vkUser, commentId), String.format("Comment is not from %s", vkUser.getUsername()));
        LOG.info("Like post");
        myPage.likePost();
        LOG.info(String.format("Gets response from checking like status for post id=%d", postId));
        jsonNode = VkApiUtils.createIsLikedRequest(vkUser.getId(), vkUser.getId(), postId);
        int liked = jsonNode.get(Fields.RESPONSE).get(Fields.LIKED).asInt();
        LOG.info("Checks if like status is 'LIKED'");
        Assert.assertEquals(liked, LikeStatus.LIKED.getValue(), String.format("User id=%s did not like item id=%d", vkUser.getId(), postId));
    }
}
