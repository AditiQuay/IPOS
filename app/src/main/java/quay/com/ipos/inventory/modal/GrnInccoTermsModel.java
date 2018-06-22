package quay.com.ipos.inventory.modal;

/**
 * Created by niraj.kumar on 6/20/2018.
 */

public class GrnInccoTermsModel {
    private String grnIncoDetail;
    private boolean grnPayBySender;
    private boolean grnPayByReceiver;
    private double grnPayAmount;

    public String getGrnIncoDetail() {
        return grnIncoDetail;
    }

    public void setGrnIncoDetail(String grnIncoDetail) {
        this.grnIncoDetail = grnIncoDetail;
    }

    public boolean isGrnPayBySender() {
        return grnPayBySender;
    }

    public void setGrnPayBySender(boolean grnPayBySender) {
        this.grnPayBySender = grnPayBySender;
    }

    public boolean isGrnPayByReceiver() {
        return grnPayByReceiver;
    }

    public void setGrnPayByReceiver(boolean grnPayByReceiver) {
        this.grnPayByReceiver = grnPayByReceiver;
    }

    public double getGrnPayAmount() {
        return grnPayAmount;
    }

    public void setGrnPayAmount(double grnPayAmount) {
        this.grnPayAmount = grnPayAmount;
    }
}
