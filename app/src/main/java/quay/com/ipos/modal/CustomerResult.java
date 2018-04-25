package quay.com.ipos.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CustomerResult {

@SerializedName("customer")
@Expose
private ArrayList<Customer> customer = new ArrayList<>();

public ArrayList<Customer> getCustomer() {
return customer;
}

public void setCustomer(ArrayList<Customer> customer) {
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

    }
}