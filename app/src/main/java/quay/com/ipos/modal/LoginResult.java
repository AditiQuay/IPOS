package quay.com.ipos.modal;

import java.util.List;

public class LoginResult {

    /**
     * Code : 200
     * UserAccess : {"ID":1,"UserEmailID":"niraj.kumar@quayintech.com","UserPwd":"","ActivedeviceToken":"cnBCzrqNzOg:APA91bH1iB5LHy9HeeHt65y7B2YSSq1bC1yjK01NPfw_FuoEV15QCRVRQ26gLX6qbIkg2vJCW6-JsV6CmjTDKY6zr30u07A7RFuQ2KK7RfRXn-TTs37EDunntODoeR-yGPHQfixpo3z3","AccessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIwMDAwMDAwMDAwMDAwMDAiLCJlbWFpbCI6Im5pcmFqLmt1bWFyQHF1YXlpbnRlY2guY29tIiwianRpIjoiYmE1YTVkMDItNTFjOC00ZDMxLThiZmQtZTRkNWI1MTczZmFjIiwiZXhwIjoxNTc5NDQ5Nzg4LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODcvIiwiYXVkIjoiaHR0cDovL2xvY2FsaG9zdDo4MDg3LyJ9.iOA1PK5CNMLchjIQvFjJVJ2i6PHnZ7ovpDcCjMu3z3s","IsActive":true,"AccessTo":"PSS","deviceToken":"cnBCzrqNzOg:APA91bH1iB5LHy9HeeHt65y7B2YSSq1bC1yjK01NPfw_FuoEV15QCRVRQ26gLX6qbIkg2vJCW6-JsV6CmjTDKY6zr30u07A7RFuQ2KK7RfRXn-TTs37EDunntODoeR-yGPHQfixpo3z3","EmpCode":"6000014","RoleCode":3,"WorklocationID":2,"BusinessPlaceCode":"Store002","StoreName":"Store 2","UserRole":"User","EntityId":1,"PrincipalName":"Mccoy","PrincipalId":1,"EntityName":"K.G. Traders","worklocations":[{"ID":2,"BusinessPlaceCode":"Store002","LocationName":"Store 2","Address1":"Sector -23","Address2":"Sector -23A","LocationCity":"Shalimar Bag","LocationState":"Delhi","LocationPIN":"110088","LocationPhone1":"7456745646","LocationPhone2":"7456745643","GeoTaggingLong":"76.510156","LocationCharge1":20,"LocationCharge2":51,"LocationWorkHoursFrom":"19:00:00","LocationWorkHoursTo":"20:00:00","LocationEmail1":"mcd@gmail.com","LocationEmail2":"mcd2@gmail.com","CreateDate":"2018-05-10T11:59:27.16","ModifiedDate":"2018-05-10T11:59:27.16","Active":true,"LocationStateCode":"06","EntityIdRef":1}]}
     * UserMenu : [{"userName":"M","userImage":"M","account":"My KycBusiness","key":"M","data":[]},{"userName":"P","userImage":"P","account":"My KycBusiness","key":"P","data":[{"title":"New Order","icon":"http://13.127.101.233/image/new_order.png","child":[]},{"title":"Order Center","icon":"http://13.127.101.233/image/order_center.png","child":[]},{"title":"Dashboard & Insights","icon":"http://13.127.101.233/image/insights.png","child":[]},{"title":"Product Catalogue","icon":"http://13.127.101.233/image/catalogue.png","child":[]},{"title":"Stock & Price","icon":"http://13.127.101.233/image/stock_price.png","child":[]},{"title":"Loyalty Program","icon":"http://13.127.101.233/image/loyalty.png","child":[]},{"title":"Partner Connect","icon":"http://13.127.101.233/image/partner.png","child":[]}]},{"userName":"I","userImage":"I","account":"My KycBusiness","key":"I","data":[{"title":"Mostly Used","icon":"http://13.127.101.233/image/mostly_used.png","child":[]},{"title":"Billing & Cash","icon":"http://13.127.101.233/image/biling.png","child":[{"name":"Retail Sales (OTC & Online)","icon":""},{"name":"DDR Sales (B2B)","icon":""},{"name":"Day Closure","icon":""},{"name":"Loyalty Program","icon":""},{"name":"Offer Discount","icon":""}]},{"title":"Manage Store","icon":"http://13.127.101.233/image/store.png","child":[{"name":"Inventory In/Out","icon":""},{"name":"Asset In/Out","icon":""},{"name":"Store Expenses","icon":""},{"name":"Employee Management","icon":""}]},{"title":"Manage KycBusiness","icon":"http://13.127.101.233/image/business.png","child":[{"name":"Miscellaneous Income","icon":""},{"name":"Expense & IV","icon":""},{"name":"Accounting & Settlements","icon":""},{"name":"Auto Bank Reconcillation","icon":""},{"name":"Secondary Sales Tracking","icon":""},{"name":"Compliance Tracking","icon":""}]},{"title":"Insights & Analytics","icon":"http://13.127.101.233/image/insights.png","child":[]}]}]
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
         * ActivedeviceToken : cnBCzrqNzOg:APA91bH1iB5LHy9HeeHt65y7B2YSSq1bC1yjK01NPfw_FuoEV15QCRVRQ26gLX6qbIkg2vJCW6-JsV6CmjTDKY6zr30u07A7RFuQ2KK7RfRXn-TTs37EDunntODoeR-yGPHQfixpo3z3
         * AccessToken : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIwMDAwMDAwMDAwMDAwMDAiLCJlbWFpbCI6Im5pcmFqLmt1bWFyQHF1YXlpbnRlY2guY29tIiwianRpIjoiYmE1YTVkMDItNTFjOC00ZDMxLThiZmQtZTRkNWI1MTczZmFjIiwiZXhwIjoxNTc5NDQ5Nzg4LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODcvIiwiYXVkIjoiaHR0cDovL2xvY2FsaG9zdDo4MDg3LyJ9.iOA1PK5CNMLchjIQvFjJVJ2i6PHnZ7ovpDcCjMu3z3s
         * IsActive : true
         * AccessTo : PSS
         * deviceToken : cnBCzrqNzOg:APA91bH1iB5LHy9HeeHt65y7B2YSSq1bC1yjK01NPfw_FuoEV15QCRVRQ26gLX6qbIkg2vJCW6-JsV6CmjTDKY6zr30u07A7RFuQ2KK7RfRXn-TTs37EDunntODoeR-yGPHQfixpo3z3
         * EmpCode : 6000014
         * RoleCode : 3
         * WorklocationID : 2
         * BusinessPlaceCode : Store002
         * StoreName : Store 2
         * UserRole : User
         * EntityId : 1
         * PrincipalName : Mccoy
         * PrincipalId : 1
         * EntityName : K.G. Traders
         * worklocations : [{"ID":2,"BusinessPlaceCode":"Store002","LocationName":"Store 2","Address1":"Sector -23","Address2":"Sector -23A","LocationCity":"Shalimar Bag","LocationState":"Delhi","LocationPIN":"110088","LocationPhone1":"7456745646","LocationPhone2":"7456745643","GeoTaggingLong":"76.510156","LocationCharge1":20,"LocationCharge2":51,"LocationWorkHoursFrom":"19:00:00","LocationWorkHoursTo":"20:00:00","LocationEmail1":"mcd@gmail.com","LocationEmail2":"mcd2@gmail.com","CreateDate":"2018-05-10T11:59:27.16","ModifiedDate":"2018-05-10T11:59:27.16","Active":true,"LocationStateCode":"06","EntityIdRef":1}]
         */

