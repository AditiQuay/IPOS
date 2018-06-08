package quay.com.ipos.partnerConnect.model;

public class BusinessLocation {
    private String businessPINCode;

    private String BusinessZone;

    private String businessState;

    private String businessCity;

    public String getBusinessPINCode ()
    {
        return businessPINCode;
    }

    public void setBusinessPINCode (String businessPINCode)
    {
        this.businessPINCode = businessPINCode;
    }

    public String getBusinessZone ()
    {
        return BusinessZone;
    }

    public void setBusinessZone (String BusinessZone)
    {
        this.BusinessZone = BusinessZone;
    }

    public String getBusinessState ()
    {
        return businessState;
    }

    public void setBusinessState (String businessState)
    {
        this.businessState = businessState;
    }

    public String getBusinessCity ()
    {
        return businessCity;
    }

    public void setBusinessCity (String businessCity)
    {
        this.businessCity = businessCity;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [businessPINCode = "+businessPINCode+", BusinessZone = "+BusinessZone+", businessState = "+businessState+", businessCity = "+businessCity+"]";
    }
}
