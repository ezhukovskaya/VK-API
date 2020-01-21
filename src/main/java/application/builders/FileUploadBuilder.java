package application.builders;

import application.constants.Parametres;
import application.constants.VkMethods;
import application.constants.VkMethodsName;
import application.models.VkUser;

public class FileUploadBuilder extends BaseBuilder {
    public static String getWallUploadRequest(VkUser user) {
        return String.format(createMethod(String.format(VkMethodsName.DOCS, VkMethods.GET_WALL_UPLOAD_SERVER)), createAccessToken(user.getAccessToken()) + AMP + createVersion());
    }

    public static String getSaveWallFileRequest(String file,  VkUser user) {
        return String.format(createMethod(String.format(VkMethodsName.DOCS, VkMethods.SAVE)), createField(Parametres.FILE, file)  + AMP + createAccessToken(user.getAccessToken()) + AMP + createVersion() + AMP);
    }
}
