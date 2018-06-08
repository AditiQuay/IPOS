package quay.com.ipos.partnerConnect.model;

import java.util.List;

public class Account {
    public String entityAcountHolderName;
    public String entityAcountNo;
    public String entityAcountType;
    public String entityBankName;
    public String entityBankIFSCode;
    public String entityBranchAdddres;
    public boolean isSecurityCheques;

    public List<Cheques> cheques;






   /* "Account": {
        "entityAcountHolderName": "K.G. Traders",
                "entityAcountNo": "0000998821340",
                "entityAcountType": "Saving",
                "entityBankName": "Bank Of baroda",
                "entityBankIFSCode": "BB0001267",
                "entityBranchAdddres": "New Gurgaon",
                "isSecurityCheques": true,
                "cheques": [
        {
                 "ID": 1,
                "EntityBankAcHoderID": 3,
                "entitydrawnAccount": "1234567896",
                "chequeNo": "9991237",
                "MaxLimitAmount": 50000
        },
        {
            "ID": 2,
                "EntityBankAcHoderID": 3,
                "entitydrawnAccount": "7774523901",
                "chequeNo": "8881432",
                "MaxLimitAmount": 10000
        }
            ]
    },*/
}
