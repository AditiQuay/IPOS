package quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by niraj.kumar on 7/10/2018.
 */

public class RealmTransferDetail extends RealmObject {

    /**
     * grnNumber : GRN00000002
     * receivedDate : 6/14/2018 3:09:22 PM
     * totalItems : 2
     * value : 6660000
     * poQty : 66600
     * openQty : 0
     * balanceQty : 66600
     * transporterName :
     * transporterLRName :
     * transporterTruckNumber :
     * transporterEWayBillNumber :
     * transporterEWayBillValidityDate :
     * transporterDriverName :
     * transporterDriverMobileNumber :
     * transporterAddress :
     * poItemDetails : [{"materialCode":"BLLO2KG","materialName":"BILLO 2KG","openQty":0,"inQty":0,"apQty":0,"balanceQty":33300,"gRNItemInfoDetails":{"tabList":[{"tabTitle":"Normal","tabId":1},{"tabTitle":"Defect","tabId":2},{"tabTitle":"Others","tabId":3},{"tabTitle":"Missing / Short-Landed Units","tabId":4},{"tabTitle":"Broken","tabId":5},{"tabTitle":"Torn or Leaking Containers","tabId":6},{"tabTitle":"Damaged Units (wet, crushed, etc.)","tabId":7},{"tabTitle":"Empty and Light Units","tabId":8}],"actionList":[{"actionTitle":"Copy to Defect bucket","actionID":9},{"actionTitle":"Move to Defect bucket","actionID":10},{"actionTitle":"Delete from the list","actionID":11},{"actionTitle":"Immediate Reject & Sent","actionID":12},{"actionTitle":"Mark it for Quality Inspection ","actionID":13},{"actionTitle":"Inititate Return Process","actionID":14},{"actionTitle":"Return with no action","actionID":15}],"data":[]}},{"materialCode":"BLLO3KG","materialName":"BILLO 3KG","openQty":0,"inQty":0,"apQty":0,"balanceQty":33300,"gRNItemInfoDetails":{"tabList":[{"tabTitle":"Normal","tabId":1},{"tabTitle":"Defect","tabId":2},{"tabTitle":"Others","tabId":3},{"tabTitle":"Missing / Short-Landed Units","tabId":4},{"tabTitle":"Broken","tabId":5},{"tabTitle":"Torn or Leaking Containers","tabId":6},{"tabTitle":"Damaged Units (wet, crushed, etc.)","tabId":7},{"tabTitle":"Empty and Light Units","tabId":8}],"actionList":[{"actionTitle":"Copy to Defect bucket","actionID":9},{"actionTitle":"Move to Defect bucket","actionID":10},{"actionTitle":"Delete from the list","actionID":11},{"actionTitle":"Immediate Reject & Sent","actionID":12},{"actionTitle":"Mark it for Quality Inspection ","actionID":13},{"actionTitle":"Inititate Return Process","actionID":14},{"actionTitle":"Return with no action","actionID":15}],"data":[]}}]
     * poIncoTerms : [{"grnIncoDetail":"Loading","grnPayBySender":true,"grnPayByReceiver":false,"grnPayAmount":999},{"grnIncoDetail":"Shipping","grnPayBySender":true,"grnPayByReceiver":false,"grnPayAmount":9999},{"grnIncoDetail":"Unload","grnPayBySender":true,"grnPayByReceiver":false,"grnPayAmount":999},{"grnIncoDetail":"Toll","grnPayBySender":true,"grnPayByReceiver":false,"grnPayAmount":999},{"grnIncoDetail":"E-Way Bill","grnPayBySender":true,"grnPayByReceiver":false,"grnPayAmount":999},{"grnIncoDetail":"Unload 1","grnPayBySender":false,"grnPayByReceiver":true,"grnPayAmount":999}]
     * poPaymentTermsType : MilestoneBased
     * poPaymentTerms : [{"grnPaymentTermsDetail":"After Deleivery","grnPaymentTermsPer":50,"grnPaymentTermsInvoiceDue":"30"}]
     * poTermsAndConditions : [{"grnTermsAndConditionSrNo":1,"grnTermsAndConditionDetail":"Lorem Ipsum is simply dummy "},{"grnTermsAndConditionSrNo":2,"grnTermsAndConditionDetail":"psum is simply dummy "}]
     * poAttachments : [{"grnAttachmentName":"Bavistin","grnAttachmentUrl":"http://13.127.101.233/Image/469673.png","grnAttachmentType":"png"}]
     */

