package quay.com.ipos.partnerConnect.partnerConnectAdapter;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.partnerConnect.model.KeyBusinessInfo;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.MyView> {
    private Context context;
    private ButtonListener buttonListener;

    private List<KeyBusinessInfo> list = new ArrayList<>();
    private String[] partnerType = {"Retailer", "Distributor", "Dealer", "Principal"};
    private String[] partnerKeyPosition = {"Director", "Manager", "Executive"};

    private List<String> listPartner = new ArrayList<>();
    private List<String> listPosition = new ArrayList<>();

    private ArrayAdapter partnerTypeHeading;
    private ArrayAdapter contactPosition;

    public BusinessAdapter(FragmentActivity activity) {
        this.context = activity;
        listPartner = Arrays.asList(partnerType);
        listPosition = Arrays.asList(partnerKeyPosition);

        //Creating the ArrayAdapter instance having the mPartnerType list
        partnerTypeHeading = new ArrayAdapter(context, android.R.layout.simple_spinner_item, partnerType);
        partnerTypeHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Creating the ArrayAdapter instance having the mPartnerType list
        contactPosition = new ArrayAdapter(context, android.R.layout.simple_spinner_item, partnerKeyPosition);
        contactPosition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_item, parent, false);
        // return new BusinessAdapter.MyView(itemView);
        return new BusinessAdapter.MyView(itemView, new MyCustomEditTextListener(), new MyCustomSpinnerListener());

    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        try {


            //holder.editPartnerType.setAdapter(partnerTypeHeading);
            // holder.editKeyContactPos.setAdapter(contactPosition);


            // magic code for editText
            holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition(), holder);
            holder.editContactPerson.setText(list.get(holder.getAdapterPosition()).mContactPerson);
            holder.editCompanyName.setText(list.get(holder.getAdapterPosition()).mCompanyName);
            holder.editPAN.setText(list.get(holder.getAdapterPosition()).mPAN);
            holder.editCIN.setText(list.get(holder.getAdapterPosition()).mCIN);
            holder.editPartnerType.setText(list.get(holder.getAdapterPosition()).mPartnerType);
            holder.editKeyContactPos.setText(list.get(holder.getAdapterPosition()).mContactPosition);

            holder.editPinCode.setText(list.get(holder.getAdapterPosition()).BusinessLocation.mPINCode);
            holder.editCity.setText(list.get(holder.getAdapterPosition()).BusinessLocation.mCity);
            holder.editState.setText(list.get(holder.getAdapterPosition()).BusinessLocation.mState);
            holder.editZone.setText(list.get(holder.getAdapterPosition()).BusinessLocation.mZone);


            // magic code for spinner
            holder.myCustomSpinnerListener.updatePosition(holder.getAdapterPosition());

            String mPartnerType = list.get(holder.getAdapterPosition()).mPartnerType;
            if (mPartnerType != null) {
                if (listPartner.contains(mPartnerType)) {
                    int index = listPartner.indexOf(mPartnerType);
                    holder.editPartnerType.setSelection(index + 1);
                }
            }

            String mContactPosition = list.get(holder.getAdapterPosition()).mContactPosition;
            if (mContactPosition != null) {
                if (listPosition.contains(mContactPosition)) {
                    int index = listPosition.indexOf(mContactPosition);
                    holder.editKeyContactPos.setSelection(index + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyView extends RecyclerView.ViewHolder {
        private TextInputEditText editPartnerType, editKeyContactPos;
        private TextInputEditText editCompanyName, editCIN, editPAN, editContactPerson, editState, editCity, editPinCode, editZone;
        private TextView textViewBusinessInfoHeading;
        public MyCustomEditTextListener myCustomEditTextListener;
        public MyCustomSpinnerListener myCustomSpinnerListener;


        public MyView(View itemView, MyCustomEditTextListener myCustomEditTextListener, MyCustomSpinnerListener myCustomSpinnerListener) {
            super(itemView);
            editPartnerType = itemView.findViewById(R.id.editPartnerType);
            editKeyContactPos = itemView.findViewById(R.id.editKeyContactPos);
            textViewBusinessInfoHeading = itemView.findViewById(R.id.textViewBusinessInfoHeading);

            //TextInputEdiText
            editCompanyName = itemView.findViewById(R.id.editCompanyName);
            editCIN = itemView.findViewById(R.id.editCIN);
            editPAN = itemView.findViewById(R.id.editPAN);
            editContactPerson = itemView.findViewById(R.id.editContactPerson);
            editState = itemView.findViewById(R.id.editState);
            editCity = itemView.findViewById(R.id.editCity);
            editPinCode = itemView.findViewById(R.id.editPinCode);
            editZone = itemView.findViewById(R.id.editZone);


            //magic code for editText
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.editCompanyName.addTextChangedListener(myCustomEditTextListener);
            this.editCIN.addTextChangedListener(myCustomEditTextListener);
            this.editPAN.addTextChangedListener(myCustomEditTextListener);
            this.editContactPerson.addTextChangedListener(myCustomEditTextListener);
            this.editState.addTextChangedListener(myCustomEditTextListener);
            this.editCity.addTextChangedListener(myCustomEditTextListener);
            this.editPinCode.addTextChangedListener(myCustomEditTextListener);
            this.editZone.addTextChangedListener(myCustomEditTextListener);
            this.editPartnerType.addTextChangedListener(myCustomEditTextListener);
            this.editKeyContactPos.addTextChangedListener(myCustomEditTextListener);

            //magic code for spinner
            this.myCustomSpinnerListener = myCustomSpinnerListener;
            //   this.editPartnerType.setOnItemSelectedListener(myCustomSpinnerListener);
            //    this.editKeyContactPos.setOnItemSelectedListener(myCustomSpinnerListener);


        }
    }

    public void clearData() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public void loadData(List<KeyBusinessInfo> newData) {
        this.list = newData;
        notifyDataSetChanged();
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, KeyBusinessInfo data) {
        this.list.add(position, data);
        notifyItemInserted(position);
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

                if (holder.editCompanyName != null) {
                    if (holder.editCompanyName.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mCompanyName = charSequence.toString();
                    }
                }
                if (holder.editCIN != null) {
                    if (holder.editCIN.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mCIN = charSequence.toString();
                    }
                }
                if (holder.editPAN != null) {
                    if (holder.editPAN.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mPAN = charSequence.toString();
                    }
                }
                if (holder.editContactPerson != null) {
                    if (holder.editContactPerson.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mContactPerson = charSequence.toString();
                    }
                }
                if (holder.editPartnerType != null) {
                    if (holder.editPartnerType.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mPartnerType = charSequence.toString();
                    }
                }
                if (holder.editKeyContactPos != null) {
                    if (holder.editKeyContactPos.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mContactPosition = charSequence.toString();
                    }
                }


                //business Location

                if (holder.editCity != null) {
                    if (holder.editCity.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).BusinessLocation.mCity = charSequence.toString();
                    }
                }

                if (holder.editState != null) {
                    if (holder.editState.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).BusinessLocation.mState = charSequence.toString();
                    }
                }
                if (holder.editZone != null) {
                    if (holder.editZone.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).BusinessLocation.mZone = charSequence.toString();
                    }
                }
                if (holder.editPinCode != null) {
                    if (holder.editPinCode.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).BusinessLocation.mPINCode = charSequence.toString();
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
              /*  case R.id.editPartnerType:
                    if (i != -1)
                        list.get(position).mPartnerType = listPartner.get(i);
                    else
                        list.get(position).mPartnerType = "";
                    break;
                case R.id.editKeyContactPos:
                    if (i != -1)
                        list.get(position).mContactPosition = listPosition.get(i);
                    else
                        list.get(position).mContactPosition = "";
                    break;*/
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