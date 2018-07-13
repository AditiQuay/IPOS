package quay.com.ipos.customerInfo.customerInfoModal;

/**
 * Created by niraj.kumar on 6/5/2018.
 */

public class CustomerServerRequestModel {

    /**
     * LocalID : 0
     * ServerId : 0
     * StatusCode : 200
     * Message : Success
     */

    private String LocalID;
    private String ServerId;
    private String StatusCode;
    private String Message;

    public String getLocalID() {
        return LocalID;
    }

    public void setLocalID(String LocalID) {
        this.LocalID = LocalID;
    }

    public String getServerId() {
        return ServerId;
    }

    public void setServerId(String ServerId) {
        this.ServerId = ServerId;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String StatusCode) {
        this.StatusCode = StatusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
