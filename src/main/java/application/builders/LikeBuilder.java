package application.builders;

import application.constants.Parametres;
import application.constants.VkMethods;
import application.constants.VkMethodsName;
import application.models.VkUser;

public class LikeBuilder extends BaseBuilder {

    public static String createIsPostLikedRequest(String ownerId, String userId, String itemId, VkUser user) {
        return createField(VkMethodsName.LIKES, VkMethods.IS_LIKED) + createRequiredField(user) + createField(Parametres.OWNER_ID, ownerId) + createField(Parametres.USER_ID, userId) + createField(Parametres.ITEM_ID, itemId);
    }
}