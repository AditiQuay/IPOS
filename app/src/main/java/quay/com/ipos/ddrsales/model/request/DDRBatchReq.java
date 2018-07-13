package quay.com.ipos.ddrsales.model.request;

import quay.com.ipos.ddrsales.model.DDR;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;

public class DDRBatchReq {
   /* {

 {
  "employeeCode": "600001",
  "employeeRole": "user",
  "businessPlaceId": "1",
  "stateCode": "string",
  "entityID": "string",
  "entityType": "string",
  "entityUserCode": "string",
  "entityUserRole": "string",
  "poNumber": "BAVISTIN 2LT",
  "searchParam": "string",
  "moduleType": "DDR",
  "barCodeNumber": "NA"
}

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


    public DDRBatchReq(String productId, DDR ddr) {
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
        this.barCodeNumber ="NA";
    }
}
