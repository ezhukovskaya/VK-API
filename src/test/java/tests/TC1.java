package tests;

import application.constants.ApiInfo;
import application.constants.Fields;
import application.constants.UsersInfo;
import application.enums.LikeStatus;
import application.models.VkUser;
import application.pageObjects.pages.MyPage;
import application.steps.Steps;
import application.utils.VkApiUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC1 extends BaseTest {
    private static final Logger LOG = Logger.getLogger(TC1.class);
    private String imagePath = System.getProperty("user.dir") + "/src/main/java/application/resources/photo.jpg";
    private String imageUrl = "https://vk.com/photo%s_%s";

    @Test
    public void vkTest() {
        VkUser firstUser = Steps.getVkUser(UsersInfo.FIRST_USER_USERNAME, UsersInfo.FIRST_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER1);
        Steps.authorization(firstUser);
        MyPage myPageFirstUser = new MyPage();
        String userPageLink = Steps.getUserPageAddress(myPageFirstUser);
        String firstUserId = Steps.getUserId(userPageLink);
        int postId = Steps.getWallPostId(firstUser, firstUserId);
        String postText = Steps.getPostText(firstUserId, postId, myPageFirstUser);
        Assert.assertTrue(myPageFirstUser.getPost().wallPostIsFromRightUser(firstUserId, postId), String.format("Post is not from %s", firstUser.getUsername()));
        LOG.info(String.format("Checks if Post message matches %s", postText));
        Assert.assertTrue(myPageFirstUser.getPost().getWallPostText(firstUserId, postId).contains(postText), "Texts are different");
        String mediaId = Steps.getMediaId(imagePath, firstUserId, firstUserId, firstUser);
        Assert.assertTrue(Steps.postEditedWithPhotoWallPost(imagePath, firstUserId, firstUserId, firstUser, postId, "qwerty", imageUrl, Fields.PHOTO, mediaId));
        Assert.assertFalse(myPageFirstUser.getPost().getWallPostText(firstUserId, postId).contains(postText));
        int commentId = Steps.getWallCommentId(postId, firstUser);
        LOG.info("Checks if Comment is from right user");
        Assert.assertTrue(myPageFirstUser.getPost().commentIsFromRightUser(firstUser, commentId, firstUserId), String.format("Comment is not from %s", firstUser.getUsername()));
        LOG.info("Like post");
        myPageFirstUser.getPost().likePost(firstUserId, postId);
        int liked = Steps.getLikeStatus(firstUserId, firstUserId, postId, firstUser);
        LOG.info("Checks if like status is 'LIKED'");
        Assert.assertEquals(liked, LikeStatus.LIKED.getValue(), String.format("User id=%s did not like item id=%d", firstUserId, postId));
        LOG.info("Gets response from deleting wall post");
        VkApiUtils.createWallPostDeleteRequest(firstUserId, postId, firstUser);
        Assert.assertFalse(myPageFirstUser.getPost().wallPostIsFromRightUser(firstUserId, postId), String.format("Post id=%d is not deleted", postId));
    }
}
