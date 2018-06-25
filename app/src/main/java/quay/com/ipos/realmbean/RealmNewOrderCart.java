package quay.com.ipos.realmbean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by ankush.bansal on 04-06-2018.
 */

public class RealmNewOrderCart extends RealmObject {



    @PrimaryKey
    private String iProductModalId;
    private String productCode;
    private String sProductName;
    private String productImage;
    private int sProductPrice;
    private String sProductStock;
    private int sProductWeight;
    private boolean isDiscount;
    private int gstPerc;
    private int cgst;
    private int sgst;
    private int salesPrice;
    private int nrv;
    private int gpl;
    private int mrp;
    private String barCodeNumber;
    private int points;
    private String pointsBasedOn;
    private int valueFrom;
    private int valueTo;
    private int pointsPer;
    private boolean isReserveStock;
    private boolean isCheckStock;
    private boolean isStockDisplay;
    private String discount;
    private boolean isSync=true;
    private boolean isUpdate=true;
    private String OrderId;
    private boolean isAdded;
    private int qty;
    private int totalPrice;
    private double discountPrice;
    private int totalPoints;
    private int totalStock;
    private boolean isFreeItem;
    private boolean isRuleApplied;
    private String parentProductId;
    private int mCheckStock;
    private boolean checkStockClick;
private double accumulatedLoyality;
private String pUOM;
private String mUOM;
private double conversionFactor;


    public String getiProductModalId() {
        return iProductModalId;
    }

    public void setiProductModalId(String iProductModalId) {
        this.iProductModalId = iProductModalId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getsProductName() {
        return sProductName;
    }

    public void setsProductName(String sProductName) {
        this.sProductName = sProductName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getsProductPrice() {
        return sProductPrice;
    }

    public void setsProductPrice(int sProductPrice) {
        this.sProductPrice = sProductPrice;
    }

    public String getsProductStock() {
        return sProductStock;
    }

    public void setsProductStock(String sProductStock) {
        this.sProductStock = sProductStock;
    }

    public int getsProductWeight() {
        return sProductWeight;
    }

    public void setsProductWeight(int sProductWeight) {
        this.sProductWeight = sProductWeight;
    }

    public boolean isDiscount() {
        return isDiscount;
    }

    public void setDiscount(boolean discount) {
        isDiscount = discount;
    }

    public int getGstPerc() {
        return gstPerc;
    }

    public void setGstPerc(int gstPerc) {
        this.gstPerc = gstPerc;
    }

    public int getCgst() {
        return cgst;
    }

    public void setCgst(int cgst) {
        this.cgst = cgst;
    }

    public int getSgst() {
        return sgst;
    }

    public void setSgst(int sgst) {
        this.sgst = sgst;
    }

    public int getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(int salesPrice) {
        this.salesPrice = salesPrice;
    }

    public int getNrv() {
        return nrv;
    }

    public void setNrv(int nrv) {
        this.nrv = nrv;
    }

    public int getGpl() {
        return gpl;
    }

    public void setGpl(int gpl) {
        this.gpl = gpl;
    }

    public int getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }

    public String getBarCodeNumber() {
        return barCodeNumber;
    }

    public void setBarCodeNumber(String barCodeNumber) {
        this.barCodeNumber = barCodeNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPointsBasedOn() {
        return pointsBasedOn;
    }

    public void setPointsBasedOn(String pointsBasedOn) {
        this.pointsBasedOn = pointsBasedOn;
    }

    public int getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(int valueFrom) {
        this.valueFrom = valueFrom;
    }

    public int getValueTo() {
        return valueTo;
    }

    public void setValueTo(int valueTo) {
        this.valueTo = valueTo;
    }

    public int getPointsPer() {
        return pointsPer;
    }

    public void setPointsPer(int pointsPer) {
        this.pointsPer = pointsPer;
    }

    public boolean isReserveStock() {
        return isReserveStock;
    }

    public void setReserveStock(boolean reserveStock) {
        isReserveStock = reserveStock;
    }

    public boolean isCheckStock() {
        return isCheckStock;
    }

    public void setCheckStock(boolean checkStock) {
        isCheckStock = checkStock;
    }

    public boolean isStockDisplay() {
        return isStockDisplay;
    }

    public void setStockDisplay(boolean stockDisplay) {
        isStockDisplay = stockDisplay;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        this.OrderId = orderId;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public boolean isFreeItem() {
        return isFreeItem;
    }

    public void setFreeItem(boolean freeItem) {
        isFreeItem = freeItem;
    }

    public boolean isRuleApplied() {
        return isRuleApplied;
    }

    public void setRuleApplied(boolean ruleApplied) {
        isRuleApplied = ruleApplied;
    }

    public String getParentProductId() {
        return parentProductId;
    }

    public void setParentProductId(String parentProductId) {
        this.parentProductId = parentProductId;
    }

    public double getAccumulatedLoyality() {
        return accumulatedLoyality;
    }

    public void setAccumulatedLoyality(double accumulatedLoyality) {
        this.accumulatedLoyality = accumulatedLoyality;
    }

    public int getmCheckStock() {
        return mCheckStock;
    }

    public void setmCheckStock(int mCheckStock) {
        this.mCheckStock = mCheckStock;
    }

    public boolean isCheckStockClick() {
        return checkStockClick;
    }

    public void setCheckStockClick(boolean checkStockClick) {
        this.checkStockClick = checkStockClick;
    }

    public String getpUOM() {
        return pUOM;
    }

    public void setpUOM(String pUOM) {
        this.pUOM = pUOM;
    }

    public String getmUOM() {
        return mUOM;
    }

    public void setmUOM(String mUOM) {
        this.mUOM = mUOM;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }
}


