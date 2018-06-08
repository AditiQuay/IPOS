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
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.partnerConnectAdapter.BillingAdapter;
import quay.com.ipos.partnerConnect.partnerConnectModel.BillingModel;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class BillingAddressFragment extends Fragment implements InitInterface, ButtonListener {
    private View main;
    private TextView textViewBillingInfoHeading, textViewMadatory, textViewLastUpdated;
    private RecyclerView recyclerViewBillingInfo;
    private Button btnBillingCancel, btnBillingsubmit;
    private ArrayList<BillingModel> billingModels = new ArrayList<>();
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.billing_address, container, false);
        mContext = getActivity();
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
        return main;
    }

    @Override
    public void findViewById() {
        textViewBillingInfoHeading = main.findViewById(R.id.textViewBillingInfoHeading);
        textViewMadatory = main.findViewById(R.id.textViewMadatory);
        textViewLastUpdated = main.findViewById(R.id.textViewLastUpdated);
        recyclerViewBillingInfo = main.findViewById(R.id.recyclerViewBillingInfo);
        btnBillingCancel = main.findViewById(R.id.btnBillingCancel);
        btnBillingsubmit = main.findViewById(R.id.btnBillingsubmit);
    }

    @Override
    public void applyInitValues() {
        billingModels.clear();

        BillingModel billingModel = new BillingModel();
        billingModel.setAddressType("");
        billingModel.setBillingBusinessPlace("");
        billingModel.setBillingAddress("");
        billingModel.setBillingCity("");
        billingModel.setBillingState("");
        billingModel.setBillingGSTIN("");
        billingModel.setBillingContactPerson("");
        billingModel.setBillingContactPersonNumber("");
        billingModels.add(billingModel);


        recyclerViewBillingInfo.setHasFixedSize(false);
        recyclerViewBillingInfo.setLayoutManager(new LinearLayoutManager(mContext));
        BillingAdapter businessAdapter = new BillingAdapter(mContext, billingModels, this);
        recyclerViewBillingInfo.setAdapter(businessAdapter);
    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewBillingInfoHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewMadatory, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewMadatory, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewLastUpdated, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(recyclerViewBillingInfo, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnBillingCancel, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnBillingsubmit, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
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
}
