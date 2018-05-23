package quay.com.ipos.customerInfo.customerInfoModal;

import quay.com.ipos.enums.CustomerEnum;

/**
 * Created by niraj.kumar on 5/22/2018.
 */

public class CustomerModel {
    public static final String TABLE_NAME = "customerList";


    private String customerID;
    private String customerName;
    private String customerPoints;
    private String customerPhone;
    private String customerPhone2;
    private String customerPhone3;
    private String customerEmail;
    private String customerEmail2;
    private String customerDom;
    private String customerBday;
    private String customerGender;
    private String customerFirstName;
    private String customerLastName;
    private String custoemrGstin;
    private String customerStatus;
    private String customerDesignation;
    private String customerCompany;
    private String customerRelationship;
    private String cfactor;
    private String customerAddress;
    private String customerState;
    private String customerCity;
    private String customerPin;

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    private String customerImage;
    private String lastBillingDate;
    private String lastBillingAmount;
    private String issuggestion;
    private String suggestion;
    private String recentOrders;
    private int isSync;

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    private String searchParam;
    private String storeId;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + CustomerEnum.ColoumnCustomerID + " INTEGER PRIMARY KEY,"
                    + CustomerEnum.ColoumnCustomerName + " TEXT,"
                    + CustomerEnum.ColoumnCustomerPoints + " TEXT,"
                    + CustomerEnum.ColoumnCustomerPhone + " TEXT,"
                    + CustomerEnum.ColoumnCustomerPhone2 + " TEXT,"
                    + CustomerEnum.ColoumnCustomerPhone3 + " TEXT,"
                    + CustomerEnum.ColoumnCustomerEmail + " TEXT,"
                    + CustomerEnum.ColoumnCustomerEmail2 + " TEXT,"
                    + CustomerEnum.ColoumnCustomerDom + " TEXT,"
                    + CustomerEnum.ColoumnCustomerBday + " TEXT,"
                    + CustomerEnum.ColoumnCustomerGender + " TEXT,"
                    + CustomerEnum.ColoumnCustomerFirstName + " TEXT,"
                    + CustomerEnum.ColoumnCustomerLastName + " TEXT,"
                    + CustomerEnum.ColoumnCustoemrGstin + " TEXT,"
                    + CustomerEnum.ColoumnCustomerStatus + " TEXT,"
                    + CustomerEnum.ColoumnCustomerDesignation + " TEXT,"
                    + CustomerEnum.ColoumnCustomerCompany + " TEXT,"
                    + CustomerEnum.ColoumnCustomerRelationship + " TEXT,"
                    + CustomerEnum.ColoumnCfactor + " TEXT,"
                    + CustomerEnum.ColoumnCustomerAddress + " TEXT,"
                    + CustomerEnum.ColoumnCustomerState + " TEXT,"
                    + CustomerEnum.ColoumnCustomerCity + " TEXT,"
                    + CustomerEnum.ColoumnCustomerPin + " TEXT,"
                    + CustomerEnum.ColoumnCustomerImage + " TEXT,"
                    + CustomerEnum.ColoumnLastBillingDate + " TEXT,"
                    + CustomerEnum.ColoumnLastBillingAmount + " TEXT,"
                    + CustomerEnum.ColoumnIsSuggestion + " TEXT,"
                    + CustomerEnum.ColoumnSuggestion + " TEXT,"
                    + CustomerEnum.ColoumnRecentOrders + " TEXT,"
                    + CustomerEnum.ColoumnIsSync + " INTEGER"
                    + ")";


