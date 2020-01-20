package application.builders;

import application.constants.Parametres;
import application.constants.VkMethods;
import application.constants.VkMethodsName;
import application.models.VkUser;

public class PhotoUploadBuilder extends BaseBuilder {
    public static String getWallUploadRequest(String id, VkUser user) {
        return String.format(createMethod(String.format(VkMethodsName.PHOTOS, VkMethods.GET_WALL_UPLOAD_SERVER)), createGroupId(id) + AMP + createAccessToken(user.getAccessToken()) + AMP + createVersion());
    }

    public static String getSaveWallPhotoRequest(String photo, String ownerId, String groupId, String hash, String server, VkUser user) {
        return String.format(createMethod(String.format(VkMethodsName.PHOTOS, VkMethods.SAVE_WALL_PHOTO)), createPhoto(photo) + AMP + createOwnerId(ownerId) + AMP + createGroupId(groupId) + AMP + createAccessToken(user.getAccessToken()) + AMP + createVersion() + AMP + createField(Parametres.HASH, hash) + AMP + createField(Parametres.SERVER, server));
    }
}