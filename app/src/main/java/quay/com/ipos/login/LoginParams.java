package quay.com.ipos.login;

/**
 * Created by niraj.kumar on 5/3/2018.
 */

class LoginParams {
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String deviceId;
    public String email;
}
