package application.pageObjects.forms;

import application.constants.ID;
import framework.elements.Button;
import org.openqa.selenium.By;

public class Header {
    private Button options = new Button(By.xpath(String.format("//a[@id='%s']", ID.OPTIONS_BUTTON_ID)), "Options");

    public Options getOptions() {
        return new Options();
    }

    public void clickOption(){
        options.click();
    }
}
