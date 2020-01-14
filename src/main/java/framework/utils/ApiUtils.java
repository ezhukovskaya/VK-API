package framework.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.http.HttpHeaders;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;

public class ApiUtils {

    static final Logger log = Logger.getLogger(ApiUtils.class);

    public static JsonNode postRequest(String url, String name, String password, Object body) {
        log.info(String.format("Gets %s JSON body as String", body.toString()));
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.post(url).basicAuth(name, password).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).body(body.toString()).asJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResponse.getBody();
    }

    public static JsonNode postRequest(String url, Object object){
        log.info(String.format("Gets %s JSON body as String", object.toString()));
        HttpResponse<JsonNode> jsonResponse = null;
        try{
            jsonResponse = Unirest.post(url).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).body(object.toString()).asJson();
        } catch (Exception e){
            e.printStackTrace();
        }
        return jsonResponse.getBody();
    }
}