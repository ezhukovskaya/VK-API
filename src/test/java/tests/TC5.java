package tests;

import application.constants.ApiInfo;
import application.constants.Fields;
import application.constants.UsersInfo;
import application.models.VkUser;
import application.pageObjects.pages.MyPage;
import application.steps.Steps;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TC5 extends BaseTest {
    private final String filePath = System.getProperty("user.dir") + "/src/main/java/application/resources/photo.jpg";
    private String imageUrl = "https://vk.com/photo%s_%s";
    @DataProvider(name = "users")
    public Object[][] getData() {
        VkUser firstUser = Steps.getVkUser(UsersInfo.FIRST_USER_USERNAME, UsersInfo.FIRST_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER1);
        VkUser secondUser = Steps.getVkUser(UsersInfo.SECOND_USER_USERNAME, UsersInfo.SECOND_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER2);
        return new Object[][]{{firstUser}, {secondUser}};
    }

    @Test(dataProvider = "users")
    public void vkTest(VkUser vkUser){
        Steps.authorization(vkUser);
        MyPage myPage = new MyPage();
        String userPageLink = Steps.getUserPageAddress(myPage);
        String userId = Steps.getUserId(userPageLink);
        String mediaId = Steps.getMediaId(filePath, userId, userId, vkUser);
        int postId = Steps.getWallPostIdWithAttachment(vkUser, userId, mediaId, Fields.PHOTO);
        String postText = Steps.getPostText(userId, postId, myPage);
        Assert.assertTrue(myPage.getPost().getWallPostText(userId, postId).contains(postText), "Texts are different");
        Assert.assertTrue(Steps.postWithPhoto(filePath, userId, userId, mediaId, vkUser, Fields.PHOTO, imageUrl));
    }
}
