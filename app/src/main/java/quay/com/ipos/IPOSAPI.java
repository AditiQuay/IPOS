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
    public static String WEB_SERVICE_RETAIL_ORDER_CENTER = "Retail/RetailOrderCenter";
    public static String WEB_SERVICE_RETAIL_CreateMaterialMaster = "Retail/CreateMaterialMaster";
    public static String WEB_SERVICE_RETAIL_CustomerPointsRedeemRequest = "Retail/CustomerPointsRedeemRequest";
    public static String WEB_SERVICE_RETAIL_ValidateCustomerPointsRedeemRequest = "Retail/ValidateCustomerPointsRedeemRequest";
    public static String WEB_SERVICE_ProductDetailUsingBarCode = "Retail/ProductDetailUsingBarCode";
    public static String WEB_SERVICE_RETAIL_CUSTOMER_LIST = "Retail/RetailCustomerList";
    public static String WEB_SERVICE_RETAIL_ORDER_CENTER_PRINT = "Retail/RetailOrderCenterPrint";


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
    public static String WEB_SERVICE_INVENTORYPRODIUCTSEARCH = "Retail/SearchRetailProduct";



    //Niraj and ankush


    public static String WEB_SERVICE_PRODUCT_MAIN = "ProductCatalog/ProductCatalogMain";
    public static String WEB_SERVICE_PRODUCT_DETAIL = "ProductCatalog/ProductCatalogDetail";
    public static String WEB_SERVICE_PRODUCT_DESCRIPTION = "ProductCatalog/ProductCatalogDescription";
    public static String WEB_SERVICE_RETAIL_SPINNER_LIST = "RetailCustomers/GetRetailCustomeBaseAddInfo";
    public static String WEB_SERVICE_CUSTOMER_DATA = "RetailCustomers/SyncRetailCustomerData";

    //inventory
    public static String WEB_SERVICE_INventoryPONUMBERS = WEB_SERVICE_BASE_URL + "Inventory/GetPOSummary";
    public static String WEB_SERVICE_NOGetBusinessPlaces = "Inventory/GetBusinessPlaces";
    public static String WEB_SERVICE_GetPOSummaryDetail = WEB_SERVICE_BASE_URL + "Inventory/GetPOSummaryDetail";
    public static String WEB_SERVICE_GetPOInfo = WEB_SERVICE_BASE_URL + "Inventory/GetPOInfo";
    public static String WEB_SERVICE_GET_GRN_SUMMARY = WEB_SERVICE_BASE_URL+"Inventory/GetGRNSummary";
    public static String WEB_SERVICE_GET_GRN_SUMMARY_DETAIL = WEB_SERVICE_BASE_URL+"Inventory/GetGRNSummaryDetail";
    public static String WEB_SERVICE_GET_GRN_SUMMARY_SUBMIT = WEB_SERVICE_BASE_URL+"Inventory/SubmitGRNSummaryDetail";
    public static String WEB_SERVICE_GET_PO_CREATE = WEB_SERVICE_BASE_URL+"Inventory/SubmitPODetail";
    public static String WEB_SERVICE_GetPODETAILS = WEB_SERVICE_BASE_URL + "Inventory/PODetail";

    //inventory Transfer Out

    public static String WEB_SERVICE_GetTransferOutDetail = WEB_SERVICE_BASE_URL + "Inventory/TransferOutDetail";
    public static String WEB_SERVICE_SubmitTransferOut = WEB_SERVICE_BASE_URL + "Inventory/SubmitTransferOut";
    public static String WEB_SERVICE_TransferOutSummary = WEB_SERVICE_BASE_URL + "Inventory/TransferOutSummary";
    public static String WEB_SERVICE_TransferOutSummaryDetail = WEB_SERVICE_BASE_URL + "Inventory/TransferOutSummaryDetail";
    //------------niraj and ankush end

    //redeem
    public static String WEB_SERVICE_NOPointsRedeem = WEB_SERVICE_BASE_URL + "PSSNewOrder/NOPointsRedeem";
    public static String WEB_SERVICE_ValidateNOCustomerRedeemPoint = WEB_SERVICE_BASE_URL + "PSSNewOrder/ValidateNOCustomerRedeemPoint";


   //partner connect & kyc
    public static final String BASE_URL = "http://13.127.101.233:8087/";
    //@Query("sort") String sort  "api/PSSConnects/getPssConnectDetail?strEntityId=1";
    //partner connect
    public static final String PARTNER_CONNECT_API = "api/PSSConnects/getPssConnectDetail";
    public static final String PARTNER_CONNECT_UPDATE_API = "api/PSSConnects/UpdatePssdetail";
    //kyc
    public static final String KYC_PARTNER_API = "api/PSSConnects/getPssRequesttDetail";
    public static final String KYC_PARTNER_ACCEPT = "api/PSSConnects/KyCAccept";

    //ddr sales
    public static final String DDR_NO_SUMMARY = "/api/DDR/OrderCenterSummary";//New
    public static final String GET_ORDER_DETAIL = "/api/DDR/GetOrderDetail";
    public static final String DDR_CREATE_AND_UPDATE_API = "api/DDR/SubmitDDRPartnerDetail";

    public static final String DDR_MASTER_VIEW_API = "/api/DDR/DDRPartnerDetail";
    public static final String DDR_GetDDRList = "/api/DDR/GetDDRList";
    public static final String DDR_GetDDRProductList = "/api/DDR/GetDDRProductList";
    public static final String DDR_SUBMIT = "/api/DDR/SubmitDDRDetail";
    public static final String DDR_GETDDRBATCHUPDATEDETAIL = "/api/DDR/GetDDRBatchUpdateDetail";


    //compliance
    public static final String COMPLIANCE_DATA = "/api/ComplianceTracker/GetTaskschedularDetails";
    public static final String SYNC_DATA_URL = "/api/ComplianceTracker/SyncSubTaskschedularData";
    public static final String COMPLIANCE_TASK_APPROVE = "/api/ComplianceTracker/TaskApprove";

    //TransferIn
    public static final String GET_TRANSFER_OUT_GRN_SUMMARY = WEB_SERVICE_BASE_URL+"Inventory/GetTransferOutGRNSummary";
    public static final String GET_TRANSFER_OUT_GRN_SUMMARY_DETAIL = WEB_SERVICE_BASE_URL+"Inventory/GetTransferOutGRNSummaryDetail";
    public static final String GET_TRANSFER_OUT_GRN_SUBMIT_DETAIL = WEB_SERVICE_BASE_URL+"Inventory/SubmitTransferOutGRNSummaryDetail";

}