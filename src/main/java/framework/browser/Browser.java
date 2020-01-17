package framework.browser;

import framework.utils.PropertiesRead;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Browser {
    private static WebDriver driver;
    private static final int TIMEOUT = Integer.parseInt(PropertiesRead.readFromFrameworkConfig("implicitlyTimeout"));
    private static final int EXPLICIT_TIMEOUT = Integer.parseInt(PropertiesRead.readFromFrameworkConfig("explicitTimeout"));


    private static final Logger log = Logger.getLogger(Browser.class);


    public static WebDriver getBrowser() {
        if (driver == null) {
            driver = BrowserFactory.getBrowser();
        }
        return driver;
    }

    public static void goToUrl(String url) {
        log.info("Go to " + url);
        getBrowser().get(url);
    }

    public static void maximize() {
        log.info("Full screen mode is on");
        getBrowser().manage().window().maximize();
    }

    public static void close() {
        log.info("Browser closes");
        getBrowser().close();
    }

    public static void setImplicitlyWait() {
        log.info("Timeout is " + TIMEOUT);
        getBrowser().manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
    }

    public static void setExplicitWait(By by){
        (new WebDriverWait(getBrowser(), EXPLICIT_TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static String getCurrentUrl(){
        log.info("Getting current Url");
        return getBrowser().getCurrentUrl();
    }
}
