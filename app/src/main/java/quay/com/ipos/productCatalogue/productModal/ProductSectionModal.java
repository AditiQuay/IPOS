package quay.com.ipos.productCatalogue.productModal;

import java.util.ArrayList;

/**
 * Created by niraj.kumar on 4/25/2018.
 */

public class ProductSectionModal {
    private String headerTitle;
    public ArrayList<ProductItemModal> getProductItemModals() {
        return productItemModals;
    }

    public void setProductItemModals(ArrayList<ProductItemModal> productItemModals) {
        this.productItemModals = productItemModals;
    }

    private ArrayList<ProductItemModal> productItemModals;

    public ProductSectionModal() {

    }

    public ProductSectionModal(String headerTitle, ArrayList<ProductItemModal> productItemModals) {
        this.headerTitle = headerTitle;
        this.productItemModals = productItemModals;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }


}