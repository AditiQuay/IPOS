package quay.com.ipos.partnerConnect.model;

public class KeyBusinessContactInfo {
    private String keyContactEntityEmpPosition;

    private String keyEntityEmpEmail;

    private String KeyContactEntityEmpperson;

    private String keyEntityEmpMobile1;

    private String keyEntityEmpMobile2;

    private String keyEntityEmpNote;

    public String getKeyContactEntityEmpPosition ()
    {
        return keyContactEntityEmpPosition;
    }

    public void setKeyContactEntityEmpPosition (String keyContactEntityEmpPosition)
    {
        this.keyContactEntityEmpPosition = keyContactEntityEmpPosition;
    }

    public String getKeyEntityEmpEmail ()
    {
        return keyEntityEmpEmail;
    }

    public void setKeyEntityEmpEmail (String keyEntityEmpEmail)
    {
        this.keyEntityEmpEmail = keyEntityEmpEmail;
    }

    public String getKeyContactEntityEmpperson ()
    {
        return KeyContactEntityEmpperson;
    }

    public void setKeyContactEntityEmpperson (String KeyContactEntityEmpperson)
    {
        this.KeyContactEntityEmpperson = KeyContactEntityEmpperson;
    }

    public String getKeyEntityEmpMobile1 ()
    {
        return keyEntityEmpMobile1;
    }

    public void setKeyEntityEmpMobile1 (String keyEntityEmpMobile1)
    {
        this.keyEntityEmpMobile1 = keyEntityEmpMobile1;
    }

    public String getKeyEntityEmpMobile2 ()
    {
        return keyEntityEmpMobile2;
    }

    public void setKeyEntityEmpMobile2 (String keyEntityEmpMobile2)
    {
        this.keyEntityEmpMobile2 = keyEntityEmpMobile2;
    }

    public String getKeyEntityEmpNote ()
    {
        return keyEntityEmpNote;
    }

    public void setKeyEntityEmpNote (String keyEntityEmpNote)
    {
        this.keyEntityEmpNote = keyEntityEmpNote;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [keyContactEntityEmpPosition = "+keyContactEntityEmpPosition+", keyEntityEmpEmail = "+keyEntityEmpEmail+", KeyContactEntityEmpperson = "+KeyContactEntityEmpperson+", keyEntityEmpMobile1 = "+keyEntityEmpMobile1+", keyEntityEmpMobile2 = "+keyEntityEmpMobile2+", keyEntityEmpNote = "+keyEntityEmpNote+"]";
    }
}
