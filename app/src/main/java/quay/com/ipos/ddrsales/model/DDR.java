package quay.com.ipos.ddrsales.model;

import java.io.Serializable;

public class DDR  implements Serializable{
    public String mDDRCode;
    public String mDDRName;
    public String mDDRAddress1;
    public String mDDRAddress2;
    public double mDDRCreditLimit;
    public double mDDRNonDue;
    public double mDDROverDue;


    public DDR(String mDDRCode, String mDDRName, String mDDRAddress1, String mDDRAddress2, double mDDRCreditLimit, double mDDRNonDue, double mDDROverDue) {
        this.mDDRCode = mDDRCode;
        this.mDDRName = mDDRName;
        this.mDDRAddress1 = mDDRAddress1;
        this.mDDRAddress2 = mDDRAddress2;
        this.mDDRCreditLimit = mDDRCreditLimit;
        this.mDDRNonDue = mDDRNonDue;
        this.mDDROverDue = mDDROverDue;
    }
}