        private int ID;
        private String UserEmailID;
        private String UserPwd;
        private String ActivedeviceToken;
        private String AccessToken;
        private boolean IsActive;
        private String AccessTo;
        private String deviceToken;
        private String EmpCode;
        private int RoleCode;
        private int WorklocationID;
        private String BusinessPlaceCode;
        private String StoreName;
        private String UserRole;
        private int EntityId;
        private String PrincipalName;
        private int PrincipalId;
        private String EntityName;
        private List<WorklocationsBean> worklocations;

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

        public String getAccessTo() {
            return AccessTo;
        }

        public void setAccessTo(String AccessTo) {
            this.AccessTo = AccessTo;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getEmpCode() {
            return EmpCode;
        }

        public void setEmpCode(String EmpCode) {
            this.EmpCode = EmpCode;
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

        public String getUserRole() {
            return UserRole;
        }

        public void setUserRole(String UserRole) {
            this.UserRole = UserRole;
        }

        public int getEntityId() {
            return EntityId;
        }

        public void setEntityId(int EntityId) {
            this.EntityId = EntityId;
        }

        public String getPrincipalName() {
            return PrincipalName;
        }

        public void setPrincipalName(String PrincipalName) {
            this.PrincipalName = PrincipalName;
        }

        public int getPrincipalId() {
            return PrincipalId;
        }

        public void setPrincipalId(int PrincipalId) {
            this.PrincipalId = PrincipalId;
        }

        public String getEntityName() {
            return EntityName;
        }

        public void setEntityName(String EntityName) {
            this.EntityName = EntityName;
        }

        public List<WorklocationsBean> getWorklocations() {
            return worklocations;
        }

        public void setWorklocations(List<WorklocationsBean> worklocations) {
            this.worklocations = worklocations;
        }

        public static class WorklocationsBean {
            /**
             * ID : 2
             * BusinessPlaceCode : Store002
             * LocationName : Store 2
             * Address1 : Sector -23
             * Address2 : Sector -23A
             * LocationCity : Shalimar Bag
             * LocationState : Delhi
             * LocationPIN : 110088
             * LocationPhone1 : 7456745646
             * LocationPhone2 : 7456745643
             * GeoTaggingLong : 76.510156
             * LocationCharge1 : 20
             * LocationCharge2 : 51
             * LocationWorkHoursFrom : 19:00:00
             * LocationWorkHoursTo : 20:00:00
             * LocationEmail1 : mcd@gmail.com
             * LocationEmail2 : mcd2@gmail.com
             * CreateDate : 2018-05-10T11:59:27.16
             * ModifiedDate : 2018-05-10T11:59:27.16
             * Active : true
             * LocationStateCode : 06
             * EntityIdRef : 1
             */

            private int ID;
            private String BusinessPlaceCode;
            private String LocationName;
            private String Address1;
            private String Address2;
            private String LocationCity;
            private String LocationState;
            private String LocationPIN;
            private String LocationPhone1;
            private String LocationPhone2;
            private String GeoTaggingLong;
            private int LocationCharge1;
            private int LocationCharge2;
            private String LocationWorkHoursFrom;
            private String LocationWorkHoursTo;
            private String LocationEmail1;
            private String LocationEmail2;
            private String CreateDate;
            private String ModifiedDate;
            private boolean Active;
            private String LocationStateCode;
            private int EntityIdRef;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getBusinessPlaceCode() {
                return BusinessPlaceCode;
            }

            public void setBusinessPlaceCode(String BusinessPlaceCode) {
                this.BusinessPlaceCode = BusinessPlaceCode;
            }

            public String getLocationName() {
                return LocationName;
            }

            public void setLocationName(String LocationName) {
                this.LocationName = LocationName;
            }

            public String getAddress1() {
                return Address1;
            }

            public void setAddress1(String Address1) {
                this.Address1 = Address1;
            }

            public String getAddress2() {
                return Address2;
            }

            public void setAddress2(String Address2) {
                this.Address2 = Address2;
            }

            public String getLocationCity() {
                return LocationCity;
            }

            public void setLocationCity(String LocationCity) {
                this.LocationCity = LocationCity;
            }

            public String getLocationState() {
                return LocationState;
            }

            public void setLocationState(String LocationState) {
                this.LocationState = LocationState;
            }

            public String getLocationPIN() {
                return LocationPIN;
            }

            public void setLocationPIN(String LocationPIN) {
                this.LocationPIN = LocationPIN;
            }

            public String getLocationPhone1() {
                return LocationPhone1;
            }

            public void setLocationPhone1(String LocationPhone1) {
                this.LocationPhone1 = LocationPhone1;
            }

            public String getLocationPhone2() {
                return LocationPhone2;
            }

            public void setLocationPhone2(String LocationPhone2) {
                this.LocationPhone2 = LocationPhone2;
            }

            public String getGeoTaggingLong() {
                return GeoTaggingLong;
            }

            public void setGeoTaggingLong(String GeoTaggingLong) {
                this.GeoTaggingLong = GeoTaggingLong;
            }

            public int getLocationCharge1() {
                return LocationCharge1;
            }

            public void setLocationCharge1(int LocationCharge1) {
                this.LocationCharge1 = LocationCharge1;
            }

            public int getLocationCharge2() {
                return LocationCharge2;
            }

            public void setLocationCharge2(int LocationCharge2) {
                this.LocationCharge2 = LocationCharge2;
            }

            public String getLocationWorkHoursFrom() {
                return LocationWorkHoursFrom;
            }

            public void setLocationWorkHoursFrom(String LocationWorkHoursFrom) {
                this.LocationWorkHoursFrom = LocationWorkHoursFrom;
            }

            public String getLocationWorkHoursTo() {
                return LocationWorkHoursTo;
            }

            public void setLocationWorkHoursTo(String LocationWorkHoursTo) {
                this.LocationWorkHoursTo = LocationWorkHoursTo;
            }

            public String getLocationEmail1() {
                return LocationEmail1;
            }

            public void setLocationEmail1(String LocationEmail1) {
                this.LocationEmail1 = LocationEmail1;
            }

            public String getLocationEmail2() {
                return LocationEmail2;
            }

            public void setLocationEmail2(String LocationEmail2) {
                this.LocationEmail2 = LocationEmail2;
            }

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String CreateDate) {
                this.CreateDate = CreateDate;
            }

            public String getModifiedDate() {
                return ModifiedDate;
            }

            public void setModifiedDate(String ModifiedDate) {
                this.ModifiedDate = ModifiedDate;
            }

            public boolean isActive() {
                return Active;
            }

            public void setActive(boolean Active) {
                this.Active = Active;
            }

            public String getLocationStateCode() {
                return LocationStateCode;
            }

            public void setLocationStateCode(String LocationStateCode) {
                this.LocationStateCode = LocationStateCode;
            }

            public int getEntityIdRef() {
                return EntityIdRef;
            }

            public void setEntityIdRef(int EntityIdRef) {
                this.EntityIdRef = EntityIdRef;
            }
        }
    }

    public static class UserMenuBean {
        /**
         * userName : M
         * userImage : M
         * account : My KycBusiness
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