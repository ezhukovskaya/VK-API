package application.pageObjects.forms;

import application.models.VkUser;
import framework.browser.Browser;
import framework.elements.Button;
import framework.elements.Label;
import org.openqa.selenium.By;

public class Post {

    private Label getContent(String userId, String postIdFromResponse) {
        return new Label(By.xpath(String.format("%s//div[contains(@style,'display: none;')]", getDataPostId(userId, postIdFromResponse))), "Content");
    }

    private Button getLikeButton(String ownerId, String postId) {
        return new Button(By.xpath(String.format("%s//div[contains(@class,'like_button_icon')]", getDataPostId(ownerId, postId))), "Like");
    }

    private Label getWallPost(String userId, String postIdFromResponse) {
        return new Label(By.xpath(String.format("%s", getDataPostId(userId, postIdFromResponse))), String.format("Post from user id=%s", userId));
    }

    private String getDataPostId(String userId, String postIdFromResponse) {
        return String.format("//div[@data-post-id='%s']", createFullPostId(userId, postIdFromResponse));
    }

    private Label getCommentLabel(VkUser vkUser, String commentId, String userId) {
        return new Label(By.xpath(String.format("//div[@id='post%s_%s']", userId, commentId)), String.format("Comment from %s", vkUser.getUsername()));
    }

    private String createFullPostId(String ownerId, String postIdFromResponse) {
        return String.format("%s_%s", ownerId, postIdFromResponse);
    }

    public boolean wallPostIsFromRightUser(String userId, String postIdFromResponse) {
        return getWallPost(userId, postIdFromResponse).isDisplayed();
    }

    public boolean isExists(String userId, String postIdFromResponse) {
        return getContent(userId, postIdFromResponse).isDisplayed();
    }

    public boolean commentIsFromRightUser(VkUser vkUser, String commentIdFromResponse, String userId, String postIdFromResponse) {
        Button nextReplies = new Button(By.xpath(String.format("%s//a[contains(@class, 'replies_next')]", getDataPostId(userId, postIdFromResponse))), "Next Replies");
        if (nextReplies.isDisplayed()) {
            nextReplies.click();
        }
        return getCommentLabel(vkUser, commentIdFromResponse, userId).isDisplayed();
    }

    public String getWallPostText(String userId, String postIdFromResponse) {
        By by = By.xpath(String.format("%s//div[contains(@class, 'wall_post_text')]", getDataPostId(userId, postIdFromResponse)));
        Browser.setExplicitWait(by);
        return new Label(by, "Text").getText();
    }

    public String getDocTitle(String userId, String postIdFromResponse) {
        return new Label(By.xpath(String.format("%s//a[contains(@class, 'page_doc_title')]", getDataPostId(userId, postIdFromResponse))), "Doc Name").getText();
    }


    public void likePost(String ownerId, String postId) {
        getLikeButton(ownerId, postId).click();
    }
}