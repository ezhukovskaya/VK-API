package tests;

import application.builders.PhotoUploadBuilder;
import application.constants.ApiInfo;
import application.constants.UsersInfo;
import application.models.VkUser;
import application.pageObjects.pages.MyPage;
import application.steps.Steps;
import application.utils.VkApiUtils;
import com.fasterxml.jackson.databind.JsonNode;
import framework.utils.RegEx;
import org.testng.annotations.Test;

public class PhotoUpload extends BaseTest {
    private String imagePath = System.getProperty("user.dir") + "/src/main/java/application/resources/photo.jpg";

    @Test
    public void func() {
        VkUser vkUser = Steps.getVkUser(UsersInfo.SECOND_USER_USERNAME, UsersInfo.SECOND_USER_PASSWORD, ApiInfo.ACCESS_TOKEN_USER2);
        Steps.authorization(vkUser);
        MyPage myPage = new MyPage();
        String userPageLink = Steps.getUserPageAddress(myPage);
        String secondUserId = Steps.getUserId(userPageLink);
        int postId = Steps.getWallPostId(vkUser, secondUserId);
        String uploadUrl = PhotoUploadBuilder.createUploadUrl(secondUserId, vkUser);
        String photoField = PhotoUploadBuilder.getPhotoField(uploadUrl, imagePath);
        photoField = photoField.replace("\\", "").replace("\"", "");
        JsonNode jsonNode = VkApiUtils.createWallSavePhotoRequest(photoField, vkUser);
    }
}
