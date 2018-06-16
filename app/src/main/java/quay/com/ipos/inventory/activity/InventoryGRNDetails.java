package quay.com.ipos.inventory.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.inventory.adapter.CustomGrnTermsExpandableListAdapter;
import quay.com.ipos.listeners.InitInterface;

/**
 * Created by niraj.kumar on 6/14/2018.
 */

public class InventoryGRNDetails extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private ExpandableListView expandableListView;
    private CustomGrnTermsExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private Toolbar toolbar;
    private Button btnAction, btnSave;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grn_expandable);
        mContext = InventoryGRNDetails.this;
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    @Override
    public void findViewById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnAction = findViewById(R.id.btnAction);
        btnSave = findViewById(R.id.btnAction);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setChildDivider(getResources().getDrawable(R.color.white));

        btnSave.setOnClickListener(this);
        btnAction.setOnClickListener(this);
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

        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();
        List<String> ItemDetails = new ArrayList<>();
        ItemDetails.add("United States");


        expandableListDetail.put("GRN", ItemDetails);
        expandableListDetail.put("Transporter", ItemDetails);
        expandableListDetail.put("Item details", ItemDetails);
        expandableListDetail.put("INCO Terms", ItemDetails);
        expandableListDetail.put("Attachments", ItemDetails);

        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomGrnTermsExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
             /*   Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/
                return false;
            }
        });
    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAction:
                Intent i = new Intent(mContext, InventoryWorkFlowActivity.class);
                startActivity(i);
                break;
            case R.id.btnSave:
            default:
                break;

        }
    }
}
