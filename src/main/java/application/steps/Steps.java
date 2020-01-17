package application.steps;

import application.constants.Fields;
import application.constants.VkOptions;
import com.fasterxml.jackson.databind.JsonNode;
import application.constants.URLs;
import framework.browser.Browser;
import application.models.VkUser;
import org.apache.log4j.Logger;
import application.pageObjects.pages.LoginPage;
import application.pageObjects.pages.MyPage;
import application.pageObjects.pages.UserFeed;
import application.utils.VkApiUtils;

import java.util.UUID;

public class Steps {
    private static final Logger LOG = Logger.getLogger(Steps.class);

    public static VkUser getVkUser(String username, String password, String accessToken) {
        return new VkUser(username, password, accessToken);
    }

    public static void authorization(VkUser vkUser) {
        LoginPage loginPage = new LoginPage();
        loginPage.getAuthorization().logOn(vkUser);
    }

    public static String getUserPageAddress(MyPage myPage) {
        UserFeed userFeed = new UserFeed();
        userFeed.getMenu().getGoToMyPage().click();
        Browser.setExplicitWait(myPage.getAvatarBy());
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

    public static int getWallCommentId(int postId, VkUser vkUser) {
        String randomText = UUID.randomUUID().toString();
        JsonNode jsonNode = VkApiUtils.createWallPostComment(postId, randomText, vkUser);
        LOG.info("Gets comment id");
        return jsonNode.get(Fields.RESPONSE).get(Fields.COMMENT_ID).asInt();
    }

    public static String getPostText(String userId, int postId, MyPage myPage) {
        return myPage.getPost().getWallPostText(userId, postId);
    }

    public static int getLikeStatus(String ownerId, String userId, int postId, VkUser vkUser) {
        JsonNode jsonNode = VkApiUtils.createIsLikedRequest(ownerId, userId, postId, vkUser);
        return jsonNode.get(Fields.RESPONSE).get(Fields.LIKED).asInt();
    }

    public static void logOut() {
        new MyPage().getHeader().getOptions().click(VkOptions.LOG_OUT);
    }
}
