package application.builders;

import application.constants.ApiInfo;
import application.constants.Fields;
import application.constants.Parametres;
import application.models.VkUser;
import application.utils.VkApiUtils;
import org.apache.log4j.Logger;

abstract public class BaseBuilder {
    private static final Logger LOG = Logger.getLogger(BaseBuilder.class);

    public static String getUploadUrl(String url) {
        LOG.info(String.format("Get upload URL for %s ", url));
        return VkApiUtils.createWallUploadServer(url).get(Fields.RESPONSE).get(Fields.UPLOAD_URL).textValue();
    }

    protected static String createRequiredField(VkUser vkUser) {
        return createField(Fields.ACCESS_TOKEN, vkUser.getAccessToken()) + createField(Fields.VERSION, ApiInfo.API_VERSION);
    }

    protected static String createRequiredFieldForPhoto(String server, String hash, String photo) {
        return createField(Parametres.SERVER, server) + createField(Parametres.HASH, server) + createField(Parametres.PHOTO, photo);
    }

    protected static String createField(String key, String value) {
        return new ParamsBuilder(key, value).toString();
    }

}