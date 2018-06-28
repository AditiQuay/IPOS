package quay.com.ipos.ddrsales.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ankush.bansal on 06-06-2018.
 */

public class RealmDDROrderList extends RealmObject {


    /**
     * isSync : false
     * totalTaxValue : 80
     * approvalStat : 0
     * orderValue : 477
     * businessPlace : 1
     * employeeCode : 6000013
     * poDate : 08 Jun 2018
     * totalCGSTValue : 40
     * totalValueWithoutTax : 450
     * poStatus : Pending
     * discountValue : 54
     * listspendRequestHistoryPhaseModel : []
     * businessPlaceCode : 1
     * deliveryBy : 08 Jun 2018
     * totalRoundingOffValue : ₹ 0.0
     * accumulatedLoyality : 0
     * employeeRole : user
     * orderLoyality : 45
     * poNumber : P00001
     * totalValueWithTax : 531
     * entityID : 06
     * totalDiscountValue : 54
     * totalLoyality : 45
     * totalSGSTValue : 40
     * totalIGSTValue : 81
     * cartDetail : [{"materialName":"BILLO 2KG","materialIGSTValue":81,"materialDiscountValue":54,"discountPerc":"","materialCGSTRate":9,"oldMaterialCode":"Billo","materialCGSTValue":40,"materialQty":3,"materialSGSTValue":40,"scheme":[{"oldSchemeID":1,"schemeID":1,"oldRuleID":"1","discountValue":"30","ruleID":"1","discountPerc":"10"},{"oldSchemeID":2,"schemeID":2,"oldRuleID":"2","discountValue":"24","ruleID":"2","discountPerc":"5"},{"oldSchemeID":2,"schemeID":2,"oldRuleID":"2","discountValue":"24","ruleID":"2","discountPerc":"5"},{"oldSchemeID":2,"schemeID":2,"oldRuleID":"2","discountValue":"24","ruleID":"2","discountPerc":"5"}],"materialIGSTRate":18,"materialCode":"Billo","materialValue":450,"discountValue":54,"materialUnitValue":150,"materialSGSTRate":9}]
     */
    @PrimaryKey
    private String poNumber;
    private boolean isSync;
    private int totalTaxValue;
    private String approvalStat;
    private int orderValue;
    private String businessPlace;
    private String employeeCode;
    private String poDate;
    private int totalCGSTValue;
    private int totalValueWithoutTax;
    private String poStatus;
    private int discountValue;
    private String listspendRequestHistoryPhaseModel;
    private int businessPlaceCode;
    private String deliveryBy;
    private String totalRoundingOffValue;
    private double accumulatedLoyality;
    private String employeeRole;
    private int orderLoyality;

    private String quantity;
    private String customerName;
    private int totalValueWithTax;
    private String entityID;
    private int totalDiscountValue;
    private int totalLoyality;
    private int totalSGSTValue;
    private int totalIGSTValue;
    private String cartDetail;
    private String discount;


    public boolean isIsSync() {
        return isSync;
    }

    public void setIsSync(boolean isSync) {
        this.isSync = isSync;
    }

    public int getTotalTaxValue() {
        return totalTaxValue;
    }

    public void setTotalTaxValue(int totalTaxValue) {
        this.totalTaxValue = totalTaxValue;
    }

    public String getApprovalStat() {
        return approvalStat;
    }

    public void setApprovalStat(String approvalStat) {
        this.approvalStat = approvalStat;
    }

    public int getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(int orderValue) {
        this.orderValue = orderValue;
    }

    public String getBusinessPlace() {
        return businessPlace;
    }

    public void setBusinessPlace(String businessPlace) {
        this.businessPlace = businessPlace;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getPoDate() {
        return poDate;
    }

    public void setPoDate(String poDate) {
        this.poDate = poDate;
    }

    public int getTotalCGSTValue() {
        return totalCGSTValue;
    }

    public void setTotalCGSTValue(int totalCGSTValue) {
        this.totalCGSTValue = totalCGSTValue;
    }

    public int getTotalValueWithoutTax() {
        return totalValueWithoutTax;
    }

    public void setTotalValueWithoutTax(int totalValueWithoutTax) {
        this.totalValueWithoutTax = totalValueWithoutTax;
    }

    public String getPoStatus() {
        return poStatus;
    }

    public void setPoStatus(String poStatus) {
        this.poStatus = poStatus;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public String getListspendRequestHistoryPhaseModel() {
        return listspendRequestHistoryPhaseModel;
    }

    public void setListspendRequestHistoryPhaseModel(String listspendRequestHistoryPhaseModel) {
        this.listspendRequestHistoryPhaseModel = listspendRequestHistoryPhaseModel;
    }

    public int getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    public void setBusinessPlaceCode(int businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    public String getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(String deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public String getTotalRoundingOffValue() {
        return totalRoundingOffValue;
    }

    public void setTotalRoundingOffValue(String totalRoundingOffValue) {
        this.totalRoundingOffValue = totalRoundingOffValue;
    }

    public double getAccumulatedLoyality() {
        return accumulatedLoyality;
    }

    public void setAccumulatedLoyality(double accumulatedLoyality) {
        this.accumulatedLoyality = accumulatedLoyality;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public int getOrderLoyality() {
        return orderLoyality;
    }

    public void setOrderLoyality(int orderLoyality) {
        this.orderLoyality = orderLoyality;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public int getTotalValueWithTax() {
        return totalValueWithTax;
    }

    public void setTotalValueWithTax(int totalValueWithTax) {
        this.totalValueWithTax = totalValueWithTax;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public int getTotalDiscountValue() {
        return totalDiscountValue;
    }

    public void setTotalDiscountValue(int totalDiscountValue) {
        this.totalDiscountValue = totalDiscountValue;
    }

    public int getTotalLoyality() {
        return totalLoyality;
    }

    public void setTotalLoyality(int totalLoyality) {
        this.totalLoyality = totalLoyality;
    }

    public int getTotalSGSTValue() {
        return totalSGSTValue;
    }

    public void setTotalSGSTValue(int totalSGSTValue) {
        this.totalSGSTValue = totalSGSTValue;
    }

    public int getTotalIGSTValue() {
        return totalIGSTValue;
    }

    public void setTotalIGSTValue(int totalIGSTValue) {
        this.totalIGSTValue = totalIGSTValue;
    }

    public String getCartDetail() {
        return cartDetail;
    }

    public void setCartDetail(String cartDetail) {
        this.cartDetail = cartDetail;
    }


    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
