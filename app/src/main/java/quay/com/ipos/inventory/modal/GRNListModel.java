package quay.com.ipos.inventory.modal;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by niraj.kumar on 6/19/2018.
 */

public class GRNListModel {

    private String poNumber;
    private String poStatus;
    private int poItemQty;
    private int poGRNQty;
    private int poAPQty;
    private int poBalanceQty;
    private boolean qcVisible;

    private String grnNumber;
    private String grnStatus;
    private String grnDate;
    private int grnQty;
    private int grnAPQty;
    private int grnValue;
    private boolean isAttachment;
    private boolean isAction;

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getPoStatus() {
        return poStatus;
    }

    public void setPoStatus(String poStatus) {
        this.poStatus = poStatus;
    }

    public int getPoItemQty() {
        return poItemQty;
    }

    public void setPoItemQty(int poItemQty) {
        this.poItemQty = poItemQty;
    }

    public int getPoGRNQty() {
        return poGRNQty;
    }

    public void setPoGRNQty(int poGRNQty) {
        this.poGRNQty = poGRNQty;
    }

    public int getPoAPQty() {
        return poAPQty;
    }

    public void setPoAPQty(int poAPQty) {
        this.poAPQty = poAPQty;
    }

    public int getPoBalanceQty() {
        return poBalanceQty;
    }

    public void setPoBalanceQty(int poBalanceQty) {
        this.poBalanceQty = poBalanceQty;
    }

    public boolean isQcVisible() {
        return qcVisible;
    }

    public void setQcVisible(boolean qcVisible) {
        this.qcVisible = qcVisible;
    }

    public String getGrnNumber() {
        return grnNumber;
    }

    public void setGrnNumber(String grnNumber) {
        this.grnNumber = grnNumber;
    }

    public String getGrnStatus() {
        return grnStatus;
    }

    public void setGrnStatus(String grnStatus) {
        this.grnStatus = grnStatus;
    }

    public String getGrnDate() {
        return grnDate;
    }

    public void setGrnDate(String grnDate) {
        this.grnDate = grnDate;
    }

    public int getGrnQty() {
        return grnQty;
    }

    public void setGrnQty(int grnQty) {
        this.grnQty = grnQty;
    }

    public int getGrnAPQty() {
        return grnAPQty;
    }

    public void setGrnAPQty(int grnAPQty) {
        this.grnAPQty = grnAPQty;
    }

    public int getGrnValue() {
        return grnValue;
    }

    public void setGrnValue(int grnValue) {
        this.grnValue = grnValue;
    }

    public boolean isAttachment() {
        return isAttachment;
    }

    public void setAttachment(boolean attachment) {
        isAttachment = attachment;
    }

    public boolean isAction() {
        return isAction;
    }

    public void setAction(boolean action) {
        isAction = action;
    }
}
