package application.builders;

import application.constants.Parameters;
import application.constants.VkMethods;
import application.constants.VkMethodsName;
import application.models.VkUser;

public class WallPostBuilder extends BaseBuilder {

    private static String createAttachment(String id, String mediaId, String field) {
        return createField(Parameters.ATTACHMENT, field + id + "_" + mediaId);
    }

    public static String getWallPostRequest(String id, String message, String mediaId, VkUser vkUser, String field) {
        return getDev(VkMethodsName.WALL + VkMethods.POST + createRequiredField(vkUser) + createField(Parameters.OWNER_ID, id) + createField(Parameters.MESSAGE, message) + createAttachment(id, mediaId, field));
    }

    public static String getWallPostRequest(String id, String message, VkUser vkUser) {
        return getDev(VkMethodsName.WALL + VkMethods.POST + createRequiredField(vkUser) + createField(Parameters.OWNER_ID, id) + createField(Parameters.MESSAGE, message));
    }

    public static String getEditPostRequest(String id, String postId, String message, String mediaId, VkUser vkUser, String field) {
        return getDev(VkMethodsName.WALL + VkMethods.EDIT + createRequiredField(vkUser) + createField(Parameters.OWNER_ID, id) + createField(Parameters.POST_ID, postId) + createField(Parameters.MESSAGE, message) + createAttachment(id, mediaId, field));
    }

    public static String getAddCommentRequest(String postId, String commentText, VkUser vkUser) {
        return getDev(VkMethodsName.WALL + VkMethods.COMMENT + createRequiredField(vkUser) + createField(Parameters.POST_ID, postId) + createField(Parameters.MESSAGE, commentText));
    }

    public static String getDeleteWallPostRequest(String ownerId, String postId, VkUser vkUser) {
        return getDev(VkMethodsName.WALL + VkMethods.DELETE + createRequiredField(vkUser) + createField(Parameters.OWNER_ID, ownerId) + createField(Parameters.POST_ID, postId));
    }
}