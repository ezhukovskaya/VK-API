package application.pageObjects.pages;

import framework.elements.Button;
import lombok.Data;
import org.openqa.selenium.By;

@Data
public class AnyPage {
    private Button moneyButton = new Button(By.xpath("//a[contains(@class, 'cooc__one')]"), "MoneyButton");
}
