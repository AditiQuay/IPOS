package quay.com.ipos.partnerConnect.model;

public class Cheques {
    private String entitydrawnAccount;

    private String chequeNo;

    private String MaxLimitAmount;

    public String getEntitydrawnAccount ()
    {
        return entitydrawnAccount;
    }

    public void setEntitydrawnAccount (String entitydrawnAccount)
    {
        this.entitydrawnAccount = entitydrawnAccount;
    }

    public String getChequeNo ()
    {
        return chequeNo;
    }

    public void setChequeNo (String chequeNo)
    {
        this.chequeNo = chequeNo;
    }

    public String getMaxLimitAmount ()
    {
        return MaxLimitAmount;
    }

    public void setMaxLimitAmount (String MaxLimitAmount)
    {
        this.MaxLimitAmount = MaxLimitAmount;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [entitydrawnAccount = "+entitydrawnAccount+", chequeNo = "+chequeNo+", MaxLimitAmount = "+MaxLimitAmount+"]";
    }
}
