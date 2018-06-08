package quay.com.ipos.customerInfo.customerInfoAdapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoModal.AddCustomerModel;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.listeners.MySubmitButton;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 5/31/2018.
 */

public class CustomerChildAdapter extends RecyclerView.Adapter<CustomerChildAdapter.MyView> implements MySubmitButton,AdapterView.OnItemSelectedListener {
    private static final String TAG = CustomerChildAdapter.class.getSimpleName();
    private Context context;
    private ButtonListener buttonListener;
    private ArrayList<AddCustomerModel.CustomerChildBean> childModels;
    private String[] childGender = {"Male", "Female"};
    private String child = "";
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;
    Date currentDateFormat = null;
    MySubmitButton mySubmitButton;
    public CustomerChildAdapter(Context context, ArrayList<AddCustomerModel.CustomerChildBean> childModels, ButtonListener buttonListener,MySubmitButton mySubmitButton) {
        this.context = context;
        this.childModels = childModels;
        this.buttonListener = buttonListener;
        this.mySubmitButton = mySubmitButton;
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

//        if (holder.tieChildFirstName.getText().toString().equalsIgnoreCase("")){
//            holder.tilChildfname.setErrorEnabled(true);
//            holder.tieChildFirstName.setError(context.getResources().getString(R.string.invalid_f_name));
//            holder.tieChildFirstName.setError("Please enter child first name");
//        }

        holder.tieChildLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                holder.tilChildfname.setErrorEnabled(true);
//                holder.tilChildfname.setError(null);
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
        holder.tieChildDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((Activity) context).getFragmentManager();
                datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date date = calendar.getTime();

                        String date1 = Util.getFormattedDates(date);
                        Log.e(TAG, "date1" + date1);
                        holder.tieChildDOB.setText(date1);
                    }
                }, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(context.getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(manager, "DatePickerDialog");
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

    @Override
    public void onClicked(int position, String firstName, String lastName, String childGender, String childDOB) {

    }


    public class MyView extends RecyclerView.ViewHolder {
        private TextInputLayout tilChildfname,tilLastName,tilChildDob;
        private TextInputEditText tieChildFirstName, tieChildLastName, tieChildDOB;
        private MaterialSpinner childGenderSpinner;

        public MyView(View itemView) {
            super(itemView);
            tieChildFirstName = itemView.findViewById(R.id.tieChildFirstName);
            tieChildLastName = itemView.findViewById(R.id.tieChildLastName);
            tieChildDOB = itemView.findViewById(R.id.tieChildDOB);
            tilChildfname = itemView.findViewById(R.id.tilChildfname);
            tilLastName = itemView.findViewById(R.id.tilLastName);
            tilChildDob = itemView.findViewById(R.id.tilChildDob);
            childGenderSpinner = itemView.findViewById(R.id.childGenderSpinner);
            //Personal
            FontUtil.applyTypeface(tieChildFirstName, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tieChildLastName, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tieChildDOB, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(childGenderSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(context));
        }
    }
}
