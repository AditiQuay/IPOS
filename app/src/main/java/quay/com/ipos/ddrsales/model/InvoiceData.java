package quay.com.ipos.ddrsales.model;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.ddrsales.model.response.Address;
import quay.com.ipos.ddrsales.model.response.DDRIncoTerm;
import quay.com.ipos.ddrsales.model.response.DDRProductListResponse;
import quay.com.ipos.ddrsales.model.response.DDTProductBatch;

public class InvoiceData {

    public List<Address> address=new ArrayList<>();
    public List<DDRProduct> cartList = new ArrayList<>();
    public List<DDRIncoTerm> ddrIncoTerms = new ArrayList<>();
    public List<DDTProductBatch> ddtProductBatchList = new ArrayList<>();

    public LogisticsData logisticsData  = new LogisticsData();
    private boolean initDataFeed = false;
    private static final InvoiceData ourInstance = new InvoiceData() {
    };

    public static InvoiceData getInstance() {
        return ourInstance;
    }

    private InvoiceData() {
    }

    public void setInitData(DDRProductListResponse response) {
        if(initDataFeed)
            return;
        initDataFeed = true;
        this.address = response.address;
        ddrIncoTerms = response.ddrIncoTerms;
    }

    public void cleanData() {
        initDataFeed = false;
        this.address =new ArrayList<>();
        ddrIncoTerms = new ArrayList<>();
        cartList = new ArrayList<>();
        ddtProductBatchList = new ArrayList<>();
    }
}
