package quay.com.ipos.inventory.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.inventory.adapter.CustomExpandableListAdapter;
import quay.com.ipos.inventory.adapter.CustomGrnTermsExpandableListAdapter;
import quay.com.ipos.listeners.InitInterface;

/**
 * Created by niraj.kumar on 6/14/2018.
 */

public class InventoryGRNDetails extends AppCompatActivity implements InitInterface {
    private ExpandableListView expandableListView;
    private CustomGrnTermsExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grn_expandable);
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    @Override
    public void findViewById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

//        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();
//
//        List<String> cricket = new ArrayList<String>();
//        cricket.add("India");
//
//
//        List<String> football = new ArrayList<String>();
//        football.add("Brazil");
//
//
//        List<String> basketball = new ArrayList<String>();
//        basketball.add("United States");
//
//
//        List<String> a = new ArrayList<String>();
//        a.add("United States");
//
//        List<String> v = new ArrayList<String>();
//        v.add("United States");
//
//        List<String> c = new ArrayList<String>();
//        c.add("United States");
//        List<String> d = new ArrayList<String>();
//        d.add("United States");
//        expandableListDetail.put("PO# 1800001", cricket);
//        expandableListDetail.put("Items details | 4 Items| 23 Qty", football);
//        expandableListDetail.put("INCO Terms", a);
//        expandableListDetail.put("Payment Terms", v);
//        expandableListDetail.put("Terms & Condition", c);
//        expandableListDetail.put("Attachments", d);
//        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
//        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
//        expandableListView.setAdapter(expandableListAdapter);
//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//             /*   Toast.makeText(
//                        getApplicationContext(),
//                        expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();*/
//                return false;
//            }
//        });
    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }
}
