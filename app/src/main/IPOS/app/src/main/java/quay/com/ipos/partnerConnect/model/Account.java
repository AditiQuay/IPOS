package quay.com.ipos.partnerConnect.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Account {

    public int BankDetailRowId;
    public int EntityID;


    @SerializedName("entityAcountHolderName")
    public String mAccountHolderName;

    @SerializedName("entityAcountNo")
    public String mAccountNo;

    @SerializedName("entityAcountType")
    public String mAccountType;

    @SerializedName("entityBankName")
    public String mBankName;

    @SerializedName("entityBankIFSCode")
    public String mIFSCCode;

    @SerializedName("entityBranchAdddres")
    public String mBranchAdddres;

    @SerializedName("isSecurityCheques")
    public boolean isSecurityCheques;

    @SerializedName("cheques")
    public List<Cheques> cheques;

}
