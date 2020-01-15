package framework.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.log4j.Logger;

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
}