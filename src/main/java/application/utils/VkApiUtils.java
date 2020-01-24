package application.utils;

import application.builders.*;
import application.models.VkUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import framework.utils.ApiUtils;
import org.apache.log4j.Logger;

public class VkApiUtils {
    private static final Logger LOG = Logger.getLogger(VkApiUtils.class);

    private static JsonNode getResponse(String url) {
        ObjectMapper objectMapper = new ObjectMapper();
        LOG.info("Gets response body");
        try {
            String response = ApiUtils.postRequest(url);
            return objectMapper.readValue(response, JsonNode.class);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    private static JsonNode getResponse(String field, String url, String fileSrc) {
        ObjectMapper objectMapper = new ObjectMapper();
        LOG.info("Gets response body");
        try {
            com.mashape.unirest.http.JsonNode response = ApiUtils.postRequest(field, url, fileSrc);
            return objectMapper.readValue(response.toString(), JsonNode.class);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    public static JsonNode uploadResponse(String field, String url, String fileSrc) {
        return getResponse(field, BaseBuilder.getUploadUrl(url), fileSrc);
    }

    public static JsonNode createWallPost(String id, String message, VkUser vkUser) {
        return getResponse(WallPostBuilder.getWallPostRequest(id, message, vkUser));
    }

    public static JsonNode createWallPost(String id, String message, String mediaId, VkUser vkUser, String field) {
        return getResponse(WallPostBuilder.getWallPostRequest(id, message, mediaId, vkUser, field));
    }

    public static JsonNode createWallUploadServer(String url) {
        return getResponse(url);
    }

    public static void createWallPostEdit(String id, String postId, String message, String mediaId, VkUser vkUser, String field) {
        getResponse(WallPostBuilder.getEditPostRequest(id, postId, message, mediaId, vkUser, field));
    }

    public static JsonNode createWallSavePhotoRequest(String photo, String ownerId, String groupId, String hash, String server, VkUser user) {
        return getResponse(BaseBuilder.getSaveWallPhotoRequest(photo, ownerId, groupId, hash, server, user));
    }

    public static JsonNode createWallSaveDocRequest(String photo, VkUser vkUser) {
        return getResponse(BaseBuilder.getSaveWallFileRequest(photo, vkUser));
    }

    public static JsonNode createWallPostComment(String postId, String commentText, VkUser vkUser) {
        return getResponse(WallPostBuilder.getAddCommentRequest(postId, commentText, vkUser));
    }

    public static JsonNode createIsLikedRequest(String ownerId, String userId, String itemId, VkUser vkUser, String type) {
        return getResponse(BaseBuilder.createIsPostLikedRequest(ownerId, userId, itemId, vkUser, type));
    }

    public static void createWallPostDeleteRequest(String ownerId, String postId, VkUser vkUser) {
        getResponse(WallPostBuilder.getDeleteWallPostRequest(ownerId, postId, vkUser));
    }
}
