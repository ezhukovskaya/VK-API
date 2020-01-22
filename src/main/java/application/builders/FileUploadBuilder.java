package application.builders;

import application.constants.Parametres;
import application.constants.VkMethods;
import application.constants.VkMethodsName;
import application.models.VkUser;

public class FileUploadBuilder extends BaseBuilder {
    public static String getWallUploadRequest(VkUser user) {
        return createField(VkMethodsName.DOCS, VkMethods.GET_WALL_UPLOAD_SERVER) + createRequiredField(user);
    }

    public static String getSaveWallFileRequest(String file, VkUser user) {
        return createField(VkMethodsName.DOCS, VkMethods.SAVE) + createField(Parametres.FILE, file) + createRequiredField(user);
    }
}
