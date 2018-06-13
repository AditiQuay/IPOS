package quay.com.ipos.partnerConnect.kyc;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.partnerConnect.adapter.KYCViewAllAdapter;
import quay.com.ipos.partnerConnect.model.KycModel;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 6/11/2018.
 */

public class KYCViewAll extends AppCompatActivity implements InitInterface, MyListener {
    private Context mContext;
    private static final String TAG = KYCViewAll.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView textViewDocumentsVaultHeading;
    private ArrayList<KycModel> kycModels = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private Dialog myDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_view_all);
        mContext = KYCViewAll.this;
        myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        textViewDocumentsVaultHeading = findViewById(R.id.textViewDocumentsVaultHeading);
        recyclerView = findViewById(R.id.recyclerViewDocuments);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("KYC-KGN Traders");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        kycModels.clear();
        getServerData();

        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        KYCViewAllAdapter kycViewAllAdapter = new KYCViewAllAdapter(mContext, kycModels, this);
        recyclerView.setAdapter(kycViewAllAdapter);
    }

    private void getServerData() {
        try {

            String json = Util.getAssetJsonResponse(this, "kyc_documents.json");
            JSONObject jsonArray = new JSONObject(json);
            JSONArray jsonArray1 = jsonArray.optJSONArray("data");
            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject jsonObject = jsonArray1.optJSONObject(i);
                KycModel kycModel = new KycModel();
                kycModel.documentId = jsonObject.optInt("documentId");
                kycModel.documentName = jsonObject.optString("documentName");
                kycModel.approvalStatus = jsonObject.optString("approvalStatus");
                kycModel.productUrl = jsonObject.optString("productUrl");
                kycModel.message = jsonObject.optString("message");
                kycModels.add(kycModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public void onRowClicked(int position) {

        TextView textViewTitle;
        ImageView ImvClose, imgDocumentPreview;
        TextInputLayout tilMessage;
        TextInputEditText tieMessage;
        Button btnReject, btnAccept;

        myDialog.setContentView(R.layout.view_dialog);
        textViewTitle = myDialog.findViewById(R.id.textViewTitle);
        ImvClose = myDialog.findViewById(R.id.ImvClose);
        ImvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        imgDocumentPreview = myDialog.findViewById(R.id.imgDocumentPreview);
        tilMessage = myDialog.findViewById(R.id.tilMessage);
        btnReject = myDialog.findViewById(R.id.btnReject);
        btnAccept = myDialog.findViewById(R.id.btnAccept);

        KycModel kycModel = kycModels.get(position);

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


    }

    @Override
    public void onRowClicked(int position, int value) {

    }
}
