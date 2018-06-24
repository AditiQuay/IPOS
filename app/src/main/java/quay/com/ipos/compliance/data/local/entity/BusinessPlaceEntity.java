package quay.com.ipos.compliance.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "places" , primaryKeys = "id")
public class BusinessPlaceEntity {

    @Expose
    @SerializedName("StoreId")
    public int id;

    @Expose
    @SerializedName("LocationName")
    public String name;

    @Expose
    @SerializedName("Address1")
    public String address1;

    @Expose
    @SerializedName("LocationCity")
    public String city;

    @Expose
    @SerializedName("LocationState")
    public String state;

    @Expose
    @SerializedName("RoleCode")
    public int roleCode;

    @Expose
    @SerializedName("EmpCode")
    public String empCode;




            /*
             "StoreId": 3,
             "LocationName": "Strore 3",
             "Address1": "Sector -24",
             "LocationCity": "Gurugram",
             "LocationState": "Haryana",
             "RoleCode": 2,
             "EmpCode": "6000015"

             */

    @Ignore
    public List<Task> taskSchedulerEntityList;


    public List<Task> getComplianceList() {
        return taskSchedulerEntityList;
    }
    public void setCompliances(List<Task> taskSchedulerEntityList){
        this.taskSchedulerEntityList =  taskSchedulerEntityList;

    }
}
