package quay.com.ipos.ddrsales.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import quay.com.ipos.ddrsales.model.DDRProduct;
import quay.com.ipos.modal.ProductSearchResult;

public class DDRProductListResponse {
    public List<Address> address;
    @SerializedName("listData")
    public List<DDRProduct> productList;
    public List<DDRIncoTerms> ddrIncoTerms;

}
