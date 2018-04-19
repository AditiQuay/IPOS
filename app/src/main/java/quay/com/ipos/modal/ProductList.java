package quay.com.ipos.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductList {

@SerializedName("ProductList")
@Expose
private ArrayList<Product> productList = new ArrayList<>();

public ArrayList<Product> getProduct() {
return productList;
}

public void setProduct(ArrayList<Product> productList) {
this.productList = productList;
}

    public class Product {

        @SerializedName("iProductModalId")
        @Expose
        private Integer iProductModalId;
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

        public Integer getIProductModalId() {
            return iProductModalId;
        }

        public void setIProductModalId(Integer iProductModalId) {
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