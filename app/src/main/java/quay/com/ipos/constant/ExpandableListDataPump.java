package quay.com.ipos.constant;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {

        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> Billing = new ArrayList<String>();
        Billing.add("Retail Sales (OTC & Online)");
        Billing.add("DDR Sales (B2B)");
        Billing.add("Day Closure");
        Billing.add("Loyalty Program");
        Billing.add("Offer & Discounts");


        List<String> ManageStore = new ArrayList<String>();
        ManageStore.add("Inventory In / Out");
        ManageStore.add("Asset In / Out");
        ManageStore.add("Stores Expenses");
        ManageStore.add("Employee Management");

        List<String> ManageBusiness = new ArrayList<String>();
        ManageBusiness.add("Miscellaneous Income");
        ManageBusiness.add("Expenses & JV");
        ManageBusiness.add("Accounting & Settlements");
        ManageBusiness.add("Auto Bank Reconciliation");
        ManageBusiness.add("Secondary Sales Tracking");
        ManageBusiness.add("Compliance Tracking");

        List<String> Insight = new ArrayList<String>();
        Insight.add("Insights & Alerts for Action");
        Insight.add("Dashboards & Reports");
        Insight.add("Customer Engagements");

        List<String> Settings = new ArrayList<String>();
        List<String> MostlyUsed = new ArrayList<String>();

        expandableListDetail.put("Mostly Used",MostlyUsed);
        expandableListDetail.put("Billing & Cash", Billing);
        expandableListDetail.put("Manage Store", ManageStore);
        expandableListDetail.put("Manage Business", ManageBusiness);
        expandableListDetail.put("Insights & Analytics", Insight);

        return expandableListDetail;
    }
}
