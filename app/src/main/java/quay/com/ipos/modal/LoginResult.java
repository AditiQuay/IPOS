package quay.com.ipos.modal;

import java.util.List;

public class LoginResult {
    /**
     * Code : 200
     * UserAccess : {"ID":1,"UserEmailID":"niraj.kumar@quayintech.com","UserPwd":"","ActivedeviceToken":"mmqqueertyyu","AccessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJJRU1JNDM1NiIsImVtYWlsIjoibmlyYWoua3VtYXJAcXVheWludGVjaC5jb20iLCJqdGkiOiJjNzQyN2VhYy03OTExLTQwMGYtYjJmMy04ODVlMjFhMmY3YzkiLCJleHAiOjE1MjY0NTM0MjgsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4Ny8iLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODcvIn0.CUtcV-meAZKE7pAwYzik7qH7j7ck-BH8eogaJyqUcZY","IsActive":true,"RoleCode":3,"ID1":2,"appVersion":"a2","deviceToken":"mmqqueertyyu","deviceType":"android","deviceModel":"ec890765","deviceVersion":"v1","deviceIMEI":"IEMI4356","userDeviceActive":true,"userId":"1"}
     * UserMenu : [{"key":"I","data":[{"title":"Mostly Used","icon":"","child":[]},{"title":"Billing & Cash","icon":"","child":[{"name":"Retail Sales (OTC & Online)","icon":""},{"name":"DDR Sales (B2B)","icon":""},{"name":"Day Closure","icon":""},{"name":"Loyalty Program","icon":""},{"name":"Offer Discount","icon":""}]},{"title":"Manage Store","icon":"","child":[{"name":"Inventory In/Out","icon":""},{"name":"Asset In/Out","icon":""},{"name":"Store Expenses","icon":""},{"name":"Employee Management","icon":""}]},{"title":"Manage Business","icon":"","child":[{"name":"Miscellaneous Income","icon":""},{"name":"Expense & IV","icon":""},{"name":"Accounting & Settlements","icon":""},{"name":"Auto Bank Reconcillation","icon":""},{"name":"Secondary Sales Tracking","icon":""},{"name":"Compliance Tracking","icon":""}]},{"title":"Insights & Analytics","icon":"","child":[]}]},{"key":"P","data":[{"title":"New Order","icon":"","child":[]},{"title":"Order Center","icon":"","child":[]},{"title":"Dashboard & Insights","icon":"","child":[]},{"title":"Product Catalogue","icon":"","child":[]},{"title":"Stock & Price","icon":"","child":[]},{"title":"Loyalty Program","icon":"","child":[]},{"title":"Partner Connect","icon":"","child":[]}]}]
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
         * AccessToken : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJJRU1JNDM1NiIsImVtYWlsIjoibmlyYWoua3VtYXJAcXVheWludGVjaC5jb20iLCJqdGkiOiJjNzQyN2VhYy03OTExLTQwMGYtYjJmMy04ODVlMjFhMmY3YzkiLCJleHAiOjE1MjY0NTM0MjgsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4Ny8iLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODcvIn0.CUtcV-meAZKE7pAwYzik7qH7j7ck-BH8eogaJyqUcZY
         * IsActive : true
         * RoleCode : 3
         * ID1 : 2
         * appVersion : a2
         * deviceToken : mmqqueertyyu
         * deviceType : android
         * deviceModel : ec890765
         * deviceVersion : v1
         * deviceIMEI : IEMI4356
         * userDeviceActive : true
         * userId : 1
         */

        private int ID;
        private String UserEmailID;
        private String UserPwd;
        private String ActivedeviceToken;
        private String AccessToken;
        private boolean IsActive;
        private int RoleCode;
        private int ID1;
        private String appVersion;
        private String deviceToken;
        private String deviceType;
        private String deviceModel;
        private String deviceVersion;
        private String deviceIMEI;
        private boolean userDeviceActive;
        private String userId;

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

        public int getRoleCode() {
            return RoleCode;
        }

        public void setRoleCode(int RoleCode) {
            this.RoleCode = RoleCode;
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
    }

    public static class UserMenuBean {
        /**
         * key : I
         * data : [{"title":"Mostly Used","icon":"","child":[]},{"title":"Billing & Cash","icon":"","child":[{"name":"Retail Sales (OTC & Online)","icon":""},{"name":"DDR Sales (B2B)","icon":""},{"name":"Day Closure","icon":""},{"name":"Loyalty Program","icon":""},{"name":"Offer Discount","icon":""}]},{"title":"Manage Store","icon":"","child":[{"name":"Inventory In/Out","icon":""},{"name":"Asset In/Out","icon":""},{"name":"Store Expenses","icon":""},{"name":"Employee Management","icon":""}]},{"title":"Manage Business","icon":"","child":[{"name":"Miscellaneous Income","icon":""},{"name":"Expense & IV","icon":""},{"name":"Accounting & Settlements","icon":""},{"name":"Auto Bank Reconcillation","icon":""},{"name":"Secondary Sales Tracking","icon":""},{"name":"Compliance Tracking","icon":""}]},{"title":"Insights & Analytics","icon":"","child":[]}]
         */

        private String key;
        private List<DataBean> data;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * title : Mostly Used
             * icon :
             * child : []
             */

            private String title;
            private String icon;
            private List<?> child;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public List<?> getChild() {
                return child;
            }

            public void setChild(List<?> child) {
                this.child = child;
            }
        }
    }
}