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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
    private Toolbar toolbar;
    private Button btnAction, btnSave;
    private Context mContext;
    private RelativeLayout rGrn,rTransporter,rItemsDetails,rIncco,rAttachment;
    private LinearLayout lGrn,lItemsDetails,llIncoTerms,llTermsC;
    private RelativeLayout lTransporter;
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
        btnSave = findViewById(R.id.btnSave);

        rGrn = findViewById(R.id.rGrn);
        rTransporter = findViewById(R.id.rTransporter);
        rItemsDetails = findViewById(R.id.rItemsDetails);
        rIncco = findViewById(R.id.rIncco);
        rAttachment = findViewById(R.id.rAttachment);

        rGrn.setOnClickListener(this);
        rTransporter.setOnClickListener(this);
        rItemsDetails.setOnClickListener(this);
        rIncco.setOnClickListener(this);
        rAttachment.setOnClickListener(this);

        lGrn = findViewById(R.id.lGrn);
        lTransporter = findViewById(R.id.lTransporter);
        lItemsDetails = findViewById(R.id.lItemsDetails);
        llIncoTerms = findViewById(R.id.llIncoTerms);
        llTermsC = findViewById(R.id.llTermsC);

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

                break;
            case R.id.rGrn:
                lGrn.setVisibility(View.VISIBLE);
                lTransporter.setVisibility(View.GONE);
                lItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);

                break;
            case R.id.rTransporter:
                lTransporter.setVisibility(View.VISIBLE);
                lGrn.setVisibility(View.GONE);
                lItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);

                break;
            case R.id.rItemsDetails:
                lItemsDetails.setVisibility(View.VISIBLE);
                lGrn.setVisibility(View.GONE);
                lTransporter.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);

                break;
            case R.id.rIncco:
                llIncoTerms.setVisibility(View.VISIBLE);
                lItemsDetails.setVisibility(View.GONE);
                lGrn.setVisibility(View.GONE);
                lTransporter.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);

                break;
            case R.id.rAttachment:
                llTermsC.setVisibility(View.VISIBLE);
                lGrn.setVisibility(View.GONE);
                lTransporter.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                lItemsDetails.setVisibility(View.GONE);

            default:
                break;

        }
    }
}
