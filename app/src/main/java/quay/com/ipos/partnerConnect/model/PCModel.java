package quay.com.ipos.partnerConnect.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PCModel {
    public int EntityID;//": 1
    public String psslastUpdated;

    public String RelationShipName;
    public Relationship Relationship;

    public Business Business;


    @SerializedName("Contact")
    public Contact contactDetail;

    @SerializedName("BillandDelivery")
    public List<BillnDelivery> BillandDelivery;


    public List<Account> Account;



    @SerializedName("DocumentVoults")
    public List<DocumentVoults> DocumentVoults;


    public void setLog(PCModel value) {
        this.EntityID = value.EntityID;
        this.Relationship = value.Relationship;
        this.Business = value.Business;
        this.contactDetail = value.contactDetail;
        this.BillandDelivery = value.BillandDelivery;
        this.Account = value.Account;

    }
}
