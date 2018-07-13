package quay.com.ipos.service;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.modal.GlobalSettings;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.SharedPrefUtil;

public class RequestTokenInterceptor  implements Interceptor {
    private static final String TAG =RequestTokenInterceptor.class.getSimpleName() ;

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Request request = chain.request();
        okhttp3.Request newRequest;

        String accessToken = SharedPrefUtil.getAccessToken(Constants.ACCESS_TOKEN.trim(), "", IPOSApplication.getContext());
        accessToken = "Bearer " + accessToken.trim();
        GlobalSettings globalSettings = new Gson().fromJson(Prefs.getStringPrefs(Constants.globalSettings), GlobalSettings.class);
        Log.d(TAG, "GlobalSettings " + new Gson().toJson(globalSettings));
        Log.d(TAG, "accessToken " + accessToken);
        newRequest = request.newBuilder()
                .addHeader("Authorization", accessToken)
                .addHeader("GlobalSettings", new Gson().toJson(globalSettings))
                .build();
         return chain.proceed(newRequest);//old code

   /*   //  Request request = chain.request();
        okhttp3.Response response = chain.proceed(request);

        // todo deal with the issues the way you need to
        if (response.code() == 400) {
            Log.e(TAG, "" + response.message());

            return response;
        }

        return response;*/

    }
}
