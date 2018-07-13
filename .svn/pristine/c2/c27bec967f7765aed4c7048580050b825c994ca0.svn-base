package quay.com.ipos.compliance.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "attachment"/*,
        foreignKeys = @ForeignKey(entity = TransactionEntity.class,
                parentColumns = "id",
                childColumns = "txId",
                onDelete = CASCADE)*/)
public class AttachmentEntity {
    @PrimaryKey (autoGenerate = true)
    public long id;
    public long txId;
    public String base;
    public String ext;
    public String type;
    public String name;

    public boolean isSync;
}
