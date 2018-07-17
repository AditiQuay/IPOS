package quay.com.ipos.ddrsales.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.ddrsales.model.request.DDROrderDetail;
import quay.com.ipos.ddrsales.model.response.Address;
import quay.com.ipos.ddrsales.model.response.DDRBatch;
import quay.com.ipos.ddrsales.model.response.DDRIncoTerm;
import quay.com.ipos.ddrsales.model.response.DDRProductListResponse;

public class InvoiceData implements Serializable {

    public List<Address> address = new ArrayList<>();
    public List<DDRProduct> cartList = new ArrayList<>();
    public List<DDRIncoTerm> ddrIncoTerms = new ArrayList<>();
    public List<DDRBatch> ddtProductBatchList = new ArrayList<>();
    public LogisticsData logisticsData = new LogisticsData();
    private boolean initDataFeed = false;


    /*   private static final InvoiceData ourInstance = new InvoiceData() {};

      public static InvoiceData getInstance() {
          return ourInstance;
      }
  */
    public InvoiceData() {
    }

    public void setInitData(DDRProductListResponse response) {
        if (initDataFeed)
            return;
        initDataFeed = true;
        this.address = response.address;
        ddrIncoTerms = new ArrayList<>();
    }

    public   void setInitData2(DDROrderDetail response) {
        if (initDataFeed)
            return;
        initDataFeed = true;

        //logistic Data
        this.logisticsData.mode = response.transporterMode;
        this.logisticsData.lrNumber = response.transporterLRName;
        this.logisticsData.transporter = response.transporterName;
        this.logisticsData.truckNumber = response.transporterTruckNumber;
        this.logisticsData.eWayBillNumber = response.transporterEWayBillNumber;
        this.logisticsData.eWayBillValidity = response.transporterEWayBillValidityDate;
        this.logisticsData.trackMobileNumber = response.transporterTrackNumber;
        this.logisticsData.driverName = response.transporterDriverName;
        this.logisticsData.driverMobileNumber = response.transporterDriverMobileNumber;
        this.logisticsData.address = response.transporterAddress;
        //logistic Data End

        this.address = response.address;
        ddrIncoTerms = response.dDRIncoTerms;
    }

    public void cleanData() {

        initDataFeed = false;
        this.address = new ArrayList<>();
        ddrIncoTerms = new ArrayList<>();
        cartList = new ArrayList<>();
        //ddtProductBatchList = new ArrayList<>();
    }


}
