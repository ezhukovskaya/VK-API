package application.builders;

import application.constants.ApiInfo;
import application.constants.Fields;
import application.constants.Parametres;
import application.constants.URLs;
import application.utils.VkApiUtils;
import org.apache.log4j.Logger;

abstract public class BaseBuilder {
    private static final Logger LOG = Logger.getLogger(BaseBuilder.class);
    protected static final String AMP = "&";

    protected static String getUploadUrl(String url) {
        LOG.info(String.format("Get upload URL for %s ", url));
        return VkApiUtils.createWallUploadServer(url).get(Fields.RESPONSE).get(Fields.UPLOAD_URL).textValue();
    }

    public static String createUploadUrl(String url) {
        return getUploadUrl(url);
    }

    protected static String createOwnerId(String id) {
        return String.format(Parametres.OWNER_ID, id);
    }

    protected static String createGroupId(String id) {
        return String.format(Parametres.GROUP_ID, id);
    }

    protected static String createPhoto(String photo) {
        return String.format(Parametres.PHOTO, photo);
    }

    protected static String createField(String key, String value) {
        return String.format(key, value);
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

    protected static String createFile(String src) {
        return String.format(Parametres.FILE, src);
    }
}