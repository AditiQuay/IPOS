package quay.com.ipos.inventory.modal;

/**
 * Created by niraj.kumar on 6/20/2018.
 */

public class GrnItemQtyModel {
    private String materialCode;
    private String materialName;
    private double openQty;
    private double inQty;
    private double apQty;
    private double balanceQty;

    public double getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(double isBatch) {
        this.isBatch = isBatch;
    }

    private double isBatch;
    private String gRNItemInfoDetails;

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public double getOpenQty() {
        return openQty;
    }

    public void setOpenQty(double openQty) {
        this.openQty = openQty;
    }

    public double getInQty() {
        return inQty;
    }

    public void setInQty(double inQty) {
        this.inQty = inQty;
    }

    public double getApQty() {
        return apQty;
    }

    public void setApQty(double apQty) {
        this.apQty = apQty;
    }

    public double getBalanceQty() {
        return balanceQty;
    }

    public void setBalanceQty(double balanceQty) {
        this.balanceQty = balanceQty;
    }

    public String getgRNItemInfoDetails() {
        return gRNItemInfoDetails;
    }

    public void setgRNItemInfoDetails(String gRNItemInfoDetails) {
        this.gRNItemInfoDetails = gRNItemInfoDetails;
    }
}
