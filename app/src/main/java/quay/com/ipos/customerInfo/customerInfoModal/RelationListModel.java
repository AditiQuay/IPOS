package quay.com.ipos.customerInfo.customerInfoModal;

/**
 * Created by niraj.kumar on 6/5/2018.
 */

public class RelationListModel {

    /**
     * ID : 1
     * RelationshipCode : Retailer01
     * RelationshipName : Retailer
     * Active : 1
     * CreateDate : 2018-06-03T14:15:48.01
     * CreatedBy : null
     */

    private int ID;
    private String RelationshipCode;
    private String RelationshipName;
    private int Active;
    private String CreateDate;
    private Object CreatedBy;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getRelationshipCode() {
        return RelationshipCode;
    }

    public void setRelationshipCode(String RelationshipCode) {
        this.RelationshipCode = RelationshipCode;
    }

    public String getRelationshipName() {
        return RelationshipName;
    }

    public void setRelationshipName(String RelationshipName) {
        this.RelationshipName = RelationshipName;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int Active) {
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
