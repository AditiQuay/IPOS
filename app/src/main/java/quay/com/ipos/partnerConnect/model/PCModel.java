package quay.com.ipos.partnerConnect.model;

public class PCModel {

    private DocumentVoults[] DocumentVoults;

    private BillandDelivery[] BillandDelivery;

    private Business Business;

    private Contact Contact;

    public Relationship Relationship;

    private Acount Acount;

    private String psslastUpdated;

    private String RelationShipName;

    public DocumentVoults[] getDocumentVoults ()
    {
        return DocumentVoults;
    }

    public void setDocumentVoults (DocumentVoults[] DocumentVoults)
    {
        this.DocumentVoults = DocumentVoults;
    }

    public BillandDelivery[] getBillandDelivery ()
    {
        return BillandDelivery;
    }

    public void setBillandDelivery (BillandDelivery[] BillandDelivery)
    {
        this.BillandDelivery = BillandDelivery;
    }

    public Business getBusiness ()
    {
        return Business;
    }

    public void setBusiness (Business Business)
    {
        this.Business = Business;
    }

    public Contact getContact ()
    {
        return Contact;
    }

    public void setContact (Contact Contact)
    {
        this.Contact = Contact;
    }

    public Relationship getRelationship ()
    {
        return Relationship;
    }

    public void setRelationship (Relationship Relationship)
    {
        this.Relationship = Relationship;
    }

    public Acount getAcount ()
    {
        return Acount;
    }

    public void setAcount (Acount Acount)
    {
        this.Acount = Acount;
    }

    public String getPsslastUpdated ()
    {
        return psslastUpdated;
    }

    public void setPsslastUpdated (String psslastUpdated)
    {
        this.psslastUpdated = psslastUpdated;
    }

    public String getRelationShipName ()
    {
        return RelationShipName;
    }

    public void setRelationShipName (String RelationShipName)
    {
        this.RelationShipName = RelationShipName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DocumentVoults = "+DocumentVoults+", BillandDelivery = "+BillandDelivery+", Business = "+Business+", Contact = "+Contact+", Relationship = "+Relationship+", Acount = "+Acount+", psslastUpdated = "+psslastUpdated+", RelationShipName = "+RelationShipName+"]";
    }

}
