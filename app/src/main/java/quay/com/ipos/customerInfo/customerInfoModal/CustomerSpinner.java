package quay.com.ipos.customerInfo.customerInfoModal;

/**
 * Created by niraj.kumar on 6/4/2018.
 */

public class CustomerSpinner {
    public String getStateList() {
        return stateList;
    }

    public void setStateList(String stateList) {
        this.stateList = stateList;
    }

    public String getCountryList() {
        return countryList;
    }

    public void setCountryList(String countryList) {
        this.countryList = countryList;
    }

    public String getCityList() {
        return cityList;
    }

    public void setCityList(String cityList) {
        this.cityList = cityList;
    }

    public String getDesignationList() {
        return designationList;
    }

    public void setDesignationList(String designationList) {
        this.designationList = designationList;
    }

    public String getCompanyList() {
        return companyList;
    }

    public void setCompanyList(String companyList) {
        this.companyList = companyList;
    }

    public String getCustomerList() {
        return customerList;
    }

    public void setCustomerList(String customerList) {
        this.customerList = customerList;
    }

    public String getRelationshipList() {
        return relationshipList;
    }

    public void setRelationshipList(String relationshipList) {
        this.relationshipList = relationshipList;
    }

    private String stateList;
    private String countryList;
    private String cityList;
    private String designationList;
    private String companyList;
    private String customerList;
    private String relationshipList;

    public String getCustomerTypeList() {
        return customerTypeList;
    }

    public void setCustomerTypeList(String customerTypeList) {
        this.customerTypeList = customerTypeList;
    }

    private String customerTypeList;

    public CustomerSpinner(){

    }
}
