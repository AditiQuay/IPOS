package quay.com.ipos.partnerConnect.kyc.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by niraj.kumar on 6/16/2018.
 */

public class RealmKycDetails extends RealmObject {

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    /**
     * statusCode : 200
     * response : [{"Name":"New","data":[{"ID":"A630853A-145B-45EE-88E9-0E66F1779912","REQUEST_CODE":"18_KYC_STXrPtdl","Requestor_Code":"","Overall_Status":0,"Current_Approver":"6000014","flag":null,"Justification":"","Request_Date":"","CREATED_DATE":"","MOD_DATE":null,"BusinessPlaceCode":""}]},{"Name":"In Process","data":[{"ID":"9CD677DA-DA5D-4CC5-987E-348E02C38097","REQUEST_CODE":"18_KYC_hZQRRjZj","Requestor_Code":"6000013","Overall_Status":1,"Current_Approver":"","flag":"","Justification":"","Request_Date":"","CREATED_DATE":"","MOD_DATE":"","BusinessPlaceCode":""}]},{"Name":"Verified","data":[]},{"Name":"Rejected","data":[]},{"Name":"Error","data":[{"ID":"3BEB7C20-3532-4EF3-BC92-13AAEAA09DF6","REQUEST_CODE":"18_KYC_wZXUcwAB","Requestor_Code":"6000013","Overall_Status":6,"Current_Approver":"6000017","flag":"","Justification":"reson for duplicate","Request_Date":"","CREATED_DATE":"","MOD_DATE":null,"BusinessPlaceCode":""}]}]
     * message : success
     */
    @PrimaryKey
    private String Name;
    private String data;



    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
