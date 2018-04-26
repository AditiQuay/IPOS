package quay.com.ipos.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;

public class ProductList {


    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }
    public class Datum {

        @SerializedName("iProductModalId")
        @Expose
        private Double iProductModalId;
        @SerializedName("sProductName")
        @Expose
        private String sProductName;
        @SerializedName("sProductFeature")
        @Expose
        private String sProductFeature;
        @SerializedName("sProductPrice")
        @Expose
        private String sProductPrice;
        @SerializedName("sProductPoints")
        @Expose
        private String sProductPoints;
        @SerializedName("sProductWeight")
        @Expose
        private String sProductWeight;
        @SerializedName("isDiscount")
        @Expose
        private Boolean isDiscount;
        @SerializedName("sDiscountName")
        @Expose
        private String sDiscountName;
        @SerializedName("sDiscountPrice")
        @Expose
        private String sDiscountPrice;
        private double OTCDiscount;
        private boolean discSelected = false;
        private boolean discItemSelected = false;
        private boolean ItemSelected = false;
        private boolean OTCselected = false;
        private boolean isEdited = false;

        private int qty=1;
        private double totalPrice;
        private double discount;
        private int totalQty;
        private double totalDiscountPrice;

        public double getOTCDiscount() {
            return OTCDiscount;
        }

        public void setOTCDiscount(double OTCDiscount) {
            this.OTCDiscount = OTCDiscount;
        }

        @SerializedName("GSTPerc")
        @Expose
        private Integer gSTPerc;
        @SerializedName("CGST")
        @Expose
        private Integer cGST;
        @SerializedName("SGST")
        @Expose
        private Integer sGST;

        public Integer getGSTPerc() {
            return gSTPerc;
        }

        public void setGSTPerc(Integer gSTPerc) {
            this.gSTPerc = gSTPerc;
        }

        public Integer getCGST() {
            return cGST;
        }

        public void setCGST(Integer cGST) {
            this.cGST = cGST;
        }

        public Integer getSGST() {
            return sGST;
        }

        public void setSGST(Integer sGST) {
            this.sGST = sGST;
        }

        public boolean isDiscItemSelected() {
            return discItemSelected;
        }

        public void setDiscItemSelected(boolean discItemSelected) {
            this.discItemSelected = discItemSelected;
        }

        public boolean isEdited() {
            return isEdited;
        }

        public void setEdited(boolean edited) {
            isEdited = edited;
        }

        public boolean isItemSelected() {
            return ItemSelected;
        }

        public void setItemSelected(boolean itemSelected) {
            ItemSelected = itemSelected;
        }

        public boolean isOTCselected() {
            return OTCselected;
        }

        public void setOTCselected(boolean OTCselected) {
            this.OTCselected = OTCselected;
        }

        public boolean isDiscSelected() {
            return discSelected;
        }

        public void setDiscSelected(boolean discSelected) {
            this.discSelected = discSelected;
        }

        public int getTotalQty() {
            return totalQty;
        }

        public void setTotalQty(int totalQty) {
            this.totalQty = totalQty;
        }

        public double getTotalDiscountPrice() {
            return totalDiscountPrice;
        }

        public void setTotalDiscountPrice(double totalDiscountPrice) {
            this.totalDiscountPrice = totalDiscountPrice;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public Double getIProductModalId() {
            return iProductModalId;
        }

        public void setIProductModalId(Double iProductModalId) {
            this.iProductModalId = iProductModalId;
        }

        public String getSProductName() {
            return sProductName;
        }

        public void setSProductName(String sProductName) {
            this.sProductName = sProductName;
        }

        public String getSProductFeature() {
            return sProductFeature;
        }

        public void setSProductFeature(String sProductFeature) {
            this.sProductFeature = sProductFeature;
        }

        public String getSProductPrice() {
            return sProductPrice;
        }

        public void setSProductPrice(String sProductPrice) {
            this.sProductPrice = sProductPrice;
        }

        public String getSProductPoints() {
            return sProductPoints;
        }

        public void setSProductPoints(String sProductPoints) {
            this.sProductPoints = sProductPoints;
        }

        public String getSProductWeight() {
            return sProductWeight;
        }

        public void setSProductWeight(String sProductWeight) {
            this.sProductWeight = sProductWeight;
        }

        public Boolean getIsDiscount() {
            return isDiscount;
        }

        public void setIsDiscount(Boolean isDiscount) {
            this.isDiscount = isDiscount;
        }

        public String getSDiscountName() {
            return sDiscountName;
        }

        public void setSDiscountName(String sDiscountName) {
            this.sDiscountName = sDiscountName;
        }

        public String getSDiscountPrice() {
            return sDiscountPrice;
        }

        public void setSDiscountPrice(String sDiscountPrice) {
            this.sDiscountPrice = sDiscountPrice;
        }

    }
}