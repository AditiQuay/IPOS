package quay.com.ipos.dayClosure.dayClosureActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.ui.CustomTextView;
import quay.com.ipos.utility.DateAndTimeUtil;

import static quay.com.ipos.utility.DateAndTimeUtil.DATE_AND_TIME_FORMAT_INDIA;

/**
 * Created by niraj.kumar on 7/11/2018.
 */

public class DayClosureMain extends AppCompatActivity implements InitInterface, View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private CustomTextView customTextView;
    private Context mContext;
    private MaterialSpinner userSpinner, businessSpinner;
    String[] userArray = {"All"};
    String[] businessPlace = {"Delhi", "Gurgaon"};

    private TextInputLayout tilFromDate, tilToDate;
    private TextInputEditText tieFromDate, tieToDate;

    boolean isFromDateclicked = false;
    boolean isToDateClicked = false;
    private Calendar calendar;
    private Button btnReset, btnSubmit;
    private ImageView ivFromDate, ivToDate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_closure_main);
        mContext = DayClosureMain.this;
        calendar = Calendar.getInstance();

        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
    }


    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        customTextView = findViewById(R.id.toolbarTtile);
        businessSpinner = findViewById(R.id.businessSpinner);
        userSpinner = findViewById(R.id.userSpinner);

        tilFromDate = findViewById(R.id.tilFromDate);
        tilToDate = findViewById(R.id.tilToDate);

        tieFromDate = findViewById(R.id.tieFromDate);
        tieToDate = findViewById(R.id.tieToDate);

        ivFromDate = findViewById(R.id.ivFromDate);
        ivToDate = findViewById(R.id.ivToDate);

        btnReset = findViewById(R.id.btnReset);
        btnSubmit = findViewById(R.id.btnSubmit);


        tieFromDate.setClickable(true);
        tieFromDate.setOnClickListener(this);

        tieToDate.setClickable(true);
        tieToDate.setOnClickListener(this);

        ivFromDate.setOnClickListener(this);
        ivToDate.setOnClickListener(this);

        btnReset.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        customTextView.setText(R.string.day_closure);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        ArrayAdapter businessHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, businessPlace);
        businessHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        businessSpinner.setAdapter(businessHeading);
        businessSpinner.setOnItemSelectedListener(this);


        ArrayAdapter userHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, userArray);
        userHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userHeading);
        userSpinner.setOnItemSelectedListener(this);


    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tieFromDate:
                isFromDateclicked = true;
                addFromDate();
                break;
            case R.id.ivFromDate:
                isFromDateclicked = true;
                addFromDate();
                break;
            case R.id.tieToDate:
                isToDateClicked = true;
                addToDate();
                break;
            case R.id.ivToDate:
                isToDateClicked = true;
                addToDate();
                break;
            case R.id.btnReset:
                clearData();
                break;
            case R.id.btnSubmit:
                Intent i = new Intent(mContext, DayClosureSales.class);
                startActivity(i);
            default:
                break;
        }
    }

    public void clearData() {
        tieFromDate.setText("");
        tieToDate.setText("");

    }

    public void addFromDate() {
        if (isFromDateclicked) {
            android.app.DatePickerDialog datePicker = new android.app.DatePickerDialog(mContext, new android.app.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    tieFromDate.setText(DateAndTimeUtil.toCustomStringDateAndTime(calendar, DATE_AND_TIME_FORMAT_INDIA));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
            isFromDateclicked = false;
        }
    }

    public void addToDate() {
        if (isToDateClicked) {
            android.app.DatePickerDialog datePicker = new android.app.DatePickerDialog(mContext, new android.app.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    tieToDate.setText(DateAndTimeUtil.toCustomStringDateAndTime(calendar, DATE_AND_TIME_FORMAT_INDIA));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
            isToDateClicked = false;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
