package quay.com.ipos.ddrsales.model.request;

import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;

public class POSummaryReq {
   /* {
        {
  "employeeCode": "6000015",
  "employeeRole": "user",
  "businessPlaceId": "1",
  "stateCode": "06",
  "entityID": "1",
  "entityType": "distributor",
  "entityUserCode": "NA",
  "entityUserRole": "NA",
  "poNumber": "NA",
  "searchParam": "NA",
  "moduleType": "DDR",
  "barCodeNumber": "NA"
}
    }*/

    private String employeeCode;
    private String employeeRole;
    private String businessPlaceId;
    private String stateCode;
    private String entityID;
    private String entityType;
    private String entityUserCode;
    private String entityUserRole;
    private String poNumber;
    private String searchParam;
    private String moduleType;
    private String barCodeNumber;

    public POSummaryReq() {
        this.employeeCode = Prefs.getStringPrefs(Constants.employeeCode);
        this.employeeRole =  Prefs.getStringPrefs(Constants.employeeRole);
        this.businessPlaceId = "1";
        this.stateCode = "06";
        this.entityID = "1";
        this.entityType = "distributor";
        this.entityUserCode = "NA";
        this.entityUserRole = "NA";
        this.poNumber = "NA";
        this.searchParam = "NA";
        this.moduleType = "DDR";
        this.barCodeNumber = "NA";
    }
}
