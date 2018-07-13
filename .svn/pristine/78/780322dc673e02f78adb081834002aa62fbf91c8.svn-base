package quay.com.ipos.data.remote.model;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import quay.com.ipos.data.remote.RestService;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static APIError parseError(Response<?> response) {
        if (RestService.retrofit == null) {
            Log.e("RestService.retrofit", "null");

        }
        Converter<ResponseBody, APIError> converter =
                RestService.retrofit
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            e.printStackTrace();
            return new APIError();
        }

        return error;
    }
}
