package quay.com.ipos.customerInfo.customerInfoModal;

import quay.com.ipos.enums.CustomerEnum;

/**
 * Created by niraj.kumar on 5/22/2018.
 */

public class CustomerModel {
    public static final String TABLE_NAME = "customerList";
    public static final String TABLE_SPINNER = "spinnerList";


    private String customerID;

    public String getCustomerTitle() {
        return customerTitle;
    }

    public void setCustomerTitle(String customerTitle) {
        this.customerTitle = customerTitle;
    }

    public String getCustomerMaritalStatus() {
        return customerMaritalStatus;
    }

    public void setCustomerMaritalStatus(String customerMaritalStatus) {
        this.customerMaritalStatus = customerMaritalStatus;
    }

    public String getCustomerSpouseFirstName() {
        return customerSpouseFirstName;
    }

    public void setCustomerSpouseFirstName(String customerSpouseFirstName) {
        this.customerSpouseFirstName = customerSpouseFirstName;
    }

    public String getCustomerSpouseLastName() {
        return customerSpouseLastName;
    }

    public void setCustomerSpouseLastName(String customerSpouseLastName) {
        this.customerSpouseLastName = customerSpouseLastName;
    }

    public String getCustomerSpouseDob() {
        return customerSpouseDob;
    }

    public void setCustomerSpouseDob(String customerSpouseDob) {
        this.customerSpouseDob = customerSpouseDob;
    }

    public String getCustomerChildSatus() {
        return customerChildSatus;
    }

    public void setCustomerChildSatus(String customerChildSatus) {
        this.customerChildSatus = customerChildSatus;
    }

    public String getCustomerChild() {
        return customerChild;
    }

