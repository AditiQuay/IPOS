package quay.com.ipos.enums;


public enum DashboardKeys {

    type("type"), mystores("mystores"),onlineStores("onlineStores"),otherStores("otherStores"), lowInventory("lowInventory"), title("title"), totalAmount("totalAmount"), target("target"), achievement("achievement"), totalSales("totalSales"), endofSeason("endofSeason"), awCollection("awCollection");

    private final String name;

    DashboardKeys(String s) {
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
