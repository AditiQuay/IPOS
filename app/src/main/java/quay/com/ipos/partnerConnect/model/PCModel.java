package quay.com.ipos.partnerConnect.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PCModel {

    public String empCode;
    public int EntityID;//": 1
    @SerializedName("UpdatedDate")
    public String psslastUpdated;
    //null,, 5 , 3, 6, show  Updated show     else show---(1,0)(not updated)
    public String PrevReqStatus;
    public String PrevReqStatusMessage;


    /* "UpdatedDate": "2018-06-23T20:52:52.94",
             "PrevReqStatusMessage": "Verified",
             "PrevReqStatus": "5"*/

    public String RelationShipName;
    public Relationship Relationship;

    public Business Business;


    @SerializedName("Contact")
    public Contact Contact;

    @SerializedName("BillandDelivery")
    public List<BillnDelivery> BillandDelivery;


    public List<Account> Account;


    @SerializedName("DocumentVoults")
    public List<DocumentVoults> DocumentVoults;


    public void setLog(PCModel value) {
        this.empCode = value.empCode;
        this.EntityID = value.EntityID;
        this.psslastUpdated = value.psslastUpdated;
        this.RelationShipName = value.RelationShipName;
        this.Relationship = value.Relationship;
        this.Business = value.Business;
        this.Contact = value.Contact;
        this.BillandDelivery = value.BillandDelivery;
        this.Account = value.Account;

    }

    public boolean shouldShowBottom() {
        if (PrevReqStatus == null) {
            return true;
        }
        if (PrevReqStatus.contains("5") || PrevReqStatus.contains("3") || PrevReqStatus.contains("6")) {
            return true;
        }

        return false;
    }
}
