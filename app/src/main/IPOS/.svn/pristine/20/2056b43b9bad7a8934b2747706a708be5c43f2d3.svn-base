package quay.com.ipos.compliance.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "transaction"/*,
        foreignKeys = @ForeignKey(entity = TaskSchedulerEntity.class,
                parentColumns = "id",
                childColumns = "taskId",
                onDelete = CASCADE)*/)
public class TransactionEntity {

    @PrimaryKey
    public int id;
    public int taskId;
    public String taskName;
    public String taskDescription;
    @SerializedName("CreateDate")
    public String taskStartDateTime;
    public String taskEndDateTime;
    public String assignTo;
    public String status;
    @SerializedName("Remark")
    public String remarks;
    @SerializedName("CompletedScheduleDateTime")
    public String completedDateTime;
    public String remNextScheduleDateTime;
    @SerializedName("RemLastScheduleDateTime")
    public String remLastRunDateTime;
    @SerializedName("RemIntervalTypeID")
    public String remIntervalType;
    @SerializedName("RemIntervalValue")
    public String remIntervalValue;

    @Ignore
    public List<AttachmentEntity> attachments;


}
