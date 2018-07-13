package quay.com.ipos.ddrsales.model.request;

import java.util.List;

import quay.com.ipos.ddrsales.model.DDR;
import quay.com.ipos.ddrsales.model.LogisticsData;
import quay.com.ipos.ddrsales.model.RealmDDROrderList;
import quay.com.ipos.ddrsales.model.response.Address;
import quay.com.ipos.ddrsales.model.response.DDRIncoTerm;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;

public class DDROrderDetail {
    public String employeeCode;
    public String employeeRole;
    public String businessPlaceId;
    public String stateCode;
    public String entityID;
    public String entityType;
    public String entityName;
    public double orderValue;
    public String deliveryBy;
    public double orderLoyality;
    public double accumulatedLoyality;
    public double totalLoyality;
    public double totalValueWithTax;
    public double totalCGSTValue;
    public double totalIGSTValue;
    public double totalSGSTValue;
    public double totalValueWithoutTax;
    public double totalDiscountValue;
    public double totalRoundingOffValue;
    public String receiverBillingId;
    public String receiverBillingAddress;
    public String receiverShippingId;
    public String receiverShippingAddress;
    public String transporterMode;
    public String transporterName;
    public String transporterLRName;
    public String transporterTruckNumber;
    public String transporterEWayBillNumber;
    public String transporterEWayBillValidityDate;
    public String transporterTrackNumber;
    public String transporterDriverName;
    public String transporterDriverMobileNumber;
    public String transporterAddress;
    public double pointsToRedeemValue;
    public double pointsToRedeem;
    public int isApprover;//0 or 1
    public List<DDRIncoTerm> dDRIncoTerms = null;
    public List<DDRProductCart> dDRCartDetails = null;
    public List<DDRPaymentDetail> dDRPaymentDetails = null;


    public List<Address> address;


    public DDROrderDetail(DDR ddr,
                          RealmDDROrderList invoiceSummary,
                          Address billing,
                          Address shipping,
                          LogisticsData logisticsData,
                          List<DDRIncoTerm> ddrIncoTerms,
                          List<DDRProductCart> dDRCartDetails,
                          List<DDRPaymentDetail> dDRPaymentDetails){

        this. employeeCode =Prefs.getStringPrefs(Constants.employeeCode);//"6000013";
        this. employeeRole =Prefs.getStringPrefs(Constants.employeeRole);
        this. businessPlaceId = "1";//Prefs.getStringPrefs(Constants.businessPlaceCode);//hc
        this. stateCode= "06";//hc
        this. entityID=  ddr.mDDRCode;//Prefs.getIntegerPrefs(Constants.entityCode)+"";
        this. entityType= "distributor";//hc
        this. entityName = "NA";
        this. orderValue  = invoiceSummary.getOrderValue();
        this. deliveryBy = invoiceSummary.getDeliveryBy();
        this. orderLoyality = invoiceSummary.getOrderLoyality();
        this. accumulatedLoyality = invoiceSummary.getAccumulatedLoyality();
        this. totalLoyality = invoiceSummary.getTotalLoyality();
        this. totalValueWithTax = invoiceSummary.getTotalValueWithTax();
        this. totalCGSTValue = invoiceSummary.getTotalCGSTValue();
        this. totalIGSTValue = invoiceSummary.getTotalIGSTValue();
        this. totalSGSTValue = invoiceSummary.getTotalSGSTValue();
        this. totalValueWithoutTax =invoiceSummary.getTotalValueWithoutTax();
        this. totalDiscountValue = invoiceSummary.getTotalDiscountValue();
        this.totalRoundingOffValue = Double.parseDouble(invoiceSummary.getTotalRoundingOffValue());
        this.receiverBillingId = billing.id+"";
        this. receiverBillingAddress = billing.name+"";
        this. receiverShippingId = shipping.id+"";
        this. receiverShippingAddress = shipping.name +"";
        this. transporterMode =logisticsData.mode ;
        this. transporterName = logisticsData.driverName;
        this. transporterLRName = logisticsData.lrNumber;
        this. transporterTruckNumber = logisticsData.truckNumber;
        this. transporterEWayBillNumber = logisticsData.eWayBillNumber;
        this. transporterEWayBillValidityDate = logisticsData.eWayBillValidity;
        this. transporterTrackNumber = logisticsData.trackMobileNumber;
        this. transporterDriverName = logisticsData.driverName;
        this. transporterDriverMobileNumber = logisticsData.driverMobileNumber;
        this. transporterAddress = logisticsData.address;
        this. pointsToRedeemValue=0.0;
        this. pointsToRedeem=0.0;

        this.dDRIncoTerms=ddrIncoTerms;
        this.dDRCartDetails=dDRCartDetails;
        this.dDRPaymentDetails =dDRPaymentDetails;

    }

}
