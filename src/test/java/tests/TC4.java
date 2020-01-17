package tests;

import application.constants.ApiInfo;
import application.constants.UsersInfo;
import application.enums.LikeStatus;
import application.models.VkUser;
import application.pageObjects.pages.MyPage;
import application.steps.Steps;
import application.utils.VkApiUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC4 extends BaseTest{
    @Test
    public static void vkTest(){
        VkUser secondUser = Steps.getVkUser(UsersInfo.SECOND_USER_USERNAME, UsersInfo.SECOND_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER2);
        Steps.authorization(secondUser);
        MyPage myPage = new MyPage();
        String userPageLink = Steps.getUserPageAddress(myPage);
        String secondUserId = Steps.getUserId(userPageLink);
        int postId = Steps.getWallPostId(secondUser, secondUserId);
        String postText = Steps.getPostText(secondUserId, postId, myPage);
        //редактирование и проверка
        int commentId = Steps.getWallCommentId(postId, secondUser);
        Assert.assertTrue(myPage.getPost().commentIsFromRightUser(secondUser, commentId, secondUserId), String.format("Comment is not from %s", secondUser.getUsername()));
        myPage.getPost().likePost(secondUserId, postId);
        int liked = Steps.getLikeStatus(secondUserId, secondUserId, postId, secondUser);
        Assert.assertEquals(liked, LikeStatus.LIKED.getValue(), String.format("User id=%s did not like item id=%d", secondUserId, postId));
        VkApiUtils.createWallPostDeleteRequest(secondUserId, postId, secondUser);
        Assert.assertFalse(myPage.getPost().wallPostIsFromRightUser(secondUserId, postId), String.format("Post id=%d is not deleted", postId));
    }
}