    public CustomerModel() {

    }
    public CustomerModel(String customerID,
                         String customerName,
                         String customerPoints,
                         String customerPhone,
                         String customerPhone2,
                         String customerPhone3,
                         String customerEmail,
                         String customerEmail2,
                         String customerDom,
                         String customerBday,
                         String customerGender,
                         String customerFirstName,
                         String customerLastName,
                         String custoemrGstin,
                         String customerStatus,
                         String customerDesignation,
                         String customerCompany,
                         String customerRelationship,
                         String cfactor,
                         String customerAddress,
                         String customerState,
                         String customerCity,
                         String customerPin,
                         String customerImage,
                         String lastBillingDate,
                         String lastBillingAmount,
                         String issuggestion,
                         String suggestion,
                         String recent_orders,
                         int sync) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerPoints = customerPoints;
        this.customerPhone = customerPhone;
        this.customerPhone2 = customerPhone2;
        this.customerPhone3 = customerPhone3;
        this.customerEmail = customerEmail;
        this.customerEmail2 = customerEmail2;
        this.customerDom = customerDom;
        this.customerBday = customerBday;
        this.customerGender = customerGender;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.custoemrGstin = custoemrGstin;
        this.custoemrGstin = custoemrGstin;
        this.customerStatus = customerStatus;
        this.customerDesignation = customerDesignation;
        this.customerCompany = customerCompany;
        this.customerRelationship = customerRelationship;
        this.cfactor = cfactor;
        this.customerAddress = customerAddress;
        this.customerState = customerState;
        this.customerCity = customerCity;
        this.customerPin = customerPin;
        this.customerImage = customerImage;
        this.lastBillingDate = lastBillingDate;
        this.lastBillingAmount = lastBillingAmount;
        this.issuggestion = issuggestion;
        this.suggestion = suggestion;
        this.recentOrders = recent_orders;
        this.isSync = sync;

    }


    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPoints() {
        return customerPoints;
    }

    public void setCustomerPoints(String customerPoints) {
        this.customerPoints = customerPoints;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone2() {
        return customerPhone2;
    }

    public void setCustomerPhone2(String customerPhone2) {
        this.customerPhone2 = customerPhone2;
    }

    public String getCustomerPhone3() {
        return customerPhone3;
    }

    public void setCustomerPhone3(String customerPhone3) {
        this.customerPhone3 = customerPhone3;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerEmail2() {
        return customerEmail2;
    }

    public void setCustomerEmail2(String customerEmail2) {
        this.customerEmail2 = customerEmail2;
    }

    public String getCustomerDom() {
        return customerDom;
    }

    public void setCustomerDom(String customerDom) {
        this.customerDom = customerDom;
    }

    public String getCustomerBday() {
        return customerBday;
    }

    public void setCustomerBday(String customerBday) {
        this.customerBday = customerBday;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustoemrGstin() {
        return custoemrGstin;
    }

    public void setCustoemrGstin(String custoemrGstin) {
        this.custoemrGstin = custoemrGstin;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getCustomerDesignation() {
        return customerDesignation;
    }

    public void setCustomerDesignation(String customerDesignation) {
        this.customerDesignation = customerDesignation;
    }

    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    public String getCustomerRelationship() {
        return customerRelationship;
    }

    public void setCustomerRelationship(String customerRelationship) {
        this.customerRelationship = customerRelationship;
    }

    public String getCfactor() {
        return cfactor;
    }

    public void setCfactor(String cfactor) {
        this.cfactor = cfactor;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerPin() {
        return customerPin;
    }

    public void setCustomerPin(String customerPin) {
        this.customerPin = customerPin;
    }

    public String getLastBillingDate() {
        return lastBillingDate;
    }

    public void setLastBillingDate(String lastBillingDate) {
        this.lastBillingDate = lastBillingDate;
    }

    public String getLastBillingAmount() {
        return lastBillingAmount;
    }

    public void setLastBillingAmount(String lastBillingAmount) {
        this.lastBillingAmount = lastBillingAmount;
    }

    public String getIssuggestion() {
        return issuggestion;
    }

    public void setIssuggestion(String issuggestion) {
        this.issuggestion = issuggestion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getRecentOrders() {
        return recentOrders;
    }

    public void setRecentOrders(String recentOrders) {
        this.recentOrders = recentOrders;
    }

    public int getIsSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }






}
