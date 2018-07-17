package quay.com.ipos.ddrsales.model.response;

import java.io.Serializable;

public class DDRBatch implements Serializable {

    public String number;
    public String actionTitle="NA";
    public int actionID=0;
    public int qty;
    public DDRBatch(int batchQty, String batchNumber) {
        this.qty = batchQty;
        this.number = batchNumber;
    }
}
