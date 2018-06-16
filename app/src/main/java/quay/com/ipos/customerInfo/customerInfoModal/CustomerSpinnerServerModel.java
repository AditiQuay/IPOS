package quay.com.ipos.customerInfo.customerInfoModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by niraj.kumar on 6/4/2018.
 */

public class CustomerSpinnerServerModel {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("spinnerData")
    @Expose
    private SpinnerData spinnerData;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SpinnerData getSpinnerData() {
        return spinnerData;
    }

    public void setSpinnerData(SpinnerData spinnerData) {
        this.spinnerData = spinnerData;
    }


    public class CityList {

        @SerializedName("CityCode")
        @Expose
        private String cityCode;
        @SerializedName("CityName")
        @Expose
        private String cityName;
        @SerializedName("StateCode")
        @Expose
        private Integer stateCode;
        @SerializedName("CountryCode")
        @Expose
        private Integer countryCode;

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public Integer getStateCode() {
            return stateCode;
        }

        public void setStateCode(Integer stateCode) {
            this.stateCode = stateCode;
        }

        public Integer getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(Integer countryCode) {
            this.countryCode = countryCode;
        }

    }


    public class CompanyList {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("CompaneyCode")
        @Expose
        private String companeyCode;
        @SerializedName("mCompanyName")
        @Expose
        private String companeyName;
        @SerializedName("Active")
        @Expose
        private Boolean active;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("CreatedBy")
        @Expose
        private Object createdBy;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getCompaneyCode() {
            return companeyCode;
        }

        public void setCompaneyCode(String companeyCode) {
            this.companeyCode = companeyCode;
        }

        public String getCompaneyName() {
            return companeyName;
        }

        public void setCompaneyName(String companeyName) {
            this.companeyName = companeyName;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

    }

    public class CountryList {

        @SerializedName("CountryCode")
        @Expose
        private String countryCode;
        @SerializedName("CountryName")
        @Expose
        private String countryName;
        @SerializedName("Active")
        @Expose
        private Boolean active;

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

    }


    public class CustomerList {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("CustomerCode")
        @Expose
        private String customerCode;
        @SerializedName("customerTitle")
        @Expose
        private Object customerTitle;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Mobile1")
        @Expose
        private String mobile1;
        @SerializedName("Mobile2")
        @Expose
        private String mobile2;
        @SerializedName("Mobile3")
        @Expose
        private String mobile3;
        @SerializedName("Email1")
        @Expose
        private String email1;
        @SerializedName("City")
        @Expose
        private String city;
        @SerializedName("DOB")
        @Expose
        private String dOB;
        @SerializedName("MaritalStatus")
        @Expose
        private Object maritalStatus;
        @SerializedName("DOM")
        @Expose
        private String dOM;
        @SerializedName("Gender")
        @Expose
        private String gender;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("Email2")
        @Expose
        private String email2;
        @SerializedName("GSTIN")
        @Expose
        private String gSTIN;
        @SerializedName("Status")
        @Expose
        private Boolean status;
        @SerializedName("SpouseFirstName")
        @Expose
        private Object spouseFirstName;
        @SerializedName("SpouseLastName")
        @Expose
        private Object spouseLastName;
        @SerializedName("SpouseDob")
        @Expose
        private String spouseDob;
        @SerializedName("ChildSatus")
        @Expose
        private Object childSatus;
        @SerializedName("Designation")
        @Expose
        private String designation;
        @SerializedName("Company")
        @Expose
        private String company;
        @SerializedName("KycRelationship")
        @Expose
        private String relationship;
        @SerializedName("Cfactor")
        @Expose
        private String cfactor;
        @SerializedName("CustomerTypeID")
        @Expose
        private String customerTypeID;
        @SerializedName("Address")
        @Expose
        private Object address;
        @SerializedName("State")
        @Expose
        private Object state;
        @SerializedName("PinCode")
        @Expose
        private Object pinCode;
        @SerializedName("Country")
        @Expose
        private Object country;
        @SerializedName("EmpCode")
        @Expose
        private Object empCode;
        @SerializedName("RegisteredBusinessPlaceID")
        @Expose
        private Object registeredBusinessPlaceID;
        @SerializedName("CreatedBy")
        @Expose
        private Object createdBy;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("ModifiedDate")
        @Expose
        private String modifiedDate;
        @SerializedName("ModifiedBy")
        @Expose
        private Object modifiedBy;
        @SerializedName("Active")
        @Expose
        private Object active;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getCustomerCode() {
            return customerCode;
        }

        public void setCustomerCode(String customerCode) {
            this.customerCode = customerCode;
        }

        public Object getCustomerTitle() {
            return customerTitle;
        }

