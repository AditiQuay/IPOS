package quay.com.ipos.compliance.data.remote.model;

public class AccessTokenRequest {
    public String appVersion;
    public String deviceToken;
    public String deviceType;
    public String deviceModel;
    public String deviceVersion;
    public String deviceIMEI;
    public String userDeviceActive;
    public String UserEmailID;
    public String UserPwd;
    public String AccessToken;

    public AccessTokenRequest(String appVersion, String deviceToken, String deviceType, String deviceModel, String deviceVersion, String deviceIMEI, String userDeviceActive, String userEmailID, String userPwd, String accessToken) {
        this.appVersion = appVersion;
        this.deviceToken = deviceToken;
        this.deviceType = deviceType;
        this.deviceModel = deviceModel;
        this.deviceVersion = deviceVersion;
        this.deviceIMEI = deviceIMEI;
        this.userDeviceActive = userDeviceActive;
        UserEmailID = userEmailID;
        UserPwd = userPwd;
        AccessToken = accessToken;
    }

    public AccessTokenRequest(boolean isDefault){
        this("1","","Android","MotoG3","21","12121212","1","niraj.kumar@quayintech.com","niraj@123","");
    }

}
