package quay.com.ipos.partnerConnect.model;

public class KeyBusinessInfo {
    private String EntitykeyContactPosition;

    private String EntityCIN;

    private String EntityCompaneyName;

    private String pssPartenerType;

    private String EntityKeyContactperson;

    private String EntityPAN;

    public String getEntitykeyContactPosition ()
    {
        return EntitykeyContactPosition;
    }

    public void setEntitykeyContactPosition (String EntitykeyContactPosition)
    {
        this.EntitykeyContactPosition = EntitykeyContactPosition;
    }

    public String getEntityCIN ()
    {
        return EntityCIN;
    }

    public void setEntityCIN (String EntityCIN)
    {
        this.EntityCIN = EntityCIN;
    }

    public String getEntityCompaneyName ()
    {
        return EntityCompaneyName;
    }

    public void setEntityCompaneyName (String EntityCompaneyName)
    {
        this.EntityCompaneyName = EntityCompaneyName;
    }

    public String getPssPartenerType ()
    {
        return pssPartenerType;
    }

    public void setPssPartenerType (String pssPartenerType)
    {
        this.pssPartenerType = pssPartenerType;
    }

    public String getEntityKeyContactperson ()
    {
        return EntityKeyContactperson;
    }

    public void setEntityKeyContactperson (String EntityKeyContactperson)
    {
        this.EntityKeyContactperson = EntityKeyContactperson;
    }

    public String getEntityPAN ()
    {
        return EntityPAN;
    }

    public void setEntityPAN (String EntityPAN)
    {
        this.EntityPAN = EntityPAN;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [EntitykeyContactPosition = "+EntitykeyContactPosition+", EntityCIN = "+EntityCIN+", EntityCompaneyName = "+EntityCompaneyName+", pssPartenerType = "+pssPartenerType+", EntityKeyContactperson = "+EntityKeyContactperson+", EntityPAN = "+EntityPAN+"]";
    }
}
