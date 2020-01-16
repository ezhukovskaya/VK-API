package steps;

import com.fasterxml.jackson.databind.JsonNode;
import constants.Fields;
import constants.URLs;
import constants.UsersInfo;
import constants.VkOptions;
import framework.browser.Browser;
import models.VkUser;
import org.apache.log4j.Logger;
import pageObjects.pages.LoginPage;
import pageObjects.pages.MyPage;
import pageObjects.pages.UserFeed;
import utils.VkApiUtils;

import java.util.UUID;

public class Steps {
    private static final Logger LOG = Logger.getLogger(Steps.class);

    public static VkUser getVkUser(String username, String password, String accessToken) {
        return new VkUser(username, password, UsersInfo.FIRST_USER_ID, accessToken);
    }

    public static void authorization(VkUser vkUser) {
        LoginPage loginPage = new LoginPage();
        loginPage.getAuthorization().logOn(vkUser);
    }

    public static String getUserPageAddress() throws InterruptedException {
        UserFeed userFeed = new UserFeed();
        userFeed.getMenu().getGoToMyPage().click();
        Thread.sleep(5000);
        return Browser.getCurrentUrl();
    }

    public static String getUserId(String link) {
        return link.substring(URLs.VK_COM.length() + 2);
    }

    public static int getWallPostId(VkUser vkUser, String ownerId) {
        String randomText = UUID.randomUUID().toString();
        JsonNode jsonNode = VkApiUtils.createWallPost(ownerId, randomText, vkUser);
        return jsonNode.get(Fields.RESPONSE).get(Fields.POST_ID).asInt();
    }

    public static String getPostText(VkUser vkUser, int postId, MyPage myPage) {
        return myPage.getWallPostText(vkUser, postId);
    }

    public static void like(MyPage myPage) {
        myPage.likePost();
    }

    public static void logOut() {
        new MyPage().getHeader().getOptions().click(VkOptions.LOG_OUT);
    }
}
