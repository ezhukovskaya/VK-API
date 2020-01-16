package pageObjects.forms;

import framework.elements.Button;
import javafx.scene.control.Label;
import org.openqa.selenium.By;

public class Options {

    private Button createButton(String buttonName){
        return new Button(By.xpath(String.format("//a[contains(@class,'top_profile_mrow')] and contains(text(),'%s')", buttonName)), buttonName);
    }

    public void click(String buttonName){
        createButton(buttonName).click();
    }
}
