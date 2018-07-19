package quay.com.ipos.ddrsales.model.request;

import quay.com.ipos.ddrsales.model.DDR;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;

public class DDRProductReq {
   /* {

{
  "employeeCode": "6000013",
  "employeeRole": "user",
  "businessPlaceId": "1",
  "stateCode": "06",
  "entityID": "1",
  "entityType": "distributor",
  "entityUserCode": "D000001",
  "entityUserRole": "NA",
  "poNumber": "NA",
  "searchParam": "na",
  "moduleType": "ddr",
  "barCodeNumber": "all"
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


    public DDRProductReq(String searchParam) {
        this.employeeCode = Prefs.getStringPrefs(Constants.employeeCode);//"6000013";
        this.employeeRole =  Prefs.getStringPrefs(Constants.employeeRole);
        this.businessPlaceId =  Prefs.getStringPrefs(Constants.businessPlaceCode);
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
    public DDRProductReq(String searchParam, DDR  ddr,boolean isBarCode) {
        this.employeeCode = Prefs.getStringPrefs(Constants.employeeCode);//"6000013";
        this.employeeRole =  Prefs.getStringPrefs(Constants.employeeRole);
        this.businessPlaceId =  "1";//Prefs.getStringPrefs(Constants.businessPlaceCode);
        this.stateCode = "06";
        this.entityID = Prefs.getIntegerPrefs(Constants.entityCode)+"";
        this.entityType = "distributor";
        this.entityUserCode = ddr.mDDRCode;// "D000001";
        this.entityUserRole = "NA";
        this.poNumber = "NA";
        this.searchParam = searchParam;
        this.moduleType = "DDR";
          //search param should be bar code
        this.barCodeNumber = isBarCode ? searchParam : "All";
    }
}
