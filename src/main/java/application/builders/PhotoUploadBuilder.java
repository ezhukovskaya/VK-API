package application.builders;

import application.constants.Fields;
import application.constants.Parametres;
import application.constants.VkMethods;
import application.utils.VkApiUtils;
import application.models.VkUser;

public class PhotoUploadBuilder extends BaseBuilder {

    private static String getUploadUrl(String id, VkUser vkUser) {
        return VkApiUtils.createWallUploadServer(id, vkUser).get(Fields.RESPONSE).get(Fields.UPLOAD_URL).textValue();
    }

    public static String createUploadUrl(String id, VkUser vkUser) {
        return getUploadUrl(id, vkUser);
    }

    public static String getPhotoField(String url, String fileSrc){
        return VkApiUtils.photoResponse(url, fileSrc).get(Fields.PHOTO).toString();
    }

    public static String getWallUploadRequest(String id, VkUser user) {
        return String.format(createMethod(VkMethods.GET_WALL_UPLOAD_SERVER), createGroupId(id) + AMP + createAccessToken(user.getAccessToken()) + AMP + createVersion());
    }

    public static String getSaveWallPhotoRequest(String photo, VkUser user){
        return String.format(createMethod(VkMethods.SAVE_WALL_PHOTO), createPhoto(photo) + AMP + createAccessToken(user.getAccessToken())+ AMP + createVersion());
    }

}
