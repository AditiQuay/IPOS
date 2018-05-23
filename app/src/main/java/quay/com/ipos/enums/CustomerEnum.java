package quay.com.ipos.enums;


public enum CustomerEnum {

    ColoumnCustomerID("customerID"),
    ColoumnCustomerName("customerName"),
    ColoumnCustomerPoints("customerPoints"),
    ColoumnCustomerPhone("customerPhone"),
    ColoumnCustomerPhone2("customerPhone2"),
    ColoumnCustomerPhone3("customerPhone3"),
    ColoumnCustomerEmail("customerEmail"),
    ColoumnCustomerEmail2("customerEmail2"),
    ColoumnCustomerDom("customerDom"),
    ColoumnCustomerBday("customerBday"),
    ColoumnCustomerGender("customerGender"),
    ColoumnCustomerFirstName("customerFirstName"),
    ColoumnCustomerLastName("customerLastName"),
    ColoumnCustoemrGstin("custoemrGstin"),
    ColoumnCustomerStatus("customerStatus"),
    ColoumnCustomerDesignation("customerDesignation"),
    ColoumnCustomerCompany("customerCompany"),
    ColoumnCustomerRelationship("customerRelationship"),
    ColoumnCfactor("cfactor"),
    ColoumnCustomerAddress("customerAddress"),
    ColoumnCustomerState("customerState"),
    ColoumnCustomerCity("customerCity"),
    ColoumnCustomerPin("customerPin"),
    ColoumnCustomerImage("customerImage"),
    ColoumnLastBillingDate("lastBillingDate"),
    ColoumnLastBillingAmount("lastBillingAmount"),
    ColoumnIsSuggestion("issuggestion"),
    ColoumnSuggestion("suggestion"),
    ColoumnRecentOrders("recentOrders"),
    ColoumnIsSync("isSync"),
    ColoumnFromStoreName("fromStoreName"),
    ColoumnStoreCity("storeCity"),
    ColoumnStoreState("storeState"),
    ColoumnBillDate("billDate"),
    ColoumnBillPrice("billPrice");


    private final String name;

    CustomerEnum(String s) {
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
