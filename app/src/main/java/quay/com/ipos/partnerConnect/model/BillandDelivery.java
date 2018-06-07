package quay.com.ipos.partnerConnect.model;

public class BillandDelivery { private String entityBusinessGSTIN;

    private String entityBusinessPalceAddress;

    private String entityBusinessPalceName;

    private String entityContactPersonMobile;

    private String entityContactPerson;

    private String entityBusinessCity;

    private String entityAddressType;

    private String entityBusinessState;

    public String getEntityBusinessGSTIN ()
    {
        return entityBusinessGSTIN;
    }

    public void setEntityBusinessGSTIN (String entityBusinessGSTIN)
    {
        this.entityBusinessGSTIN = entityBusinessGSTIN;
    }

    public String getEntityBusinessPalceAddress ()
    {
        return entityBusinessPalceAddress;
    }

    public void setEntityBusinessPalceAddress (String entityBusinessPalceAddress)
    {
        this.entityBusinessPalceAddress = entityBusinessPalceAddress;
    }

    public String getEntityBusinessPalceName ()
    {
        return entityBusinessPalceName;
    }

    public void setEntityBusinessPalceName (String entityBusinessPalceName)
    {
        this.entityBusinessPalceName = entityBusinessPalceName;
    }

    public String getEntityContactPersonMobile ()
    {
        return entityContactPersonMobile;
    }

    public void setEntityContactPersonMobile (String entityContactPersonMobile)
    {
        this.entityContactPersonMobile = entityContactPersonMobile;
    }

    public String getEntityContactPerson ()
    {
        return entityContactPerson;
    }

    public void setEntityContactPerson (String entityContactPerson)
    {
        this.entityContactPerson = entityContactPerson;
    }

    public String getEntityBusinessCity ()
    {
        return entityBusinessCity;
    }

    public void setEntityBusinessCity (String entityBusinessCity)
    {
        this.entityBusinessCity = entityBusinessCity;
    }

    public String getEntityAddressType ()
    {
        return entityAddressType;
    }

    public void setEntityAddressType (String entityAddressType)
    {
        this.entityAddressType = entityAddressType;
    }

    public String getEntityBusinessState ()
    {
        return entityBusinessState;
    }

    public void setEntityBusinessState (String entityBusinessState)
    {
        this.entityBusinessState = entityBusinessState;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [entityBusinessGSTIN = "+entityBusinessGSTIN+", entityBusinessPalceAddress = "+entityBusinessPalceAddress+", entityBusinessPalceName = "+entityBusinessPalceName+", entityContactPersonMobile = "+entityContactPersonMobile+", entityContactPerson = "+entityContactPerson+", entityBusinessCity = "+entityBusinessCity+", entityAddressType = "+entityAddressType+", entityBusinessState = "+entityBusinessState+"]";
    }
}
