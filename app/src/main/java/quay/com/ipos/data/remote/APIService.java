package quay.com.ipos.data.remote;


import org.json.JSONObject;

import quay.com.ipos.data.remote.model.PartnerConnectResponse;
import quay.com.ipos.data.remote.model.PartnerConnectUpdateResponse;
import quay.com.ipos.kycPartnerConnect.KYCAcceptData;
import quay.com.ipos.partnerConnect.model.KycCardResponse;
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

    @GET(URLStorage.KYC_PARTNER_API)
    Call<PartnerConnectResponse> kycConnectData(@Query("strEntityId") String strEntityId,@Query("RequestCode")String requestCode);

    @GET(URLStorage.KYC_PARTNER_API)
    Call<JSONObject> kycConnectData1(@Query("strEntityId") String strEntityId,@Query("RequestCode")String requestCode);

    @POST(URLStorage.KYC_PARTNER_ACCEPT)
    Call<PartnerConnectUpdateResponse> kycConnectUpdateData(@Body KYCAcceptData jsonObject);


    @POST(URLStorage.PARTNER_CONNECT_UPDATE_API)
    Call<PartnerConnectUpdateResponse> updatePartnerConnectData(@Body PCModel pcModel);



    @POST(URLStorage.PARTNER_CONNECT_UPDATE_API)
    Call<PartnerConnectUpdateResponse> updateKycConnectionData(@Body PCModel pcModel);


}
