package application.pageObjects.forms;

import application.models.VkUser;
import framework.elements.Button;
import framework.elements.Label;
import org.openqa.selenium.By;

public class Post {
    private static Button nextReplies;

    public Button getLikeButton(String ownerId, int postId){
        return new Button(By.xpath(String.format("%s//div[contains(@class,'like_button_icon')]", getDataPostId(ownerId, postId))), "Like");
    }

    public Label getWallPost(String userId, int postIdFromResponse) {
        nextReplies = new Button(By.xpath(String.format("%s//a[contains(@class, 'replies_next')]", getDataPostId(userId, postIdFromResponse))), "Next Replies");
        return new Label(By.xpath(String.format("%s", getDataPostId(userId, postIdFromResponse))), String.format("Post from user id=%s", userId));
    }

    private String getDataPostId(String userId, int postIdFromResponse) {
        return String.format("//div[@data-post-id='%s']", createFullPostId(userId, postIdFromResponse));
    }

    public Label getCommentLabel(VkUser vkUser, int commentId, String userId) {
        return new Label(By.xpath(String.format("//div[@id='post%s_%d']", userId, commentId)), String.format("Comment from %s", vkUser.getUsername()));
    }

    public boolean wallPostIsFromRightUser(String userId, int postIdFromResponse) {
        return getWallPost(userId, postIdFromResponse).isDisplayed();
    }

    public boolean commentIsFromRightUser(VkUser vkUser, int commentIdFromResponse, String userId) {
        if (nextReplies.isDisplayed()) {
            nextReplies.click();
        }
        return getCommentLabel(vkUser, commentIdFromResponse, userId).isDisplayed();
    }

    private String createFullPostId(String ownerId, int postIdFromResponse) {
        return String.format("%s_%d", ownerId, postIdFromResponse);
    }

    public String getWallPostText(String userId, int postIdFromResponse) {
        return getWallPost(userId, postIdFromResponse).getText();
    }

    public void likePost(String ownerId, int postId) {
        getLikeButton(ownerId, postId).click();
    }
}
