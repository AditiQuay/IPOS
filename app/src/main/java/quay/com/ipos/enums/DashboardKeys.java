package quay.com.ipos.enums;


public enum DashboardKeys {

    type("type");

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
