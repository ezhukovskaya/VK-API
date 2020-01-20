package framework.utils;

import application.constants.Fields;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ApiUtils {

    static final Logger log = Logger.getLogger(ApiUtils.class);

    public static String postRequest(String url) {
        HttpResponse<String> jsonResponse = null;
        try {
            jsonResponse = Unirest.post(url).asString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResponse.getBody();
    }


    public static JsonNode postRequest(String field, String url, String fileSrc){
        HttpResponse<JsonNode> jsonResponse = null;
        try{
            jsonResponse = Unirest.post(url).field(field, new File(fileSrc)).asJson();
        } catch (Exception e){
            e.getMessage();
        }
        return jsonResponse.getBody();
    }
}