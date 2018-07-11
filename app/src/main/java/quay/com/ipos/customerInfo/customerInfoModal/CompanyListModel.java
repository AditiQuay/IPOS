package quay.com.ipos.customerInfo.customerInfoModal;

/**
 * Created by niraj.kumar on 6/5/2018.
 */

public class CompanyListModel {

    /**
     * ID : 2
     * CompaneyCode : Crystal1
     * mCompanyName : Crystal Crop Protection LTD
     * Active : true
     * CreateDate : 2018-06-03T14:13:21.56
     * CreatedBy : null
     */

    private int ID;
    private String CompaneyCode;
    private String CompaneyName;
    private boolean Active;
    private String CreateDate;
    private Object CreatedBy;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCompaneyCode() {
        return CompaneyCode;
    }

    public void setCompaneyCode(String CompaneyCode) {
        this.CompaneyCode = CompaneyCode;
    }

    public String getCompaneyName() {
        return CompaneyName;
    }

    public void setCompaneyName(String CompaneyName) {
        this.CompaneyName = CompaneyName;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean Active) {
        this.Active = Active;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public Object getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(Object CreatedBy) {
        this.CreatedBy = CreatedBy;
    }
}
