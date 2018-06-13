package quay.com.ipos.partnerConnect.model;

import com.google.gson.annotations.SerializedName;

public class BillnDelivery {
    @SerializedName("entityAddressType")
    public String mAddressType;

    @SerializedName("entityBusinessPalceName")
    public String mBusinessType;


    @SerializedName("entityBusinessGSTIN")
    public String mGSTIN;

    @SerializedName("entityBusinessPalceAddress")
    public String mBusinessAddress;



    @SerializedName("entityContactPersonMobile")
    public String mMobile;

    @SerializedName("entityContactPerson")
    public String mContactPerson;

    @SerializedName("entityBusinessCity")
    public String mCity;



    @SerializedName("entityBusinessState")
    public String mState;
}
