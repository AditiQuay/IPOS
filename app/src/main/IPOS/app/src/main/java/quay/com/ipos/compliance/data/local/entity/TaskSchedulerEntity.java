package quay.com.ipos.compliance.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "task_scheduler"/*,
        foreignKeys = @ForeignKey(entity = BusinessPlaceEntity.class,
                parentColumns = "id",
                childColumns = "placeId",
                onDelete = CASCADE)*/)
public class TaskSchedulerEntity {

    @PrimaryKey
    public int id;
    public int placeId;
    public String complianceRecordID;
    public String complianceType;
    public String complianceName;
    public String subFunction;
    @SerializedName("TaskName")
    public String taskName;
    @SerializedName("TaskDescription")
    public String taskDescription;
    @SerializedName("TaskEndDateTime")
    public String taskEndDateTime;
    @SerializedName("TaskStartDateTime")
    public String taskStartDateTime;
    @SerializedName("TaskAssignTo")
    public String taskAssignTo;
    @SerializedName("Status")
    public int status;

    public String statusType;
    public String intervalType;
    public int intervalValue;
    public String controlName;
    public String priority;
    public String remNextScheduleDateTime;
    public String remLastScheduleDateTime;
    public String remIntervalTypeID;
    public String remIntervalValue;

    @Ignore
    @SerializedName("trdata")
    public List<TransactionEntity> txEntityList;

    public String getDateAndTime() {
        return taskStartDateTime;
    }
}
