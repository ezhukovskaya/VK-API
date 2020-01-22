package application.builders;

import application.constants.ApiInfo;
import application.constants.Fields;
import application.constants.Parametres;
import application.models.VkUser;
import application.utils.VkApiUtils;
import org.apache.log4j.Logger;

abstract public class BaseBuilder {
    private static final Logger LOG = Logger.getLogger(BaseBuilder.class);
    protected static final String AMP = "&";

    public static String createUploadUrl(String url) {
        LOG.info(String.format("Get upload URL for %s ", url));
        return VkApiUtils.createWallUploadServer(url).get(Fields.RESPONSE).get(Fields.UPLOAD_URL).textValue();
    }

    protected static String createRequiredField(VkUser vkUser) {
        return createField(Fields.ACCESS_TOKEN, vkUser.getAccessToken()) + createField(Fields.VERSION, ApiInfo.API_VERSION);
    }

    protected static String createField(String key, String value) {
        return new ParamsBuilder(key, value).toString();
    }

    protected static String createVersion() {
        return String.format(Parametres.VERSION, ApiInfo.API_VERSION);
    }

}