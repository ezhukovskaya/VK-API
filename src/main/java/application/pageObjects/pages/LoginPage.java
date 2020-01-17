package application.pageObjects.pages;

import framework.base.BaseForm;
import framework.elements.Label;
import org.openqa.selenium.By;
import application.pageObjects.forms.Authorization;

public class LoginPage extends BaseForm {

    private static Label vkLogo = new Label(By.xpath("//*[contains(@class, 'top_home_logo')]"), "VK Logo");

    public LoginPage() {
        super(vkLogo);
    }

    public Authorization getAuthorization() {
        return new Authorization();
    }
}