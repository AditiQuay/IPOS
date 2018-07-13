package quay.com.ipos.realmbean;

import org.json.JSONObject;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by niraj.kumar on 5/15/2018.
 */

public class RealmUserDetail extends RealmObject{
    /**
     * Code : 200
     * UserAccess : {"ID":1,"UserEmailID":"niraj.kumar@quayintech.com","UserPwd":"","ActivedeviceToken":"mmqqueertyyu","AccessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJJRU1JNDM1NiIsImVtYWlsIjoibmlyYWoua3VtYXJAcXVheWludGVjaC5jb20iLCJqdGkiOiI3ZTc4YjNmMS0xYTc4LTQ1ODYtOWMwYi0xYTk3OTRlOWQ0YmQiLCJleHAiOjE1MjYzODI4NzcsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4Ny8iLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODcvIn0.Ndcq0gBvkVoILCgF-fzzoEBXTuIsMi8Ug_AX-uESAU4","IsActive":true,"RoleCode":3,"ID1":2,"appVersion":"a2","deviceToken":"mmqqueertyyu","deviceType":"android","deviceModel":"ec890765","deviceVersion":"v1","deviceIMEI":"IEMI4356","userDeviceActive":true,"userId":"1"}
     * UserMenu : [{"I":[{"title":"Mostly Used","icon":"","child":[]},{"title":"Billing & Cash","icon":"","child":[{"name":"Retail Sales (OTC & Online)","icon":""},{"name":"DDR Sales (B2B)","icon":""},{"name":"Day Closure","icon":""},{"name":"Loyalty Program","icon":""},{"name":"Offer Discount","icon":""}]},{"title":"Manage Store","icon":"","child":[{"name":"Inventory In/Out","icon":""},{"name":"Asset In/Out","icon":""},{"name":"Store Expenses","icon":""},{"name":"Employee Management","icon":""}]},{"title":"Manage KycBusiness","icon":"","child":[{"name":"Miscellaneous Income","icon":""},{"name":"Expense & IV","icon":""},{"name":"Accounting & Settlements","icon":""},{"name":"Auto Bank Reconcillation","icon":""},{"name":"Secondary Sales Tracking","icon":""},{"name":"Compliance Tracking","icon":""}]},{"title":"Insights & Analytics","icon":"","child":[]}]},{"P":[{"title":"New Order","icon":"","child":[]},{"title":"Order Center","icon":"","child":[]},{"title":"Dashboard & Insights","icon":"","child":[]},{"title":"Product Catalogue","icon":"","child":[]},{"title":"Stock & Price","icon":"","child":[]},{"title":"Loyalty Program","icon":"","child":[]},{"title":"Partner Connect","icon":"","child":[]}]}]
     */
    @PrimaryKey
    private int Code;
    private String UserAccess;
    private String UserMenu;


    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getUserAccess() {
        return UserAccess;
    }

    public void setUserAccess(String userAccess) {
        UserAccess = userAccess;
    }

    public String getUserMenu() {
        return UserMenu;
    }

    public void setUserMenu(String userMenu) {
        UserMenu = userMenu;
    }
}