    public void setCustomerChild(String customerChild) {
        this.customerChild = customerChild;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerGstin() {
        return customerGstin;
    }

    public void setCustomerGstin(String customerGstin) {
        this.customerGstin = customerGstin;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    private String customerTitle;
    private String customerName;
    private String customerFirstName;
    private String customerLastName;
    private String customerGender;
    private String customerBday;
    private String customerMaritalStatus;
    private String customerSpouseFirstName;
    private String customerSpouseLastName;
    private String customerSpouseDob;
    private String customerChildSatus;
    private String customerChild;
    private String customerEmail;
    private String customerEmail2;
    private String customerPhone;
    private String customerPhone2;
    private String customerPhone3;
    private String customerAddress;
    private String customerState;
    private String customerCity;
    private String customerPin;
    private String customerCountry;
    private String customerDesignation;
    private String customerCompany;
    private String customerGstin;
    private String customer;
    private String customerRelationship;
    private String customerImage;
    private String lastBillingDate;
    private String lastBillingAmount;
    private String issuggestion;
    private String suggestion;
    private String recentOrders;

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    private String customerStatus;
    private String customerPoints;

    public String getCfactor() {
        return cfactor;
    }

    public void setCfactor(String cfactor) {
        this.cfactor = cfactor;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerDom() {
        return customerDom;
    }

    public void setCustomerDom(String customerDom) {
        this.customerDom = customerDom;
    }

    private String cfactor;
    private String customerType;
    private String customerDom;

    private int isSync;

    private String searchParam;
    private String storeId;


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

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public static final String SPINNER_TABLE_CREATE="CREATE TABLE "+TABLE_SPINNER+"("
            +CustomerEnum.ColoumnStateList+" TEXT,"
            +CustomerEnum.ColoumnCountryList+" TEXT,"
            +CustomerEnum.ColoumnCityList+" TEXT,"
            +CustomerEnum.ColoumnDesignationList+" TEXT,"
            +CustomerEnum.ColoumnCompanyList+" TEXT,"
            +CustomerEnum.ColoumnCustomerList+" TEXT,"
            +CustomerEnum.ColoumnRelationShipList+" TEXT"+")";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + CustomerEnum.ColoumnCustomerID + " INTEGER PRIMARY KEY,"
                    + CustomerEnum.ColoumnCustomerTitle + " TEXT,"
                    + CustomerEnum.ColoumnCustomerName + " TEXT,"
                    + CustomerEnum.ColoumnCustomerFirstName + " TEXT,"
                    + CustomerEnum.ColoumnCustomerLastName + " TEXT,"
                    + CustomerEnum.ColoumnCustomerGender + " TEXT,"
                    + CustomerEnum.ColoumnCustomerBday + " TEXT,"
                    + CustomerEnum.ColoumnCustomerMaritalStatus + " TEXT,"
                    + CustomerEnum.ColoumnCustomerSpouseFirstName + " TEXT,"
                    + CustomerEnum.ColoumnCustomerSpouseLastName + " TEXT,"
                    + CustomerEnum.ColoumnCustomerSpouseDob + " TEXT,"
                    + CustomerEnum.ColoumnCustomerChildStatus + " TEXT,"
                    + CustomerEnum.ColoumnCustomerChild + " TEXT,"
                    + CustomerEnum.ColoumnCustomerEmail + " TEXT,"
                    + CustomerEnum.ColoumnCustomerEmail2 + " TEXT,"
                    + CustomerEnum.ColoumnCustomerPhone + " TEXT,"
                    + CustomerEnum.ColoumnCustomerPhone2 + " TEXT,"
                    + CustomerEnum.ColoumnCustomerPhone3 + " TEXT,"
                    + CustomerEnum.ColoumnCustomerAddress + " TEXT,"
                    + CustomerEnum.ColoumnCustomerState + " TEXT,"
                    + CustomerEnum.ColoumnCustomerCity + " TEXT,"
                    + CustomerEnum.ColoumnCustomerPin + " TEXT,"
                    + CustomerEnum.ColoumnCustomerCountry + " TEXT,"
                    + CustomerEnum.ColoumnCustomerDesignation + " TEXT,"
                    + CustomerEnum.ColoumnCustomerCompany + " TEXT,"
                    + CustomerEnum.ColoumnCustomerGstin + " TEXT,"
                    + CustomerEnum.ColoumnCustomer + " TEXT,"
                    + CustomerEnum.ColoumnCustomerRelationship + " TEXT,"
                    + CustomerEnum.ColoumnCustomerImage + " TEXT,"
                    + CustomerEnum.ColoumnLastBillingDate + " TEXT,"
                    + CustomerEnum.ColoumnLastBillingAmount + " TEXT,"
                    + CustomerEnum.ColoumnIsSuggestion + " TEXT,"
                    + CustomerEnum.ColoumnSuggestion + " TEXT,"
                    + CustomerEnum.ColoumnRecentOrders + " TEXT,"
                    + CustomerEnum.ColoumnCustomerCustomerStatus + " TEXT,"
                    + CustomerEnum.ColoumnCustomerPoint + " TEXT,"
                    + CustomerEnum.ColoumncFactor + " TEXT,"
                    + CustomerEnum.ColoumncType + " TEXT,"
                    + CustomerEnum.ColoumncCustomerDOM + " TEXT,"
                    + CustomerEnum.ColoumnIsSync + " INTEGER" + ")";


    public CustomerModel() {

    }

    public CustomerModel(String customerID,
                         String customerTitle,
                         String customerName,
                         String customerFirstName,
                         String customerLastName,
                         String customerGender,
                         String customerBday,
                         String customerMaritalStatus,
                         String customerSpouseFirstName,
                         String customerSpouseLastName,
                         String customerSpouseDob,
                         String customerChildSatus,
                         String customerChild,
                         String customerEmail,
                         String customerEmail2,
                         String customerPhone,
                         String customerPhone2,
                         String customerPhone3,
                         String customerAddress,
                         String customerState,
                         String customerCity,
                         String customerPin,
                         String customerCountry,
                         String customerDesignation,
                         String customerCompany,
                         String customerGstin,
                         String customer,
                         String customerRelationship,
                         String customerImage,
                         String lastBillingDate,
                         String lastBillingAmount,
                         String issuggestion,
                         String suggestion,
                         String customerPoints,
                         String recent_orders,
                         String customerStatus,
                         String cfactor,
                         String customerType,
                         String customerDom,
                         int sync) {
        this.customerID = customerID;
        this.customerTitle = customerTitle;
        this.customerName = customerName;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerGender = customerGender;
        this.customerBday = customerBday;
        this.customerMaritalStatus = customerMaritalStatus;
        this.customerSpouseFirstName = customerSpouseFirstName;
        this.customerSpouseLastName = customerSpouseLastName;
        this.customerSpouseDob = customerSpouseDob;
        this.customerChildSatus = customerChildSatus;
        this.customerChild = customerChild;
        this.customerEmail = customerEmail;
        this.customerEmail2 = customerEmail2;
        this.customerPhone = customerPhone;
        this.customerPhone2 = customerPhone2;
        this.customerPhone3 = customerPhone3;
        this.customerAddress = customerAddress;
        this.customerState = customerState;
        this.customerCity = customerCity;
        this.customerPin = customerPin;
        this.customerCountry = customerCountry;
        this.customerDesignation = customerDesignation;
        this.customerCompany = customerCompany;
        this.customerGstin = customerGstin;
        this.customer = customer;
        this.customerRelationship = customerRelationship;
        this.customerImage = customerImage;
        this.lastBillingDate = lastBillingDate;
        this.lastBillingAmount = lastBillingAmount;
        this.issuggestion = issuggestion;
        this.suggestion = suggestion;
        this.customerPoints = customerPoints;
        this.recentOrders = recent_orders;
        this.customerStatus = customerStatus;
        this.cfactor = cfactor;
        this.customerType = customerType;
        this.customerDom = customerDom;
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
