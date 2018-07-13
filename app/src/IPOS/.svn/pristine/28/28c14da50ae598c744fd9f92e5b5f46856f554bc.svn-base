package quay.com.ipos.ddrsales.model.request;

import quay.com.ipos.utility.Prefs;

public class DDRListReq {
   /* {

  "employeeCode": "string",
  "employeeRole": "string",
  "businessPlaceId": "work location id",
  "stateCode": "state code of work",
  "entityID": "pss allowed then entityID else NA",
  "entityType": "DDR Distributor ,Dealer , Retailer",
  "entityUserCode": "NA",
  "entityUserRole": "NA",
  "poNumber": "NA",
  "searchParam": "NA",
  "moduleType": "DDR",
  "barCodeNumber": "NA"

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


    public DDRListReq(String searchParam) {
        this.employeeCode = Prefs.getStringPrefs(employeeCode);
        this.employeeRole = Prefs.getStringPrefs(employeeRole);
        this.businessPlaceId = Prefs.getStringPrefs(businessPlaceId);
        this.stateCode = Prefs.getStringPrefs(stateCode);
       // "entityID": "pss allowed then entityID else NA",
        this.entityID = Prefs.getStringPrefs(entityID);
        this.entityType = Prefs.getStringPrefs(entityType);
        this.entityUserCode = "NA";
        this.entityUserRole = "NA";
        this.poNumber = "NA";
        this.searchParam = searchParam;
        this.moduleType = "DDR";
        this.barCodeNumber = "NA";
    }
    public DDRListReq() {
        this.employeeCode = "string";
        this.employeeRole = "string";
        this.businessPlaceId = "string";
        this.stateCode = "string";
       // "entityID": "pss allowed then entityID else NA",
        this.entityID = "string";
        this.entityType = "string";
        this.entityUserCode = "string";
        this.entityUserRole = "string";
        this.poNumber = "string";
        this.searchParam = "string";
        this.moduleType = "DDR";
        this.barCodeNumber = "string";
    }
}
