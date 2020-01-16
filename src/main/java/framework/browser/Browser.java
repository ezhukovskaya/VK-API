package framework.browser;

import framework.utils.PropertiesRead;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Browser {
    private static WebDriver driver;
    private static final int TIMEOUT = Integer.parseInt(PropertiesRead.readFromFrameworkConfig("implicitlyTimeout"));


    static final Logger log = Logger.getLogger(Browser.class);


    public static WebDriver getBrowser() {
        if (driver == null) {
            driver = BrowserFactory.getBrowser();
        }
        return driver;
    }

    public static void takeScreenshot(String path) {
        log.info("Taking screenshot");
        TakesScreenshot screenshot = ((TakesScreenshot) getBrowser());
        log.info("Inserting image into the file PNG");
        File image = screenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(path);
        try {
            FileUtils.copyFile(image, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static String getCurrentUrl(){
        log.info("Getting current Url");
        return getBrowser().getCurrentUrl();
    }

}
