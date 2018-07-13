package quay.com.ipos.ddrsales.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DDR implements Serializable {
    /*"code": "D000001",
            "name": "K.G. M Traders",
            "townName": "Behruch",
            "cityName": "Bharatpur",
            "crLimit": 40000,
            "nonDue": 0,
            "overDue": 0,
            "alertFlag": false
    */


    @SerializedName("code")
    public String mDDRCode;
    @SerializedName("name")
    public String mDDRName;
    @SerializedName("townName")
    public String mDDRAddress1;
    @SerializedName("cityName")
    public String mDDRAddress2;
    @SerializedName("crLimit")
    public double mDDRCreditLimit;
    @SerializedName("nonDue")
    public double mDDRNonDue;
    @SerializedName("overDue")
    public double mDDROverDue;
    @SerializedName("alertFlag")
    public boolean alertFlag;


    public DDR(String mDDRCode, String mDDRName, String mDDRAddress1, String mDDRAddress2, double mDDRCreditLimit, double mDDRNonDue, double mDDROverDue) {
        this.mDDRCode = mDDRCode;
        this.mDDRName = mDDRName;
        this.mDDRAddress1 = mDDRAddress1;
        this.mDDRAddress2 = mDDRAddress2;
        this.mDDRCreditLimit = mDDRCreditLimit;
        this.mDDRNonDue = mDDRNonDue;
        this.mDDROverDue = mDDROverDue;
    }
}
