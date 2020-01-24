package application.builders;

import application.constants.Parametres;
import application.constants.VkMethods;
import application.constants.VkMethodsName;
import application.models.VkUser;

public class WallPostBuilder extends BaseBuilder {

    private static String createAttachment(String id, String mediaId, String field) {
        return String.format(Parametres.ATTACHMENT, field + id + "_" + mediaId);
    }

    public static String getWallPostRequest(String id, String message, String mediaId, VkUser vkUser, String field) {
        return createField(VkMethodsName.WALL, VkMethods.POST) + createRequiredField(vkUser) + createField(Parametres.OWNER_ID, id) + createField(Parametres.MESSAGE, message) + createAttachment(id, mediaId, field);
    }

    public static String getWallPostRequest(String id, String message, VkUser vkUser) {
        return createField(VkMethodsName.WALL, VkMethods.POST) + createRequiredField(vkUser) + createField(Parametres.OWNER_ID, id) + createField(Parametres.MESSAGE, message);
    }

    public static String getEditPostRequest(String id, String postId, String message, String mediaId, VkUser vkUser, String field) {
        return createField(VkMethodsName.WALL, VkMethods.EDIT) + createRequiredField(vkUser) + createField(Parametres.OWNER_ID, id) + createField(Parametres.POST_ID, postId) + createField(Parametres.MESSAGE, message) + createAttachment(id, mediaId, field);
    }

    public static String getAddCommentRequest(String postId, String commentText, VkUser vkUser) {
        return createField(VkMethodsName.WALL, VkMethods.COMMENT) + createRequiredField(vkUser) + createField(Parametres.POST_ID, postId) + createField(Parametres.MESSAGE, commentText);
    }

    public static String getDeleteWallPostRequest(String ownerId, String postId, VkUser vkUser) {
        return createField(VkMethodsName.WALL, VkMethods.DELETE) + createRequiredField(vkUser) + createField(Parametres.OWNER_ID, ownerId) + createField(Parametres.POST_ID, postId);
    }
}