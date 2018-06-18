package quay.com.ipos.kycPartnerConnect;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.kycPartnerConnect.kycAdapter.KycBusinessAdapter;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.model.BusinessLocation;
import quay.com.ipos.partnerConnect.model.KeyBusinessInfo;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class KycBusinessFragment extends Fragment implements InitInterface, View.OnClickListener {
    private static final String TAG = KycBusinessFragment.class.getSimpleName();
    private View view;
    private RelativeLayout rLayoutProductTitle;
    private TextView textViewBusinessInfoHeading, textViewMadatory, textViewLastUpdated;
    private RecyclerView recyclerViewBusinessInfo;
    private LinearLayout lLayoutBottom;
    private Context mContext;

    private View fab;
    private Button btnAdd;
    PCModel mpcModel;
    private KycBusinessAdapter businessAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.kyc_business_fragment, container, false);
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
        KYCMain kycMain = (KYCMain) getActivity();
        if (kycMain != null) {

            kycMain.getPcModelData().observe(this, new Observer<PCModel>() {
                @Override
                public void onChanged(@Nullable PCModel pcModel) {

                    mpcModel = pcModel;
                    setData(pcModel);

                }
            });
        }
    }

    private void setData(PCModel pcModel) {
        if (pcModel == null || pcModel.Business == null || pcModel.Business.KeyBusinessInfo == null) {
            Log.i(TAG, "pcModel or pcModel.Business is null");
            return;
        }

        List<KeyBusinessInfo> data = pcModel.Business.KeyBusinessInfo;
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
        fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick(view);
            }
        });
        businessAdapter = new KycBusinessAdapter(getActivity());
        recyclerViewBusinessInfo.setAdapter(businessAdapter);

        try {
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
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
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
            keyBusinessInfo.id = mpcModel.Business.KeyBusinessInfo.size() + 1;
            keyBusinessInfo.BusinessLocation = businessLocation;

            if (mpcModel.Business.KeyBusinessInfo != null) {
                mpcModel.Business.KeyBusinessInfo.add(keyBusinessInfo);
            }
            //  businessAdapter.loadData(mpcModel.Business.KeyBusinessInfo);


            KYCMain connectMain = (KYCMain) getActivity();
            if (connectMain != null) {
                //   Toast.makeText(connectMain, "hhh", Toast.LENGTH_SHORT).show();
                connectMain.getPcModelData().setValue(mpcModel);
            }
        }

    }

}
