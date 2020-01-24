package application.builders;

import application.constants.*;
import application.models.VkUser;
import application.utils.VkApiUtils;
import org.apache.log4j.Logger;

abstract public class BaseBuilder {
    private static final Logger LOG = Logger.getLogger(BaseBuilder.class);

    public static String getDev(String param) {
        return URLs.DEV_VK_API + param;
    }

    public static String getUploadUrl(String url) {
        LOG.info(String.format("Get upload URL for %s ", url));
        return VkApiUtils.createWallUploadServer(url).get(Fields.RESPONSE).get(Fields.UPLOAD_URL).textValue();
    }

    protected static String createRequiredField(VkUser vkUser) {
        return createField(Parameters.ACCESS_TOKEN, vkUser.getAccessToken()) + createField(Parameters.VERSION, ApiInfo.API_VERSION);
    }

    protected static String createRequiredFieldForPhoto(String server, String hash, String photo) {
        return createField(Parameters.SERVER, server) + createField(Parameters.HASH, hash) + createField(Parameters.PHOTO, photo);
    }

    protected static String createField(String key, String value) {
        return new ParamsBuilder(key, value).toString();
    }

    public static String getWallUploadRequest(VkUser user) {
        return getDev(VkMethodsName.DOCS + VkMethods.GET_WALL_UPLOAD_SERVER + createRequiredField(user));
    }

    public static String getSaveWallFileRequest(String file, VkUser user) {
        return getDev(VkMethodsName.DOCS + VkMethods.SAVE + createField(Parameters.FILE, file) + createRequiredField(user));
    }

    public static String createIsPostLikedRequest(String ownerId, String userId, String itemId, VkUser user, String type) {
        return getDev(VkMethodsName.LIKES + VkMethods.IS_LIKED) + createRequiredField(user) + createField(Parameters.OWNER_ID, ownerId) + createField(Parameters.USER_ID, userId) + createField(Parameters.ITEM_ID, itemId) + createField(Parameters.TYPE, type);
    }

    public static String getWallUploadRequest(String id, VkUser user) {
        return getDev(VkMethodsName.PHOTOS + VkMethods.GET_WALL_UPLOAD_SERVER + createField(Parameters.GROUP_ID, id) + createRequiredField(user));
    }

    public static String getSaveWallPhotoRequest(String photo, String ownerId, String groupId, String hash, String server, VkUser user) {
        return getDev(VkMethodsName.PHOTOS + VkMethods.SAVE_WALL_PHOTO + createField(Parameters.OWNER_ID, ownerId) + createField(Parameters.OWNER_ID, groupId) + createRequiredField(user) + createRequiredFieldForPhoto(server, hash, photo));
    }

}