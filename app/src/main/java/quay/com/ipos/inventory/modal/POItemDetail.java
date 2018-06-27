package quay.com.ipos.inventory.modal;

/**
 * Created by ankush.bansal on 15-06-2018.
 */

public class POItemDetail {
    private String title;
    public int poItemQty;
    public double poItemUnitPrice;
    public double poItemAmount;
    public double poItemIGSTValue;
    public double poItemCGSTValue;
    public double poItemSGSTValue;
    public double poItemIGSTPer;
    public double poItemCGSTPer;
    public double poItemSGSTPer;

    public int getPoItemQty() {
        return poItemQty;
    }

    public void setPoItemQty(int poItemQty) {
        this.poItemQty = poItemQty;
    }

    public double getPoItemUnitPrice() {
        return poItemUnitPrice;
    }

    public void setPoItemUnitPrice(double poItemUnitPrice) {
        this.poItemUnitPrice = poItemUnitPrice;
    }

    public double getPoItemAmount() {
        return poItemAmount;
    }

    public void setPoItemAmount(double poItemAmount) {
        this.poItemAmount = poItemAmount;
    }

    public double getPoItemIGSTValue() {
        return poItemIGSTValue;
    }

    public void setPoItemIGSTValue(double poItemIGSTValue) {
        this.poItemIGSTValue = poItemIGSTValue;
    }

    public double getPoItemCGSTValue() {
        return poItemCGSTValue;
    }

    public void setPoItemCGSTValue(double poItemCGSTValue) {
        this.poItemCGSTValue = poItemCGSTValue;
    }

    public double getPoItemSGSTValue() {
        return poItemSGSTValue;
    }

    public void setPoItemSGSTValue(double poItemSGSTValue) {
        this.poItemSGSTValue = poItemSGSTValue;
    }

    public double getPoItemIGSTPer() {
        return poItemIGSTPer;
    }

    public void setPoItemIGSTPer(double poItemIGSTPer) {
        this.poItemIGSTPer = poItemIGSTPer;
    }

    public double getPoItemCGSTPer() {
        return poItemCGSTPer;
    }

    public void setPoItemCGSTPer(double poItemCGSTPer) {
        this.poItemCGSTPer = poItemCGSTPer;
    }

    public double getPoItemSGSTPer() {
        return poItemSGSTPer;
    }

    public void setPoItemSGSTPer(double poItemSGSTPer) {
        this.poItemSGSTPer = poItemSGSTPer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}