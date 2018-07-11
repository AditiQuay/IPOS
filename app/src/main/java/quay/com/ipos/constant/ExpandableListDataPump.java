package quay.com.ipos.constant;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.realm.Realm;
import quay.com.ipos.modal.DrawerRoleModal;
import quay.com.ipos.modal.MenuModal;
import quay.com.ipos.realmbean.RealmUserDetail;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class ExpandableListDataPump {
    public static HashMap<MenuModal, List<String>> getData() {

        LinkedHashMap<MenuModal, List<String>> expandableListDetail = new LinkedHashMap<MenuModal, List<String>>();

        Realm realm =Realm.getDefaultInstance();
        RealmUserDetail realmUserDetails=realm.where(RealmUserDetail.class).findFirst();

        if (realmUserDetails!=null) {
            try {
                JSONArray jsonArray = new JSONArray(realmUserDetails.getUserMenu());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    JSONArray jsonArray1 = jsonObject.optJSONArray("data");
                    if (jsonArray1.length() > 0) {


                        for (int j = 0; j < jsonArray1.length(); j++) {
                            MenuModal menuModal = new MenuModal();
                            JSONObject jsonObject1 = jsonArray1.optJSONObject(j);

                            menuModal.setGroupTitle(jsonObject1.optString("title"));
                            menuModal.setGroupIcon(jsonObject1.optString("icon"));
                            JSONArray jsonArray2 = jsonObject1.optJSONArray("child");
                            ArrayList<String> childList = new ArrayList<String>();
                            for (int k = 0; k < jsonArray2.length(); k++) {
                                JSONObject jsonObject2 = jsonArray2.optJSONObject(k);
                                childList.add(jsonObject2.optString("name"));

                            }

                            menuModal.setArrayList(childList);
                            expandableListDetail.put(menuModal, childList);
                        }

                        break;
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



      /*  List<String> Billing = new ArrayList<String>();
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
        Insight.add("CustomerList Engagements");

        List<String> Settings = new ArrayList<String>();
        List<String> MostlyUsed = new ArrayList<String>();

        expandableListDetail.put("Mostly Used",MostlyUsed);
        expandableListDetail.put("Billing & Cash", Billing);
        expandableListDetail.put("Manage Store", ManageStore);
        expandableListDetail.put("Manage KycBusiness", ManageBusiness);
        expandableListDetail.put("Insights & Analytics", Insight);*/

        AppLog.e("tag", Util.getCustomGson().toJson(expandableListDetail));
        return expandableListDetail;
    }
}
