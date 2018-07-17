package quay.com.ipos.ddrsales.model.response;

import java.io.Serializable;

public class DDRIncoTerm  implements Serializable{
    public int grnIncoId;
    public String grnIncoDetail;
    public boolean grnPayBySender;
    public boolean grnPayByReceiver;
    public double grnPayAmount;
}
