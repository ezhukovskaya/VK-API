package tests;

import application.constants.ApiInfo;
import application.constants.Fields;
import application.constants.UsersInfo;
import application.enums.LikeStatus;
import application.models.VkUser;
import application.pageObjects.pages.MyPage;
import application.steps.Document;
import application.steps.UserWork;
import application.steps.WallWork;
import application.utils.VkApiUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class TC4 extends BaseTest {
    private static final Logger LOG = Logger.getLogger(TC4.class);
    private String randomText = UUID.randomUUID().toString();
    private final String FILE_PATH = System.getProperty("user.dir") + "/src/main/java/application/resources/file.txt";
    private final String DOC_TITLE = "file.txt";
    private final String NEW_TEXT = "MEOWMEOWMEOW!!!!!!!";

    @Test
    public void vkTest() {
        VkUser secondUser = UserWork.getVkUser(UsersInfo.SECOND_USER_USERNAME, UsersInfo.SECOND_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER2);
        UserWork.authorization(secondUser);
        MyPage myPage = new MyPage();
        String userPageLink = UserWork.getUserPageAddress(myPage);
        String secondUserId = UserWork.getUserId(userPageLink);
        int postId = WallWork.getWallPostId(secondUser, secondUserId, randomText);
        Document.postEditedWithFileWallPost(FILE_PATH, secondUserId, secondUser, postId, NEW_TEXT, Fields.DOC);
        String docTitleFromPost = myPage.getPost().getDocTitle(secondUserId, postId);
        LOG.info("Checks if the doc is the same as local");
        Assert.assertEquals(docTitleFromPost, DOC_TITLE, "Documents are different");
        LOG.info("Checks if randomText is not the same");
        Assert.assertNotEquals(randomText, myPage.getPost().getWallPostText(secondUserId, postId), "Text has not changed");
        int commentId = WallWork.getWallCommentId(postId, secondUser);
        LOG.info("Checks if Comment is from right user");
        Assert.assertTrue(myPage.getPost().commentIsFromRightUser(secondUser, commentId, secondUserId, postId), String.format("Comment is not from %s", secondUser.getUsername()));
        myPage.getPost().likePost(secondUserId, postId);
        int liked = WallWork.getLikeStatus(secondUserId, secondUserId, postId, secondUser);
        LOG.info("Checks if like status is 'LIKED'");
        Assert.assertEquals(liked, LikeStatus.LIKED.getValue(), String.format("User id=%s did not like item id=%d", secondUserId, postId));
        VkApiUtils.createWallPostDeleteRequest(secondUserId, postId, secondUser);
        LOG.info("Gets response from deleting wall post");
        Assert.assertFalse(myPage.getPost().isExists(secondUserId, postId), String.format("Post id=%d is not deleted", postId));
    }
}