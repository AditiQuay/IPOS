package quay.com.ipos.ddrsales.model;

import java.io.Serializable;

import quay.com.ipos.ddrsales.model.request.InvoiceDataSubmit;

public class LogisticsData implements Serializable{


    public static final String TransportMode[] = {"Partner Vehicle", " Own Vehicle", "Transporter"};

    public String mode="";
    public String lrNumber="";
    public String transporter="";
    public String truckNumber="";
    public String eWayBillNumber="";
    public String eWayBillValidity="";
    public String trackMobileNumber="";
    public String driverName="";
    public String driverMobileNumber="";
    public String address="";


    public void setData(InvoiceDataSubmit invoiceDataSubmit) {
        this.mode = invoiceDataSubmit.transporterMode;
        this.lrNumber = invoiceDataSubmit.transporterLRName;
        this.transporter = invoiceDataSubmit.transporterName;
        this.truckNumber = invoiceDataSubmit.transporterTruckNumber;
        this.eWayBillNumber = invoiceDataSubmit.transporterEWayBillNumber;
        this.eWayBillValidity = invoiceDataSubmit.transporterEWayBillValidityDate;
        this.trackMobileNumber = invoiceDataSubmit.transporterTrackNumber;
        this.driverName = invoiceDataSubmit.transporterDriverName;
        this.driverMobileNumber = invoiceDataSubmit.transporterDriverMobileNumber;
        this.address = invoiceDataSubmit.transporterAddress;
    }
}
