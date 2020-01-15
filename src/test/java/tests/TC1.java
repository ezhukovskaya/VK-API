package tests;

import com.fasterxml.jackson.databind.JsonNode;
import constants.*;
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

    @BeforeTest
    public void init() {
        PropertyConfigurator.configure(PropertiesRead.readFromFrameworkConfig("logfile"));
        Browser.getBrowser();
        Browser.setImplicitlyWait();
        Browser.goToUrl(URLs.VK_COM);
        Browser.maximize();
    }

    @Test
    public void vkTest() {
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
        Assert.assertTrue(myPage.wallPostIsFromRightUser(vkUser.getId(), postId), "Post is not from right user");
        Assert.assertTrue(myPage.getWallPostText(vkUser.getId(), postId).contains(randomText),"Texts are different");
    }
}
