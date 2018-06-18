package quay.com.ipos;


import okhttp3.MediaType;

/**
 * API Methods of IPOS
 */
public class IPOSAPI {
    public static String CONTENT_TYPE = "Content-Type";
    public static String APPLICATION_JSON = "application/json";
    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String WEB_SERVICE_BASE_URL = "http://13.127.101.233:8087/api/";
    public static String WEB_SERVICE_LOGIN = "Token/GenerateToken";
    public static String WEB_SERVICE_SEARCH_PRODUCT = "Retail/SearchRetailProduct";
    public static String WEB_SERVICE_RETAIL_ORDER_SUBMIT = "Retail/RetailOrderSubmit";
    public static String WEB_SERVICE_RETAIL_CustomerPointsRedeemRequest = "Retail/CustomerPointsRedeemRequest";
    public static String WEB_SERVICE_RETAIL_ValidateCustomerPointsRedeemRequest = "Retail/ValidateCustomerPointsRedeemRequest";
    public static String WEB_SERVICE_ProductDetailUsingBarCode = "Retail/ProductDetailUsingBarCode";
    public static String WEB_SERVICE_RETAIL_CUSTOMER_LIST = "RetailCustomers/RetailCustomerList";
    public static String WEB_SERVICE_RETAIL_SPINNER_LIST = "RetailCustomers/GetRetailCustomeBaseAddInfo";
    public static String WEB_SERVICE_PRODUCT_MAIN = "ProductCatalog/ProductCatalogMain";
    public static String WEB_SERVICE_PRODUCT_DETAIL = "ProductCatalog/ProductCatalogDetail";
    public static String WEB_SERVICE_PRODUCT_DESCRIPTION = "ProductCatalog/ProductCatalogDescription";
    public static String WEB_SERVICE_CUSTOMER_DATA = "RetailCustomers/SyncRetailCustomerData";

    // NEW ORDER API
    public static String WEB_SERVICE_NOPRODUCTSEARCH = "PSSNewOrder/NOProductSearch";
    public static String WEB_SERVICE_NOGetEntityBuisnessPlaces = "PSSNewOrder/NOGetEntityBuisnessPlaces";

    public static String WEB_SERVICE_NOSubmitOrder = WEB_SERVICE_BASE_URL + "PSSNewOrder/NOSubmitOrder";
    public static String WEB_SERVICE_NOSummary = WEB_SERVICE_BASE_URL + "PSSNewOrder/NOSummary";
    public static String WEB_SERVICE_NOOrderDetail = WEB_SERVICE_BASE_URL + "PSSNewOrder/NOOrderDetail";
    public static String WEB_SERVICE_NOTransaction = WEB_SERVICE_BASE_URL + "PSSNewOrder/NOTransaction";
    public static String WEB_SERVICE_CheckStock = WEB_SERVICE_BASE_URL + "PSSNewOrder/NOCheckStock";
    public static String WEB_SERVICE_KYS_PSS_DETAIL = WEB_SERVICE_BASE_URL + "PSSConnects/KYCPssdetailList";
    public static String WEB_SERVICE_KYC_PSS_REJECT = WEB_SERVICE_BASE_URL+"PSSConnects/KyCReject";


    //inventory
    public static String WEB_SERVICE_INventoryPONUMBERS = WEB_SERVICE_BASE_URL + "Inventory/GetPOSummary";
    public static String WEB_SERVICE_NOGetBusinessPlaces = "Inventory/GetBusinessPlaces";
    public static String WEB_SERVICE_GetPOSummaryDetail = WEB_SERVICE_BASE_URL + "Inventory/GetPOSummaryDetail";
    public static String WEB_SERVICE_GetPOInfo = WEB_SERVICE_BASE_URL + "Inventory/GetPOInfo";

    //redeem
    public static String WEB_SERVICE_NOPointsRedeem = WEB_SERVICE_BASE_URL + "PSSNewOrder/NOPointsRedeem";
    public static String WEB_SERVICE_ValidateNOCustomerRedeemPoint = WEB_SERVICE_BASE_URL + "PSSNewOrder/ValidateNOCustomerRedeemPoint";
}