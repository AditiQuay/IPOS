package quay.com.ipos.customerInfo.customerInfoAdapter;

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

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoModal.AddCustomerModel;
import quay.com.ipos.customerInfo.customerInfoModal.ChildModel;
import quay.com.ipos.listeners.ButtonListener;

/**
 * Created by niraj.kumar on 5/31/2018.
 */

public class CustomerChildAdapter extends RecyclerView.Adapter<CustomerChildAdapter.MyView> implements AdapterView.OnItemSelectedListener {
    private Context context;
    private ButtonListener buttonListener;
    private ArrayList<AddCustomerModel.CustomerChildBean> childModels;
    private String[] childGender = {"Male", "Female"};
    private String child = "";

    public CustomerChildAdapter(Context context, ArrayList<AddCustomerModel.CustomerChildBean> childModels, ButtonListener buttonListener) {
        this.context = context;
        this.childModels = childModels;
        this.buttonListener = buttonListener;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_child_item, parent, false);

        return new CustomerChildAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        AddCustomerModel.CustomerChildBean model = childModels.get(position);
        holder.tieChildFirstName.setText(model.getCustomerChildFirstName());
        holder.tieChildLastName.setText(model.getCustomerChildLastName());
        holder.tieChildDOB.setText(model.getCustomerChildDob());

        //Creating the ArrayAdapter instance having the name title list
        ArrayAdapter nameHeading = new ArrayAdapter(context, android.R.layout.simple_spinner_item, childGender);
        nameHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.childGenderSpinner.setAdapter(nameHeading);
        holder.childGenderSpinner.setOnItemSelectedListener(this);


        holder.tieChildLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonListener.onAdd(holder.getAdapterPosition(), holder.tieChildFirstName.getText().toString(), holder.tieChildLastName.getText().toString(),
                        child, holder.tieChildDOB.getText().toString());
            }
        });
        holder.tieChildFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonListener.onAdd(holder.getAdapterPosition(), holder.tieChildFirstName.getText().toString(), holder.tieChildLastName.getText().toString(),
                        child, holder.tieChildDOB.getText().toString());
            }
        });

        holder.tieChildDOB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonListener.onAdd(holder.getAdapterPosition(), holder.tieChildFirstName.getText().toString(), holder.tieChildLastName.getText().toString(),
                        child, holder.tieChildDOB.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return childModels.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MaterialSpinner materialSpinner = (MaterialSpinner) parent;
        String selectedSpinner = String.valueOf(materialSpinner.getSelectedItem());
        if (materialSpinner.getId() == R.id.childGenderSpinner) {
            if (selectedSpinner.equalsIgnoreCase("Yes")) {
                child = selectedSpinner;
            } else if (selectedSpinner.equalsIgnoreCase("No")) {
                child = selectedSpinner;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public class MyView extends RecyclerView.ViewHolder {
        private TextInputEditText tieChildFirstName, tieChildLastName, tieChildDOB;
        private MaterialSpinner childGenderSpinner;

        public MyView(View itemView) {
            super(itemView);
            tieChildFirstName = itemView.findViewById(R.id.tieChildFirstName);
            tieChildLastName = itemView.findViewById(R.id.tieChildLastName);
            tieChildDOB = itemView.findViewById(R.id.tieChildDOB);

            childGenderSpinner = itemView.findViewById(R.id.childGenderSpinner);

        }
    }
}
