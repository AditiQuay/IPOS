package quay.com.ipos.data.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.service.RequestTokenInterceptor;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestService {
    private static APIService getApiService(final Context context) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = SharedPrefUtil.getAccessToken(Constants.ACCESS_TOKEN.trim(), "", context.getApplicationContext());
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(IPOSAPI.BASE_URL)
                // .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        return apiService;
    }
   public  static Retrofit retrofit;
    public static APIService getApiServiceSimple() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new RequestTokenInterceptor()).build();

          retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(IPOSAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        return apiService;
    }
}
