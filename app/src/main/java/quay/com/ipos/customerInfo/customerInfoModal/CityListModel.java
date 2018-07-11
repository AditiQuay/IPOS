package quay.com.ipos.customerInfo.customerInfoModal;

/**
 * Created by niraj.kumar on 6/4/2018.
 */

public class CityListModel {

    /**
     * CityCode : 01
     * CityName : Gurgaon
     * StateCode : 8
     * CountryCode : 91
     */

    private String CityCode;
    private String CityName;
    private int StateCode;
    private int CountryCode;

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String CityCode) {
        this.CityCode = CityCode;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public int getStateCode() {
        return StateCode;
    }

    public void setStateCode(int StateCode) {
        this.StateCode = StateCode;
    }

    public int getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(int CountryCode) {
        this.CountryCode = CountryCode;
    }
}
