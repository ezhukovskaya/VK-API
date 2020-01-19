package application.builders;

import application.constants.Fields;
import application.constants.Parametres;
import application.constants.URLs;
import application.constants.ApiInfo;
import application.models.VkUser;
import application.utils.VkApiUtils;

abstract public class BaseBuilder {
    protected static final String AMP = "&";

    protected static String getUploadUrl(String id, VkUser vkUser) {
        return VkApiUtils.createWallUploadServer(id, vkUser).get(Fields.RESPONSE).get(Fields.UPLOAD_URL).textValue();
    }

    public static String createUploadUrl(String id, VkUser vkUser) {
        return getUploadUrl(id, vkUser);
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
