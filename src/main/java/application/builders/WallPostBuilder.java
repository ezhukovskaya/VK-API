package application.builders;

import application.constants.Parametres;
import application.constants.VkMethods;
import application.models.VkUser;

public class WallPostBuilder extends BaseBuilder {

    private static String createMessage(String message) {
        return String.format(Parametres.MESSAGE, message);
    }

    private static String createPostId(int postId) {
        return String.format(Parametres.POST_ID, postId);
    }

    private static String createAttachment(String attachment) {
        return String.format(Parametres.ATTACHMENT, attachment);
    }

    public static String getWallPostRequest(String id, String message, VkUser vkUser) {
        return String.format(createMethod(VkMethods.WALL_POST), createAccessToken(vkUser.getAccessToken()) + AMP + createVersion() + AMP + createOwnerId(id) + AMP + createMessage(message));
    }

    public static String getEditPostRequest(String id, int postId, String message, String attachment, VkUser vkUser) {
        return String.format(createMethod(VkMethods.WALL_EDIT), createAccessToken(vkUser.getAccessToken()) + AMP + createVersion() + AMP + createOwnerId(id) + AMP + createPostId(postId) + AMP + createMessage(message) + AMP + createAttachment(attachment));
    }

    public static String getAddCommentRequest(int postId, String commentText, VkUser vkUser){
        return String.format(createMethod(VkMethods.CREATE_COMMENT), createAccessToken(vkUser.getAccessToken()) + AMP + createVersion() + AMP + createMessage(commentText) + AMP + createPostId(postId));
    }

    public static String getDeleteWallPostRequest(String ownerId, int postId, VkUser vkUser){
        return String.format(createMethod(VkMethods.DELETE), createAccessToken(vkUser.getAccessToken()) + AMP + createVersion() + AMP + createOwnerId(ownerId) + AMP + createPostId(postId));
    }

}
