package quay.com.ipos.partnerConnect.model;

import com.google.gson.annotations.SerializedName;

public class Cheques {
    public int ID;
    public String EntityBankAcHoderID;

   // public String mSecurityCheque;

    @SerializedName("entitydrawnAccount")
    public String mDrawnAccountNo;

    @SerializedName("chequeNo")
    public String mChequeNo;

    @SerializedName("MaxLimitAmount")
    public String mMaxLimitAmount;

    public String CreateDate;
    public String CreatedBy;
    public String ModifiedBy;
    public String ModifiedDate;



}
