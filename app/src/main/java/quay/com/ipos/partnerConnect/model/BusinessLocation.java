package quay.com.ipos.partnerConnect.model;

import com.google.gson.annotations.SerializedName;

public class BusinessLocation {
    @SerializedName("businessPINCode")
    public String mPINCode;
    @SerializedName("BusinessZone")
    public String mZone;
    @SerializedName("businessState")
    public String mState;
    @SerializedName("businessCity")
    public String mCity;


}
