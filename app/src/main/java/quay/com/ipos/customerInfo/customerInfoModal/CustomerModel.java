package quay.com.ipos.customerInfo.customerInfoModal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import quay.com.ipos.enums.CustomerEnum;

/**
 * Created by niraj.kumar on 5/22/2018.
 */

public class CustomerModel {
    public static final String TABLE_NAME = "customerList";
    public static final String TABLE_SPINNER = "spinnerList";
    public static final String KEY_LOCALID = "localId";

    public String getRegisteredBusinessPlaceID() {
        return registeredBusinessPlaceID;
    }

    public void setRegisteredBusinessPlaceID(String registeredBusinessPlaceID) {
        this.registeredBusinessPlaceID = registeredBusinessPlaceID;
    }

    private String registeredBusinessPlaceID;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    private String customerCode;


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

    public List<CustomeChildListModel.ChildList> getCustomerChild() {
        return customerChild;
    }

    public void setCustomerChild(List<CustomeChildListModel.ChildList> customerChild) {
        this.customerChild = customerChild;
    }

    public void setCustomerChild(String customerChild) {
        this.customerChild = new Gson().fromJson(customerChild, new TypeToken<List<CustomeChildListModel.ChildList>>() {
        }.getType());

    }


    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
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
    private List<CustomeChildListModel.ChildList> customerChild;
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
    private double customerPointsPerValue;

    public double getCustomerRedeemPoints() {
        return customerRedeemPoints;
    }

    public void setCustomerRedeemPoints(double customerRedeemPoints) {
        this.customerRedeemPoints = customerRedeemPoints;
    }

    public double getCustomerAdjustPoints() {
        return customerAdjustPoints;
    }

    public void setCustomerAdjustPoints(double customerAdjustPoints) {
        this.customerAdjustPoints = customerAdjustPoints;
    }

    public double getCustomerExpirePoints() {
        return customerExpirePoints;
    }

    public void setCustomerExpirePoints(double customerExpirePoints) {
        this.customerExpirePoints = customerExpirePoints;
    }

    public double getCustomerReversePoints() {
        return customerReversePoints;
    }

    public void setCustomerReversePoints(double customerReversePoints) {
        this.customerReversePoints = customerReversePoints;
    }

    private double customerRedeemPoints;
    private double customerAdjustPoints;
    private double customerExpirePoints;
    private double customerReversePoints;


    public double getPointsPerValue() {
        return pointsPerValue;
    }

    public void setPointsPerValue(double pointsPerValue) {
        this.pointsPerValue = pointsPerValue;
    }

    private double pointsPerValue;

    public String getCustoemrGstin() {
        return custoemrGstin;
    }

    public void setCustoemrGstin(String custoemrGstin) {
        this.custoemrGstin = custoemrGstin;
    }

    private String custoemrGstin;
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

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    private int localId;
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

    public static final String SPINNER_TABLE_CREATE = "CREATE TABLE " + TABLE_SPINNER + "("
            + CustomerEnum.ColoumnCityList + " TEXT,"
            + CustomerEnum.ColoumnStateList + " TEXT,"
            + CustomerEnum.ColoumnCountryList + " TEXT,"
            + CustomerEnum.ColoumnDesignationList + " TEXT,"
            + CustomerEnum.ColoumnCompanyList + " TEXT,"
            + CustomerEnum.ColoumnCustomerList + " TEXT,"
            + CustomerEnum.ColoumnRelationShipList + " TEXT,"
            + CustomerEnum.ColoumnTypeList + " TEXT" + ")";

    // Create table SQL query
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_LOCALID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CustomerEnum.ColoumnCustomerID.toString() + " INTEGER,"
            + CustomerEnum.ColoumnCustomerTitle.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerName.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerFirstName.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerLastName.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerGender.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerBday.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerMaritalStatus.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerSpouseFirstName.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerSpouseLastName.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerSpouseDob.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerChildStatus.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerChild.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerEmail.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerEmail2.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerPhone.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerPhone2.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerPhone3.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerAddress.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerState.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerCity.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerPin.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerCountry.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerDesignation.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerCompany.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerGstin.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomer.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerRelationship.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerImage.toString() + " TEXT,"
            + CustomerEnum.ColoumnLastBillingDate.toString() + " TEXT,"
            + CustomerEnum.ColoumnLastBillingAmount.toString() + " TEXT,"
            + CustomerEnum.ColoumnIsSuggestion.toString() + " TEXT,"
            + CustomerEnum.ColoumnSuggestion.toString() + " TEXT,"
            + CustomerEnum.ColoumnRecentOrders.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerCustomerStatus.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerPoint.toString() + " TEXT,"
            + CustomerEnum.ColoumncFactor.toString() + " TEXT,"
            + CustomerEnum.ColoumncType.toString() + " TEXT,"
            + CustomerEnum.ColoumncCustomerDOM.toString() + " TEXT,"
            + CustomerEnum.ColoumnCustomerCode.toString() + " TEXT,"
            + CustomerEnum.ColoumnRegisteredBusinessPlace.toString() + " TEXT,"
            + CustomerEnum.ColoumnPointsPerValue.toString() + " REAL,"
            + CustomerEnum.ColumnRedeemPoints.toString()+" REAL,"
            + CustomerEnum.ColumnAdjustedPoints.toString()+" REAL,"
            + CustomerEnum.ColumnExpirePoints.toString()+" REAL,"
            + CustomerEnum.CoulmnReversePoints.toString()+" REAL,"
            + CustomerEnum.ColoumnIsSync.toString() + " INTEGER" + ")";


    public CustomerModel() {

    }

    public CustomerModel(
            String customerID,
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
            String customerCode,
            String registeredBusinessPlaceID,
            double customerPointsPerValue,
            double customerRedeemPoints,
            double customerAdjustPoints,
            double customerExpirePoints,
            double customerReversePoints,
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
        setCustomerChild(customerChild);
        // this.customerChild = customerChild;
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
        this.custoemrGstin = customerGstin;
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
        this.customerCode = customerCode;
        this.registeredBusinessPlaceID = registeredBusinessPlaceID;
        this.customerPointsPerValue = customerPointsPerValue;
        this.customerRedeemPoints = customerRedeemPoints;
        this.customerAdjustPoints = customerAdjustPoints;
        this.customerExpirePoints = customerExpirePoints;
        this.customerReversePoints = customerReversePoints;
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


    public double getCustomerPointsPerValue() {
        return customerPointsPerValue;
    }

    public void setCustomerPointsPerValue(double customerPointsPerValue) {
        this.customerPointsPerValue = customerPointsPerValue;
    }
}
