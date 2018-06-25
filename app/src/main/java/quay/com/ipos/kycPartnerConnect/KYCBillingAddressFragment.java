package quay.com.ipos.kycPartnerConnect;

import android.arch.lifecycle.Observer;
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
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.kycPartnerConnect.kycAdapter.KycBillingAdapter;
import quay.com.ipos.partnerConnect.model.BillnDelivery;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.utility.DateAndTimeUtil;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class KYCBillingAddressFragment extends Fragment {
    private TextView textViewBillingInfoHeading, textViewMadatory, textViewLastUpdated;
    private RecyclerView recyclerView;
    private Context mContext;
    private KycBillingAdapter businessAdapter;
    private View btnAdd;
    private PCModel mpcModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kyc_billing_address, container, false);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAdd = view.findViewById(R.id.btnAdd);
        textViewBillingInfoHeading = view.findViewById(R.id.textViewBillingInfoHeading);
        textViewMadatory = view.findViewById(R.id.textViewMadatory);
        textViewLastUpdated = view.findViewById(R.id.textViewLastUpdated);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        businessAdapter = new KycBillingAdapter(mContext);
        recyclerView.setAdapter(businessAdapter);

        loadData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewField();
            }
        });
    }

    private void loadData() {
        KYCMain partnerConnectMain = (KYCMain) getActivity();
        partnerConnectMain.getPcModelData().observe(this, new Observer<PCModel>() {
            @Override
            public void onChanged(@Nullable PCModel pcModel) {
                mpcModel = pcModel;
                textViewLastUpdated.setText(DateAndTimeUtil.getMyDateAndTime("Last Updated :" , pcModel.psslastUpdated));

                businessAdapter.loadData(pcModel.BillandDelivery);
            }
        });
    }

    private void addNewField() {
        if (mpcModel != null && mpcModel.BillandDelivery != null) {


            BillnDelivery billnDelivery = new BillnDelivery();
            billnDelivery.ID = 0;
            mpcModel.BillandDelivery.add(billnDelivery);

            KYCMain connectMain = (KYCMain) getActivity();
            if (connectMain != null) {
                connectMain.getPcModelData().setValue(mpcModel);
            }
        }
    }
}




