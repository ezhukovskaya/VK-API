package application.pageObjects.forms;

import application.constants.ID;
import framework.elements.Button;
import lombok.Data;
import org.openqa.selenium.By;

@Data
public class Menu {
    private Button goToMyPage = getButton(ID.GO_TO_MY_PAGE_ID, "My profile");

    public Button getButton(String id, String name) {
        return new Button(By.xpath(String.format("//li[@id='%s']", id)), name);
    }
}