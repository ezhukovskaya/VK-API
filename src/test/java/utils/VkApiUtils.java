package utils;

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
        return getResponse(WallPostBuilder.getURL(id, message));
    }

    public static JsonNode createWallUploadServer(String id){
        return getResponse(PhotoUploadBuilder.getURL(id));
    }

    public static JsonNode createWallPostEdit(String id, int postId, String message, String attachment){
        return getResponse(WallPostBuilder.getURL(id, postId, message, attachment));
    }

    public static JsonNode createPhotoUpload(String id, String fileSrc){
        return getResponse(PhotoUploadBuilder.createUploadUrl(id, fileSrc));
    }
}
