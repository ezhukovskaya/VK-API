package pageObjects.pages;

import framework.base.BaseForm;
import framework.elements.Label;
import org.openqa.selenium.By;

public class MyPage extends BaseForm {
    private static Label avatar = new Label(By.xpath("//img[contains(@class, 'page_avatar_img')]"), "Page Avatar Img");

    public MyPage() {
        super(avatar);
    }
}
