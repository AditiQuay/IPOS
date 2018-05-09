package quay.com.ipos.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResult {

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("UserAccess")
    @Expose
    private List<UserAccess> userAccess = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<UserAccess> getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(List<UserAccess> userAccess) {
        this.userAccess = userAccess;
    }

    public class UserAccess {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("UserEmailID")
        @Expose
        private String userEmailID;
        @SerializedName("UserPwd")
        @Expose
        private String userPwd;
        @SerializedName("ActivedeviceToken")
        @Expose
        private String activedeviceToken;
        @SerializedName("AccessToken")
        @Expose
        private String accessToken;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("RoleCode")
        @Expose
        private Integer roleCode;
        @SerializedName("ID1")
        @Expose
        private Integer iD1;
        @SerializedName("appVersion")
        @Expose
        private String appVersion;
        @SerializedName("deviceToken")
        @Expose
        private String deviceToken;
        @SerializedName("deviceType")
        @Expose
        private String deviceType;
        @SerializedName("deviceModel")
        @Expose
        private String deviceModel;
        @SerializedName("deviceVersion")
        @Expose
        private String deviceVersion;
        @SerializedName("deviceIMEI")
        @Expose
        private String deviceIMEI;
        @SerializedName("userDeviceActive")
        @Expose
        private Boolean userDeviceActive;
        @SerializedName("userId")
        @Expose
        private String userId;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getUserEmailID() {
            return userEmailID;
        }

        public void setUserEmailID(String userEmailID) {
            this.userEmailID = userEmailID;
        }

        public String getUserPwd() {
            return userPwd;
        }

        public void setUserPwd(String userPwd) {
            this.userPwd = userPwd;
        }

        public String getActivedeviceToken() {
            return activedeviceToken;
        }

        public void setActivedeviceToken(String activedeviceToken) {
            this.activedeviceToken = activedeviceToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public Integer getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(Integer roleCode) {
            this.roleCode = roleCode;

        }

        public Integer getID1() {
            return iD1;
        }

        public void setID1(Integer iD1) {
            this.iD1 = iD1;
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

        public Boolean getUserDeviceActive() {
            return userDeviceActive;
        }

        public void setUserDeviceActive(Boolean userDeviceActive) {
            this.userDeviceActive = userDeviceActive;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

    }
}