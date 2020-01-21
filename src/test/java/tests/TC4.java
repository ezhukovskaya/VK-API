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

import java.util.UUID;

public class TC4 extends BaseTest {
    private static final Logger LOG = Logger.getLogger(TC4.class);
    private String randomText = UUID.randomUUID().toString();
    private String filePath = System.getProperty("user.dir") + "/src/main/java/application/resources/file.txt";
    private final String DOC_TITLE = "file.txt";
    private final String NEW_TEXT = "MEOWMEOWMEOW!!!!!!!";

    @Test
    public void vkTest() {
        VkUser secondUser = Steps.getVkUser(UsersInfo.SECOND_USER_USERNAME, UsersInfo.SECOND_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER2);
        Steps.authorization(secondUser);
        MyPage myPage = new MyPage();
        String userPageLink = Steps.getUserPageAddress(myPage);
        String secondUserId = Steps.getUserId(userPageLink);
        int postId = Steps.getWallPostId(secondUser, secondUserId, randomText);
        Steps.postEditedWithFileWallPost(filePath, secondUserId, secondUser, postId, NEW_TEXT, Fields.DOC);
        String docTitleFromPost = myPage.getPost().getDocTitle(secondUserId, postId);
        LOG.info("Checks if the doc is the same as local");
        Assert.assertEquals(docTitleFromPost, DOC_TITLE, "Documents are different");
        LOG.info("Checks if randomText is not the same");
        Assert.assertNotEquals(randomText, myPage.getPost().getWallPostText(secondUserId, postId), "Text has not changed");
        int commentId = Steps.getWallCommentId(postId, secondUser);
        LOG.info("Checks if Comment is from right user");
        Assert.assertTrue(myPage.getPost().commentIsFromRightUser(secondUser, commentId, secondUserId, postId), String.format("Comment is not from %s", secondUser.getUsername()));
        myPage.getPost().likePost(secondUserId, postId);
        int liked = Steps.getLikeStatus(secondUserId, secondUserId, postId, secondUser);
        LOG.info("Checks if like status is 'LIKED'");
        Assert.assertEquals(liked, LikeStatus.LIKED.getValue(), String.format("User id=%s did not like item id=%d", secondUserId, postId));
        VkApiUtils.createWallPostDeleteRequest(secondUserId, postId, secondUser);
        LOG.info("Gets response from deleting wall post");
        Assert.assertFalse(myPage.getPost().isExists(secondUserId, postId), String.format("Post id=%d is not deleted", postId));
    }
}