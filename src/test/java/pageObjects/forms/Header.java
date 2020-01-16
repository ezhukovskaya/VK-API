package pageObjects.forms;

import framework.elements.Button;
import org.openqa.selenium.By;

public class Header {
    private Button options = new Button(By.xpath("//a[@id='top_profile_link']"), "Options");

    public Options getOptions() {
        options.click();
        return new Options();
    }
}
