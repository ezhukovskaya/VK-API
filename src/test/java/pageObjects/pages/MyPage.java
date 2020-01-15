package pageObjects.pages;

import framework.base.BaseForm;
import framework.elements.Label;
import org.openqa.selenium.By;

public class MyPage extends BaseForm {
    private static Label avatar = new Label(By.xpath("//img[contains(@class, 'page_avatar_img')]"), "Page Avatar Img");

    public MyPage() {
        super(avatar);
    }

    public static Label getWallPost(String ownerId, int postIdFromResponse) {
        return new Label(By.xpath(String.format("//div[@data-post-id='%s']", createFullPostId(ownerId, postIdFromResponse))), "Post");
    }

    public boolean wallPostIsFromRightUser(String ownerId, int postIdFromResponse) {
        return getWallPost(ownerId, postIdFromResponse).isDisplayed();
    }

    private static String createFullPostId(String ownerId, int postIdFromResponse) {
        return String.format("%s_%d", ownerId, postIdFromResponse);
    }

    public String getWallPostText(String ownerId, int postIdFromResponse){
        return getWallPost(ownerId, postIdFromResponse).getText();
    }
}
