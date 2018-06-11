package quay.com.ipos.partnerConnect.model;

import com.google.gson.annotations.SerializedName;

public class BillnDelivery {

    @SerializedName("entityBusinessGSTIN")
    public String mGSTIN;

    @SerializedName("entityBusinessPalceAddress")
    public String mBusinessAddress;

    @SerializedName("entityBusinessPalceName")
    public String mBusinessName;

    @SerializedName("entityContactPersonMobile")
    public String mMobile;

    @SerializedName("entityContactPerson")
    public String mContact;

    @SerializedName("entityBusinessCity")
    public String mCity;

    @SerializedName("entityAddressType")
    public String mAddressType;

    @SerializedName("entityBusinessState")
    public String mState;
}
