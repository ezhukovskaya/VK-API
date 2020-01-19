package application.utils;

import application.builders.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import framework.utils.ApiUtils;
import application.models.VkUser;

public class VkApiUtils {
    private static JsonNode getResponse(String url) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String response = ApiUtils.postRequest(url);
            return objectMapper.readValue(response, JsonNode.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JsonNode getResponse(String url, String fileSrc) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            com.mashape.unirest.http.JsonNode response = ApiUtils.postRequest(url, fileSrc);
            return objectMapper.readValue(response.toString(), JsonNode.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode uploadResponse(String fileSrc, String id, VkUser vkUser) {
        return getResponse(BaseBuilder.createUploadUrl(id, vkUser), fileSrc);
    }

    public static JsonNode createWallPost(String id, String message, VkUser vkUser) {
        return getResponse(WallPostBuilder.getWallPostRequest(id, message, vkUser));
    }

    public static JsonNode createWallPost(String id, String message, String mediaId, VkUser vkUser, String field) {
        return getResponse(WallPostBuilder.getWallPostRequest(id, message, mediaId, vkUser, field));
    }

    public static JsonNode createWallUploadServer(String id, VkUser vkUser) {
        return getResponse(PhotoUploadBuilder.getWallUploadRequest(id, vkUser));
    }

    public static JsonNode createWallPostEdit(String id, int postId, String message, String mediaId, VkUser vkUser, String field) {
        return getResponse(WallPostBuilder.getEditPostRequest(id, postId, message, mediaId, vkUser, field));
    }

    public static JsonNode createWallSavePhotoRequest(String photo, String ownerId, String groupId, String hash, String server, VkUser user) {
        return getResponse(PhotoUploadBuilder.getSaveWallPhotoRequest(photo, ownerId, groupId, hash, server, user));
    }

    public static JsonNode createWallSaveDocRequest(String photo, String ownerId, String groupId, VkUser vkUser) {
        return getResponse(FileUploadBuilder.getSaveWallFileRequest(photo, ownerId, groupId, vkUser));
    }

    public static JsonNode createWallPostComment(int postId, String commentText, VkUser vkUser) {
        return getResponse(WallPostBuilder.getAddCommentRequest(postId, commentText, vkUser));
    }

    public static JsonNode createIsLikedRequest(String ownerId, String userId, int itemId, VkUser vkUser) {
        return getResponse(LikeBuilder.createIsPostLikedRequest(ownerId, userId, itemId, vkUser));
    }

    public static JsonNode createWallPostDeleteRequest(String ownerId, int postId, VkUser vkUser) {
        return getResponse(WallPostBuilder.getDeleteWallPostRequest(ownerId, postId, vkUser));
    }
}
