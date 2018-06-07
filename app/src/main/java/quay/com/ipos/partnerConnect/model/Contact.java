package quay.com.ipos.partnerConnect.model;

public class Contact {
    private KeyBusinessContactInfo KeyBusinessContactInfo;

    public KeyBusinessContactInfo getKeyBusinessContactInfo ()
    {
        return KeyBusinessContactInfo;
    }

    public void setKeyBusinessContactInfo (KeyBusinessContactInfo KeyBusinessContactInfo)
    {
        this.KeyBusinessContactInfo = KeyBusinessContactInfo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [KeyBusinessContactInfo = "+KeyBusinessContactInfo+"]";
    }
}
