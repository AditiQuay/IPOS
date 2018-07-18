package quay.com.ipos.compliance.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "attachment"/*,
        foreignKeys = @ForeignKey(entity = TransactionEntity.class,
                parentColumns = "id",
                childColumns = "txId",
                onDelete = CASCADE)*/)
public class AttachmentEntity {
    @PrimaryKey
    public long id;
    @SerializedName("TaskTransactionID")
    public long txId;
    @SerializedName("DocAttachment")
    public String base;
    @SerializedName("fileextension")
    public String extension;
    @SerializedName("Type")
    public String type;
    @SerializedName("fileName")
    public String name;
    @SerializedName("url")
    public String url;


  /*   "attachmentList": [
    {
        "ID": 0,
            "TaskTransactionID": 0,
            "DocAttachment": "string",
            "fileextension": "string",
            "fileName": "string",
            "Type": "string",
            "url": "string"
    }
  ]*/
}
