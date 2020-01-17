package framework.elements;

import framework.base.BaseElement;
import framework.browser.Browser;
import org.openqa.selenium.By;
import org.apache.log4j.Logger;

public class TextBox extends BaseElement {
    static final Logger log = Logger.getLogger(TextBox.class);

    public TextBox(By locator, String name) {
        super(locator, name);
    }

    public void sendKeys(String text) {
        log.info("TextBox takes " + text);
        Browser.getBrowser().findElement(elementLocator).sendKeys(text);
    }
}
