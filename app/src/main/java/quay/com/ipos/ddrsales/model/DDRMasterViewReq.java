package quay.com.ipos.ddrsales.model;

import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;

public class DDRMasterViewReq {

   /* {
        "employeeCode": "string",
            "employeeRole": "string",
            "businessPlaceId": "string",
            "stateCode": "string",
            "entityID": "string",
            "entityType": "string",
            "entityUserCode": "string",
            "entityUserRole": "string",
            "poNumber": "string",
            "searchParam": "string",
            "moduleType": "string",
            "barCodeNumber": "string"
    }*/

    public String employeeCode;
    public String employeeRole;
    public String businessPlaceId;
    public String stateCode;
    public String entityID;
    public String entityType;
    public String entityUserCode;
    public String entityUserRole;
    public String poNumber;
    public String searchParam;
    public String moduleType;
    public String barCodeNumber;


    public DDRMasterViewReq(DDR ddr) {
        this.employeeCode = Prefs.getStringPrefs(Constants.employeeCode);
        this.employeeRole = Prefs.getStringPrefs(Constants.employeeRole);
        this.businessPlaceId = "1";
        this.stateCode = "06";
        this.entityID = ddr.mDDRCode;
        this.entityType = "user";
        this.entityUserCode = "NA";
        this.entityUserRole = "NA";
        this.poNumber = "NA";
        this.searchParam = "NA";
        this.moduleType = "DDR";
        this.barCodeNumber = "NA";
    }
}
