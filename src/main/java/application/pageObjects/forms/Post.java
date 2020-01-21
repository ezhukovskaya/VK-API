package application.pageObjects.forms;

import application.models.VkUser;
import framework.browser.Browser;
import framework.elements.Button;
import framework.elements.Label;
import org.openqa.selenium.By;

public class Post {

    private Label getContent(String userId, int postIdFromResponse) {
        return new Label(By.xpath(String.format("%s//div[contains(@style,'display: none;')]", getDataPostId(userId, postIdFromResponse))), "Content");
    }

    private Button getLikeButton(String ownerId, int postId) {
        return new Button(By.xpath(String.format("%s//div[contains(@class,'like_button_icon')]", getDataPostId(ownerId, postId))), "Like");
    }

    private Label getWallPost(String userId, int postIdFromResponse) {
        return new Label(By.xpath(String.format("%s", getDataPostId(userId, postIdFromResponse))), String.format("Post from user id=%s", userId));
    }

    private String getDataPostId(String userId, int postIdFromResponse) {
        return String.format("//div[@data-post-id='%s']", createFullPostId(userId, postIdFromResponse));
    }

    private Label getCommentLabel(VkUser vkUser, int commentId, String userId) {
        return new Label(By.xpath(String.format("//div[@id='post%s_%d']", userId, commentId)), String.format("Comment from %s", vkUser.getUsername()));
    }

    private String createFullPostId(String ownerId, int postIdFromResponse) {
        return String.format("%s_%d", ownerId, postIdFromResponse);
    }

    public boolean wallPostIsFromRightUser(String userId, int postIdFromResponse) {
        return getWallPost(userId, postIdFromResponse).isDisplayed();
    }

    public boolean isExists(String userId, int postIdFromResponse) {
        return getContent(userId, postIdFromResponse).isDisplayed();
    }

    public boolean commentIsFromRightUser(VkUser vkUser, int commentIdFromResponse, String userId, int postIdFromResponse) {
        Button nextReplies = new Button(By.xpath(String.format("%s//a[contains(@class, 'replies_next')]", getDataPostId(userId, postIdFromResponse))), "Next Replies");
        if (nextReplies.isDisplayed()) {
            nextReplies.click();
        }
        return getCommentLabel(vkUser, commentIdFromResponse, userId).isDisplayed();
    }

    public String getWallPostText(String userId, int postIdFromResponse) {
        By by = By.xpath(String.format("%s//div[contains(@class, 'wall_post_text')]", getDataPostId(userId, postIdFromResponse)));
        Browser.setExplicitWait(by);
        return new Label(by, "Text").getText();
    }

    public String getDocTitle(String userId, int postIdFromResponse) {
        return new Label(By.xpath(String.format("%s//a[contains(@class, 'page_doc_title')]", getDataPostId(userId, postIdFromResponse))), "Doc Name").getText();
    }


    public void likePost(String ownerId, int postId) {
        getLikeButton(ownerId, postId).click();
    }
}