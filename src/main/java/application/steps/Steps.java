package application.steps;

import application.builders.FileUploadBuilder;
import application.builders.PhotoUploadBuilder;
import application.constants.Fields;
import application.constants.URLs;
import application.constants.VkOptions;
import application.models.VkUser;
import application.pageObjects.pages.LoginPage;
import application.pageObjects.pages.MyPage;
import application.pageObjects.pages.UserFeed;
import application.utils.VkApiUtils;
import com.fasterxml.jackson.databind.JsonNode;
import framework.browser.Browser;
import framework.utils.ImageUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

public class Steps {
    private static final Logger LOG = Logger.getLogger(Steps.class);
    private static final String BACKSLASH = "\\\\";
    private static final String QUOTES = "\"";
    private static final String EMPTY = "";

    private static String getMediaIdFromResponse(JsonNode array) {
        LOG.info("Gets Media Id");
        if (array.isArray()) {
            for (JsonNode arr : array) {
                return arr.get(Fields.ID).toString();
            }
        }
        return null;
    }

    private static JsonNode uploadPhotoOnServer(String imagePath, String ownerId, String groupId, VkUser vkUser) {
        LOG.info("Gets response to upload photo");
        JsonNode jsonNode = VkApiUtils.uploadResponse(Fields.PHOTO, PhotoUploadBuilder.getWallUploadRequest(ownerId, vkUser), imagePath);
        LOG.info("Encodes photoField to " + StandardCharsets.UTF_8);
        String photoField = URLEncoder.encode(jsonNode.get(Fields.PHOTO).textValue().replaceAll(BACKSLASH, EMPTY), StandardCharsets.UTF_8);
        LOG.info("Gets hash from the response");
        String hashField = jsonNode.get(Fields.HASH).toString().replace(QUOTES, EMPTY);
        LOG.info("Gets server from the response");
        String serverField = jsonNode.get(Fields.SERVER).toString();
        return VkApiUtils.createWallSavePhotoRequest(photoField, ownerId, groupId, hashField, serverField, vkUser);
    }

    private static String getDocIdFromResponse(JsonNode response) {
        return response.get(Fields.RESPONSE).get(Fields.DOC).get(Fields.ID).toString();
    }

    private static String getUploadedDocId(String filePath, VkUser vkUser) {
        JsonNode jsonNode = VkApiUtils.uploadResponse(Fields.FILE, FileUploadBuilder.getWallUploadRequest(vkUser), filePath);
        String fileField = URLEncoder.encode(jsonNode.get(Fields.FILE).textValue());
        jsonNode = VkApiUtils.createWallSaveDocRequest(fileField, vkUser);
        return getDocIdFromResponse(jsonNode);
    }

    private static JsonNode getJsonMediaId(String filePath, String ownerId, String groupId, VkUser vkUser) {
        LOG.info("Gets response from creating Wall Save Photo");
        JsonNode jsonNode = uploadPhotoOnServer(filePath, ownerId, groupId, vkUser);
        return jsonNode.findPath(Fields.RESPONSE);
    }

    private static JsonNode getSizes(JsonNode array) {
        if (array.isArray()) {
            for (JsonNode arr : array) {
                return arr.get(Fields.SIZES);
            }
        }
        return null;
    }

    private static JsonNode getWallPostWithAttachment(VkUser vkUser, String ownerId, String mediaId, String field) {
        String randomText = UUID.randomUUID().toString();
        return VkApiUtils.createWallPost(ownerId, randomText, mediaId, vkUser, field);
    }

    private static String getUploadedImageURL(JsonNode array) {
        LOG.info("Gets ");
        if (array.isArray()) {
            return array.get(array.size() - 1).get(Fields.URL).textValue();
        }
        return null;
    }

    public static boolean postEditedWithFileWallPost(String filePath, String ownerId, VkUser vkUser, int postId, String newMessage, String fileField) {
        String mediaId = getUploadedDocId(filePath, vkUser);
        VkApiUtils.createWallPostEdit(ownerId, postId, newMessage, mediaId, vkUser, fileField);
        return true;
    }


    public static float postEditedWithPhotoWallPost(String imagePath, String ownerId, String groupId, VkUser vkUser, int postId, String newMessage, String fileField) {
        LOG.info("Gets image response");
        JsonNode imageResponse = getJsonMediaId(imagePath, ownerId, groupId, vkUser);
        LOG.info("Gets media id");
        String mediaId = getMediaIdFromResponse(imageResponse);
        LOG.info("Edit post");
        VkApiUtils.createWallPostEdit(ownerId, postId, newMessage, mediaId, vkUser, fileField);
        LOG.info("Gets Uploaded Image URL");
        String imageUrl = getUploadedImageURL(Objects.requireNonNull(getSizes(imageResponse)));
        return ImageUtils.compareImage(imagePath, imageUrl);
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

    public static int getWallPostId(VkUser vkUser, String ownerId, String message) {
        JsonNode jsonNode = VkApiUtils.createWallPost(ownerId, message, vkUser);
        return jsonNode.get(Fields.RESPONSE).get(Fields.POST_ID).asInt();
    }


    public static double isAttached(String imagePath, VkUser vkUser, String ownerId, String groupId) {
        LOG.info("Gets image response");
        JsonNode imageResponse = getJsonMediaId(imagePath, ownerId, groupId, vkUser);
        LOG.info("Gets image Url");
        String imageUrl = getUploadedImageURL(Objects.requireNonNull(getSizes(imageResponse)));
        return ImageUtils.compareImage(imagePath, imageUrl);
    }

    public static int getPostId(String imagePath, VkUser vkUser, String ownerId, String groupId, String field) {
        JsonNode imageResponse = getJsonMediaId(imagePath, ownerId, groupId, vkUser);
        String mediaId = getMediaIdFromResponse(imageResponse);
        JsonNode response = getWallPostWithAttachment(vkUser, ownerId, mediaId, field);
        return response.get(Fields.RESPONSE).get(Fields.POST_ID).asInt();
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