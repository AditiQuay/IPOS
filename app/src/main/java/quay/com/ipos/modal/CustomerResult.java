package quay.com.ipos.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CustomerResult {

    @SerializedName("customer")
    @Expose
    private List<Customer> customer = null;

    public List<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }
    public class Customer {

        @SerializedName("customerID")
        @Expose
        private Integer customerID;
        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("customer_points")
        @Expose
        private String customerPoints;
        @SerializedName("customer_phone")
        @Expose
        private String customerPhone;
        @SerializedName("customer_email")
        @Expose
        private String customerEmail;
        @SerializedName("customer_bday")
        @Expose
        private String customerBday;
        @SerializedName("customer_address")
        @Expose
        private CustomerAddress customerAddress;
        @SerializedName("last_billing")
        @Expose
        private LastBilling lastBilling;
        @SerializedName("recent_orders")
        @Expose
        private List<RecentOrder> recentOrders = null;

        public Integer getCustomerID() {
            return customerID;
        }

        public void setCustomerID(Integer customerID) {
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

        public String getCustomerEmail() {
            return customerEmail;
        }

        public void setCustomerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
        }

        public String getCustomerBday() {
            return customerBday;
        }

        public void setCustomerBday(String customerBday) {
            this.customerBday = customerBday;
        }

        public CustomerAddress getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(CustomerAddress customerAddress) {
            this.customerAddress = customerAddress;
        }

        public LastBilling getLastBilling() {
            return lastBilling;
        }

        public void setLastBilling(LastBilling lastBilling) {
            this.lastBilling = lastBilling;
        }

        public List<RecentOrder> getRecentOrders() {
            return recentOrders;
        }

        public void setRecentOrders(List<RecentOrder> recentOrders) {
            this.recentOrders = recentOrders;
        }

    }

    public class CustomerAddress {

        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("pin")
        @Expose
        private String pin;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPin() {
            return pin;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }

    }

    public class LastBilling {

        @SerializedName("last_billing_date")
        @Expose
        private String lastBillingDate;
        @SerializedName("last_billing_amount")
        @Expose
        private String lastBillingAmount;
        @SerializedName("suggestion")
        @Expose
        private Suggestion suggestion;

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

        public Suggestion getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(Suggestion suggestion) {
            this.suggestion = suggestion;
        }

    }


    public class RecentOrder {

        @SerializedName("from_store_name")
        @Expose
        private String fromStoreName;
        @SerializedName("store_city")
        @Expose
        private String storeCity;
        @SerializedName("store_state")
        @Expose
        private String storeState;
        @SerializedName("bill_date")
        @Expose
        private String billDate;
        @SerializedName("bill_price")
        @Expose
        private String billPrice;

        public String getFromStoreName() {
            return fromStoreName;
        }

        public void setFromStoreName(String fromStoreName) {
            this.fromStoreName = fromStoreName;
        }

        public String getStoreCity() {
            return storeCity;
        }

        public void setStoreCity(String storeCity) {
            this.storeCity = storeCity;
        }

        public String getStoreState() {
            return storeState;
        }

        public void setStoreState(String storeState) {
            this.storeState = storeState;
        }

        public String getBillDate() {
            return billDate;
        }

        public void setBillDate(String billDate) {
            this.billDate = billDate;
        }

        public String getBillPrice() {
            return billPrice;
        }

        public void setBillPrice(String billPrice) {
            this.billPrice = billPrice;
        }

    }


    public class Suggestion {

        @SerializedName("is_suggestion")
        @Expose
        private Boolean isSuggestion;
        @SerializedName("suggestion")
        @Expose
        private String suggestion;

        public Boolean getIsSuggestion() {
            return isSuggestion;
        }

        public void setIsSuggestion(Boolean isSuggestion) {
            this.isSuggestion = isSuggestion;
        }

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }

    }
}