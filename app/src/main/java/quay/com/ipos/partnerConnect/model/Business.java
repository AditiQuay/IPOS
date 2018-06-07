package quay.com.ipos.partnerConnect.model;

public class Business {
    private BusinessLocation BusinessLocation;

    private KeyBusinessInfo KeyBusinessInfo;

    public BusinessLocation getBusinessLocation ()
    {
        return BusinessLocation;
    }

    public void setBusinessLocation (BusinessLocation BusinessLocation)
    {
        this.BusinessLocation = BusinessLocation;
    }

    public KeyBusinessInfo getKeyBusinessInfo ()
    {
        return KeyBusinessInfo;
    }

    public void setKeyBusinessInfo (KeyBusinessInfo KeyBusinessInfo)
    {
        this.KeyBusinessInfo = KeyBusinessInfo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [BusinessLocation = "+BusinessLocation+", KeyBusinessInfo = "+KeyBusinessInfo+"]";
    }
}
