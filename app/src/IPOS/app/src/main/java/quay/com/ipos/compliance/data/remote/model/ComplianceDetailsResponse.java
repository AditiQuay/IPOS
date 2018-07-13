package quay.com.ipos.compliance.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;

import quay.com.ipos.compliance.data.local.entity.BusinessPlaceEntity;
import quay.com.ipos.compliance.data.local.entity.Employee;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.local.entity.Task;

public class ComplianceDetailsResponse {
    @Expose
    @SerializedName("statusCode")
    public int statusCode;
    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("response")
    public ComplianceData response;


    public class ComplianceData {
        @Expose
        @SerializedName("storesList")
        public List<BusinessPlaceEntity> businessPlaceList;
        @Expose
        @SerializedName("TaskData")
        public List<Task> taskList;
        @Expose
        @SerializedName("SubTaskData")
        public List<SubTask> subTaskList;
        @Expose
        @SerializedName("applyTo")
        public List<Employee> employeeList;

    }


   /* {
        "Code":200,
            "compliance_data":{
        "storesLst": [
        {
                "StoreId":3,
                "LocationName":"Strore 3",
                "Address1":"Sector -24",
                "LocationCity":"Gurugram",
                "LocationState":"Haryana",
                "RoleCode":2,
                "EmpCode":"6000015"
        },
        {
            "StoreId":1,
                "LocationName":"Strore 1",
                "Address1":"Sector -21",
                "LocationCity":"Gurugram",
                "LocationState":"Haryana",
                "RoleCode":3,
                "EmpCode":"6000015"
        }
        ],
        "TaskData": [
        {
            "ID":1,
                "ControlMasterID":1,
                "TaskName":"Task1 ",
                "TaskDescription":"Task 1 desc",
                "TaskStartDateTime":"2018-05-21T13:07:46.203",
                "TaskEndDateTime":"2018-05-21T13:07:46.203",
                "TaskAssignTo":"10001905",
                "RecurrenceCount":4,
                "NextScheduleDateTime":"2018-05-21T13:07:46.203",
                "LastScheduleRunDateTime":"2018-05-21T13:07:46.203",
                "IntervalTypeID":2,
                "IntervalValue":2,
                "Status":2,
                "SourceReference":null,
                "TaskFunction":"task 1 function",
                "TaskSubFunction":null,
                "LocationCode":1,
                "RemNextScheduleDateTime":"2018-05-21T13:07:46.203",
                "RemLastScheduleDateTime":"2018-05-21T13:07:46.203",
                "RemIntervalTypeID":1,
                "RemIntervalValue":2,
                "CreateDate":"2018-05-21T13:07:46.203",
                "Satustype":"Cancelled",
                "IntervalType":"Hours",
                "RemType":"Min",
                "ComplianceName":"Business"
        },
        {
            "ID":2,
                "ControlMasterID":2,
                "TaskName":"Task2",
                "TaskDescription":"Task 2 desc",
                "TaskStartDateTime":"2018-05-21T13:07:46.203",
                "TaskEndDateTime":"2018-05-21T13:07:46.203",
                "TaskAssignTo":"10001905",
                "RecurrenceCount":5,
                "NextScheduleDateTime":"2018-05-21T13:07:46.203",
                "LastScheduleRunDateTime":"2018-05-21T13:07:46.203",
                "IntervalTypeID":1,
                "IntervalValue":5,
                "Status":1,
                "SourceReference":null,
                "TaskFunction":"task 2 function",
                "TaskSubFunction":null,
                "LocationCode":1,
                "RemNextScheduleDateTime":"2018-05-21T13:07:46.203",
                "RemLastScheduleDateTime":"2018-05-21T13:07:46.203",
                "RemIntervalTypeID":2,
                "RemIntervalValue":5,
                "CreateDate":"2018-05-21T13:07:46.203",
                "Satustype":"Complete",
                "IntervalType":"Min",
                "RemType":"Hours",
                "ComplianceName":"Stattutory"
        }
        ],
        "SubTaskData": [
        {
            "ID":1,
                "TaskSchedulerID":1,
                "SubTaskName":"Sub task for task 1",
                "SubTaskDescription":"Sub task for task 1",
                "SubTaskStartDateTime":"2018-05-23T09:40:27.577",
                "SubTaskEndDateTime":"2018-05-23T09:40:27.577",
                "SubTaskAssignTo":"10001905",
                "RecurrenceCount":5,
                "NextScheduleDateTime":"2018-05-23T09:40:27.577",
                "LastRunDateTime":"2018-05-23T09:40:27.577",
                "IntervalTypeID":2,
                "IntervalValue":5,
                "Status":"2",
                "RemNextScheduleDateTime":"2018-05-23T09:40:27.577",
                "RemLastRunDateTime":"2018-05-23T09:40:27.577",
                "RemIntervalType":1,
                "RemIntervalValue":3,
                "Createdate":"2018-05-23T09:40:27.577",
                "Active":true,
                "Satustype":"Cancelled",
                "IntervalType":"Hours",
                "RemType":"Min"
        },
        {
            "ID":3,
                "TaskSchedulerID":2,
                "SubTaskName":"sub task for task 2",
                "SubTaskDescription":"sub task for task 2",
                "SubTaskStartDateTime":"2018-05-23T09:40:59.913",
                "SubTaskEndDateTime":"2018-05-23T09:40:59.913",
                "SubTaskAssignTo":"10001905",
                "RecurrenceCount":3,
                "NextScheduleDateTime":"2018-05-23T09:40:59.913",
                "LastRunDateTime":"2018-05-23T09:40:59.913",
                "IntervalTypeID":3,
                "IntervalValue":4,
                "Status":"1",
                "RemNextScheduleDateTime":"2018-05-23T09:40:59.913",
                "RemLastRunDateTime":"2018-05-23T09:40:59.913",
                "RemIntervalType":2,
                "RemIntervalValue":4,
                "Createdate":"2018-05-23T09:40:59.913",
                "Active":true,
                "Satustype":"Complete",
                "IntervalType":"Day",
                "RemType":"Hours"
        }
        ],
        "applyTo": [
        {
            "EmpCode":"6000013",
                "EmpName":"Vilas Jadhav"
        },
        {
            "EmpCode":"6000014",
                "EmpName":"Niraj Kumar"
        },
        {
            "EmpCode":"6000015",
                "EmpName":"Deepak Kumar"
        }
        ]
    }
    }*/

}
