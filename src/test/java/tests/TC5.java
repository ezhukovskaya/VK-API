package tests;

import application.constants.Parameters;
import application.constants.UsersInfo;
import application.models.VkUser;
import application.pageObjects.pages.MyPage;
import application.steps.Picture;
import application.steps.UserWork;
import application.steps.WallWork;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TC5 extends BaseTest {
    private static final Logger LOG = Logger.getLogger(TC1.class);
    private final String filePath = System.getProperty("user.dir") + "/src/main/java/application/resources/photo.jpg";
    private final double IMAGE_ACCURACY = 70.0;

    @DataProvider(name = "users")
    public Object[][] getData() {
        VkUser firstUser = UserWork.getVkUser(UsersInfo.FIRST_USER_USERNAME, UsersInfo.FIRST_USER_PASSWORD, UsersInfo.ACCESS_TOKEN_USER1);
        VkUser secondUser = UserWork.getVkUser(UsersInfo.SECOND_USER_USERNAME, UsersInfo.SECOND_USER_PASSWORD, UsersInfo.ACCESS_TOKEN_USER2);
        return new Object[][]{{firstUser}, {secondUser}};
    }

    @Test(dataProvider = "users")
    public void vkTest(VkUser vkUser) {
        UserWork.authorization(vkUser);
        MyPage myPage = new MyPage();
        String userPageLink = UserWork.getUserPageAddress(myPage);
        String userId = UserWork.getUserId(userPageLink);
        String postId = Picture.getPostId(filePath, vkUser, userId, userId, Parameters.PHOTO);
        String postText = WallWork.getPostText(userId, postId, myPage);
        LOG.info("Checks if Post message matches text");
        Assert.assertTrue(myPage.getPost().getWallPostText(userId, postId).contains(postText), "Texts are different");
        LOG.info("Checks if the pic is the same as local");
        Assert.assertTrue(Picture.isAttached(filePath, vkUser, userId, userId) >= IMAGE_ACCURACY, "Pics are different");
    }
}
