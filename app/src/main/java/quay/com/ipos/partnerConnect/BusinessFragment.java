package quay.com.ipos.partnerConnect;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.partnerConnectAdapter.BusinessAdapter;
import quay.com.ipos.partnerConnect.partnerConnectModel.BusinessModel;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class BusinessFragment extends Fragment implements InitInterface, ButtonListener, View.OnClickListener {
    private View main;
    private RelativeLayout rLayoutProductTitle;
    private TextView textViewBusinessInfoHeading, textViewMadatory, textViewLastUpdated;
    private RecyclerView recyclerViewBusinessInfo;
    private LinearLayout lLayoutBottom;
    private Button btnCancel, btnsubmit;
    private Context mContext;
    private ArrayList<BusinessModel> businessModels = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.business_fragment, container, false);
        mContext = getActivity();
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
        return main;
    }

    @Override
    public void findViewById() {
        rLayoutProductTitle = main.findViewById(R.id.rLayoutProductTitle);
        textViewBusinessInfoHeading = main.findViewById(R.id.textViewPersonalHeading);
        textViewMadatory = main.findViewById(R.id.textViewMadatory);
        textViewLastUpdated = main.findViewById(R.id.textViewLastUpdated);
        recyclerViewBusinessInfo = main.findViewById(R.id.recyclerViewBusinessInfo);
        lLayoutBottom = main.findViewById(R.id.lLayoutBottom);
        btnCancel = main.findViewById(R.id.btnCancel);
        btnsubmit = main.findViewById(R.id.btnsubmit);

        btnCancel.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
    }

    @Override
    public void applyInitValues() {

        businessModels.clear();

        BusinessModel businessModel = new BusinessModel();
        businessModel.setPartnerType("");
        businessModel.setPartnerCompanyName("");
        businessModel.setPartnerCinNumber("");
        businessModel.setPartnerPanNumber("");
        businessModel.setPartnerKeyContact("");
        businessModel.setPartnerContactPosition("");
        businessModel.setPartnerState("");
        businessModel.setPartnerCity("");
        businessModel.setPartnerPinCode("");
        businessModel.setPartnerZone("");
        businessModels.add(businessModel);


        recyclerViewBusinessInfo.setHasFixedSize(true);
        recyclerViewBusinessInfo.setLayoutManager(new LinearLayoutManager(mContext));
        BusinessAdapter businessAdapter = new BusinessAdapter(mContext, businessModels, this);
        recyclerViewBusinessInfo.setAdapter(businessAdapter);
    }

    @Override
    public void applyTypeFace() {
        //Personal
        FontUtil.applyTypeface(rLayoutProductTitle, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewBusinessInfoHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewMadatory, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewLastUpdated, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(recyclerViewBusinessInfo, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(lLayoutBottom, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnCancel, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnsubmit, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onAdd(int position, String firstName, String lastName, String childGender, String childDOB) {

    }

    @Override
    public void onPartnerAdd(int position, String distributerType, String companyName, String cinNumber, String panNumber, String contactPerson, String contactPosition, String partnerState, String partnerCity, String partnerPinCode, String partnerZone) {
        BusinessModel model = new BusinessModel();
        model.setPartnerType(distributerType);
        model.setPartnerCompanyName(companyName);
        model.setPartnerCinNumber(cinNumber);
        model.setPartnerPanNumber(panNumber);
        model.setPartnerKeyContact(contactPerson);
        model.setPartnerContactPosition(contactPosition);
        model.setPartnerState(partnerState);
        model.setPartnerCity(partnerCity);
        model.setPartnerPinCode(partnerPinCode);
        model.setPartnerZone(partnerZone);
        businessModels.set(position, model);
    }

    @Override
    public void onContactAdd(int position, String role, String name, String primaryMobileNum, String secondaryMobileNum) {

    }

    @Override
    public void onClick(View v) {

    }
}
