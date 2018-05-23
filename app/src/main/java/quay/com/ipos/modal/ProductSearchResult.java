package quay.com.ipos.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductSearchResult {

    @SerializedName("isOTC")
    @Expose
    private Boolean isOTC;
    @SerializedName("otcPerc")
    @Expose
    private Integer otcPerc;
    @SerializedName("otcValue")
    @Expose
    private Integer otcValue;
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;

    public Boolean getIsOTC() {
        return isOTC;
    }

    public void setIsOTC(Boolean isOTC) {
        this.isOTC = isOTC;
    }

    public Integer getOtcPerc() {
        return otcPerc;
    }

    public void setOtcPerc(Integer otcPerc) {
        this.otcPerc = otcPerc;
    }

    public Integer getOtcValue() {
        return otcValue;
    }

    public void setOtcValue(Integer otcValue) {
        this.otcValue = otcValue;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("iProductModalId")
        @Expose
        private String iProductModalId;
        @SerializedName("sProductName")
        @Expose
        private String sProductName;
        @SerializedName("sProductFeature")
        @Expose
        private String sProductFeature;
        @SerializedName("productImage")
        @Expose
        private String productImage;
        @SerializedName("sProductPrice")
        @Expose
        private Double sProductPrice;
        @SerializedName("sProductStock")
        @Expose
        private Integer sProductStock;
        @SerializedName("sProductWeight")
        @Expose
        private Integer sProductWeight;
        @SerializedName("isDiscount")
        @Expose
        private Boolean isDiscount;
        @SerializedName("gstPerc")
        @Expose
        private Double gstPerc;
        @SerializedName("cgst")
        @Expose
        private Double cgst;
        @SerializedName("sgst")
        @Expose
        private Double sgst;
        @SerializedName("salesPrice")
        @Expose
        private Double salesPrice;
        @SerializedName("nrv")
        @Expose
        private Double nrv;
        @SerializedName("gpl")
        @Expose
        private Double gpl;
        @SerializedName("mrp")
        @Expose
        private Double mrp;
        @SerializedName("barCodeNumber")
        @Expose
        private String barCodeNumber;
        @SerializedName("discount")
        @Expose
        private ArrayList<Discount> discount = null;


        private boolean isAdded=false;
        private boolean isNonCheckOutAdded=false;
        private double OTCDiscount;
        private boolean discSelected = false;
        private boolean discItemSelected = false;
        private boolean ItemSelected = false;
        private boolean OTCselected = false;
        private boolean isEdited = false;

        private int qty=1;
        private double totalPrice;
        private double discountPrice;
        private int totalQty;
        private double totalDiscountPrice;

        public String getIProductModalId() {
            return iProductModalId;
        }

        public void setIProductModalId(String iProductModalId) {
            this.iProductModalId = iProductModalId;
        }

        public boolean isNonCheckOutAdded() {
            return isNonCheckOutAdded;
        }

        public void setNonCheckOutAdded(boolean nonCheckOutAdded) {
            isNonCheckOutAdded = nonCheckOutAdded;
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

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public Double getSProductPrice() {
            return sProductPrice;
        }

        public void setSProductPrice(Double sProductPrice) {
            this.sProductPrice = sProductPrice;
        }

        public Integer getSProductStock() {
            return sProductStock;
        }

        public void setSProductStock(Integer sProductStock) {
            this.sProductStock = sProductStock;
        }

        public Integer getSProductWeight() {
            return sProductWeight;
        }

        public void setSProductWeight(Integer sProductWeight) {
            this.sProductWeight = sProductWeight;
        }

        public Boolean getIsDiscount() {
            return isDiscount;
        }

        public void setIsDiscount(Boolean isDiscount) {
            this.isDiscount = isDiscount;
        }

        public Double getGstPerc() {
            return gstPerc;
        }

        public void setGstPerc(Double gstPerc) {
            this.gstPerc = gstPerc;
        }

        public Double getCgst() {
            return cgst;
        }

        public void setCgst(Double cgst) {
            this.cgst = cgst;
        }

        public Double getSgst() {
            return sgst;
        }

        public void setSgst(Double sgst) {
            this.sgst = sgst;
        }

        public Double getSalesPrice() {
            return salesPrice;
        }

        public void setSalesPrice(Double salesPrice) {
            this.salesPrice = salesPrice;
        }

        public Double getNrv() {
            return nrv;
        }

        public void setNrv(Double nrv) {
            this.nrv = nrv;
        }

        public Double getGpl() {
            return gpl;
        }

        public void setGpl(Double gpl) {
            this.gpl = gpl;
        }

        public Double getMrp() {
            return mrp;
        }

        public void setMrp(Double mrp) {
            this.mrp = mrp;
        }

        public String getBarCodeNumber() {
            return barCodeNumber;
        }

        public void setBarCodeNumber(String barCodeNumber) {
            this.barCodeNumber = barCodeNumber;
        }

        public ArrayList<Discount> getDiscount() {
            return discount;
        }

        public void setDiscount(ArrayList<Discount> discount) {
            this.discount = discount;
        }
        public double getOTCDiscount() {
            return OTCDiscount;
        }

        public void setOTCDiscount(double OTCDiscount) {
            this.OTCDiscount = OTCDiscount;
        }

        public boolean isDiscSelected() {
            return discSelected;
        }

        public void setDiscSelected(boolean discSelected) {
            this.discSelected = discSelected;
        }

        public boolean isDiscItemSelected() {
            return discItemSelected;
        }

        public void setDiscItemSelected(boolean discItemSelected) {
            this.discItemSelected = discItemSelected;
        }

        // custom methods
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

        public boolean isEdited() {
            return isEdited;
        }

        public void setEdited(boolean edited) {
            isEdited = edited;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public boolean isAdded() {
            return isAdded;
        }

        public void setAdded(boolean added) {
            isAdded = added;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public double getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(double discountPrice) {
            this.discountPrice = discountPrice;
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
    }


    public class Discount {

        @SerializedName("sDiscountName")
        @Expose
        private String sDiscountName;
        @SerializedName("sDiscountDisplayName")
        @Expose
        private String sDiscountDisplayName;
        @SerializedName("rule")
        @Expose
        private ArrayList<Rule> rule = null;

        public String getSDiscountName() {
            return sDiscountName;
        }

        public void setSDiscountName(String sDiscountName) {
            this.sDiscountName = sDiscountName;
        }

        public String getSDiscountDisplayName() {
            return sDiscountDisplayName;
        }

        public void setSDiscountDisplayName(String sDiscountDisplayName) {
            this.sDiscountDisplayName = sDiscountDisplayName;
        }

        public ArrayList<Rule> getRule() {
            return rule;
        }

        public void setRule(ArrayList<Rule> rule) {
            this.rule = rule;
        }

    }

    public class Rule {

        @SerializedName("sDiscountType")
        @Expose
        private String sDiscountType;
        @SerializedName("sDiscountValue")
        @Expose
        private Integer sDiscountValue;
        @SerializedName("sEligibilityBasedOn")
        @Expose
        private String sEligibilityBasedOn;
        @SerializedName("sDiscountBasedOn")
        @Expose
        private String sDiscountBasedOn;
        @SerializedName("slabFrom")
        @Expose
        private Integer slabFrom;
        @SerializedName("slabTO")
        @Expose
        private Integer slabTO;
        @SerializedName("packSize")
        @Expose
        private Integer packSize;
        @SerializedName("isCrossBundle")
        @Expose
        private Boolean isCrossBundle;
        @SerializedName("opsCriteria")
        @Expose
        private String opsCriteria;
        @SerializedName("ruleType")
        @Expose
        private String ruleType;
        @SerializedName("ruleNotes")
        @Expose
        private String ruleNotes;
        @SerializedName("ruleID")
        @Expose
        private Integer ruleID;
        @SerializedName("ruleNumber")
        @Expose
        private Integer ruleNumber;
        @SerializedName("ruleSequence")
        @Expose
        private Integer ruleSequence;
        @SerializedName("ruleProdecessors")
        @Expose
        private Integer ruleProdecessors;

        public String getSDiscountType() {
            return sDiscountType;
        }

        public void setSDiscountType(String sDiscountType) {
            this.sDiscountType = sDiscountType;
        }

        public Integer getSDiscountValue() {
            return sDiscountValue;
        }

        public void setSDiscountValue(Integer sDiscountValue) {
            this.sDiscountValue = sDiscountValue;
        }

        public String getSEligibilityBasedOn() {
            return sEligibilityBasedOn;
        }

        public void setSEligibilityBasedOn(String sEligibilityBasedOn) {
            this.sEligibilityBasedOn = sEligibilityBasedOn;
        }

        public String getSDiscountBasedOn() {
            return sDiscountBasedOn;
        }

        public void setSDiscountBasedOn(String sDiscountBasedOn) {
            this.sDiscountBasedOn = sDiscountBasedOn;
        }

        public Integer getSlabFrom() {
            return slabFrom;
        }

        public void setSlabFrom(Integer slabFrom) {
            this.slabFrom = slabFrom;
        }

        public Integer getSlabTO() {
            return slabTO;
        }

        public void setSlabTO(Integer slabTO) {
            this.slabTO = slabTO;
        }

        public Integer getPackSize() {
            return packSize;
        }

        public void setPackSize(Integer packSize) {
            this.packSize = packSize;
        }

        public Boolean getIsCrossBundle() {
            return isCrossBundle;
        }

        public void setIsCrossBundle(Boolean isCrossBundle) {
            this.isCrossBundle = isCrossBundle;
        }

        public String getOpsCriteria() {
            return opsCriteria;
        }

        public void setOpsCriteria(String opsCriteria) {
            this.opsCriteria = opsCriteria;
        }

        public String getRuleType() {
            return ruleType;
        }

        public void setRuleType(String ruleType) {
            this.ruleType = ruleType;
        }

        public String getRuleNotes() {
            return ruleNotes;
        }

        public void setRuleNotes(String ruleNotes) {
            this.ruleNotes = ruleNotes;
        }

        public Integer getRuleID() {
            return ruleID;
        }

        public void setRuleID(Integer ruleID) {
            this.ruleID = ruleID;
        }

        public Integer getRuleNumber() {
            return ruleNumber;
        }

        public void setRuleNumber(Integer ruleNumber) {
            this.ruleNumber = ruleNumber;
        }

        public Integer getRuleSequence() {
            return ruleSequence;
        }

        public void setRuleSequence(Integer ruleSequence) {
            this.ruleSequence = ruleSequence;
        }

        public Integer getRuleProdecessors() {
            return ruleProdecessors;
        }

        public void setRuleProdecessors(Integer ruleProdecessors) {
            this.ruleProdecessors = ruleProdecessors;
        }

    }
}