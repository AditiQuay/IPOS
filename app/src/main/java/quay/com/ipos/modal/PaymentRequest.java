package quay.com.ipos.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;

public class PaymentRequest {
    @SerializedName("customerJson")
    @Expose
    private CustomerModel customerJson;
    @SerializedName("orderTimestamp")
    @Expose
    private String orderTimestamp;
    @SerializedName("orderDateTime")
    @Expose
    private String orderDateTime;
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("pointsToRedeem")
    @Expose
    private double pointsToRedeem;
    @SerializedName("pointsToRedeemValue")
    @Expose
    private double pointsToRedeemValue;
    @SerializedName("freeItemQty")
    @Expose
    private int freeItemQty;
    @SerializedName("employeeCode")
    @Expose
    private String employeeCode;
    @SerializedName("employeeRole")
    @Expose
    private String employeeRole="";
    @SerializedName("orderValue")
    @Expose
    private Double orderValue;
    @SerializedName("orderLoyality")
    @Expose
    private Integer orderLoyality;
    @SerializedName("businessPlace")
    @Expose
    private String businessPlace;
    @SerializedName("businessPlaceCode")
    @Expose
    private Integer businessPlaceCode;
    @SerializedName("totalValueWithTax")
    @Expose
    private Double totalValueWithTax;
    @SerializedName("totalCGSTValue")
    @Expose
    private Double totalCGSTValue;
    @SerializedName("totalIGSTValue")
    @Expose
    private Double totalIGSTValue;
    @SerializedName("totalSGSTValue")
    @Expose
    private Double totalSGSTValue;
    @SerializedName("totalValueWithoutTax")
    @Expose
    private Double totalValueWithoutTax;
    @SerializedName("totalTaxValue")
    @Expose
    private Double totalTaxValue;
    @SerializedName("totalDiscountValue")
    @Expose
    private Double totalDiscountValue;
    @SerializedName("totalRoundingOffValue")
    @Expose
    private Double totalRoundingOffValue;
    @SerializedName("cartDetail")
    @Expose
    private ArrayList<CartDetail> cartDetail = new ArrayList<>();
    @SerializedName("paymentDetail")
    @Expose
    private ArrayList<PaymentDetail> paymentDetail = new ArrayList<>();
    @SerializedName("entityID")
    @Expose
    private Integer entityID;


    public CustomerModel getCustomerJson() {
        return customerJson;
    }

    public void setCustomerJson(CustomerModel customerJson) {
        this.customerJson = customerJson;
    }

