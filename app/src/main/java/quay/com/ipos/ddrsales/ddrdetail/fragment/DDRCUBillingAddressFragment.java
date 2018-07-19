package quay.com.ipos.ddrsales.ddrdetail.fragment;

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
import quay.com.ipos.ddrsales.ddrdetail.DDRCUActivity;
import quay.com.ipos.partnerConnect.model.BillnDelivery;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.partnerConnect.partnerConnectAdapter.BillingAdapter;
import quay.com.ipos.utility.DateAndTimeUtil;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class DDRCUBillingAddressFragment extends Fragment {
    private TextView textViewBillingInfoHeading, textViewMadatory, textViewLastUpdated;
    private RecyclerView recyclerView;
    private Context mContext;
    private BillingAdapter businessAdapter;
    private View btnAdd;
    private PCModel mpcModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ddrcu_billing_address, container, false);
        mContext = getActivity();


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            onResume();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }

        DDRCUActivity mainActivity = (DDRCUActivity)getActivity();
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do what you want
                addNewField();
                //Toast.makeText(getActivity(), "Add Billing Fragment", Toast.LENGTH_SHORT).show();
            }
        });
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
        businessAdapter = new BillingAdapter(mContext);
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
        DDRCUActivity DDRCUActivity = (DDRCUActivity) getActivity();
        DDRCUActivity.getPcModelData().observe(this, new Observer<PCModel>() {
            @Override
            public void onChanged(@Nullable PCModel pcModel) {
                mpcModel = pcModel;
               // textViewLastUpdated.setText(DateAndTimeUtil.getMyDateAndTime("Last Updated :" , mpcModel.psslastUpdated));

                if (pcModel.BillandDelivery != null && pcModel.BillandDelivery.size() == 0) {
                    BillnDelivery billnDelivery = new BillnDelivery();
                    pcModel.BillandDelivery.add(billnDelivery);
                }
                businessAdapter.loadData(pcModel.BillandDelivery);
            }
        });
    }

    private void addNewField() {
        if (mpcModel != null && mpcModel.BillandDelivery != null) {


            BillnDelivery billnDelivery = new BillnDelivery();
            billnDelivery.ID = 0;
            mpcModel.BillandDelivery.add(billnDelivery);

            DDRCUActivity connectMain = (DDRCUActivity) getActivity();
            if (connectMain != null) {
                connectMain.getPcModelData().setValue(mpcModel);
            }
        }
    }
}




