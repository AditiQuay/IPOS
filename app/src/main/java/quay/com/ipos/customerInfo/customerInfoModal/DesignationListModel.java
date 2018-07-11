package quay.com.ipos.customerInfo.customerInfoModal;

/**
 * Created by niraj.kumar on 6/5/2018.
 */

public class DesignationListModel {

    /**
     * ID : 3
     * DesignationCode : BkndDev01
     * DesignationName : Backend Developer
     * Active : true
     * CreateDate : 2018-06-03T14:10:30.327
     * CreatedBy : null
     */

    private int ID;
    private String DesignationCode;
    private String DesignationName;
    private boolean Active;
    private String CreateDate;
    private Object CreatedBy;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDesignationCode() {
        return DesignationCode;
    }

    public void setDesignationCode(String DesignationCode) {
        this.DesignationCode = DesignationCode;
    }

    public String getDesignationName() {
        return DesignationName;
    }

    public void setDesignationName(String DesignationName) {
        this.DesignationName = DesignationName;
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
