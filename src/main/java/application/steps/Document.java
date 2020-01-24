package application.steps;

import application.builders.BaseBuilder;
import application.constants.Fields;
import application.models.VkUser;
import application.utils.VkApiUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.net.URLEncoder;

public class Document {

    private static String getDocIdFromResponse(JsonNode response) {
        return response.get(Fields.RESPONSE).get(Fields.DOC).get(Fields.ID).toString();
    }

    private static String getUploadedDocId(String filePath, VkUser vkUser) {
        JsonNode jsonNode = VkApiUtils.uploadResponse(Fields.FILE, BaseBuilder.getWallUploadRequest(vkUser), filePath);
        String fileField = URLEncoder.encode(jsonNode.get(Fields.FILE).textValue());
        jsonNode = VkApiUtils.createWallSaveDocRequest(fileField, vkUser);
        return getDocIdFromResponse(jsonNode);
    }

    public static void postEditedWithFileWallPost(String filePath, String ownerId, VkUser vkUser, String postId, String newMessage, String fileField) {
        String mediaId = getUploadedDocId(filePath, vkUser);
        VkApiUtils.createWallPostEdit(ownerId, postId, newMessage, mediaId, vkUser, fileField);
    }








}