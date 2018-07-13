package quay.com.ipos.partnerConnect.model;

import com.google.gson.annotations.SerializedName;

public class DocumentVoults {

    public int ID;

    public String RequestNo;


    public int EntityId;


    @SerializedName("DocFilename")
    public String DocFilename;


    @SerializedName("Doctype")
    public String Doctype;


    @SerializedName("DocFileBase64")
    public String DocFileBase64;

    public boolean isApproved;


    //sample
  /*  {
        "ID": 1,
            "RequestNo": "3",
            "EntiId": 1,
            "Doctype": "PAN",
            "DocFilename": "mypan.png",
            "DocFileBase64": null
    },*/
  public DocumentVoults(int EntiId,String mDocType){
      this.EntityId = EntiId;
      this.Doctype = mDocType;
      this.RequestNo = "0";
      this.isApproved = false;
      this.DocFileBase64 = "";
      this.DocFilename = "";

  }

}
