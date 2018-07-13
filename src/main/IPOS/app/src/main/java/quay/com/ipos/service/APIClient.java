package quay.com.ipos.service;

import android.content.Context;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;

/**
 * API client to make HTTP Calls
 */
public class APIClient {

    public static OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(240, TimeUnit.SECONDS);
        httpClient.addInterceptor(new RequestTokenInterceptor());
        OkHttpClient okHttpClient = httpClient.build();

        return okHttpClient;
    }

    public static Request getRequest(Context context, String url) {
        String authKey = SharedPrefUtil.getString(Constants.ACCESS_TOKEN, "", context);
        return new Request.Builder()
                .header(IPOSAPI.CONTENT_TYPE, IPOSAPI.APPLICATION_JSON)
                .header(Constants.ACCESS_TOKEN, authKey)
                .url(url)
                .build();
    }

    public static Request getKycRequest(Context context, String url) {
        String authKey = SharedPrefUtil.getString(Constants.ACCESS_TOKEN, "", context);
        return new Request.Builder()
                .header(IPOSAPI.CONTENT_TYPE, IPOSAPI.APPLICATION_JSON)
                .url(url)
                .build();
    }

    public static Request getPostRequest(Context context, String url, RequestBody requestBody) {
        String authKey = SharedPrefUtil.getString(Constants.ACCESS_TOKEN, "", context);
        return new Request.Builder()
                .header(IPOSAPI.CONTENT_TYPE, IPOSAPI.APPLICATION_JSON)
                .header(Constants.ACCESS_TOKEN, authKey)


                .url(url)
                .post(requestBody)
                .build();
    }

    public static Request getPutRequest(Context context, String url, RequestBody requestBody) {
        String authKey = SharedPrefUtil.getString(Constants.ACCESS_TOKEN, "", context);
        return new Request.Builder()
                .header(IPOSAPI.CONTENT_TYPE, IPOSAPI.APPLICATION_JSON)
                .header(Constants.ACCESS_TOKEN, authKey)
                .url(url)
                .put(requestBody)
                .build();
    }

    public static Request deleteRequest(Context context, String url) {
        String authKey = SharedPrefUtil.getString(Constants.ACCESS_TOKEN, "", context);
        return new Request.Builder()
                .header(IPOSAPI.CONTENT_TYPE, IPOSAPI.APPLICATION_JSON)
                .header(Constants.ACCESS_TOKEN, authKey)
                .url(url)
                .delete()
                .build();
    }

    /**
     * Description: Converts json params to query string param
     *
     * @param parameters Json Object
     * @return query string param
     */
    public static String buildQS(JSONObject parameters) {
        StringBuffer req = new StringBuffer();
        Iterator<String> iterator = parameters.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String val = parameters.optString(key);
            if (val != null) {
                req.append(key);
                req.append('=');
                req.append(val);
            }
            if (iterator.hasNext()) {
                req.append('&');
            }
        }
        return req.toString();
    }

}
