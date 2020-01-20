package application.pageObjects.forms;

import framework.elements.Button;
import lombok.Data;
import org.openqa.selenium.By;

@Data
public class Menu {
    private Button goToMyPage = getButton("l_pr", "My profile");

    public Button getButton(String id, String name) {
        return new Button(By.xpath(String.format("//li[@id='%s']", id)), name);
    }
}
