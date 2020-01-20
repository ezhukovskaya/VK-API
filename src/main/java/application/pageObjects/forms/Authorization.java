package application.pageObjects.forms;

import application.models.VkUser;
import framework.elements.Button;
import framework.elements.TextBox;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class Authorization {
    private TextBox loginField = new TextBox(By.xpath("//input[@id='index_email']"), "Log In Field");
    private TextBox passwordField = new TextBox(By.xpath("//input[@id='index_pass']"), "Password Field");
    private Button loginButton = new Button(By.xpath("//button[@id='index_login_button']"), "Login Button");

    public void logOn(VkUser vkUser) {
        loginField.sendKeys(vkUser.getUsername());
        passwordField.sendKeys(vkUser.getPassword());
        loginButton.click();
    }
}
