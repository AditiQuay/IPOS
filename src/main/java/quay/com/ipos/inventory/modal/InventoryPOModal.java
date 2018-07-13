package quay.com.ipos.inventory.modal;

import java.util.ArrayList;

/**
 * Created by ankush.bansal on 15-06-2018.
 */

public class InventoryPOModal {

    private String poNumber;
    private String poDate;
    private String poValidityDate;
    private double value;
    private double gstValue;
    private String poSupplierName;
    private String poSupplierAddress;
    private String getPoSupplierGSTIN;
    private String billingAddress;
    private String deliveryAddress;

    private ArrayList<ItemsDetailsModal> itemsDetailsModals;

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getPoDate() {
        return poDate;
    }

    public void setPoDate(String poDate) {
        this.poDate = poDate;
    }

    public String getPoValidityDate() {
        return poValidityDate;
    }

    public void setPoValidityDate(String poValidityDate) {
        this.poValidityDate = poValidityDate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getGstValue() {
        return gstValue;
    }

    public void setGstValue(double gstValue) {
        this.gstValue = gstValue;
    }

    public String getPoSupplierName() {
        return poSupplierName;
    }

    public void setPoSupplierName(String poSupplierName) {
        this.poSupplierName = poSupplierName;
    }

    public String getPoSupplierAddress() {
        return poSupplierAddress;
    }

    public void setPoSupplierAddress(String poSupplierAddress) {
        this.poSupplierAddress = poSupplierAddress;
    }

    public String getGetPoSupplierGSTIN() {
        return getPoSupplierGSTIN;
    }

    public void setGetPoSupplierGSTIN(String getPoSupplierGSTIN) {
        this.getPoSupplierGSTIN = getPoSupplierGSTIN;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public ArrayList<ItemsDetailsModal> getItemsDetailsModals() {
        return itemsDetailsModals;
    }

    public void setItemsDetailsModals(ArrayList<ItemsDetailsModal> itemsDetailsModals) {
        this.itemsDetailsModals = itemsDetailsModals;
    }
}


