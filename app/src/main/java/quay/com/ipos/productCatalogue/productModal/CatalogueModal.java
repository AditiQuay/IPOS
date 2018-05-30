package quay.com.ipos.productCatalogue.productModal;

import io.realm.RealmObject;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class CatalogueModal extends RealmObject {
    public String count;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String companyName;
    public String productId;

    public String getsProductUrl() {
        return sProductUrl;
    }

    public void setsProductUrl(String sProductUrl) {
        this.sProductUrl = sProductUrl;
    }

    public String sProductUrl;
    public String storeID;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getsProductFeature() {
        return sProductFeature;
    }

    public void setsProductFeature(String sProductFeature) {
        this.sProductFeature = sProductFeature;
    }

    public String getsProductPrice() {
        return sProductPrice;
    }

    public void setsProductPrice(String sProductPrice) {
        this.sProductPrice = sProductPrice;
    }

    public String getsDataSheet() {
        return sDataSheet;
    }

    public void setsDataSheet(String sDataSheet) {
        this.sDataSheet = sDataSheet;
    }

    public String getsPoints() {
        return sPoints;
    }

    public void setsPoints(String sPoints) {
        this.sPoints = sPoints;
    }

    public boolean getIsOnOffer() {
        return isOnOffer;
    }

    public void setIsOnOffer(boolean isOnOffer) {
        this.isOnOffer = isOnOffer;
    }

    public boolean getIsCalculator() {
        return isCalculator;
    }

    public void setIsCalculator(boolean isCalculator) {
        this.isCalculator = isCalculator;
    }

    public boolean getIsDataSheet() {
        return isDataSheet;
    }

    public void setIsDataSheet(boolean isDataSheet) {
        this.isDataSheet = isDataSheet;
    }

    public String productCode;
    public String sProductName;
    public String sProductFeature;
    public String sProductPrice;
    public String sDataSheet;
    public String sPoints;
    public boolean isOnOffer;
    public boolean isCalculator;
    public boolean isDataSheet;

    public CatalogueModal() {

    }

}
