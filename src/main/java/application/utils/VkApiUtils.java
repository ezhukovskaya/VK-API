package application.utils;

import application.builders.WallPostBuilder;
import application.builders.LikeBuilder;
import application.builders.PhotoUploadBuilder;
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

    private static JsonNode getResponse(String url, String fileSrc){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            com.mashape.unirest.http.JsonNode response = ApiUtils.postRequest(url, fileSrc);
            return objectMapper.readValue(response.toString(), JsonNode.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode photoResponse(String url, String fileSrc){
        return getResponse(url, fileSrc);
    }

    public static JsonNode createWallPost(String id, String message, VkUser vkUser) {
        return getResponse(WallPostBuilder.getWallPostRequest(id, message, vkUser));
    }

    public static JsonNode createWallUploadServer(String id, VkUser vkUser) {
        return getResponse(PhotoUploadBuilder.getWallUploadRequest(id, vkUser));
    }

    public static JsonNode createWallPostEdit(String id, int postId, String message, String attachment, VkUser vkUser) {
        return getResponse(WallPostBuilder.getEditPostRequest(id, postId, message, attachment,vkUser ));
    }

    public static JsonNode createWallSavePhotoRequest(String photo, VkUser user){
        return getResponse(PhotoUploadBuilder.getSaveWallPhotoRequest(photo, user));
    }

    public static JsonNode createWallPostComment(int postId, String commentText, VkUser vkUser) {
        return getResponse(WallPostBuilder.getAddCommentRequest(postId, commentText, vkUser));
    }

    public static JsonNode createIsLikedRequest(String ownerId, String userId, int itemId, VkUser vkUser){
        return getResponse(LikeBuilder.createIsPostLikedRequest(ownerId, userId, itemId, vkUser));
    }

    public static JsonNode createWallPostDeleteRequest(String ownerId, int postId, VkUser vkUser){
        return getResponse(WallPostBuilder.getDeleteWallPostRequest(ownerId, postId, vkUser));
    }
}
