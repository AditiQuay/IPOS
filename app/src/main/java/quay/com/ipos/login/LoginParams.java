package quay.com.ipos.login;

/**
 * Created by niraj.kumar on 5/3/2018.
 */

class LoginParams {
    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public void setUserPwd(String userPwd) {
        UserPwd = userPwd;
    }

    public void setUserDeviceActive(int userDeviceActive) {
        this.userDeviceActive = userDeviceActive;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String userEmailId;
    public String UserPwd;
    public int userDeviceActive;
    public String deviceIMEI;
    public String deviceVersion;
    public String deviceModel;
    public String deviceType;
    public String deviceToken;
    public String appVersion;

}