    public String getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(String orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public double getPointsToRedeem() {
        return pointsToRedeem;
    }

    public void setPointsToRedeem(double pointsToRedeem) {
        this.pointsToRedeem = pointsToRedeem;
    }

    public double getPointsToRedeemValue() {
        return pointsToRedeemValue;
    }

    public void setPointsToRedeemValue(double pointsToRedeemValue) {
        this.pointsToRedeemValue = pointsToRedeemValue;
    }

    public Integer getEntityID() {
        return entityID;
    }

    public void setEntityID(Integer entityID) {
        this.entityID = entityID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public int getFreeItemQty() {
        return freeItemQty;
    }

    public void setFreeItemQty(int freeItemQty) {
        this.freeItemQty = freeItemQty;
    }

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

    public Double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Double orderValue) {
        this.orderValue = orderValue;
    }

    public Integer getOrderLoyality() {
        return orderLoyality;
    }

    public void setOrderLoyality(Integer orderLoyality) {
        this.orderLoyality = orderLoyality;
    }

    public String getBusinessPlace() {
        return businessPlace;
    }

    public void setBusinessPlace(String businessPlace) {
        this.businessPlace = businessPlace;
    }

    public Integer getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    public void setBusinessPlaceCode(Integer businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    public Double getTotalValueWithTax() {
        return totalValueWithTax;
    }

    public void setTotalValueWithTax(Double totalValueWithTax) {
        this.totalValueWithTax = totalValueWithTax;
    }

    public Double getTotalCGSTValue() {
        return totalCGSTValue;
    }

    public void setTotalCGSTValue(Double totalCGSTValue) {
        this.totalCGSTValue = totalCGSTValue;
    }

    public Double getTotalIGSTValue() {
        return totalIGSTValue;
    }

    public void setTotalIGSTValue(Double totalIGSTValue) {
        this.totalIGSTValue = totalIGSTValue;
    }

    public Double getTotalSGSTValue() {
        return totalSGSTValue;
    }

    public void setTotalSGSTValue(Double totalSGSTValue) {
        this.totalSGSTValue = totalSGSTValue;
    }

    public Double getTotalValueWithoutTax() {
        return totalValueWithoutTax;
    }

    public void setTotalValueWithoutTax(Double totalValueWithoutTax) {
        this.totalValueWithoutTax = totalValueWithoutTax;
    }

    public Double getTotalTaxValue() {
        return totalTaxValue;
    }

    public void setTotalTaxValue(Double totalTaxValue) {
        this.totalTaxValue = totalTaxValue;
    }

    public Double getTotalDiscountValue() {
        return totalDiscountValue;
    }

    public void setTotalDiscountValue(Double totalDiscountValue) {
        this.totalDiscountValue = totalDiscountValue;
    }

    public Double getTotalRoundingOffValue() {
        return totalRoundingOffValue;
    }

    public void setTotalRoundingOffValue(Double totalRoundingOffValue) {
        this.totalRoundingOffValue = totalRoundingOffValue;
    }

    public ArrayList<CartDetail> getCartDetail() {
        return cartDetail;
    }

    public void setCartDetail(ArrayList<CartDetail> cartDetail) {
        this.cartDetail = cartDetail;
    }

    public ArrayList<PaymentDetail> getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(ArrayList<PaymentDetail> paymentDetail) {
        this.paymentDetail = paymentDetail;
    }



    public class Scheme {

        @SerializedName("schemeID")
        @Expose
        private String schemeID;
        @SerializedName("ruleID")
        @Expose
        private Integer ruleID;
        @SerializedName("discountValue")
        @Expose
        private Double discountValue;
        @SerializedName("discountPerc")
        @Expose
        private Integer discountPerc;

        public String getSchemeID() {
            return schemeID;
        }

        public void setSchemeID(String schemeID) {
            this.schemeID = schemeID;
        }

        public Integer getRuleID() {
            return ruleID;
        }

        public void setRuleID(Integer ruleID) {
            this.ruleID = ruleID;
        }

        public Double getDiscountValue() {
            return discountValue;
        }

        public void setDiscountValue(Double discountValue) {
            this.discountValue = discountValue;
        }

        public Integer getDiscountPerc() {
            return discountPerc;
        }

        public void setDiscountPerc(Integer discountPerc) {
            this.discountPerc = discountPerc;
        }

    }

    public class PaymentDetail {

        @SerializedName("paymentType")
        @Expose
        private String paymentType;
        @SerializedName("detailInfo")
        @Expose
        private ArrayList<DetailInfo> detailInfo = new ArrayList<>();

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public ArrayList<DetailInfo> getDetailInfo() {
            return detailInfo;
        }

        public void setDetailInfo(ArrayList<DetailInfo> detailInfo) {
            this.detailInfo = detailInfo;
        }

    }

    public class DetailInfo {

        @SerializedName("cashReturnAmt")
        @Expose
        private Double cashReturnAmt;
        @SerializedName("cashReceivedAmt")
        @Expose
        private Double cashReceivedAmt;
        @SerializedName("totalAmt")
        @Expose
        private Double totalAmt;
        @SerializedName("cashIsCOD")
        @Expose
        private boolean cashIsCOD;
        @SerializedName("cardPaymentAmt")
        @Expose
        private Double cardPaymentAmt;
        @SerializedName("cardType")
        @Expose
        private String cardType;
        @SerializedName("cardLastDigits")
        @Expose
        private String cardLastDigits;
        @SerializedName("cardExpirationDate")
        @Expose
        private String cardExpirationDate;
        @SerializedName("cardTxnId")
        @Expose
        private String cardTxnId;
        @SerializedName("pointsToRedeem")
        @Expose
        private Double pointsToRedeem;
        @SerializedName("pointsValue")
        @Expose
        private Double pointsValue;
        @SerializedName("pointsOtp")
        @Expose
        private String pointsOtp;

        public Double getCashReturnAmt() {
            return cashReturnAmt;
        }

        public void setCashReturnAmt(Double cashReturnAmt) {
            this.cashReturnAmt = cashReturnAmt;
        }

        public Double getCashReceivedAmt() {
            return cashReceivedAmt;
        }

        public void setCashReceivedAmt(Double cashReceivedAmt) {
            this.cashReceivedAmt = cashReceivedAmt;
        }

        public Double getTotalAmt() {
            return totalAmt;
        }

        public void setTotalAmt(Double totalAmt) {
            this.totalAmt = totalAmt;
        }

        public boolean getCashIsCOD() {
            return cashIsCOD;
        }

        public void setCashIsCOD(boolean cashIsCOD) {
            this.cashIsCOD = cashIsCOD;
        }

        public Double getCardPaymentAmt() {
            return cardPaymentAmt;
        }

        public void setCardPaymentAmt(Double cardPaymentAmt) {
            this.cardPaymentAmt = cardPaymentAmt;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCardLastDigits() {
            return cardLastDigits;
        }

        public void setCardLastDigits(String cardLastDigits) {
            this.cardLastDigits = cardLastDigits;
        }

        public String getCardExpirationDate() {
            return cardExpirationDate;
        }

        public void setCardExpirationDate(String cardExpirationDate) {
            this.cardExpirationDate = cardExpirationDate;
        }

        public String getCardTxnId() {
            return cardTxnId;
        }

        public void setCardTxnId(String cardTxnId) {
            this.cardTxnId = cardTxnId;
        }

        public Double getPointsToRedeem() {
            return pointsToRedeem;
        }

        public void setPointsToRedeem(Double pointsToRedeem) {
            this.pointsToRedeem = pointsToRedeem;
        }

        public Double getPointsValue() {
            return pointsValue;
        }

        public void setPointsValue(Double pointsValue) {
            this.pointsValue = pointsValue;
        }

        public String getPointsOtp() {
            return pointsOtp;
        }

        public void setPointsOtp(String pointsOtp) {
            this.pointsOtp = pointsOtp;
        }

    }
    public class CartDetail {
        @SerializedName("isFreeItem")
        @Expose
        private boolean isFreeItem;

        @SerializedName("materialCode")
        @Expose
        private String materialCode;
        @SerializedName("materialStockAvail")
        @Expose
        private Integer materialStockAvail;
        @SerializedName("materialID")
        @Expose
        private String materialID;
        @SerializedName("materialName")
        @Expose
        private String materialName;
        @SerializedName("materialValue")
        @Expose
        private Double materialValue;
        @SerializedName("materialTotalValue")
        @Expose
        private Double materialTotalValue;
        @SerializedName("materialQty")
        @Expose
        private Integer materialQty;
        @SerializedName("materialDiscountValue")
        @Expose
        private Double materialDiscountValue;
        @SerializedName("materialUnitValue")
        @Expose
        private Double materialUnitValue;
        @SerializedName("materialCGSTRate")
        @Expose
        private Double materialCGSTRate;
        @SerializedName("materialCGSTValue")
        @Expose
        private Double materialCGSTValue;
        @SerializedName("materialSGSTRate")
        @Expose
        private Double materialSGSTRate;
        @SerializedName("materialSGSTValue")
        @Expose
        private Double materialSGSTValue;
        @SerializedName("materialIGSTRate")
        @Expose
        private Double materialIGSTRate;
        @SerializedName("materialIGSTValue")
        @Expose
        private Double materialIGSTValue;
        @SerializedName("scheme")
        @Expose
        private ArrayList<Scheme> scheme = new ArrayList<>();
        @SerializedName("discountValue")
        @Expose
        private Double discountValue;
        @SerializedName("discountPerc")
        @Expose
        private Integer discountPerc;
        private String hsnCode;

        public Double getMaterialTotalValue() {
            return materialTotalValue;
        }

        public void setMaterialTotalValue(Double materialTotalValue) {
            this.materialTotalValue = materialTotalValue;
        }

        public String getHsnCode() {
            return hsnCode;
        }

        public void setHsnCode(String hsnCode) {
            this.hsnCode = hsnCode;
        }

        public boolean getIsFreeItem() {
            return isFreeItem;
        }

        public void setIsFreeItem(boolean isFreeItem) {
            this.isFreeItem = isFreeItem;
        }

        public String getMaterialCode() {
            return materialCode;
        }

        public void setMaterialCode(String materialCode) {
            this.materialCode = materialCode;
        }

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public Double getMaterialValue() {
            return materialValue;
        }

        public void setMaterialValue(Double materialValue) {
            this.materialValue = materialValue;
        }

        public Integer getMaterialQty() {
            return materialQty;
        }

        public String getMaterialID() {
            return materialID;
        }

        public void setMaterialID(String materialID) {
            this.materialID = materialID;
        }

        public Integer getMaterialStockAvail() {
            return materialStockAvail;
        }

        public void setMaterialStockAvail(Integer materialStockAvail) {
            this.materialStockAvail = materialStockAvail;
        }

        public void setMaterialQty(Integer materialQty) {
            this.materialQty = materialQty;
        }

        public Double getMaterialDiscountValue() {
            return materialDiscountValue;
        }

        public void setMaterialDiscountValue(Double materialDiscountValue) {
            this.materialDiscountValue = materialDiscountValue;
        }

        public Double getMaterialUnitValue() {
            return materialUnitValue;
        }

        public void setMaterialUnitValue(Double materialUnitValue) {
            this.materialUnitValue = materialUnitValue;
        }

        public Double getMaterialCGSTRate() {
            return materialCGSTRate;
        }

        public void setMaterialCGSTRate(Double materialCGSTRate) {
            this.materialCGSTRate = materialCGSTRate;
        }

        public Double getMaterialCGSTValue() {
            return materialCGSTValue;
        }

        public void setMaterialCGSTValue(Double materialCGSTValue) {
            this.materialCGSTValue = materialCGSTValue;
        }

        public Double getMaterialSGSTRate() {
            return materialSGSTRate;
        }

        public void setMaterialSGSTRate(Double materialSGSTRate) {
            this.materialSGSTRate = materialSGSTRate;
        }

        public Double getMaterialSGSTValue() {
            return materialSGSTValue;
        }

        public void setMaterialSGSTValue(Double materialSGSTValue) {
            this.materialSGSTValue = materialSGSTValue;
        }

        public Double getMaterialIGSTRate() {
            return materialIGSTRate;
        }

        public void setMaterialIGSTRate(Double materialIGSTRate) {
            this.materialIGSTRate = materialIGSTRate;
        }

        public Double getMaterialIGSTValue() {
            return materialIGSTValue;
        }

        public void setMaterialIGSTValue(Double materialIGSTValue) {
            this.materialIGSTValue = materialIGSTValue;
        }

        public ArrayList<Scheme> getScheme() {
            return scheme;
        }

        public void setScheme(ArrayList<Scheme> scheme) {
            this.scheme = scheme;
        }

        public Double getDiscountValue() {
            return discountValue;
        }

        public void setDiscountValue(Double discountValue) {
            this.discountValue = discountValue;
        }

        public Integer getDiscountPerc() {
            return discountPerc;
        }

        public void setDiscountPerc(Integer discountPerc) {
            this.discountPerc = discountPerc;
        }

    }

}