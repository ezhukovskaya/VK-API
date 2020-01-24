package application.steps;

import application.constants.Fields;
import application.models.VkUser;
import application.pageObjects.pages.MyPage;
import application.utils.VkApiUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;

import java.util.UUID;

public class WallWork {
    private static final Logger LOG = Logger.getLogger(UserWork.class);

    public static String getWallPostId(VkUser vkUser, String ownerId, String message) {
        JsonNode jsonNode = VkApiUtils.createWallPost(ownerId, message, vkUser);
        return jsonNode.get(Fields.RESPONSE).get(Fields.POST_ID).toString();
    }

    public static String getWallCommentId(String postId, VkUser vkUser) {
        String randomText = UUID.randomUUID().toString();
        JsonNode jsonNode = VkApiUtils.createWallPostComment(postId, randomText, vkUser);
        LOG.info("Gets comment id");
        return jsonNode.get(Fields.RESPONSE).get(Fields.COMMENT_ID).toString();
    }

    public static String getPostText(String userId, String postId, MyPage myPage) {
        return myPage.getPost().getWallPostText(userId, postId);
    }

    public static int getLikeStatus(String ownerId, String userId, String postId, VkUser vkUser, String type) {
        JsonNode jsonNode = VkApiUtils.createIsLikedRequest(ownerId, userId, postId, vkUser, type);
        return jsonNode.get(Fields.RESPONSE).get(Fields.LIKED).asInt();
    }
}
