package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import framework.utils.ApiUtils;
import models.VkUser;
import models.WallPost;

public class VkApiUtils {
    private static JsonNode getResponse(String name, String password, String url, Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            String json = objectMapper.writeValueAsString(object);
            com.mashape.unirest.http.JsonNode response = ApiUtils.postRequest(url, name, password, json);
            return objectMapper.readValue(response.toString(), JsonNode.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static JsonNode createWallPost(VkUser vkUser, String url, String token, String version, String message){
        WallPost wallPost = new WallPost(vkUser.getId(), message, token, version);
        return getResponse(vkUser.getUsername(), vkUser.getPassword(), url, wallPost);
    }
}
