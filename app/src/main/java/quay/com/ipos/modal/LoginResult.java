package quay.com.ipos.modal;

import java.util.List;

public class LoginResult {

    /**
     * Code : 200
     * UserAccess : {"ID":1,"UserEmailID":"niraj.kumar@quayintech.com","UserPwd":"","ActivedeviceToken":"mmqqueertyyu","AccessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJJRU1JNDM1NiIsImVtYWlsIjoibmlyYWoua3VtYXJAcXVheWludGVjaC5jb20iLCJqdGkiOiIwOWU4ZGY3OC0zMDNlLTRiNDItYTRhYS05Y2UyMzAwYTg0YmUiLCJleHAiOjE1MjcwNzU2MTMsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4Ny8iLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODcvIn0.CohKUdg0HN4OFFSwC35w6lRZVQescuRq7_R5WnNmnnM","IsActive":true,"ID1":2,"appVersion":"a2","deviceToken":"mmqqueertyyu","deviceType":"android","deviceModel":"ec890765","deviceVersion":"v1","deviceIMEI":"IEMI4356","userDeviceActive":true,"userId":"1","RoleCode":3,"WorklocationID":1,"BusinessPlaceCode":"mcd001","StoreName":"Strore 1"}
     * UserMenu : [{"userName":"M","userImage":"M","account":"My Business","key":"M","data":[]},{"userName":"P","userImage":"P","account":"My Business","key":"P","data":[{"title":"New Order","icon":"http://13.127.101.233/image/new_order.png","child":[]},{"title":"Order Center","icon":"http://13.127.101.233/image/order_center.png","child":[]},{"title":"Dashboard & Insights","icon":"http://13.127.101.233/image/insights.png","child":[]},{"title":"Product Catalogue","icon":"http://13.127.101.233/image/catalogue.png","child":[]},{"title":"Stock & Price","icon":"http://13.127.101.233/image/stock_price.png","child":[]},{"title":"Loyalty Program","icon":"http://13.127.101.233/image/loyalty.png","child":[]},{"title":"Partner Connect","icon":"http://13.127.101.233/image/partner.png","child":[]}]},{"userName":"I","userImage":"I","account":"My Business","key":"I","data":[{"title":"Mostly Used","icon":"http://13.127.101.233/image/mostly_used.png","child":[]},{"title":"Billing & Cash","icon":"http://13.127.101.233/image/biling.png","child":[{"name":"Retail Sales (OTC & Online)","icon":""},{"name":"DDR Sales (B2B)","icon":""},{"name":"Day Closure","icon":""},{"name":"Loyalty Program","icon":""},{"name":"Offer Discount","icon":""}]},{"title":"Manage Store","icon":"http://13.127.101.233/image/store.png","child":[{"name":"Inventory In/Out","icon":""},{"name":"Asset In/Out","icon":""},{"name":"Store Expenses","icon":""},{"name":"Employee Management","icon":""}]},{"title":"Manage Business","icon":"http://13.127.101.233/image/business.png","child":[{"name":"Miscellaneous Income","icon":""},{"name":"Expense & IV","icon":""},{"name":"Accounting & Settlements","icon":""},{"name":"Auto Bank Reconcillation","icon":""},{"name":"Secondary Sales Tracking","icon":""},{"name":"Compliance Tracking","icon":""}]},{"title":"Insights & Analytics","icon":"http://13.127.101.233/image/insights.png","child":[]}]}]
     */

    private int Code;
    private UserAccessBean UserAccess;
    private List<UserMenuBean> UserMenu;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public UserAccessBean getUserAccess() {
        return UserAccess;
    }

    public void setUserAccess(UserAccessBean UserAccess) {
        this.UserAccess = UserAccess;
    }

    public List<UserMenuBean> getUserMenu() {
        return UserMenu;
    }

    public void setUserMenu(List<UserMenuBean> UserMenu) {
        this.UserMenu = UserMenu;
    }

