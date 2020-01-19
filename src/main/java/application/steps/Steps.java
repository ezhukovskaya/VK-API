package application.steps;

import application.builders.BaseBuilder;
import application.constants.Fields;
import application.constants.VkOptions;
import com.fasterxml.jackson.databind.JsonNode;
import application.constants.URLs;
import framework.browser.Browser;
import application.models.VkUser;
import framework.utils.Base64Utils;
import org.apache.log4j.Logger;
import application.pageObjects.pages.LoginPage;
import application.pageObjects.pages.MyPage;
import application.pageObjects.pages.UserFeed;
import application.utils.VkApiUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Steps {
    private static final Logger LOG = Logger.getLogger(Steps.class);

    private static String getMediaIdFromResponse(JsonNode array) {
        if (array.isArray()) {
            for (JsonNode arr : array) {
                return arr.get(Fields.ID).toString();
            }
        }
        return null;
    }

    private static JsonNode uploadPhotoOnServer(String imagePath, String ownerId, String groupId, VkUser vkUser) {
        JsonNode jsonNode = VkApiUtils.uploadResponse(imagePath, ownerId, vkUser);
        String photoField = URLEncoder.encode(jsonNode.get(Fields.PHOTO).textValue().replaceAll("\\\\", ""), StandardCharsets.UTF_8);
        String hashField = jsonNode.get(Fields.HASH).toString().replace("\"", "");
        String serverField = jsonNode.get(Fields.SERVER).toString();
        return VkApiUtils.createWallSavePhotoRequest(photoField, ownerId, groupId, hashField, serverField, vkUser);
    }

    private static JsonNode uploadFileOnServer(String filePath, String ownerId, String groupId, VkUser vkUser) {
        JsonNode jsonNode = VkApiUtils.uploadResponse(filePath, ownerId, vkUser);
        String fileField = URLEncoder.encode(jsonNode.get(Fields.FILE).textValue());
        return VkApiUtils.createWallSaveDocRequest(fileField, ownerId, groupId, vkUser);
    }

    public static boolean postEditedWithFileWallPost(String ownerId, VkUser vkUser, int postId, String newMessage, String fileField, String mediaId) {
        VkApiUtils.createWallPostEdit(ownerId, postId, newMessage, mediaId, vkUser, fileField);
        return true;
    }

    public static String getMediaId(String filePath, String ownerId, String groupId, VkUser vkUser) {
        JsonNode jsonNode = uploadPhotoOnServer(filePath, ownerId, groupId, vkUser);
        JsonNode response = jsonNode.findPath(Fields.RESPONSE);
        return getMediaIdFromResponse(response);
    }

    public static boolean postWithPhoto(String imagePath, String id, String groupId, String mediaId, VkUser vkUser, String field, String imageUrl){
        String randomText = UUID.randomUUID().toString();
        VkApiUtils.createWallPost(id, randomText, mediaId, vkUser, field);
        String localImage = Base64Utils.getBase64EncodedImage(imagePath);
        String remoteImage = Base64Utils.getBase64EncodedImageByURL(String.format(imageUrl, groupId, mediaId));
        return localImage.equals(remoteImage);
    }


    public static boolean postEditedWithPhotoWallPost(String imagePath, String ownerId, String groupId, VkUser vkUser, int postId, String newMessage, String imageUrl, String fileField, String mediaId) {
        VkApiUtils.createWallPostEdit(ownerId, postId, newMessage, mediaId, vkUser, fileField);
        String localImage = Base64Utils.getBase64EncodedImage(imagePath);
        String remoteImage = Base64Utils.getBase64EncodedImageByURL(String.format(imageUrl, groupId, mediaId));
        return localImage.equals(remoteImage);
    }

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

    public static int getWallPostIdWithAttachment(VkUser vkUser, String ownerId, String mediaId, String field) {
        String randomText = UUID.randomUUID().toString();
        JsonNode jsonNode = VkApiUtils.createWallPost(ownerId, randomText, mediaId, vkUser, field);
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
