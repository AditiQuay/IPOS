package quay.com.ipos.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PrintViewResult {

    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("businessPlaceName")
    @Expose
    private String businessPlaceName;
    @SerializedName("locationPhone1")
    @Expose
    private String locationPhone1;
    @SerializedName("locationEmail1")
    @Expose
    private String locationEmail1;
    @SerializedName("gstin")
    @Expose
    private String gstin;
    @SerializedName("cin")
    @Expose
    private String cin;
    @SerializedName("orderNo")
    @Expose
    private String orderNo;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("totalQty")
    @Expose
    private String totalQty;
    @SerializedName("totalItem")
    @Expose
    private String totalItem;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("totalSaleAmount")
    @Expose
    private String totalSaleAmount;
    @SerializedName("totalDiscountAmount")
    @Expose
    private String totalDiscountAmount;
    @SerializedName("netTotalAmount")
    @Expose
    private String netTotalAmount;
    @SerializedName("totalCgst")
    @Expose
    private String totalCgst;
    @SerializedName("totalSgst")
    @Expose
    private String totalSgst;
    @SerializedName("totalIgst")
    @Expose
    private String totalIgst;
    @SerializedName("roundingOff")
    @Expose
    private String roundingOff;
    @SerializedName("totalPointsToRedeem")
    @Expose
    private String totalPointsToRedeem;
    @SerializedName("totalPointsToRedeemValue")
    @Expose
    private String totalPointsToRedeemValue;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("itemDetails")
    @Expose
    private ArrayList<ItemDetail> itemDetails = new ArrayList<>();
    @SerializedName("paymentsDetails")
    @Expose
    private ArrayList<PaymentsDetail> paymentsDetails = new ArrayList<>();
    @SerializedName("gstSummary")
    @Expose
    private ArrayList<GstSummary> gstSummary = new ArrayList<>();

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getBusinessPlaceName() {
        return businessPlaceName;
    }

    public void setBusinessPlaceName(String businessPlaceName) {
        this.businessPlaceName = businessPlaceName;
    }

    public String getTotalPointsToRedeem() {
        return totalPointsToRedeem;
    }

    public void setTotalPointsToRedeem(String totalPointsToRedeem) {
        this.totalPointsToRedeem = totalPointsToRedeem;
    }

    public String getTotalPointsToRedeemValue() {
        return totalPointsToRedeemValue;
    }

    public void setTotalPointsToRedeemValue(String totalPointsToRedeemValue) {
        this.totalPointsToRedeemValue = totalPointsToRedeemValue;
    }

    public String getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(String totalItem) {
        this.totalItem = totalItem;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalSaleAmount() {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(String totalSaleAmount) {
        this.totalSaleAmount = totalSaleAmount;
    }

    public String getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(String totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public String getNetTotalAmount() {
        return netTotalAmount;
    }

    public void setNetTotalAmount(String netTotalAmount) {
        this.netTotalAmount = netTotalAmount;
    }

    public String getTotalCgst() {
        return totalCgst;
    }

    public void setTotalCgst(String totalCgst) {
        this.totalCgst = totalCgst;
    }

    public String getTotalSgst() {
        return totalSgst;
    }

    public void setTotalSgst(String totalSgst) {
        this.totalSgst = totalSgst;
    }

    public String getTotalIgst() {
        return totalIgst;
    }

    public void setTotalIgst(String totalIgst) {
        this.totalIgst = totalIgst;
    }

    public String getRoundingOff() {
        return roundingOff;
    }

    public void setRoundingOff(String roundingOff) {
        this.roundingOff = roundingOff;
    }

    public String getLocationPhone1() {
        return locationPhone1;
    }

    public void setLocationPhone1(String locationPhone1) {
        this.locationPhone1 = locationPhone1;
    }

    public String getLocationEmail1() {
        return locationEmail1;
    }

    public void setLocationEmail1(String locationEmail1) {
        this.locationEmail1 = locationEmail1;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public ArrayList<ItemDetail> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ArrayList<ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public ArrayList<PaymentsDetail> getPaymentsDetails() {
        return paymentsDetails;
    }

    public void setPaymentsDetails(ArrayList<PaymentsDetail> paymentsDetails) {
        this.paymentsDetails = paymentsDetails;
    }

    public ArrayList<GstSummary> getGstSummary() {
        return gstSummary;
    }

    public void setGstSummary(ArrayList<GstSummary> gstSummary) {
        this.gstSummary = gstSummary;
    }
    public class PaymentsDetail {

        @SerializedName("orderNo")
        @Expose
        private String orderNo;
        @SerializedName("returnValue")
        @Expose
        private String returnValue;
        @SerializedName("cardType")
        @Expose
        private String cardType;
        @SerializedName("paymentID")
        @Expose
        private String paymentID;
        @SerializedName("modeOfPayment")
        @Expose
        private String modeOfPayment;
        @SerializedName("amount")
        @Expose
        private Double amount;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("cardNo")
        @Expose
        private String cardNo;
        @SerializedName("nameOnCard")
        @Expose
        private String nameOnCard;
        @SerializedName("expiryDate")
        @Expose
        private String expiryDate;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPaymentID() {
            return paymentID;
        }

        public void setPaymentID(String paymentID) {
            this.paymentID = paymentID;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getModeOfPayment() {
            return modeOfPayment;
        }

        public void setModeOfPayment(String modeOfPayment) {
            this.modeOfPayment = modeOfPayment;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getNameOnCard() {
            return nameOnCard;
        }

        public void setNameOnCard(String nameOnCard) {
            this.nameOnCard = nameOnCard;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getReturnValue() {
            return returnValue;
        }

        public void setReturnValue(String returnValue) {
            this.returnValue = returnValue;
        }
    }
    public class ItemDetail {

        @SerializedName("materialName")
        @Expose
        private String materialName;
        @SerializedName("hsnCode")
        @Expose
        private String hsnCode;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("unitPrice")
        @Expose
        private Double unitPrice;
        @SerializedName("totalPrice")
        @Expose
        private Double totalPrice;
        @SerializedName("iGSTRate")
        @Expose
        private Double iGSTRate;
        @SerializedName("iGSTValue")
        @Expose
        private Double iGSTValue;
        @SerializedName("isFreeItem")
        @Expose
        private Boolean isFreeItem;
        @SerializedName("cGSTRate")
        @Expose
        private Double cGSTRate;
        @SerializedName("cGSTValue")
        @Expose
        private Double cGSTValue;
        @SerializedName("sGSTRate")
        @Expose
        private Double sGSTRate;
        @SerializedName("sGSTValue")
        @Expose
        private Double sGSTValue;
        @SerializedName("discountValue")
        @Expose
        private Double discountValue;

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public String getHsnCode() {
            return hsnCode;
        }

        public void setHsnCode(String hsnCode) {
            this.hsnCode = hsnCode;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(Double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Double getIGSTRate() {
            return iGSTRate;
        }

        public void setIGSTRate(Double iGSTRate) {
            this.iGSTRate = iGSTRate;
        }

        public Double getIGSTValue() {
            return iGSTValue;
        }

        public void setIGSTValue(Double iGSTValue) {
            this.iGSTValue = iGSTValue;
        }

        public Boolean getIsFreeItem() {
            return isFreeItem;
        }

        public void setIsFreeItem(Boolean isFreeItem) {
            this.isFreeItem = isFreeItem;
        }

        public Double getCGSTRate() {
            return cGSTRate;
        }

        public void setCGSTRate(Double cGSTRate) {
            this.cGSTRate = cGSTRate;
        }

        public Double getCGSTValue() {
            return cGSTValue;
        }

        public void setCGSTValue(Double cGSTValue) {
            this.cGSTValue = cGSTValue;
        }

        public Double getSGSTRate() {
            return sGSTRate;
        }

        public void setSGSTRate(Double sGSTRate) {
            this.sGSTRate = sGSTRate;
        }

        public Double getSGSTValue() {
            return sGSTValue;
        }

        public void setSGSTValue(Double sGSTValue) {
            this.sGSTValue = sGSTValue;
        }

        public Double getDiscountValue() {
            return discountValue;
        }

        public void setDiscountValue(Double discountValue) {
            this.discountValue = discountValue;
        }

    }

    public class GstSummary {

        @SerializedName("cGSTRate")
        @Expose
        private Double cGSTRate;
        @SerializedName("sGSTRate")
        @Expose
        private Double sGSTRate;
        @SerializedName("cGSTValue")
        @Expose
        private Double cGSTValue;
        @SerializedName("sGSTValue")
        @Expose
        private Double sGSTValue;
        @SerializedName("totalPrice")
        @Expose
        private Double totalPrice;

        public Double getCGSTRate() {
            return cGSTRate;
        }

        public void setCGSTRate(Double cGSTRate) {
            this.cGSTRate = cGSTRate;
        }

        public Double getSGSTRate() {
            return sGSTRate;
        }

        public void setSGSTRate(Double sGSTRate) {
            this.sGSTRate = sGSTRate;
        }

        public Double getCGSTValue() {
            return cGSTValue;
        }

        public void setCGSTValue(Double cGSTValue) {
            this.cGSTValue = cGSTValue;
        }

        public Double getSGSTValue() {
            return sGSTValue;
        }

        public void setSGSTValue(Double sGSTValue) {
            this.sGSTValue = sGSTValue;
        }

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }

    }
}