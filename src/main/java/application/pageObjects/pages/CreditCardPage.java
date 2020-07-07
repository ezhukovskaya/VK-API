package application.pageObjects.pages;

import framework.base.BaseForm;
import framework.elements.Label;
import lombok.Getter;
import org.openqa.selenium.By;

@Getter
public class CreditCardPage {
    private Label creditCardText = new Label(By.className("check-block__main-text"), "Credit Card Text");
}
