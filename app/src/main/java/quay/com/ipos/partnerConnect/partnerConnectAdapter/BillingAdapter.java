package quay.com.ipos.partnerConnect.partnerConnectAdapter;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.partnerConnect.partnerConnectModel.BillingModel;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.MyView> implements AdapterView.OnItemSelectedListener {
    private String[] addressType = {"Bill & Deliver"};
    private String[] businessPlace = {"Shop / Store"};
    private String[] state = {"New Delhi"};
    String addressTypeText, businessPlaceText, stateText;
    private ArrayList<BillingModel> billingModels;
    private Context mContext;
    private ButtonListener buttonListener;

    public BillingAdapter(Context context, ArrayList<BillingModel> billingModels, ButtonListener buttonListener) {
        this.mContext = context;
        this.buttonListener = buttonListener;
        this.billingModels = billingModels;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.billing_item, parent, false);

        return new BillingAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(MyView holder, int position) {
        BillingModel billingModel = billingModels.get(position);


        //Creating the ArrayAdapter instance having the addressType list
        ArrayAdapter addressAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, addressType);
        addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.addressTypeSpinner.setAdapter(addressAdapter);
        holder.addressTypeSpinner.setOnItemSelectedListener(this);


        //Creating the ArrayAdapter instance having the BusinessPlace list
        ArrayAdapter businessPlaceAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, businessPlace);
        businessPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.businessPlaceSpinner.setAdapter(businessPlaceAdapter);
        holder.businessPlaceSpinner.setOnItemSelectedListener(this);


        //Creating the ArrayAdapter instance having the state list
        ArrayAdapter stateAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, state);
        businessPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.stateSpinner.setAdapter(businessPlaceAdapter);
        holder.stateSpinner.setOnItemSelectedListener(this);

        holder.tieAddress.setText(billingModel.getBillingAddress());
        holder.tieCity.setText(billingModel.getBillingCity());
        holder.tieGstin.setText(billingModel.getBillingGSTIN());
        holder.tieContactPerson.setText(billingModel.getBillingContactPerson());
        holder.tieContactPersonNumber.setText(billingModel.getBillingContactPersonNumber());

    }

    @Override
    public int getItemCount() {
        return billingModels.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MaterialSpinner materialSpinner = (MaterialSpinner) parent;
        String selectedSpinner = String.valueOf(materialSpinner.getSelectedItem());

        if (materialSpinner.getId() == R.id.addressTypeSpinner) {
            addressTypeText = selectedSpinner;
        } else if (materialSpinner.getId() == R.id.businessPlaceSpinner) {
            businessPlaceText = selectedSpinner;
        } else if (materialSpinner.getId() == R.id.stateSpinner) {
            stateText = selectedSpinner;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class MyView extends RecyclerView.ViewHolder {
        private MaterialSpinner addressTypeSpinner, businessPlaceSpinner, stateSpinner;
        private TextInputLayout tilAddress, tilCity, tilGstin, tilContactPerson, tilContactPersonNumber;
        private TextInputEditText tieAddress, tieCity, tieGstin, tieContactPerson, tieContactPersonNumber;

        public MyView(View itemView) {
            super(itemView);
            addressTypeSpinner = itemView.findViewById(R.id.addressTypeSpinner);
            businessPlaceSpinner = itemView.findViewById(R.id.businessPlaceSpinner);
            stateSpinner = itemView.findViewById(R.id.stateSpinner);
            tilAddress = itemView.findViewById(R.id.tilAddress);
            tilCity = itemView.findViewById(R.id.tilCity);
            tilGstin = itemView.findViewById(R.id.tilGstin);
            tilContactPerson = itemView.findViewById(R.id.tilContactPerson);
            tilContactPersonNumber = itemView.findViewById(R.id.tilContactPersonNumber);
            tieAddress = itemView.findViewById(R.id.tieAddress);
            tieCity = itemView.findViewById(R.id.tieCity);
            tieGstin = itemView.findViewById(R.id.tieGstin);
            tieContactPerson = itemView.findViewById(R.id.tieContactPerson);
            tieContactPersonNumber = itemView.findViewById(R.id.tieContactPersonNumber);
        }
    }
}
