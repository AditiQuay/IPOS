package quay.com.ipos.partnerConnect.model;

public class DocumentVoults {
    private String DocFilename;

    private String Doctype;

    private String DocFileBase64;

    public String getDocFilename ()
    {
        return DocFilename;
    }

    public void setDocFilename (String DocFilename)
    {
        this.DocFilename = DocFilename;
    }

    public String getDoctype ()
    {
        return Doctype;
    }

    public void setDoctype (String Doctype)
    {
        this.Doctype = Doctype;
    }

    public String getDocFileBase64 ()
    {
        return DocFileBase64;
    }

    public void setDocFileBase64 (String DocFileBase64)
    {
        this.DocFileBase64 = DocFileBase64;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DocFilename = "+DocFilename+", Doctype = "+Doctype+", DocFileBase64 = "+DocFileBase64+"]";
    }
}
