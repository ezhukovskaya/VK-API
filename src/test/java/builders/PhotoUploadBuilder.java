package builders;

import constants.Fields;
import constants.VkMethods;
import utils.VkApiUtils;

public class PhotoUploadBuilder extends BaseBuilder {
    private static String getUploadUrl(String id) {
        return VkApiUtils.createWallUploadServer(id).get(Fields.RESPONSE).get(Fields.UPLOAD_URL).toString()+"%s";
    }

    public static String createUploadUrl(String id, String fileSrc) {
        System.out.println(String.format(getUploadUrl(id), AMP + createFile(fileSrc)));
        return String.format(getUploadUrl(id), AMP + createFile(fileSrc));
    }

    public static String getPhoto(String id, String fileSrc) {
        return VkApiUtils.createPhotoUpload(id, fileSrc).get(Fields.PHOTO).toString();
    }

    public static String getURL(String id) {
        return String.format(createMethod(VkMethods.GET_WALL_UPLOAD_SERVER), createGroupId(id) + AMP + createAccessToken() + AMP + createVersion());
    }


}