        public void setCustomerTitle(Object customerTitle) {
            this.customerTitle = customerTitle;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile1() {
            return mobile1;
        }

        public void setMobile1(String mobile1) {
            this.mobile1 = mobile1;
        }

        public String getMobile2() {
            return mobile2;
        }

        public void setMobile2(String mobile2) {
            this.mobile2 = mobile2;
        }

        public String getMobile3() {
            return mobile3;
        }

        public void setMobile3(String mobile3) {
            this.mobile3 = mobile3;
        }

        public String getEmail1() {
            return email1;
        }

        public void setEmail1(String email1) {
            this.email1 = email1;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDOB() {
            return dOB;
        }

        public void setDOB(String dOB) {
            this.dOB = dOB;
        }

        public Object getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(Object maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getDOM() {
            return dOM;
        }

        public void setDOM(String dOM) {
            this.dOM = dOM;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail2() {
            return email2;
        }

        public void setEmail2(String email2) {
            this.email2 = email2;
        }

        public String getGSTIN() {
            return gSTIN;
        }

        public void setGSTIN(String gSTIN) {
            this.gSTIN = gSTIN;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public Object getSpouseFirstName() {
            return spouseFirstName;
        }

        public void setSpouseFirstName(Object spouseFirstName) {
            this.spouseFirstName = spouseFirstName;
        }

        public Object getSpouseLastName() {
            return spouseLastName;
        }

        public void setSpouseLastName(Object spouseLastName) {
            this.spouseLastName = spouseLastName;
        }

        public String getSpouseDob() {
            return spouseDob;
        }

        public void setSpouseDob(String spouseDob) {
            this.spouseDob = spouseDob;
        }

        public Object getChildSatus() {
            return childSatus;
        }

        public void setChildSatus(Object childSatus) {
            this.childSatus = childSatus;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getCfactor() {
            return cfactor;
        }

        public void setCfactor(String cfactor) {
            this.cfactor = cfactor;
        }

        public String getCustomerTypeID() {
            return customerTypeID;
        }

        public void setCustomerTypeID(String customerTypeID) {
            this.customerTypeID = customerTypeID;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public Object getPinCode() {
            return pinCode;
        }

        public void setPinCode(Object pinCode) {
            this.pinCode = pinCode;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public Object getEmpCode() {
            return empCode;
        }

        public void setEmpCode(Object empCode) {
            this.empCode = empCode;
        }

        public Object getRegisteredBusinessPlaceID() {
            return registeredBusinessPlaceID;
        }

        public void setRegisteredBusinessPlaceID(Object registeredBusinessPlaceID) {
            this.registeredBusinessPlaceID = registeredBusinessPlaceID;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public Object getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(Object modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public Object getActive() {
            return active;
        }

        public void setActive(Object active) {
            this.active = active;
        }

    }

    public class DesignationList {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("DesignationCode")
        @Expose
        private String designationCode;
        @SerializedName("DesignationName")
        @Expose
        private String designationName;
        @SerializedName("Active")
        @Expose
        private Boolean active;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("CreatedBy")
        @Expose
        private Object createdBy;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getDesignationCode() {
            return designationCode;
        }

        public void setDesignationCode(String designationCode) {
            this.designationCode = designationCode;
        }

        public String getDesignationName() {
            return designationName;
        }

        public void setDesignationName(String designationName) {
            this.designationName = designationName;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

    }

    public class RelationshipList {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("RelationshipCode")
        @Expose
        private String relationshipCode;
        @SerializedName("RelationshipName")
        @Expose
        private String relationshipName;
        @SerializedName("Active")
        @Expose
        private Integer active;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("CreatedBy")
        @Expose
        private Object createdBy;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getRelationshipCode() {
            return relationshipCode;
        }

        public void setRelationshipCode(String relationshipCode) {
            this.relationshipCode = relationshipCode;
        }

        public String getRelationshipName() {
            return relationshipName;
        }

        public void setRelationshipName(String relationshipName) {
            this.relationshipName = relationshipName;
        }

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

    }


    public class SpinnerData {

        @SerializedName("cityList")
        @Expose
        private List<CityList> cityList = null;
        @SerializedName("stateList")
        @Expose
        private List<StateList> stateList = null;
        @SerializedName("countryList")
        @Expose
        private List<CountryList> countryList = null;
        @SerializedName("designationList")
        @Expose
        private List<DesignationList> designationList = null;
        @SerializedName("companyList")
        @Expose
        private List<CompanyList> companyList = null;
        @SerializedName("customerList")
        @Expose
        private List<CustomerList> customerList = null;
        @SerializedName("relationshipList")
        @Expose
        private List<RelationshipList> relationshipList = null;

        public List<CityList> getCityList() {
            return cityList;
        }

        public void setCityList(List<CityList> cityList) {
            this.cityList = cityList;
        }

        public List<StateList> getStateList() {
            return stateList;
        }

        public void setStateList(List<StateList> stateList) {
            this.stateList = stateList;
        }

        public List<CountryList> getCountryList() {
            return countryList;
        }

        public void setCountryList(List<CountryList> countryList) {
            this.countryList = countryList;
        }

        public List<DesignationList> getDesignationList() {
            return designationList;
        }

        public void setDesignationList(List<DesignationList> designationList) {
            this.designationList = designationList;
        }

        public List<CompanyList> getCompanyList() {
            return companyList;
        }

        public void setCompanyList(List<CompanyList> companyList) {
            this.companyList = companyList;
        }

        public List<CustomerList> getCustomerList() {
            return customerList;
        }

        public void setCustomerList(List<CustomerList> customerList) {
            this.customerList = customerList;
        }

        public List<RelationshipList> getRelationshipList() {
            return relationshipList;
        }

        public void setRelationshipList(List<RelationshipList> relationshipList) {
            this.relationshipList = relationshipList;
        }

    }


    public class StateList {

        @SerializedName("CountryCode")
        @Expose
        private String countryCode;
        @SerializedName("StateCode")
        @Expose
        private String stateCode;
        @SerializedName("StateName")
        @Expose
        private String stateName;
        @SerializedName("Active")
        @Expose
        private Boolean active;

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

    }
}
