package quay.com.ipos.partnerConnect.kyc.model;

/**
 * Created by niraj.kumar on 6/15/2018.
 */

public class KycPSSDetailsModel {
    private String ID;
    public boolean isApprover;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getREQUEST_CODE() {
        return REQUEST_CODE;
    }

    public void setREQUEST_CODE(String REQUEST_CODE) {
        this.REQUEST_CODE = REQUEST_CODE;
    }

    public String getRequestor_Code() {
        return Requestor_Code;
    }

    public void setRequestor_Code(String requestor_Code) {
        Requestor_Code = requestor_Code;
    }

    public int getOverall_Status() {
        return Overall_Status;
    }

    public void setOverall_Status(int overall_Status) {
        Overall_Status = overall_Status;
    }

    public String getCurrent_Approver() {
        return Current_Approver;
    }

    public void setCurrent_Approver(String current_Approver) {
        Current_Approver = current_Approver;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getJustification() {
        return Justification;
    }

    public void setJustification(String justification) {
        Justification = justification;
    }

    public String getRequest_Date() {
        return Request_Date;
    }

    public void setRequest_Date(String request_Date) {
        Request_Date = request_Date;
    }

    public String getCREATED_DATE() {
        return CREATED_DATE;
    }

    public void setCREATED_DATE(String CREATED_DATE) {
        this.CREATED_DATE = CREATED_DATE;
    }

    public String getMOD_DATE() {
        return MOD_DATE;
    }

    public void setMOD_DATE(String MOD_DATE) {
        this.MOD_DATE = MOD_DATE;
    }

    public String getBusinessPlaceCode() {
        return BusinessPlaceCode;
    }

    public void setBusinessPlaceCode(String businessPlaceCode) {
        BusinessPlaceCode = businessPlaceCode;
    }

    public String getSectionChanged() {
        return sectionChanged;
    }

    public void setSectionChanged(String sectionChanged) {
        this.sectionChanged = sectionChanged;
    }

    private String REQUEST_CODE;
    private String Requestor_Code;
    private int Overall_Status;
    private String Current_Approver;
    private String flag;
    private String Justification;
    private String Request_Date;
    private String CREATED_DATE;
    private String MOD_DATE;
    private String BusinessPlaceCode;
    private String sectionChanged;

}
