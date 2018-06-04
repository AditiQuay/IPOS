package quay.com.ipos;

/**
 * API Methods of IPOS
 */
public class IPOSAPI {
    public static String CONTENT_TYPE = "Content-Type";
    public static String APPLICATION_JSON = "application/json";
    //  MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String WEB_SERVICE_BASE_URL = "http://13.127.101.233:8087/api/";
    public static String WEB_SERVICE_LOGIN = "Token/GenerateToken";
    public static String WEB_SERVICE_SEARCH_PRODUCT = "Retail/SearchRetailProduct";
    public static String WEB_SERVICE_ProductDetailUsingBarCode = "Retail/ProductDetailUsingBarCode";
    public static String WEB_SERVICE_RETAIL_CUSTOMER_LIST = "RetailCustomers/RetailCustomerList";
    public static String WEB_SERVICE_PRODUCT_MAIN = "ProductCatalog/ProductCatalogMain";
    public static String WEB_SERVICE_PRODUCT_DETAIL = "ProductCatalog/ProductCatalogDetail";
    public static String WEB_SERVICE_PRODUCT_DESCRIPTION = "ProductCatalog/ProductCatalogDescription";

    // NEW ORDER API
    public static String WEB_SERVICE_NOProductSearch = "PSSNewOrder/NOProductSearch";
    public static String WEB_SERVICE_NOGetEntityBuisnessPlaces = "PSSNewOrder/NOGetEntityBuisnessPlaces";
}
