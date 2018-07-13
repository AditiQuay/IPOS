package quay.com.ipos.modal;

import java.util.ArrayList;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class ExpandableListDrawerModel {
    //PROPERTIES OF A SINGLE TEAM
    public String Name;
    public String Image;
    public ArrayList<String> listGroupItem=new ArrayList<String>();

    public ExpandableListDrawerModel(String Name)
    {
        this.Name=Name;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return Name;
    }
}
