package quay.com.ipos.data.remote.model;

import quay.com.ipos.partnerConnect.model.PCModel;

public class PartnerConnectResponse {
    public int statusCode;
    public int error;
    public String errorDescription;
    public String message;
    public PCModel response;

}
