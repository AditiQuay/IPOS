package quay.com.ipos.inventory.modal;

/**
 * Created by ankush.bansal on 15-06-2018.
 */

public class POPaymentTerms {

    public String poPaymentTermsDetail;
    public double poPaymentTermsPer;
    public String poPaymentTermsInvoiceDue;

    public String getPoPaymentTermsDetail() {
        return poPaymentTermsDetail;
    }

    public void setPoPaymentTermsDetail(String poPaymentTermsDetail) {
        this.poPaymentTermsDetail = poPaymentTermsDetail;
    }

    public double getPoPaymentTermsPer() {
        return poPaymentTermsPer;
    }

    public void setPoPaymentTermsPer(double poPaymentTermsPer) {
        this.poPaymentTermsPer = poPaymentTermsPer;
    }

    public String getPoPaymentTermsInvoiceDue() {
        return poPaymentTermsInvoiceDue;
    }

    public void setPoPaymentTermsInvoiceDue(String poPaymentTermsInvoiceDue) {
        this.poPaymentTermsInvoiceDue = poPaymentTermsInvoiceDue;
    }
}
