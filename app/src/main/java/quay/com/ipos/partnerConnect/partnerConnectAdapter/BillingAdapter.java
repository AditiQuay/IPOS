package quay.com.ipos.partnerConnect.partnerConnectAdapter;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.partnerConnect.model.BillnDelivery;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.MyView> {
    private String[] addressTypeArray = {"Bill", "Delivery", "Bill & Delivery"};
    private String[] businessTypeArray = {"Shop/Store", "Warehouse"};


    private List<BillnDelivery> list = new ArrayList<>();
    private Context mContext;

    private List<String> addressList = new ArrayList<>();
    private List<String> businessList = new ArrayList<>();

    private ArrayAdapter addressAdapter;
    private ArrayAdapter businessPlaceAdapter;


    public BillingAdapter(Context context) {
        this.mContext = context;

        this.list = new ArrayList<>();

        //Creating the ArrayAdapter instance having the addressTypeArray list
        addressAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, addressTypeArray);
        addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Creating the ArrayAdapter instance having the BusinessPlace list
        businessPlaceAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, businessTypeArray);
        businessPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        addressList = Arrays.asList(addressTypeArray);
        businessList = Arrays.asList(businessTypeArray);
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.billing_item, parent, false);

        return new MyView(itemView, new MyCustomEditTextListener(), new MyCustomSpinnerListener());
    }

    @Override
    public void onBindViewHolder(MyView holder, final int position) {
        try {
            BillnDelivery billnDelivery = list.get(position);

            holder.spinnerAddressType.setAdapter(addressAdapter);
            holder.spinnerBusinessType.setAdapter(businessPlaceAdapter);


            // magic code for editText
            holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition(), holder);
            holder.editAddress.setText(list.get(holder.getAdapterPosition()).mBusinessAddress);
            holder.editMobile.setText(list.get(holder.getAdapterPosition()).mMobile);
            holder.editGstin.setText(list.get(holder.getAdapterPosition()).mGSTIN);
            holder.editContactPerson.setText(list.get(holder.getAdapterPosition()).mContactPerson);
            holder.editCity.setText(list.get(holder.getAdapterPosition()).mCity);
            holder.editState.setText(list.get(holder.getAdapterPosition()).mState);

            // magic code for spinner
            holder.myCustomSpinnerListener.updatePosition(holder.getAdapterPosition());

            String mAddressType = list.get(holder.getAdapterPosition()).mAddressType;
            if (mAddressType != null) {
                if (addressList.contains(mAddressType)) {
                    int index = addressList.indexOf(mAddressType);
                    holder.spinnerAddressType.setSelection(index + 1);
                }
            }

            String mBusinessType = list.get(holder.getAdapterPosition()).mBusinessType;
            if (mBusinessType != null) {
                if (businessList.contains(mBusinessType)) {
                    int index = businessList.indexOf(mBusinessType);
                    holder.spinnerBusinessType.setSelection(index + 1);
                }
            }

            if (billnDelivery.ID == 0 && position != 0) {
                holder.btnRemove.setVisibility(View.VISIBLE);
            } else {
                holder.btnRemove.setVisibility(View.GONE);
            }

            holder.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void loadData(List<BillnDelivery> list) {
        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    public class MyView extends RecyclerView.ViewHolder {
        private MaterialSpinner spinnerAddressType, spinnerBusinessType;
        private TextInputEditText editState, editAddress, editCity, editGstin, editContactPerson, editMobile;

        public MyCustomEditTextListener myCustomEditTextListener;
        public MyCustomSpinnerListener myCustomSpinnerListener;

        public View btnRemove;

        public MyView(View itemView, MyCustomEditTextListener myCustomEditTextListener, MyCustomSpinnerListener myCustomSpinnerListener) {
            super(itemView);
            spinnerAddressType = itemView.findViewById(R.id.spinnerAddressType);
            spinnerBusinessType = itemView.findViewById(R.id.spinnerBusinessType);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            editState = itemView.findViewById(R.id.editState);
            editAddress = itemView.findViewById(R.id.editAddress);
            editCity = itemView.findViewById(R.id.editCity);
            editGstin = itemView.findViewById(R.id.editGstin);
            editContactPerson = itemView.findViewById(R.id.editContactPerson);
            editMobile = itemView.findViewById(R.id.editMobile);


            //magic code for editText
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.editAddress.addTextChangedListener(myCustomEditTextListener);
            this.editCity.addTextChangedListener(myCustomEditTextListener);
            this.editGstin.addTextChangedListener(myCustomEditTextListener);
            this.editContactPerson.addTextChangedListener(myCustomEditTextListener);
            this.editMobile.addTextChangedListener(myCustomEditTextListener);
            this.editState.addTextChangedListener(myCustomEditTextListener);

            //magic code for spinner
            this.myCustomSpinnerListener = myCustomSpinnerListener;
            this.spinnerAddressType.setOnItemSelectedListener(myCustomSpinnerListener);
            this.spinnerBusinessType.setOnItemSelectedListener(myCustomSpinnerListener);

        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private MyView holder;

        public void updatePosition(int position, MyView holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            try {

                if (holder.editAddress != null) {
                    if (holder.editAddress.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mBusinessAddress = charSequence.toString();
                    }
                }
                if (holder.editMobile != null) {
                    if (holder.editMobile.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mMobile = charSequence.toString();
                    }
                }
                if (holder.editState != null) {
                    if (holder.editState.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mState = charSequence.toString();
                    }
                }
                if (holder.editCity != null) {
                    if (holder.editCity.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mCity = charSequence.toString();
                    }
                }
                if (holder.editContactPerson != null) {
                    if (holder.editContactPerson.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mContactPerson = charSequence.toString();
                    }
                }
                if (holder.editGstin != null) {
                    if (holder.editGstin.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mGSTIN = charSequence.toString();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

            // no op
        }
    }

    private class MyCustomSpinnerListener implements AdapterView.OnItemSelectedListener {
        private int position;

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (adapterView.getId()) {
                case R.id.spinnerAddressType:
                    if (i != -1)
                        list.get(position).mAddressType = addressList.get(i);
                    else
                        list.get(position).mAddressType = "";
                    break;
                case R.id.spinnerBusinessType:
                    if (i != -1)
                        list.get(position).mBusinessType = businessList.get(i);
                    else
                        list.get(position).mBusinessType = "";
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

        public void updatePosition(int position) {
            this.position = position;
        }
    }
}
