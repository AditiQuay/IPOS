package quay.com.ipos.partnerConnect;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.data.remote.model.PartnerConnectResponse;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.adapter.RelOneAdapter;
import quay.com.ipos.partnerConnect.adapter.RelThreeAdapter;
import quay.com.ipos.partnerConnect.adapter.RelTwoAdapter;
import quay.com.ipos.partnerConnect.model.Business;
import quay.com.ipos.partnerConnect.model.BusinessLocation;
import quay.com.ipos.partnerConnect.model.KeyBusinessInfo;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.partnerConnect.partnerConnectAdapter.BusinessAdapter;
import quay.com.ipos.partnerConnect.partnerConnectModel.BusinessModel;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class BusinessFragment extends Fragment implements InitInterface, ButtonListener, View.OnClickListener {
    private static final String TAG = BusinessFragment.class.getSimpleName();
    private View view;
    private RelativeLayout rLayoutProductTitle;
    private TextView textViewBusinessInfoHeading, textViewMadatory, textViewLastUpdated;
    private RecyclerView recyclerViewBusinessInfo;
    private LinearLayout lLayoutBottom;
    private Button btnCancel, btnsubmit;
    private Context mContext;
    private ArrayList<BusinessModel> businessModels = new ArrayList<>();
    private View fab;
    private Button btnAdd;
    PCModel mpcModel;
    private BusinessAdapter businessAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.business_fragment, container, false);
        mContext = getActivity();
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
        getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "hhh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        PartnerConnectMain partnerConnectMain = (PartnerConnectMain) getActivity();
        if (partnerConnectMain != null) {
            partnerConnectMain.getPcModelData().observe(this, new Observer<PCModel>() {
                @Override
                public void onChanged(@Nullable PCModel pcModel) {
                    mpcModel = pcModel;
                    setData(pcModel);

                }
            });
        }
    }

    private void setData(PCModel pcModel) {
        if (pcModel == null && pcModel.Business == null && pcModel.Business.KeyBusinessInfo == null) {
            Log.i(TAG, "pcModel or pcModel.Business is null");
            return;
        }
        List<KeyBusinessInfo> data = pcModel.Business.KeyBusinessInfo;
        //  businessAdapter = new BusinessAdapter(getActivity(), pcModel.Business.KeyBusinessInfo, BusinessFragment.this);
        //     recyclerViewBusinessInfo.setAdapter(businessAdapter);

        businessAdapter.loadData(data);
    }

    @Override
    public void findViewById() {
        btnAdd = view.findViewById(R.id.btnAdd);
        rLayoutProductTitle = view.findViewById(R.id.rLayoutProductTitle);
        textViewBusinessInfoHeading = view.findViewById(R.id.textViewPersonalHeading);
        textViewMadatory = view.findViewById(R.id.textViewMadatory);
        textViewLastUpdated = view.findViewById(R.id.textViewLastUpdated);
        recyclerViewBusinessInfo = view.findViewById(R.id.recyclerViewBusinessInfo);
        lLayoutBottom = view.findViewById(R.id.lLayoutBottom);
        btnCancel = getActivity().findViewById(R.id.btnCancel);
        btnsubmit = getActivity().findViewById(R.id.btnsubmit);
        fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick(view);
            }
        });
        businessAdapter = new BusinessAdapter(getActivity(), BusinessFragment.this);
        recyclerViewBusinessInfo.setAdapter(businessAdapter);

        try {
            btnCancel.setOnClickListener(this);
            btnsubmit.setOnClickListener(this);
            fab.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewField();
            }
        });

        view.findViewById(R.id.btnClearAll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (businessAdapter != null) {
                    businessAdapter.clearData();
                }
            }
        });

    }

    @Override
    public void applyInitValues() {

      /*  businessModels.clear();

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
        businessModels.add(businessModel);*/


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
        switch (v.getId()) {
            case R.id.fab:
                addNewField();
                break;

        }
    }

    private void addNewField() {
        if (mpcModel != null) {
            Toast.makeText(getActivity(), "hhhvv", Toast.LENGTH_SHORT).show();

            BusinessLocation businessLocation = new BusinessLocation();
            KeyBusinessInfo keyBusinessInfo = new KeyBusinessInfo();
            keyBusinessInfo.BusinessLocation = businessLocation;
            if (mpcModel.Business.KeyBusinessInfo != null) {
                mpcModel.Business.KeyBusinessInfo.add(keyBusinessInfo);
            }
          //  businessAdapter.loadData(mpcModel.Business.KeyBusinessInfo);


            PartnerConnectMain connectMain = (PartnerConnectMain) getActivity();
            if (connectMain != null) {
             //   Toast.makeText(connectMain, "hhh", Toast.LENGTH_SHORT).show();
                connectMain.getPcModelData().setValue(mpcModel);
            }
        }

    }
}
