package quay.com.ipos.partnerConnect.model;

import com.google.gson.annotations.SerializedName;

public class DocumentVoults {

    public int ID;

    public String DocFilename;

    @SerializedName("Doctype")
    public String Doctype;

    public String DocFileBase64;


}
