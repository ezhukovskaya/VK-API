package application.pageObjects.pages;

import application.pageObjects.forms.Header;
import framework.base.BaseForm;
import framework.elements.Label;
import org.openqa.selenium.By;
import application.pageObjects.forms.Post;

public class MyPage extends BaseForm {
    private static By avatarBy = By.xpath("//img[contains(@class, 'page_avatar_img')]");
    private static Label avatar = new Label(avatarBy, "Page Avatar Img");

    public MyPage() {
        super(avatar);
    }

    public Header getHeader() {
        return new Header();
    }

    public Post getPost(){
        return new Post();
    }

    public By getAvatarBy(){
        return avatarBy;
    }
}