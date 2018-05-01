package quay.com.ipos.customerInfo.customerInfoModal;

import java.io.Serializable;

/**
 * Created by niraj.kumar on 4/30/2018.
 */

public class CustomerInfoModal implements Serializable {
    public int id;
    public String customerName;
    public String customerEmail;
    public String customerBillingDate;
    public String customerBillingAmount;
    public String customerBirthDay;
    public String customerMobileNumber;
    public String customerPoints;

    public CustomerInfoModal(){

    }
}
