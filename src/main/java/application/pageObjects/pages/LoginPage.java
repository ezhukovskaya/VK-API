package application.pageObjects.pages;

import framework.base.BaseForm;
import framework.elements.Button;
import framework.elements.Label;
import framework.elements.TextBox;
import lombok.Getter;
import org.openqa.selenium.By;

@Getter
public class LoginPage extends BaseForm {

    private static final Label registrationBg = new Label(By.xpath("//*[contains(@class, 'register__bg')]"), "Registration bg");
    private TextBox lastName = new TextBox(By.id("lastname"), "Last Name");
    private TextBox firstName = new TextBox(By.id("firstname"), "First Name");
    private TextBox middleName = new TextBox(By.id("middlename"), "Middle Name");
    private TextBox bDay = new TextBox(By.id("bday"),"BDay");
    private TextBox phone = new TextBox(By.id("phone"), "Phone");
    private Button sendButton = new Button(By.xpath("//input[contains(@class, 'js-send')]"),"Sen Button");

    public LoginPage() {
        super(registrationBg);
    }
}