package pageObjects.pages;

import framework.base.BaseForm;
import framework.elements.Button;
import framework.elements.Label;
import models.VkUser;
import org.openqa.selenium.By;

public class MyPage extends BaseForm {
    private static Label avatar = new Label(By.xpath("//img[contains(@class, 'page_avatar_img')]"), "Page Avatar Img");
    private static Button nextReplies;
    private static Button like;

    public MyPage() {
        super(avatar);
    }

    public static Label getWallPost(VkUser vkUser, int postIdFromResponse) {
        like = new Button(By.xpath(String.format("%s//div[contains(@class,'like_button_icon')]", getDataPostId(vkUser, postIdFromResponse))), "Like");
        nextReplies = new Button(By.xpath(String.format("%s//a[contains(@class, 'replies_next')]",  getDataPostId(vkUser, postIdFromResponse))), "Next Replies");
        return new Label(By.xpath(String.format("%s",  getDataPostId(vkUser, postIdFromResponse))), String.format("Post from %s", vkUser.getUsername()));
    }

    private static String getDataPostId(VkUser vkUser, int postIdFromResponse){
        return String.format("//div[@data-post-id='%s']", createFullPostId(vkUser.getId(), postIdFromResponse));
    }

    public static Label getCommentLabel(VkUser vkUser, int commentId){
        return new Label(By.xpath(String.format("//div[@id='post%s_%d']", vkUser.getId(), commentId)), String.format("Comment from %s", vkUser.getUsername()));
    }

    public boolean wallPostIsFromRightUser(VkUser vkUser, int postIdFromResponse) {
        return getWallPost(vkUser, postIdFromResponse).isDisplayed();
    }

    public boolean commentIsFromRightUser(VkUser vkUser, int commentIdFromResponse){
        if(nextReplies.isDisplayed()) {
            nextReplies.click();
        }
        return getCommentLabel(vkUser, commentIdFromResponse).isDisplayed();
    }

    private static String createFullPostId(String ownerId, int postIdFromResponse) {
        return String.format("%s_%d", ownerId, postIdFromResponse);
    }

    public String getWallPostText(VkUser vkUser, int postIdFromResponse){
        return getWallPost(vkUser, postIdFromResponse).getText();
    }

    public void likePost(){
        like.click();
    }
}
