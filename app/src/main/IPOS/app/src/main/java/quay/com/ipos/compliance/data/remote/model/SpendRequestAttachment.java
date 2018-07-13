package quay.com.ipos.compliance.data.remote.model;

/**
 * Created by deepak.kumar1 on 12-04-2018.
 */

public class SpendRequestAttachment {
    /* {
          "AttachmentBase": "sample string 1",
      "AttachmentExtension": "sample string 2",
      "AttachmentType": "sample string 3",
      "AttachmentName": "sample string 4"
     },*/
    public String AttachmentBase = "Base64";//convwerted base 64 file
    public String AttachmentExtension = ".pdf";
    public String AttachmentType = "PDF File";
    public String AttachmentName = "BT.Pdf";
}
