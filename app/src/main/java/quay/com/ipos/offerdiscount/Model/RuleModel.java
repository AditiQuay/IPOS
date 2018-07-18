package quay.com.ipos.offerdiscount.Model;

/**
 * Created by aditi.bhuranda on 16-07-2018.
 */

public class RuleModel {
    int iOPSScopeID;
    String sProduct;
    int iProductGroup;
    int iProductCategory;
    int iSubCategory;
    String sSKU;
    String sBrand;
    int iRuleID;
    int iRuleNumber;
    String sRuleType;
    int iPredecessors;
    int iSequence;
    String sSchemeType;
    String sStartDate;
    String sEndDate;
    String sRuleNotes;
    int iMaterialBucketID;
    String sEligibilityBasedOn;
    String sDiscountType;
    String sDiscountBasedOn;
    int iValue;
    int iFrom;
    int iTo;
  boolean isCrossBundle;
  String mCriteria;

    public boolean isCrossBundle() {
        return isCrossBundle;
    }

    public void setCrossBundle(boolean crossBundle) {
        isCrossBundle = crossBundle;
    }

    public String getmCriteria() {
        return mCriteria;
    }

    public void setmCriteria(String mCriteria) {
        this.mCriteria = mCriteria;
    }

    public int getiValue() {
        return iValue;
    }

    public void setiValue(int iValue) {
        this.iValue = iValue;
    }

    public int getiFrom() {
        return iFrom;
    }

    public void setiFrom(int iFrom) {
        this.iFrom = iFrom;
    }

    public int getiTo() {
        return iTo;
    }

    public void setiTo(int iTo) {
        this.iTo = iTo;
    }

    public int getiOPSScopeID() {
        return iOPSScopeID;
    }

    public void setiOPSScopeID(int iOPSScopeID) {
        this.iOPSScopeID = iOPSScopeID;
    }

    public String getsProduct() {
        return sProduct;
    }

    public void setsProduct(String sProduct) {
        this.sProduct = sProduct;
    }

    public int getiProductGroup() {
        return iProductGroup;
    }

    public void setiProductGroup(int iProductGroup) {
        this.iProductGroup = iProductGroup;
    }

    public int getiProductCategory() {
        return iProductCategory;
    }

    public void setiProductCategory(int iProductCategory) {
        this.iProductCategory = iProductCategory;
    }

    public int getiSubCategory() {
        return iSubCategory;
    }

    public void setiSubCategory(int iSubCategory) {
        this.iSubCategory = iSubCategory;
    }

    public String getsSKU() {
        return sSKU;
    }

    public void setsSKU(String sSKU) {
        this.sSKU = sSKU;
    }

    public String getsBrand() {
        return sBrand;
    }

    public void setsBrand(String sBrand) {
        this.sBrand = sBrand;
    }

    public int getiRuleID() {
        return iRuleID;
    }

    public void setiRuleID(int iRuleID) {
        this.iRuleID = iRuleID;
    }

    public int getiRuleNumber() {
        return iRuleNumber;
    }

    public void setiRuleNumber(int iRuleNumber) {
        this.iRuleNumber = iRuleNumber;
    }

    public String getsRuleType() {
        return sRuleType;
    }

    public void setsRuleType(String sRuleType) {
        this.sRuleType = sRuleType;
    }

    public int getiPredecessors() {
        return iPredecessors;
    }

    public void setiPredecessors(int iPredecessors) {
        this.iPredecessors = iPredecessors;
    }

    public int getiSequence() {
        return iSequence;
    }

    public void setiSequence(int iSequence) {
        this.iSequence = iSequence;
    }

    public String getsSchemeType() {
        return sSchemeType;
    }

    public void setsSchemeType(String sSchemeType) {
        this.sSchemeType = sSchemeType;
    }

    public String getsStartDate() {
        return sStartDate;
    }

    public void setsStartDate(String sStartDate) {
        this.sStartDate = sStartDate;
    }

    public String getsEndDate() {
        return sEndDate;
    }

    public void setsEndDate(String sEndDate) {
        this.sEndDate = sEndDate;
    }

    public String getsRuleNotes() {
        return sRuleNotes;
    }

    public void setsRuleNotes(String sRuleNotes) {
        this.sRuleNotes = sRuleNotes;
    }

    public int getiMaterialBucketID() {
        return iMaterialBucketID;
    }

    public void setiMaterialBucketID(int iMaterialBucketID) {
        this.iMaterialBucketID = iMaterialBucketID;
    }

    public String getsEligibilityBasedOn() {
        return sEligibilityBasedOn;
    }

    public void setsEligibilityBasedOn(String sEligibilityBasedOn) {
        this.sEligibilityBasedOn = sEligibilityBasedOn;
    }

    public String getsDiscountType() {
        return sDiscountType;
    }

    public void setsDiscountType(String sDiscountType) {
        this.sDiscountType = sDiscountType;
    }

    public String getsDiscountBasedOn() {
        return sDiscountBasedOn;
    }

    public void setsDiscountBasedOn(String sDiscountBasedOn) {
        this.sDiscountBasedOn = sDiscountBasedOn;
    }
}
