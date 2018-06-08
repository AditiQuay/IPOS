package quay.com.ipos.partnerConnect.model;

public class Acount {
    private String entityAcountNo;

    private String isSecurityCheques;

    private Cheques[] cheques;

    private String entityBankIFSCode;

    private String entityBankName;

    private String entityAcountHolderName;

    private String entityAcountType;

    private String entityBranchAdddres;

    public String getEntityAcountNo ()
    {
        return entityAcountNo;
    }

    public void setEntityAcountNo (String entityAcountNo)
    {
        this.entityAcountNo = entityAcountNo;
    }

    public String getIsSecurityCheques ()
    {
        return isSecurityCheques;
    }

    public void setIsSecurityCheques (String isSecurityCheques)
    {
        this.isSecurityCheques = isSecurityCheques;
    }

    public Cheques[] getCheques ()
    {
        return cheques;
    }

    public void setCheques (Cheques[] cheques)
    {
        this.cheques = cheques;
    }

    public String getEntityBankIFSCode ()
    {
        return entityBankIFSCode;
    }

    public void setEntityBankIFSCode (String entityBankIFSCode)
    {
        this.entityBankIFSCode = entityBankIFSCode;
    }

    public String getEntityBankName ()
    {
        return entityBankName;
    }

    public void setEntityBankName (String entityBankName)
    {
        this.entityBankName = entityBankName;
    }

    public String getEntityAcountHolderName ()
    {
        return entityAcountHolderName;
    }

    public void setEntityAcountHolderName (String entityAcountHolderName)
    {
        this.entityAcountHolderName = entityAcountHolderName;
    }

    public String getEntityAcountType ()
    {
        return entityAcountType;
    }

    public void setEntityAcountType (String entityAcountType)
    {
        this.entityAcountType = entityAcountType;
    }

    public String getEntityBranchAdddres ()
    {
        return entityBranchAdddres;
    }

    public void setEntityBranchAdddres (String entityBranchAdddres)
    {
        this.entityBranchAdddres = entityBranchAdddres;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [entityAcountNo = "+entityAcountNo+", isSecurityCheques = "+isSecurityCheques+", cheques = "+cheques+", entityBankIFSCode = "+entityBankIFSCode+", entityBankName = "+entityBankName+", entityAcountHolderName = "+entityAcountHolderName+", entityAcountType = "+entityAcountType+", entityBranchAdddres = "+entityBranchAdddres+"]";
    }
}
