package quay.com.ipos.modal;

/**
 * Created by aditi.bhuranda on 05-07-2018.
 */

public class ManageMaterialMasterModel {
   /* {
        "employeeCode": "6000013",
            "businessPlaceCode": "1",
            "materialCode": "Test",
            "description": "Testing description",
            "displayName": "Test",
            "grossWt": 500,
            "netWt": 400,
            "basicUOM": "ltr",
            "saleUOM": "ml",
            "conversionMultiplier": 1000,
            "hsnCode": "hsntest",
            "productCode": "ACETOXY1",
            "dimensionL": 100,
            "dimensionB": 200,
            "dimensionH": 300,
            "materialTypeID": "Insectiside",
            "imageID": 1,
            "loyaltyTableRef": "MaterialBaseLoyalty",
            "isRedeemAllow": "1",
            "barCodeNumber": "asdfghjkl",
            "isMaterialSerial": "0",
            "isMaterialBatch": "1",
            "isShelfLife": "1",
            "shelfLifeInDays": "50",
            "isActive": "1",
            "materialClass": "Insectiside",
            "purchaseUOM": "ltr",
            "basicPrice": 100,
            "nrv": 90,
            "gpl": 90,
            "mrp": 150,
            "imageBaseString": "",
            "imageType": ""
    }
*/
    String materialCode;
    String description;
    String displayName;
    Double grossWt;
    Double netWt;
    String basicUOM;
    String saleUOM;
    double conversionMultiplier=0.0;
    String HSNCode;
    String productCode;
    double dimensionL;
    double dimensionB;
    double dimensionH;
    String materialTypeID;
    Integer imageID;
    String loyaltyTableRef;
    String isRedeemAllow;
    String barCodeNumber;
    String isMaterialSerial;
    String isMaterialBatch;
    String isShelfLife;
    String shelfLifeInDays;
    String isActive;
    String materialClass;
    String purchaseUOM;
    double basicPrice ;
    double nrv ;
    double gpl ;
    double mrp ;
    String imageBaseString ;
    String imageType ;
    String  employeeCode ;
    String businessPlaceCode ;

    public String getImageBaseString() {
        return imageBaseString;
    }

    public void setImageBaseString(String imageBaseString) {
        this.imageBaseString = imageBaseString;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    public void setBusinessPlaceCode(String businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    public double getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(double basicPrice) {
        this.basicPrice = basicPrice;
    }

    public double getNrv() {
        return nrv;
    }

    public void setNrv(double nrv) {
        this.nrv = nrv;
    }

    public double getGpl() {
        return gpl;
    }

    public void setGpl(double gpl) {
        this.gpl = gpl;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Double getGrossWt() {
        return grossWt;
    }

    public void setGrossWt(Double grossWt) {
        this.grossWt = grossWt;
    }

    public Double getNetWt() {
        return netWt;
    }

    public void setNetWt(Double netWt) {
        this.netWt = netWt;
    }

    public String getBasicUOM() {
        return basicUOM;
    }

    public void setBasicUOM(String basicUOM) {
        this.basicUOM = basicUOM;
    }

    public String getSaleUOM() {
        return saleUOM;
    }

    public void setSaleUOM(String saleUOM) {
        this.saleUOM = saleUOM;
    }

    public double getConversionMultiplier() {
        return conversionMultiplier;
    }

    public void setConversionMultiplier(double conversionMultiplier) {
        this.conversionMultiplier = conversionMultiplier;
    }

    public String getHSNCode() {
        return HSNCode;
    }

    public void setHSNCode(String HSNCode) {
        this.HSNCode = HSNCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getDimensionL() {
        return dimensionL;
    }

    public void setDimensionL(double dimensionL) {
        this.dimensionL = dimensionL;
    }

    public double getDimensionB() {
        return dimensionB;
    }

    public void setDimensionB(double dimensionB) {
        this.dimensionB = dimensionB;
    }

    public double getDimensionH() {
        return dimensionH;
    }

    public void setDimensionH(double dimensionH) {
        this.dimensionH = dimensionH;
    }

    public String getMaterialTypeID() {
        return materialTypeID;
    }

    public void setMaterialTypeID(String materialTypeID) {
        this.materialTypeID = materialTypeID;
    }

    public Integer getImageID() {
        return imageID;
    }

    public void setImageID(Integer imageID) {
        this.imageID = imageID;
    }

    public String getLoyaltyTableRef() {
        return loyaltyTableRef;
    }

    public void setLoyaltyTableRef(String loyaltyTableRef) {
        this.loyaltyTableRef = loyaltyTableRef;
    }

    public String getIsRedeemAllow() {
        return isRedeemAllow;
    }

    public void setIsRedeemAllow(String isRedeemAllow) {
        this.isRedeemAllow = isRedeemAllow;
    }

    public String getBarCodeNumber() {
        return barCodeNumber;
    }

    public void setBarCodeNumber(String barCodeNumber) {
        this.barCodeNumber = barCodeNumber;
    }

    public String getIsMaterialSerial() {
        return isMaterialSerial;
    }

    public void setIsMaterialSerial(String isMaterialSerial) {
        this.isMaterialSerial = isMaterialSerial;
    }

    public String getIsMaterialBatch() {
        return isMaterialBatch;
    }

    public void setIsMaterialBatch(String isMaterialBatch) {
        this.isMaterialBatch = isMaterialBatch;
    }

    public String getIsShelfLife() {
        return isShelfLife;
    }

    public void setIsShelfLife(String isShelfLife) {
        this.isShelfLife = isShelfLife;
    }

    public String getShelfLifeInDays() {
        return shelfLifeInDays;
    }

    public void setShelfLifeInDays(String shelfLifeInDays) {
        this.shelfLifeInDays = shelfLifeInDays;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getMaterialClass() {
        return materialClass;
    }

    public void setMaterialClass(String materialClass) {
        this.materialClass = materialClass;
    }

    public String getPurchaseUOM() {
        return purchaseUOM;
    }

    public void setPurchaseUOM(String purchaseUOM) {
        this.purchaseUOM = purchaseUOM;
    }
}