    @PrimaryKey
    private String grnNumber;
    private String poNumber;
    private String receivedDate;
    private double totalItems;
    private double value;
    private double poQty;
    private double openQty;
    private double balanceQty;
    private String transporterName;
    private String transporterLRName;
    private String transporterTruckNumber;
    private String transporterEWayBillNumber;
    private String transporterEWayBillValidityDate;
    private String transporterDriverName;
    private String transporterDriverMobileNumber;
    private String transporterAddress;
    private String poPaymentTermsType;
    private String poItemDetails;
    private String poIncoTerms;
    private String poPaymentTerms;
    private String poTermsAndConditions;
    private String poAttachments;
    private String employeeCode;

    public String getGrnNumber() {
        return grnNumber;
    }

    public void setGrnNumber(String grnNumber) {
        this.grnNumber = grnNumber;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public double getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getPoQty() {
        return poQty;
    }

    public void setPoQty(int poQty) {
        this.poQty = poQty;
    }

    public double getOpenQty() {
        return openQty;
    }

    public void setOpenQty(int openQty) {
        this.openQty = openQty;
    }

    public double getBalanceQty() {
        return balanceQty;
    }

    public void setBalanceQty(int balanceQty) {
        this.balanceQty = balanceQty;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName;
    }

    public String getTransporterLRName() {
        return transporterLRName;
    }

    public void setTransporterLRName(String transporterLRName) {
        this.transporterLRName = transporterLRName;
    }

    public String getTransporterTruckNumber() {
        return transporterTruckNumber;
    }

    public void setTransporterTruckNumber(String transporterTruckNumber) {
        this.transporterTruckNumber = transporterTruckNumber;
    }

    public String getTransporterEWayBillNumber() {
        return transporterEWayBillNumber;
    }

    public void setTransporterEWayBillNumber(String transporterEWayBillNumber) {
        this.transporterEWayBillNumber = transporterEWayBillNumber;
    }

    public String getTransporterEWayBillValidityDate() {
        return transporterEWayBillValidityDate;
    }

    public void setTransporterEWayBillValidityDate(String transporterEWayBillValidityDate) {
        this.transporterEWayBillValidityDate = transporterEWayBillValidityDate;
    }

    public String getTransporterDriverName() {
        return transporterDriverName;
    }

    public void setTransporterDriverName(String transporterDriverName) {
        this.transporterDriverName = transporterDriverName;
    }

    public String getTransporterDriverMobileNumber() {
        return transporterDriverMobileNumber;
    }

    public void setTransporterDriverMobileNumber(String transporterDriverMobileNumber) {
        this.transporterDriverMobileNumber = transporterDriverMobileNumber;
    }

    public String getTransporterAddress() {
        return transporterAddress;
    }

    public void setTransporterAddress(String transporterAddress) {
        this.transporterAddress = transporterAddress;
    }

    public String getPoPaymentTermsType() {
        return poPaymentTermsType;
    }

    public void setPoPaymentTermsType(String poPaymentTermsType) {
        this.poPaymentTermsType = poPaymentTermsType;
    }


    public String getPoItemDetails() {
        return poItemDetails;
    }

    public void setPoItemDetails(String poItemDetails) {
        this.poItemDetails = poItemDetails;
    }

    public String getPoIncoTerms() {
        return poIncoTerms;
    }

    public void setPoIncoTerms(String poIncoTerms) {
        this.poIncoTerms = poIncoTerms;
    }

    public String getPoPaymentTerms() {
        return poPaymentTerms;
    }

    public void setPoPaymentTerms(String poPaymentTerms) {
        this.poPaymentTerms = poPaymentTerms;
    }

    public String getPoTermsAndConditions() {
        return poTermsAndConditions;
    }

    public void setPoTermsAndConditions(String poTermsAndConditions) {
        this.poTermsAndConditions = poTermsAndConditions;
    }

    public String getPoAttachments() {
        return poAttachments;
    }

    public void setPoAttachments(String poAttachments) {
        this.poAttachments = poAttachments;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public void setTotalItems(double totalItems) {
        this.totalItems = totalItems;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setPoQty(double poQty) {
        this.poQty = poQty;
    }

    public void setOpenQty(double openQty) {
        this.openQty = openQty;
    }

    public void setBalanceQty(double balanceQty) {
        this.balanceQty = balanceQty;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
}
