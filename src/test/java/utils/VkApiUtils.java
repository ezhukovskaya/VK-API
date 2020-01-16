package utils;

import builders.LikeBuilder;
import builders.PhotoUploadBuilder;
import builders.WallPostBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import framework.utils.ApiUtils;

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

    public static JsonNode createWallPost(String id, String message) {
        return getResponse(WallPostBuilder.getWallPostRequest(id, message));
    }

    public static JsonNode createWallUploadServer(String id) {
        return getResponse(PhotoUploadBuilder.getWallUploadRequest(id));
    }

    public static JsonNode createWallPostEdit(String id, int postId, String message, String attachment) {
        return getResponse(WallPostBuilder.getEditPostRequest(id, postId, message, attachment));
    }

    public static JsonNode createPhotoUpload(String id, String fileSrc) {
        return getResponse(PhotoUploadBuilder.createUploadUrl(id, fileSrc));
    }

    public static JsonNode createSaveWallPhoto(String id, String fileSrc) {
        return getResponse(PhotoUploadBuilder.getSaveWallPhotoRequest(id, fileSrc));
    }

    public static JsonNode createWallPostComment(int postId, String commentText) {
        return getResponse(WallPostBuilder.getAddCommentRequest(postId, commentText));
    }

    public static JsonNode createIsLikedRequest(String ownerId, String userId, int itemId){
        return getResponse(LikeBuilder.createIsPostLikedRequest(ownerId, userId, itemId));
    }

    public static JsonNode createWallPostDeleteRequest(String ownerId, int postId){
        return getResponse(WallPostBuilder.getDeleteWallPostRequest(ownerId, postId));
    }
}
