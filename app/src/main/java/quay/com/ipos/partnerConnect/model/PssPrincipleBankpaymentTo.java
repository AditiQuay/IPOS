package quay.com.ipos.partnerConnect.model;

public class PssPrincipleBankpaymentTo {
    private String principleAccountNo;

    private String principleBankIFSCode;

    private String principleBankBranchName;

    private String principleBankName;

    private String EntityPaytoName;

    public String getPrincipleAccountNo ()
    {
        return principleAccountNo;
    }

    public void setPrincipleAccountNo (String principleAccountNo)
    {
        this.principleAccountNo = principleAccountNo;
    }

    public String getPrincipleBankIFSCode ()
    {
        return principleBankIFSCode;
    }

    public void setPrincipleBankIFSCode (String principleBankIFSCode)
    {
        this.principleBankIFSCode = principleBankIFSCode;
    }

    public String getPrincipleBankBranchName ()
    {
        return principleBankBranchName;
    }

    public void setPrincipleBankBranchName (String principleBankBranchName)
    {
        this.principleBankBranchName = principleBankBranchName;
    }

    public String getPrincipleBankName ()
    {
        return principleBankName;
    }

    public void setPrincipleBankName (String principleBankName)
    {
        this.principleBankName = principleBankName;
    }

    public String getEntityPaytoName ()
    {
        return EntityPaytoName;
    }

    public void setEntityPaytoName (String EntityPaytoName)
    {
        this.EntityPaytoName = EntityPaytoName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [principleAccountNo = "+principleAccountNo+", principleBankIFSCode = "+principleBankIFSCode+", principleBankBranchName = "+principleBankBranchName+", principleBankName = "+principleBankName+", EntityPaytoName = "+EntityPaytoName+"]";
    }
}
