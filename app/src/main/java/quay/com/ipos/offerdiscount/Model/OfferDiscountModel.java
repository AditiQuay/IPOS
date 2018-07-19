package quay.com.ipos.offerdiscount.Model;

import java.util.ArrayList;

/**
 * Created by aditi.bhuranda on 16-07-2018.
 */

public class OfferDiscountModel {

    String sName;
    String sDescription;
    String sDisplayName;
    String sDisplayImage;
    String sStartDate;
    String sEndDate;
    String sEntity;
    String sLOB;
    String sBusinessPlaces;
    String sState;
    String sCustomerGroup;
    String sPolicyDocument;
    ArrayList<RuleModel > ruleModels = new ArrayList<>();

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public String getsDisplayName() {
        return sDisplayName;
    }

    public void setsDisplayName(String sDisplayName) {
        this.sDisplayName = sDisplayName;
    }

    public String getsDisplayImage() {
        return sDisplayImage;
    }

    public void setsDisplayImage(String sDisplayImage) {
        this.sDisplayImage = sDisplayImage;
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

    public String getsEntity() {
        return sEntity;
    }

    public void setsEntity(String sEntity) {
        this.sEntity = sEntity;
    }

    public String getsLOB() {
        return sLOB;
    }

    public void setsLOB(String sLOB) {
        this.sLOB = sLOB;
    }

    public String getsBusinessPlaces() {
        return sBusinessPlaces;
    }

    public void setsBusinessPlaces(String sBusinessPlaces) {
        this.sBusinessPlaces = sBusinessPlaces;
    }

    public String getsState() {
        return sState;
    }

    public void setsState(String sState) {
        this.sState = sState;
    }

    public String getsCustomerGroup() {
        return sCustomerGroup;
    }

    public void setsCustomerGroup(String sCustomerGroup) {
        this.sCustomerGroup = sCustomerGroup;
    }

    public String getsPolicyDocument() {
        return sPolicyDocument;
    }

    public void setsPolicyDocument(String sPolicyDocument) {
        this.sPolicyDocument = sPolicyDocument;
    }

    public ArrayList<RuleModel> getRuleModels() {
        return ruleModels;
    }

    public void setRuleModels(ArrayList<RuleModel> ruleModels) {
        this.ruleModels = ruleModels;
    }
}
