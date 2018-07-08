package quay.com.ipos.enums;


public enum RetailSalesEnum {
    discountPrice("discountPrice"),
    accumulatedLoyality("accumulatedLoyality"),
    discountTotal("discountTotal"),
    isRuleApplied("isRuleApplied"),
    isFreeItem("isFreeItem"),
    totalPoints("totalPoints"),
    totalPrice("totalPrice"),
    isOTC("isOTC"),
    otcPerc("otcPerc"),
    otcValue("otcValue"),
    productCode("productCode"),
    iProductModalId("iProductModalId"),
    sProductName("sProductName"),
    sProductFeature("sProductFeature"),
    sCharacteristicType("sCharacteristicType"),
    sCharacteristicValues("sCharacteristicValues"),
    sCharacteristicValue("sCharacteristicValue"),
    productImage("productImage"),
    sProductPrice("sProductPrice"),
    sProductStock("sProductStock"),
    sProductWeight("sProductWeight"),
    isDiscount("isDiscount"),
    gstPerc("gstPerc"),
    cgst("cgst"),
    sgst("sgst"),
    salesPrice("salesPrice"),
    nrv("nrv"),
    gpl("gpl"),
    mrp("mrp"),
    barCodeNumber("barCodeNumber"),
    discount("discount"),
    sDiscountName("sDiscountName"),
    sDiscountDisplayName("sDiscountDisplayName"),
    rule("rule"),
    sDiscountType("sDiscountType"),
    sDiscountValue("sDiscountValue"),
    sEligibilityBasedOn("sEligibilityBasedOn"),
    sDiscountBasedOn("sDiscountBasedOn"),
    slabFrom("slabFrom"),
    slabTO("slabTO"),
    packSize("packSize"),
    isCrossBundle("isCrossBundle"),
    opsCriteria("opsCriteria"),
    ruleType("ruleType"),
    ruleNotes("ruleNotes"),
    ruleID("ruleID"),
    ruleNumber("ruleNumber"),
    ruleSequence("ruleSequence"),
    ruleProdecessors("ruleProdecessors"),
    opsType("opsType"),
    points("points"),
    pointsBasedOn("pointsBasedOn"),
    valueFrom("valueFrom"),
    valueTo("valueTo"),
    pointsPer("pointsPer"),
    percent("P"),
    value("V"),
    SP("SP"),
    MRP("MRP"),
    NRP("NRP"),
    GPL("GPL"),
    Quantity("Q"),
    Lowest("L"),
    MAX("M"),
    Product("P"),
    Others("O"),
    isAdded("isAdded"),
    qty("qty");

    private final String name;

    RetailSalesEnum(String s) {
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
