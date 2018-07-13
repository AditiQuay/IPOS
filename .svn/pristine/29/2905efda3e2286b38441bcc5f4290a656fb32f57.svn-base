package quay.com.ipos.data.remote.model;

import quay.com.ipos.partnerConnect.model.KeyValuePairs;
import quay.com.ipos.partnerConnect.model.KycCardModel;
import quay.com.ipos.partnerConnect.model.PCModel;

public class PartnerConnectResponse {

    public int statusCode;
    public int error;
    public String errorDescription;
    public String message;
    public PCModel response;
    public KeyValuePairs keyValuePairs;


    public void setDefaultValue() {
        if (keyValuePairs == null) {
            keyValuePairs = new KeyValuePairs();
            keyValuePairs.setDefaultValue();
        }
    }

}
