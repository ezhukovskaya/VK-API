package application.builders;

import application.constants.Fields;
import application.constants.Parametres;
import application.constants.VkMethods;
import application.constants.VkMethodsName;
import application.enums.ItemType;
import application.models.VkUser;

public class LikeBuilder extends BaseBuilder {

    public static String createIsPostLikedRequest(String ownerId, String userId, int itemId, VkUser user) {
        return createField(VkMethodsName.LIKES, VkMethods.IS_LIKED) + createRequiredField(user) + createField(Parametres.OWNER_ID, ownerId) + createField(Parametres.USER_ID, userId) + createField(Parametres.ITEM_ID, itemId)
        return String.format(createMethod(String.format(VkMethodsName.LIKES, VkMethods.IS_LIKED)),
                createAccessToken(user.getAccessToken()) + AMP + createVersion() + AMP + createOwnerId(ownerId) + AMP + createUserId(userId) + AMP + createItemId(itemId) + AMP + createType(ItemType.POST.name().toLowerCase()));
    }
}