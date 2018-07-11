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

public class CustomerChildAdapter extends RecyclerView.Adapter<CustomerChildAdapter.MyView> implements MySubmitButton, AdapterView.OnItemSelectedListener {
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
    MyChildValidation myChildValidation;
    private String firstName;
    private String lastName;
    private String dob;
    private Calendar calendar;
    public interface MyChildValidation {
        void childValidated(boolean value);

        void childPreviousValidated(boolean value);
    }

    boolean isChildFilledFailed = false;
    boolean isPreviousChildValidated = false;

    public CustomerChildAdapter(Context context, ArrayList<AddCustomerModel.CustomerChildBean> childModels, ButtonListener buttonListener, MySubmitButton mySubmitButton, MyChildValidation myChildValidation) {
        this.context = context;
        this.childModels = childModels;
        this.buttonListener = buttonListener;
        this.mySubmitButton = mySubmitButton;
        this.myChildValidation = myChildValidation;
        calendar = Calendar.getInstance();
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_child_item, parent, false);

        return new MyView(itemView);
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
                firstName = holder.tieChildFirstName.getText().toString();
                lastName = holder.tieChildLastName.getText().toString();
                dob = holder.tieChildDOB.getText().toString();
                buttonListener.onAdd(holder.getAdapterPosition(),firstName, lastName, child, dob);
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
                firstName = holder.tieChildFirstName.getText().toString();
                lastName = holder.tieChildLastName.getText().toString();
                dob = holder.tieChildDOB.getText().toString();
                buttonListener.onAdd(holder.getAdapterPosition(),firstName, lastName, child, dob);
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
                firstName = holder.tieChildFirstName.getText().toString();
                lastName = holder.tieChildLastName.getText().toString();
                dob = holder.tieChildDOB.getText().toString();
                buttonListener.onAdd(holder.getAdapterPosition(),firstName, lastName, child, dob);
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
                Calendar c1 = Calendar.getInstance();
                c1.set(1980, 0, 1);

                Calendar maximunDate = Calendar.getInstance();
                maximunDate.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(true);
                datePickerDialog.setMinDate(c1);
                datePickerDialog.setMaxDate(maximunDate);
                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
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
                buttonListener.onAdd(position,firstName, lastName, child, dob);
            } else if (selectedSpinner.equalsIgnoreCase("No")) {
                child = selectedSpinner;
                buttonListener.onAdd(position,firstName, lastName, child, dob);
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
        private TextInputEditText tieChildFirstName, tieChildLastName, tieChildDOB;
        private MaterialSpinner childGenderSpinner;

        public MyView(View itemView) {
            super(itemView);
            tieChildFirstName = itemView.findViewById(R.id.tieChildFirstName);
            tieChildLastName = itemView.findViewById(R.id.tieChildLastName);
            tieChildDOB = itemView.findViewById(R.id.tieChildDOB);
            childGenderSpinner = itemView.findViewById(R.id.childGenderSpinner);
            //Personal
            FontUtil.applyTypeface(tieChildFirstName, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tieChildLastName, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tieChildDOB, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(childGenderSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(context));
        }
    }


}
