package quay.com.ipos.pss_order.modal;

/**
 * Created by ankush.bansal on 07-06-2018.
 */

public class DiscountModal {

    private double discountTotal;
    private String sDiscountName;
    private String rule;
    private String sDiscountDisplayName;
    private boolean sDiscountStrikeOut;

    public double getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(double discountTotal) {
        this.discountTotal = discountTotal;
    }

    public String getsDiscountName() {
        return sDiscountName;
    }

    public void setsDiscountName(String sDiscountName) {
        this.sDiscountName = sDiscountName;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getsDiscountDisplayName() {
        return sDiscountDisplayName;
    }

    public void setsDiscountDisplayName(String sDiscountDisplayName) {
        this.sDiscountDisplayName = sDiscountDisplayName;
    }

    public boolean issDiscountStrikeOut() {
        return sDiscountStrikeOut;
    }

    public void setsDiscountStrikeOut(boolean sDiscountStrikeOut) {
        this.sDiscountStrikeOut = sDiscountStrikeOut;
    }
}
