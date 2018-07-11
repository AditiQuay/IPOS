package quay.com.ipos.customerInfo.customerInfoModal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niraj.kumar on 6/5/2018.
 */

public class CustomeChildListModel {
   public List<ChildList> customerChildList=new ArrayList<>();

    public class ChildList {
        public String customerChildFirstName;//": "string",
        public String customerChildLastName;//": "string",
        public String customerChildGender;//": "string",
        public String customerChildDob;//": "string"
    }

   /* "customerChild": [
    {
        "customerChildFirstName": "string",
            "customerChildLastName": "string",
            "customerChildGender": "string",
            "customerChildDob": "string"
    }
    ]*/

}
