package application.pageObjects.forms;

import framework.elements.Button;
import org.openqa.selenium.By;

public class Options {

    private Button createButton(String id) {
        return new Button(By.xpath(String.format("//a[contains(@class,'top_profile_mrow') and contains(@id,'%s')]", id)), id);
    }

    public void click(String id) {
        createButton(id).click();
    }
}