package quay.com.ipos.data.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestService {
    public static APIService getApiService(final Context context) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
               // String token = SharedPrefUtil.getAccessToken(KeyConstants.ACCESS_TOKEN.trim(), "", context.getApplicationContext());
                Request newRequest = chain.request().newBuilder()
                       // .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(URLStorage.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        APIService apiService = retrofit.create(APIService.class);
        return apiService;
    }
    public static APIService getApiServiceSimple(final Context context) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLStorage.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        return apiService;
    }
}
