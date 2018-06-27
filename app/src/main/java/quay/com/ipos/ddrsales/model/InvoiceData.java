package quay.com.ipos.ddrsales.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.ddrsales.model.response.Address;
import quay.com.ipos.ddrsales.model.response.DDRIncoTerms;
import quay.com.ipos.ddrsales.model.response.DDRProductListResponse;
import quay.com.ipos.ddrsales.model.response.DDTProductBatch;
import quay.com.ipos.modal.ProductSearchResult;

public class InvoiceData {

    public List<Address> address=new ArrayList<>();
    public List<DDRProduct> cartList = new ArrayList<>();
    public List<DDRIncoTerms> ddrIncoTerms = new ArrayList<>();
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
