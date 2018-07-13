package quay.com.ipos.inventory.modal;

/**
 * Created by ankush.bansal on 15-06-2018.
 */

public class POIncoTerms {


    public String poIncoDetail;
    public boolean poPayBySender;
    public boolean poPayByReceiver;
    public double poPayAmount;

    public String getPoIncoDetail() {
        return poIncoDetail;
    }

    public void setPoIncoDetail(String poIncoDetail) {
        this.poIncoDetail = poIncoDetail;
    }

    public boolean isPoPayBySender() {
        return poPayBySender;
    }

    public void setPoPayBySender(boolean poPayBySender) {
        this.poPayBySender = poPayBySender;
    }

    public boolean isPoPayByReceiver() {
        return poPayByReceiver;
    }

    public void setPoPayByReceiver(boolean poPayByReceiver) {
        this.poPayByReceiver = poPayByReceiver;
    }

    public double getPoPayAmount() {
        return poPayAmount;
    }

    public void setPoPayAmount(double poPayAmount) {
        this.poPayAmount = poPayAmount;
    }
}
