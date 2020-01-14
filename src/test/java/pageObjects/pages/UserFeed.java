package pageObjects.pages;

import constants.UsersInfo;
import framework.base.BaseForm;
import framework.elements.Label;
import org.openqa.selenium.By;
import pageObjects.forms.Menu;

public class UserFeed extends BaseForm {

    private static Label firstUserNickname = createLabel(UsersInfo.FIRST_USER_ID);

    private static Label createLabel(String vkId){
        return new Label(By.xpath(String.format("//a[contains(@data-from-id, '%s')]", vkId)), String.format("ID %s feed page label", vkId));
    }

    public UserFeed() {
        super(firstUserNickname);
    }

    public Menu getMenu(){
        return new Menu();
    }
}
