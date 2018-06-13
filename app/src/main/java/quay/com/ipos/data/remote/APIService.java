package quay.com.ipos.data.remote;


import org.json.JSONObject;

import quay.com.ipos.data.remote.model.PartnerConnectResponse;
import quay.com.ipos.data.remote.model.PartnerConnectUpdateResponse;
import quay.com.ipos.partnerConnect.model.PCModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mertsimsek on 19/05/2017.
 */

public interface APIService {

    @GET(URLStorage.PARTNER_CONNECT_API)
    Call<PartnerConnectResponse> loadPartnerConnectData(@Query("strEntityId") String strEntityId);


    @POST(URLStorage.PARTNER_CONNECT_UPDATE_API)
    Call<PartnerConnectUpdateResponse> updatePartnerConnectData(@Body PCModel pcModel);

    @POST(URLStorage.PARTNER_CONNECT_UPDATE_API)
    Call<JSONObject> updatePartnerConnectData1(@Body PCModel pcModel);


}
