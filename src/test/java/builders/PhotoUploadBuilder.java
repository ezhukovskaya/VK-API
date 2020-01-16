package builders;

import constants.Fields;
import constants.Parametres;
import constants.VkMethods;
import models.VkUser;
import utils.VkApiUtils;

public class PhotoUploadBuilder extends BaseBuilder {
    private static String createPhoto(String fileSrc){
        return String.format(Parametres.PHOTO, fileSrc);
    }

    private static String getUploadUrl(String id, VkUser vkUser) {
        return VkApiUtils.createWallUploadServer(id, vkUser).get(Fields.RESPONSE).get(Fields.UPLOAD_URL).textValue();
    }

    public static String createUploadUrl(String id, String fileSrc, VkUser vkUser) {
        return getUploadUrl(id, vkUser)+AMP+createPhoto(fileSrc);
    }

    private static String getPhoto(String id, String fileSrc, VkUser vkUser) {
        return VkApiUtils.createPhotoUpload(id, fileSrc, vkUser).get(Fields.PHOTO).toString();
    }

    public static String getSaveWallPhotoRequest(String id, String fileSrc, VkUser user){
        return String.format(createMethod(VkMethods.SAVE_WALL_PHOTO), getPhoto(id, fileSrc, user) + AMP + createAccessToken(user.getAccessToken()) + AMP + createVersion());
    }

    public static String getPhotoFromSavePhotoRequest(String id, String fileSrc, VkUser vkUser){
        return VkApiUtils.createSaveWallPhoto(id, fileSrc, vkUser).get(Fields.RESPONSE).get(Fields.PHOTO).toString();
    }

    public static String getWallUploadRequest(String id, VkUser user) {
        return String.format(createMethod(VkMethods.GET_WALL_UPLOAD_SERVER), createGroupId(id) + AMP + createAccessToken(user.getAccessToken()) + AMP + createVersion());
    }


}
