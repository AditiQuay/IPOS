package quay.com.ipos.productCatalogue.productModal;

import java.io.Serializable;

/**
 * Created by niraj.kumar on 4/25/2018.
 */

public class ProductItemModal implements Serializable {
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    private int productId;
    private String productName;
    private String productUrl;
    private String productDescription;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    private String count;

    public ProductItemModal() {
    }

    public ProductItemModal(String productName, String productUrl) {
        this.productName = productName;
        this.productUrl = productUrl;
    }





}
