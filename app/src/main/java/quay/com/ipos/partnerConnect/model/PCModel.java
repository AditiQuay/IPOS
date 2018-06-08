package quay.com.ipos.partnerConnect.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PCModel {

    public String RelationShipName;
    public String psslastUpdated;
    public Relationship Relationship;

    public Business Business;
    public  Contact Contact;
    public List<DocumentVoults> DocumentVoults;
    @SerializedName("ddd")
    public List<BillandDelivery> BillandDelivery;
    public Account Account;


}
