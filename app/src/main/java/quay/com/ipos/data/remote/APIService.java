package quay.com.ipos.data.remote;


import quay.com.ipos.data.remote.model.PartnerConnectResponse;
import quay.com.ipos.partnerConnect.model.PCModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mertsimsek on 19/05/2017.
 */

public interface APIService {

    @GET(URLStorage.PATNER_CONNECT_API)
    Call<PartnerConnectResponse> loadPartnerConnectData();



}
