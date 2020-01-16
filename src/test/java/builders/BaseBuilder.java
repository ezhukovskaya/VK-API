package builders;

import constants.ApiInfo;
import constants.Parametres;
import constants.URLs;

abstract public class BaseBuilder {
    protected static final String AMP = "&";

    protected static String createOwnerId(String id) {
        return String.format(Parametres.OWNER_ID, id);
    }

    protected static String createGroupId(String id) {
        return String.format(Parametres.GROUP_ID, id);
    }

    protected static String createAccessToken(String accessToken) {
        return String.format(Parametres.ACCESS_TOKEN, accessToken);
    }

    protected static String createVersion() {
        return String.format(Parametres.VERSION, ApiInfo.API_VERSION);
    }

    protected static String createMethod(String method) {
        return String.format(URLs.DEV_VK_API, method);
    }

    protected static String createFile(String src){
        return String.format(Parametres.FILE, src);
    }
}
