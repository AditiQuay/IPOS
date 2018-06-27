package quay.com.ipos.ddrsales.model.response;

public class DDRBatch {
    public int batchQty;
    public String batchNumber;

    public DDRBatch(int batchQty, String batchNumber) {
        this.batchQty = batchQty;
        this.batchNumber = batchNumber;
    }
}
