package builders;

import constants.Parametres;
import constants.VkMethods;

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

    public static String getWallPostRequest(String id, String message) {
        return String.format(createMethod(VkMethods.WALL_POST), createAccessToken() + AMP + createVersion() + AMP + createOwnerId(id) + AMP + createMessage(message));
    }

    public static String getEditPostRequest(String id, int postId, String message, String attachment) {
        return String.format(createMethod(VkMethods.WALL_EDIT), createAccessToken() + AMP + createVersion() + AMP + createOwnerId(id) + AMP + createPostId(postId) + AMP + createMessage(message) + AMP + createAttachment(attachment));
    }

    public static String getAddCommentRequest(int postId, String commentText){
        return String.format(createMethod(VkMethods.CREATE_COMMENT), createAccessToken() + AMP + createVersion() + AMP + createMessage(commentText) + AMP + createPostId(postId));
    }

}
