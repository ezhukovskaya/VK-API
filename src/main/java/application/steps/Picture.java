package application.steps;

import application.builders.BaseBuilder;
import application.constants.ExtraSymbol;
import application.constants.Parameters;
import application.constants.Fields;
import application.models.VkUser;
import application.utils.VkApiUtils;
import com.fasterxml.jackson.databind.JsonNode;
import framework.utils.ImageUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

public class Picture {
    private static final Logger LOG = Logger.getLogger(Document.class);

    private static JsonNode uploadPhotoOnServer(String imagePath, String ownerId, String groupId, VkUser vkUser) {
        LOG.info("Gets response to upload photo");
        JsonNode jsonNode = VkApiUtils.uploadResponse(Parameters.PHOTO, BaseBuilder.getWallUploadRequest(ownerId, vkUser), imagePath);
        LOG.info("Encodes photoField to " + StandardCharsets.UTF_8);
        String photoField = URLEncoder.encode(jsonNode.get(Fields.PHOTO).textValue().replaceAll(ExtraSymbol.BACKSLASH, ExtraSymbol.EMPTY), StandardCharsets.UTF_8);
        LOG.info("Gets hash from the response");
        String hashField = jsonNode.get(Fields.HASH).toString().replace(ExtraSymbol.QUOTES, ExtraSymbol.EMPTY);
        LOG.info("Gets server from the response");
        String serverField = jsonNode.get(Fields.SERVER).toString();
        return VkApiUtils.createWallSavePhotoRequest(photoField, ownerId, groupId, hashField, serverField, vkUser);
    }

    private static String getMediaIdFromResponse(JsonNode array) {
        LOG.info("Gets Media Id");
        if (array.isArray()) {
            for (JsonNode arr : array) {
                return arr.get(Fields.ID).toString();
            }
        }
        return null;
    }

    private static JsonNode getSizes(JsonNode array) {
        if (array.isArray()) {
            for (JsonNode arr : array) {
                return arr.get(Fields.SIZES);
            }
        }
        return null;
    }

    private static JsonNode getJsonMediaId(String filePath, String ownerId, String groupId, VkUser vkUser) {
        LOG.info("Gets response from creating Wall Save Photo");
        JsonNode jsonNode = uploadPhotoOnServer(filePath, ownerId, groupId, vkUser);
        return jsonNode.findPath(Fields.RESPONSE);
    }

    private static String getUploadedImageURL(JsonNode array) {
        LOG.info("Gets ");
        if (array.isArray()) {
            return array.get(array.size() - 1).get(Fields.URL).textValue();
        }
        return null;
    }

    private static JsonNode getWallPostWithAttachment(VkUser vkUser, String ownerId, String mediaId, String field) {
        String randomText = UUID.randomUUID().toString();
        return VkApiUtils.createWallPost(ownerId, randomText, mediaId, vkUser, field);
    }

    public static float postEditedWithPhotoWallPost(String imagePath, String ownerId, String groupId, VkUser vkUser, String postId, String newMessage, String fileField) {
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

    public static double isAttached(String imagePath, VkUser vkUser, String ownerId, String groupId) {
        LOG.info("Gets image response");
        JsonNode imageResponse = getJsonMediaId(imagePath, ownerId, groupId, vkUser);
        LOG.info("Gets image Url");
        String imageUrl = getUploadedImageURL(Objects.requireNonNull(getSizes(imageResponse)));
        return ImageUtils.compareImage(imagePath, imageUrl);
    }

    public static String getPostId(String imagePath, VkUser vkUser, String ownerId, String groupId, String field) {
        JsonNode imageResponse = getJsonMediaId(imagePath, ownerId, groupId, vkUser);
        String mediaId = getMediaIdFromResponse(imageResponse);
        JsonNode response = getWallPostWithAttachment(vkUser, ownerId, mediaId, field);
        return response.get(Fields.RESPONSE).get(Fields.POST_ID).toString();
    }
}
