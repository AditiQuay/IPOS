package quay.com.ipos.data.remote;


import java.util.List;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.compliance.constants.URLStorage;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.remote.model.AccessTokenRequest;
import quay.com.ipos.compliance.data.remote.model.AccessTokenResponse;
import quay.com.ipos.compliance.data.remote.model.ComplianceDetailsResponse;
import quay.com.ipos.compliance.data.remote.model.SynResponse;
import quay.com.ipos.data.remote.model.KCYApproveResponse;
import quay.com.ipos.data.remote.model.KycPartnerConnectResponse;
import quay.com.ipos.ddrsales.model.POSummary;
import quay.com.ipos.data.remote.model.PartnerConnectResponse;
import quay.com.ipos.data.remote.model.PartnerConnectUpdateResponse;
import quay.com.ipos.ddrsales.model.request.DDRListReq;
import quay.com.ipos.ddrsales.model.request.DDRProductReq;
import quay.com.ipos.ddrsales.model.request.POSummaryReq;
import quay.com.ipos.ddrsales.model.response.DDRProductListResponse;
import quay.com.ipos.ddrsales.model.response.GetDDRList;
import quay.com.ipos.kycPartnerConnect.KYCAcceptData;

import quay.com.ipos.partnerConnect.model.PCModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by deepak on 19/05/2017.
 */

public interface APIService {

    /***Partner Connect API***/

    @GET(IPOSAPI.PARTNER_CONNECT_API)
    Call<PartnerConnectResponse> loadPartnerConnectData(@Query("strEntityId") String strEntityId);

    @POST(IPOSAPI.PARTNER_CONNECT_UPDATE_API)
    Call<PartnerConnectUpdateResponse> updatePartnerConnectData(@Body PCModel pcModel);

    /**&&&&&Partner Connect API&&&&***/


    /***KYC API***/

    @GET(IPOSAPI.KYC_PARTNER_API)
    Call<KycPartnerConnectResponse> getKYCConnectData(@Query("strEntityId") String strEntityId, @Query("RequestCode") String requestCode);

    @POST(IPOSAPI.KYC_PARTNER_ACCEPT)
    Call<KCYApproveResponse> kycConnectUpdateDataAccept(@Body KYCAcceptData jsonObject);

    /***&&&&&KYC API&&&&&***/


    /***DDR Sales API***/

    @POST(IPOSAPI.DDR_NO_SUMMARY)
    Call<POSummary> DDR_NO_SUMMARY(@Body POSummaryReq poSummaryReq);

    @POST(IPOSAPI.DDR_GetDDRList)
    Call<GetDDRList> DDR_GetDDRList(@Body DDRListReq req);


    @POST(IPOSAPI.DDR_GetDDRProductList)
    Call<DDRProductListResponse> DDR_GetDDRProductList(@Body DDRProductReq req);


    /***&&&&&DDR Sales API&&&&&***/

    /**** Compliance API***/
    @POST(IPOSAPI.COMPLIANCE_DATA)
    Call<ComplianceDetailsResponse> loadComplianceDetail();

    @POST(URLStorage.SYNC_DATA_URL)
    Call<List<SynResponse>> syncData(@Body List<SubTask> subTaskList);

    @POST(URLStorage.LOGIN_URL)
    Call<AccessTokenResponse> loadAccessToken(@Body AccessTokenRequest accessTokenRequest);


    /***&&&&& Compliance API&&&&&***/


}