    public static class UserAccessBean {
        /**
         * ID : 1
         * UserEmailID : niraj.kumar@quayintech.com
         * UserPwd :
         * ActivedeviceToken : mmqqueertyyu
         * AccessToken : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJJRU1JNDM1NiIsImVtYWlsIjoibmlyYWoua3VtYXJAcXVheWludGVjaC5jb20iLCJqdGkiOiIwOWU4ZGY3OC0zMDNlLTRiNDItYTRhYS05Y2UyMzAwYTg0YmUiLCJleHAiOjE1MjcwNzU2MTMsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4Ny8iLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODcvIn0.CohKUdg0HN4OFFSwC35w6lRZVQescuRq7_R5WnNmnnM
         * IsActive : true
         * ID1 : 2
         * appVersion : a2
         * deviceToken : mmqqueertyyu
         * deviceType : android
         * deviceModel : ec890765
         * deviceVersion : v1
         * deviceIMEI : IEMI4356
         * userDeviceActive : true
         * userId : 1
         * RoleCode : 3
         * WorklocationID : 1
         * BusinessPlaceCode : mcd001
         * StoreName : Strore 1
         */

        private int ID;
        private String UserEmailID;
        private String UserPwd;
        private String ActivedeviceToken;
        private String AccessToken;
        private boolean IsActive;
        private int ID1;
        private String appVersion;
        private String deviceToken;
        private String deviceType;
        private String deviceModel;
        private String deviceVersion;
        private String deviceIMEI;
        private boolean userDeviceActive;
        private String userId;
        private int RoleCode;
        private int WorklocationID;
        private String BusinessPlaceCode;
        private String StoreName;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getUserEmailID() {
            return UserEmailID;
        }

        public void setUserEmailID(String UserEmailID) {
            this.UserEmailID = UserEmailID;
        }

        public String getUserPwd() {
            return UserPwd;
        }

        public void setUserPwd(String UserPwd) {
            this.UserPwd = UserPwd;
        }

        public String getActivedeviceToken() {
            return ActivedeviceToken;
        }

        public void setActivedeviceToken(String ActivedeviceToken) {
            this.ActivedeviceToken = ActivedeviceToken;
        }

        public String getAccessToken() {
            return AccessToken;
        }

        public void setAccessToken(String AccessToken) {
            this.AccessToken = AccessToken;
        }

        public boolean isIsActive() {
            return IsActive;
        }

        public void setIsActive(boolean IsActive) {
            this.IsActive = IsActive;
        }

        public int getID1() {
            return ID1;
        }

        public void setID1(int ID1) {
            this.ID1 = ID1;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
        }

        public String getDeviceVersion() {
            return deviceVersion;
        }

        public void setDeviceVersion(String deviceVersion) {
            this.deviceVersion = deviceVersion;
        }

        public String getDeviceIMEI() {
            return deviceIMEI;
        }

        public void setDeviceIMEI(String deviceIMEI) {
            this.deviceIMEI = deviceIMEI;
        }

        public boolean isUserDeviceActive() {
            return userDeviceActive;
        }

        public void setUserDeviceActive(boolean userDeviceActive) {
            this.userDeviceActive = userDeviceActive;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getRoleCode() {
            return RoleCode;
        }

        public void setRoleCode(int RoleCode) {
            this.RoleCode = RoleCode;
        }

        public int getWorklocationID() {
            return WorklocationID;
        }

        public void setWorklocationID(int WorklocationID) {
            this.WorklocationID = WorklocationID;
        }

        public String getBusinessPlaceCode() {
            return BusinessPlaceCode;
        }

        public void setBusinessPlaceCode(String BusinessPlaceCode) {
            this.BusinessPlaceCode = BusinessPlaceCode;
        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String StoreName) {
            this.StoreName = StoreName;
        }
    }

    public static class UserMenuBean {
        /**
         * userName : M
         * userImage : M
         * account : My Business
         * key : M
         * data : []
         */

        private String userName;
        private String userImage;
        private String account;
        private String key;
        private List<?> data;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<?> getData() {
            return data;
        }

        public void setData(List<?> data) {
            this.data = data;
        }
    }
}