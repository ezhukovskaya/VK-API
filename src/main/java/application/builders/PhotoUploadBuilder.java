package application.builders;

import application.constants.Parametres;
import application.constants.VkMethods;
import application.constants.VkMethodsName;
import application.models.VkUser;

public class PhotoUploadBuilder extends BaseBuilder {
    public static String getWallUploadRequest(String id, VkUser user) {
        return createField(VkMethodsName.PHOTOS, VkMethods.GET_WALL_UPLOAD_SERVER) + createField(Parametres.GROUP_ID, id) + createRequiredField(user);
    }

    public static String getSaveWallPhotoRequest(String photo, String ownerId, String groupId, String hash, String server, VkUser user) {
        return createField(VkMethodsName.PHOTOS, VkMethods.SAVE_WALL_PHOTO) + createField(Parametres.OWNER_ID, ownerId) + createField(Parametres.OWNER_ID, groupId) + createRequiredField(user) + createRequiredFieldForPhoto(server, hash, photo);
    }
}