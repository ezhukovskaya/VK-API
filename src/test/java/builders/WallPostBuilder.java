package builders;

import constants.ApiInfo;
import constants.Parametres;
import constants.URLs;
import constants.VkMethods;

public class WallPostBuilder {
    private static final String AMP = "&";

    private static String createOwnerId(String id) {
        return String.format(Parametres.OWNER_ID, id);
    }

    private static String createMessage(String message) {
        return String.format(Parametres.MESSAGE, message);
    }

    private static String createAccessToken() {
        return String.format(Parametres.ACCESS_TOKEN, ApiInfo.ACCESS_TOKEN);
    }

    private static String createVersion() {
        return String.format(Parametres.VERSION, ApiInfo.API_VERSION);
    }

    private static String createMethod() {
        return String.format(URLs.DEV_VK_API, VkMethods.WALL_POST);
    }

    public static String getURL(String id, String message) {
        return String.format(createMethod(), createAccessToken() + AMP + createVersion() + AMP + createOwnerId(id) + AMP + createMessage(message));
    }

}
