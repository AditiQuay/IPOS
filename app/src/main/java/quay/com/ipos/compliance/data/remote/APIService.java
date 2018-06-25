package quay.com.ipos.compliance.data.remote;


import com.google.gson.JsonObject;


import java.util.List;

import quay.com.ipos.compliance.constants.URLStorage;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.remote.model.AccessTokenRequest;
import quay.com.ipos.compliance.data.remote.model.AccessTokenResponse;
import quay.com.ipos.compliance.data.remote.model.ComplianceDetailsResponse;
import quay.com.ipos.compliance.data.remote.model.SynResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by mertsimsek on 19/05/2017.
 */

public interface APIService {

    @POST(URLStorage.LOGIN_URL)
    Call<AccessTokenResponse> loadAccessToken(@Body AccessTokenRequest accessTokenRequest);

    @POST(URLStorage.USER_PROFILE_URL)
    Call<ComplianceDetailsResponse> loadComplianceDetail();


    @POST(URLStorage.USER_PROFILE_URL)
    Call<JsonObject> test();

    @GET(URLStorage.USER_PROFILE_URL_NEW)
    Call<JsonObject> testNew();

    @GET(URLStorage.USER_PROFILE_URL_NEW2)
    Call<JsonObject> testNew2();

    @POST(URLStorage.SYNC_DATA_URL)
    Call<List<SynResponse>> syncData(@Body List<SubTask> subTaskList);


}
