package quay.com.ipos.realmbean;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ankush.bansal on 06-06-2018.
 */

public class RealmOrderList extends RealmObject {

    /**
     * employeeCode : string
     * employeeRole : string
     * poNumber : string
     * poDate : 2018-06-06T16:57:07.845Z
     * poStatus : string
     * orderValue : 0
     * discountValue : 0
     * deliveryBy : 2018-06-06T16:57:07.845Z
     * orderLoyality : 0
     * accumulatedLoyality : 0
     * totalLoyality : 0
     * businessPlace : string
     * businessPlaceCode : string
     * entityID : string
     * totalValueWithTax : 0
     * totalCGSTValue : 0
     * totalIGSTValue : 0
     * totalSGSTValue : 0
     * totalValueWithoutTax : 0
     * totalTaxValue : 0
     * totalDiscountValue : 0
     * totalRoundingOffValue : 0
     * cartDetail : [{"materialCode":"string","materialName":"string","materialValue":0,"materialQty":0,"materialDiscountValue":0,"materialUnitValue":0,"materialCGSTRate":0,"materialCGSTValue":0,"materialSGSTRate":0,"materialSGSTValue":0,"materialIGSTRate":0,"materialIGSTValue":0,"schemeID":0,"ruleID":0,"discountValue":0,"discountPerc":0}]
     * listspendRequestHistoryPhaseModel : [{"phaseName":"string","positionStatus":0,"listSpendRequestHistoryModel":[{"status":"string","date":"string","header":"string","flag":"string","comment":"string"}]}]
     */
    @PrimaryKey
    private String poNumber;
    private String employeeCode;
    private String employeeRole;
    private String poDate;
    private String poStatus;
    private int orderValue;
    private int discountValue;
    private String deliveryBy;
    private int orderLoyality;
    private int accumulatedLoyality;
    private int totalLoyality;
    private String businessPlace;
    private String businessPlaceCode;
    private String entityID;
    private int totalValueWithTax;
    private int totalCGSTValue;
    private int totalIGSTValue;
    private int totalSGSTValue;
    private int totalValueWithoutTax;
    private int totalTaxValue;
    private int totalDiscountValue;
    private int totalRoundingOffValue;
    private String customerName;
    private String cartDetail;
    private String listspendRequestHistoryPhaseModel;
    private int quantity;

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

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

    public String getPoStatus() {
        return poStatus;
    }

    public void setPoStatus(String poStatus) {
        this.poStatus = poStatus;
    }

    public int getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(int orderValue) {
        this.orderValue = orderValue;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public String getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(String deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public int getOrderLoyality() {
        return orderLoyality;
    }

    public void setOrderLoyality(int orderLoyality) {
        this.orderLoyality = orderLoyality;
    }

    public int getAccumulatedLoyality() {
        return accumulatedLoyality;
    }

    public void setAccumulatedLoyality(int accumulatedLoyality) {
        this.accumulatedLoyality = accumulatedLoyality;
    }

    public int getTotalLoyality() {
        return totalLoyality;
    }

    public void setTotalLoyality(int totalLoyality) {
        this.totalLoyality = totalLoyality;
    }

    public String getBusinessPlace() {
        return businessPlace;
    }

    public void setBusinessPlace(String businessPlace) {
        this.businessPlace = businessPlace;
    }

    public String getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    public void setBusinessPlaceCode(String businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public int getTotalValueWithTax() {
        return totalValueWithTax;
    }

    public void setTotalValueWithTax(int totalValueWithTax) {
        this.totalValueWithTax = totalValueWithTax;
    }

    public int getTotalCGSTValue() {
        return totalCGSTValue;
    }

    public void setTotalCGSTValue(int totalCGSTValue) {
        this.totalCGSTValue = totalCGSTValue;
    }

    public int getTotalIGSTValue() {
        return totalIGSTValue;
    }

    public void setTotalIGSTValue(int totalIGSTValue) {
        this.totalIGSTValue = totalIGSTValue;
    }

    public int getTotalSGSTValue() {
        return totalSGSTValue;
    }

    public void setTotalSGSTValue(int totalSGSTValue) {
        this.totalSGSTValue = totalSGSTValue;
    }

    public int getTotalValueWithoutTax() {
        return totalValueWithoutTax;
    }

    public void setTotalValueWithoutTax(int totalValueWithoutTax) {
        this.totalValueWithoutTax = totalValueWithoutTax;
    }

    public int getTotalTaxValue() {
        return totalTaxValue;
    }

    public void setTotalTaxValue(int totalTaxValue) {
        this.totalTaxValue = totalTaxValue;
    }

    public int getTotalDiscountValue() {
        return totalDiscountValue;
    }

    public void setTotalDiscountValue(int totalDiscountValue) {
        this.totalDiscountValue = totalDiscountValue;
    }

    public int getTotalRoundingOffValue() {
        return totalRoundingOffValue;
    }

    public void setTotalRoundingOffValue(int totalRoundingOffValue) {
        this.totalRoundingOffValue = totalRoundingOffValue;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCartDetail() {
        return cartDetail;
    }

    public void setCartDetail(String cartDetail) {
        this.cartDetail = cartDetail;
    }

    public String getListspendRequestHistoryPhaseModel() {
        return listspendRequestHistoryPhaseModel;
    }

    public void setListspendRequestHistoryPhaseModel(String listspendRequestHistoryPhaseModel) {
        this.listspendRequestHistoryPhaseModel = listspendRequestHistoryPhaseModel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
