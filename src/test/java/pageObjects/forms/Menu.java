package pageObjects.forms;

import framework.elements.Button;
import lombok.Data;
import org.openqa.selenium.By;

@Data
public class Menu {
    private Button goToMyPage = new Button(By.xpath("//li[@id='l_pr']"), "My profile");
}
