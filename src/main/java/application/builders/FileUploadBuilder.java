package application.builders;

import application.constants.Parametres;
import application.constants.VkMethods;
import application.models.VkUser;

public class FileUploadBuilder extends BaseBuilder {
    public static String createUploadUrl(String id, VkUser vkUser){
        return getUploadUrl(id, vkUser);
    }

    public static String getWallUploadRequest(String id, VkUser user) {
        return String.format(createMethod(VkMethods.GET_WALL_UPLOAD_SERVER), createGroupId(id) + AMP + createAccessToken(user.getAccessToken()) + AMP + createVersion());
    }

    public static String getSaveWallFileRequest(String file, String ownerId, String groupId, VkUser user) {
        return String.format(createMethod(VkMethods.DOCS_SAVE), createField(Parametres.FILE, file) + AMP + createOwnerId(ownerId) + AMP + createGroupId(groupId) + AMP + createAccessToken(user.getAccessToken()) + AMP + createVersion() + AMP);
    }
}
