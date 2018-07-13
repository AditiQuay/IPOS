package quay.com.ipos.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aditi.bhuranda on 03-07-2018.
 */

public class RetailOrderCentreRequest {

    @SerializedName("employeeCode")
    @Expose
    private String employeeCode;
    @SerializedName("employeeRole")
    @Expose
    private String employeeRole;
    @SerializedName("businessPlaceCode")
    @Expose
    private String businessPlaceCode;
    @SerializedName("businessStateCode")
    @Expose
    private String businessStateCode;
    @SerializedName("storeId")
    @Expose
    private String storeId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("searchParam")
    @Expose
    private String searchParam;

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

    public String getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    public void setBusinessPlaceCode(String businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    public String getBusinessStateCode() {
        return businessStateCode;
    }

    public void setBusinessStateCode(String businessStateCode) {
        this.businessStateCode = businessStateCode;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }
}
