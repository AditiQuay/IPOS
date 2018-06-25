package quay.com.ipos.service;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
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
        return chain.proceed(newRequest);
    }
}
