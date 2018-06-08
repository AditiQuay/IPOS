package quay.com.ipos.partnerConnect;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.partnerConnect.partnerConnectAdapter.AccountAdapter;
import quay.com.ipos.partnerConnect.partnerConnectAdapter.ContactInfoAdapter;
import quay.com.ipos.partnerConnect.partnerConnectModel.AccountsModel;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class AccountFragment extends Fragment implements InitInterface, ButtonListener, View.OnClickListener {
    private static final String TAG = AccountFragment.class.getSimpleName();
    private TextView textViewLastUpdated, textViewAccountInfoHeading, textViewMadatory;
    private RecyclerView recyclerViewAccountInfo;
    private Button btnAccountCancel;
    private Button btnAccountSubmit;
    private View main;
    private Context mContext;

    private List<AccountsModel> accountsModels = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.account_info_fragment, container, false);
        mContext = getActivity();
        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
        return main;
    }

    @Override
    public void findViewById() {
        textViewMadatory = main.findViewById(R.id.textViewMadatory);
        textViewAccountInfoHeading = main.findViewById(R.id.textViewAccountInfoHeading);
        textViewLastUpdated = main.findViewById(R.id.textViewLastUpdated);
        recyclerViewAccountInfo = main.findViewById(R.id.recyclerViewAccountInfo);
        btnAccountCancel = main.findViewById(R.id.btnAccountCancel);
        btnAccountSubmit = main.findViewById(R.id.btnAccountSubmit);

       /* btnAccountCancel.setOnClickListener(this);
        btnAccountCancel.setOnClickListener(this);*/

    }

    @Override
    public void applyInitValues() {

        accountsModels.clear();

        AccountsModel accountsModel = new AccountsModel();
        accountsModel.setAccountHolderName("");
        accountsModel.setAccountNumber("");
        accountsModel.setAccountType("");
        accountsModel.setBankName("");
        accountsModel.setIfscCode("");
        accountsModel.setBranchAddress("");
        accountsModel.setSecurityCheck("");
        accountsModel.setDrawnOnAccount("");
        accountsModel.setChequeNumber("");
        accountsModel.setMaxAmount("");

        accountsModels.add(accountsModel);

        //recyclerViewAccountInfo.setHasFixedSize(true);
        recyclerViewAccountInfo.setLayoutManager(new LinearLayoutManager(mContext));
        //AccountAdapter businessAdapter = new AccountAdapter(mContext, accountsModels, this);
        //recyclerViewAccountInfo.setAdapter(businessAdapter);
    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewLastUpdated, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewLastUpdated, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewAccountInfoHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewMadatory, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(recyclerViewAccountInfo, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnAccountCancel, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnAccountSubmit, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onAdd(int position, String firstName, String lastName, String childGender, String childDOB) {

    }

    @Override
    public void onPartnerAdd(int position, String distributerType, String companyName, String cinNumber, String panNumber, String contactPerson, String contactPosition, String partnerState, String partnerCity, String partnerPin, String partnerZone) {

    }

    @Override
    public void onContactAdd(int position, String role, String name, String primaryMobileNum, String secondaryMobileNum) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAccountCancel:

                break;
            case R.id.btnAccountSubmit:
            default:
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }
    private void loadData() {
        PartnerConnectMain partnerConnectMain = (PartnerConnectMain) getActivity();
        if (partnerConnectMain != null) {
            partnerConnectMain.getPcModelData().observe(this, new Observer<PCModel>() {
                @Override
                public void onChanged(@Nullable PCModel pcModel) {
                    pcModel = pcModel;
                    setData(pcModel);

                }
            });
        }
    }
    private void setData(PCModel pcModel) {
        if (pcModel == null && pcModel.Business == null) {
            Log.i(TAG, "pcModel or pcModel.Business is null");
            return;
        }


        recyclerViewAccountInfo.setAdapter(new AccountAdapter(getActivity(), pcModel.Account.cheques, AccountFragment.this));
    }
}
