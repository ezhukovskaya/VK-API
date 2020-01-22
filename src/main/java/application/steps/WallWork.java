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

    public static int getWallPostId(VkUser vkUser, String ownerId, String message) {
        JsonNode jsonNode = VkApiUtils.createWallPost(ownerId, message, vkUser);
        return jsonNode.get(Fields.RESPONSE).get(Fields.POST_ID).asInt();
    }

    public static int getWallCommentId(int postId, VkUser vkUser) {
        String randomText = UUID.randomUUID().toString();
        JsonNode jsonNode = VkApiUtils.createWallPostComment(postId, randomText, vkUser);
        LOG.info("Gets comment id");
        return jsonNode.get(Fields.RESPONSE).get(Fields.COMMENT_ID).asInt();
    }

    public static String getPostText(String userId, int postId, MyPage myPage) {
        return myPage.getPost().getWallPostText(userId, postId);
    }

    public static int getLikeStatus(String ownerId, String userId, int postId, VkUser vkUser) {
        JsonNode jsonNode = VkApiUtils.createIsLikedRequest(ownerId, userId, postId, vkUser);
        return jsonNode.get(Fields.RESPONSE).get(Fields.LIKED).asInt();
    }
}
