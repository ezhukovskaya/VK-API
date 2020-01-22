package application.pageObjects.forms;

import application.constants.ID;
import application.models.VkUser;
import framework.elements.Button;
import framework.elements.TextBox;
import org.openqa.selenium.By;

public class Authorization {
    private TextBox loginField = new TextBox(By.xpath(String.format("//input[@id='%s']", ID.LOGIN_FIELD_ID)), "Log In Field");
    private TextBox passwordField = new TextBox(By.xpath(String.format("//input[@id='%s']", ID.PASSWORD_FIELD_ID)), "Password Field");
    private Button loginButton = new Button(By.xpath(String.format("//button[@id='%s']", ID.LOGIN_BUTTON_ID)), "Login Button");

    public void logOn(VkUser vkUser) {
        loginField.sendKeys(vkUser.getUsername());
        passwordField.sendKeys(vkUser.getPassword());
        loginButton.click();
    }
}