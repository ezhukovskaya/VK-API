package application.steps;

import application.constants.URLs;
import application.constants.VkOptions;
import application.models.VkUser;
import application.pageObjects.pages.LoginPage;
import application.pageObjects.pages.MyPage;
import application.pageObjects.pages.UserFeed;
import framework.browser.Browser;
import org.apache.log4j.Logger;

public class UserWork {
    private static final Logger LOG = Logger.getLogger(UserWork.class);


    public static VkUser getVkUser(String username, String password, String accessToken) {
        return new VkUser(username, password, accessToken);
    }

    public static void authorization(VkUser vkUser) {
        LoginPage loginPage = new LoginPage();
        loginPage.getAuthorization().logOn(vkUser);
    }

    public static String getUserPageAddress(MyPage myPage) {
        UserFeed userFeed = new UserFeed();
        userFeed.getMenu().getGoToMyPage().click();
        Browser.setExplicitWait(myPage.getAvatarBy());
        return Browser.getCurrentUrl();
    }

    public static String getUserId(String link) {
        return link.substring(URLs.URL.length() + 2);
    }


    public static void logOut() {
        new MyPage().getHeader().clickOption();
        new MyPage().getHeader().getOptions().click(VkOptions.LOG_OUT);
    }
}