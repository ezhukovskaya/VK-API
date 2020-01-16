package steps;

import com.fasterxml.jackson.databind.JsonNode;
import constants.Fields;
import constants.URLs;
import constants.UsersInfo;
import constants.VkOptions;
import framework.browser.Browser;
import models.VkUser;
import org.apache.log4j.Logger;
import pageObjects.pages.LoginPage;
import pageObjects.pages.MyPage;
import pageObjects.pages.UserFeed;
import utils.VkApiUtils;

public class Steps {
    private static final Logger LOG = Logger.getLogger(Steps.class);
    public static VkUser getVkUser(String username, String password){
       return new VkUser(username, password, UsersInfo.FIRST_USER_ID);
    }

    public static void authorization(VkUser vkUser){
        LoginPage loginPage = new LoginPage();
        loginPage.getAuthorization().logOn(vkUser);
    }

    public static String getUserPageAddress(){
        UserFeed userFeed = new UserFeed();
        userFeed.getMenu().getGoToMyPage().click();
        return Browser.getCurrentUrl();
    }

    public static String getUserId(String link){
        return link.substring(URLs.VK_COM.length()-1);
    }

    public static int getWallPostId(VkUser vkUser, String text){
        JsonNode jsonNode = VkApiUtils.createWallPost(vkUser.getId(), text);
        return jsonNode.get(Fields.RESPONSE).get(Fields.POST_ID).asInt();
    }

    public static void like(){
        new MyPage().likePost();
    }

    public static void logOut(){
        new MyPage().getHeader().getOptions().click(VkOptions.LOG_OUT);
    }
}
