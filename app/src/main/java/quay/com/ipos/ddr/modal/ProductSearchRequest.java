package quay.com.ipos.ddr.modal;

/**
 * Created by ankush.bansal on 04-06-2018.
 */

public class ProductSearchRequest {
    /**
     * entityCode : 1
     * entityRole : manager
     * entityStateCode : 06
     * searchParam : b
     * businessPlaceCode : 1
     * barCodeNumber : NA
     */

    private String entityCode;
    private String entityRole;
    private String entityStateCode;
    private String searchParam;
    private String businessPlaceCode;
    private String barCodeNumber;
    private String employeeCode;
    private String employeeRole;
    private String moduleType;

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityRole() {
        return entityRole;
    }

    public void setEntityRole(String entityRole) {
        this.entityRole = entityRole;
    }

    public String getEntityStateCode() {
        return entityStateCode;
    }

    public void setEntityStateCode(String entityStateCode) {
        this.entityStateCode = entityStateCode;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    public String getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    public void setBusinessPlaceCode(String businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    public String getBarCodeNumber() {
        return barCodeNumber;
    }

    public void setBarCodeNumber(String barCodeNumber) {
        this.barCodeNumber = barCodeNumber;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }
}
