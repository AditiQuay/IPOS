package quay.com.ipos.partnerConnect.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class KeyBusinessInfo {
    public String sync;
    public int id;

    @NonNull
    @SerializedName("partnerType")
    public String mPartnerType;

    @SerializedName("CompaneyName")
    public String mCompanyName;
    @SerializedName("CIN")
    public String mCIN;
    @SerializedName("PAN")
    public String mPAN;
    @SerializedName("keyContactPerson")
    public String mContactPerson;
    @SerializedName("keyContactPosition")
    public String mContactPosition;


    // private List<BusinessLocation> BusinessLocation;
    public BusinessLocation BusinessLocation;

    public int getId() {
        return id;
    }


    /* "partnerType": "Principal",
             "mCompanyName": "K.G. Traders",
             "CIN": "",
             "PAN": "",
             "keyContactPerson": "Deepak Kumar",
             "keyContactPosition": "Traders Description",
             "BusinessLocation": {
        "businessState": "Haryana",
                "businessCity": "Gurugram",
                "businessPINCode": "122022",
                "BusinessZone": null
    }*/
}
