package quay.com.ipos.enums;

/**
 * Created by niraj.kumar on 5/25/2018.
 */

public enum ProductCatalogueEnum {
    info("info"), section("section"),sectionProduct("sectionProduct"),sDataCalculator("sDataCalculator"),
    sectionItems("sectionItems"), productId("productId"),productNameMain("productName"),productMainUrl("productUrl"),count("count"),

    productData("data"),productCode("productCode"),sProductName("sProductName"),
    sProductUrl("sProductUrl"),sProductFeature("sProductFeature"),sProductPrice("sProductPrice"),sDataSheet("sDataSheet"),
    sPoints("sPoints"),isOnOffer("isOnOffer"),isCalculator("isCalculator"),isDataSheet("isDataSheet");

    private final String name;

    ProductCatalogueEnum(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equalsIgnoreCase(otherName);
    }

    public String toString() {
        return this.name;
    }
}
