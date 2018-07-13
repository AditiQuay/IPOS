package com.quayintech.tasklib.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import com.quayintech.tasklib.R;
import com.quayintech.tasklib.constants.KeyConstant;
import com.quayintech.tasklib.databinding.FragmentRepeatMonthDayBinding;
import com.quayintech.tasklib.interfaces.RepeatUntilPageHandler;
import com.quayintech.tasklib.utils.DateTimeUtils;
import com.quayintech.tasklib.view.DayOfMonthPickerView;
import com.quayintech.tasklib.viewmodel.DayofMonthViewModel;

public class RepeatMonthDayFragment extends Fragment implements RepeatUntilPageHandler, DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentRepeatMonthDayBinding binding;
    private DayofMonthViewModel viewModel;
    private String mOnDaysMonth;

    public RepeatMonthDayFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RepeatMonthDayFragment newInstance(String param1, String param2) {
        RepeatMonthDayFragment fragment = new RepeatMonthDayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_repeat, container, false);
        View view = inflater.inflate(R.layout.fragment_repeat_month_day, container, false);
        binding = DataBindingUtil.bind(view);
        viewModel = new DayofMonthViewModel(this, getActivity());
        binding.setHandler(this);
        binding.setViewModel(viewModel);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


     /*   binding.mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                viewModel.setStrDueDate(year, month, dayOfMonth, 0, 0);
                binding.subTitle.setText("" + year + "-" + month + "-" + dayOfMonth);

            }
        });*/


        binding.mNumberPickerSlots.setMinValue(0);
        binding.mNumberPickerSlots.setMaxValue(4);
        binding.mNumberPickerSlots.setDisplayedValues(KeyConstant.WEEK_SLOTS);

        binding.mNumberPickerWeeks.setMinValue(0);
        binding.mNumberPickerWeeks.setMaxValue(6);
        binding.mNumberPickerWeeks.setDisplayedValues(KeyConstant.WEEKS);

        binding.mCalendarView.setListener(new DayOfMonthPickerView.OnDayOfMonthPickerListener() {
            @Override
            public void onDayOfMonthSelected(int dayOfMonth) {
              //  Toast.makeText(getContext(), "" + dayOfMonth, Toast.LENGTH_SHORT).show();
                mOnDaysMonth = dayOfMonth+"";

            }
        });

        binding.mNumberPickerSlots.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                onClickSpecific(picker);
            }
        });
        binding.mNumberPickerWeeks.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                onClickSpecific(picker);

            }
        });
        int selectedDay = Integer.parseInt(DateTimeUtils.getDayOfMonth());

        mOnDaysMonth = selectedDay + "";
        binding.mCalendarView.setSelected(selectedDay);

        binding.mNumberPickerWeeks.setValue(getWeekValue(getCurrentWeek()));
        binding.mNumberPickerSlots.setValue(getWeekNoValue());

        binding.mNumberPickerSlots.getValue();


    }

    private int getWeekNoValue() {
        int currentWeekNo = DateTimeUtils.getWeekNo();
        return currentWeekNo;
    }

    private int getWeekValue(String currentWeek) {
        if(currentWeek.contentEquals("Sunday"))
        return 0;
        if(currentWeek.contentEquals("Monday"))
        return 1;
        if(currentWeek.contentEquals("Tuesday"))
        return 2;
        if(currentWeek.contentEquals("Wednesday"))
        return 3;
        if(currentWeek.contentEquals("Thursday"))
        return 4;
        if(currentWeek.contentEquals("Friday"))
        return 5;
        if(currentWeek.contentEquals("Saturday"))
        return 6;

        return 0;
    }

    private String getCurrentWeek() {
        return DateTimeUtils.getDayOfTheWeek();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClickCloseTask(View view) {
        getActivity().finish();
    }

    @Override
    public void onClickAddSubtask(View view) {
        Intent intent = getActivity().getIntent();
        intent.putExtra("mOnDaysMonth", mOnDaysMonth);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();

    }

    @Override
    public void onClickDefault(View view) {
        viewModel.setViewCalender(true);
        viewModel.setViewWeek(false);
        mOnDaysMonth = binding.mCalendarView.getSelected()+"";
//        binding.subTitle.setText(getString(R.string.forever));

    }

    @Override
    public void onClickSpecific(View view) {
        viewModel.setViewCalender(false);
        viewModel.setViewWeek(true);
        String s = KeyConstant.WEEK_SLOTS[binding.mNumberPickerSlots.getValue()]
                +" " +KeyConstant.WEEKS[binding.mNumberPickerWeeks.getValue()];
        mOnDaysMonth = s;


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        viewModel.setStrDueDate(year, month, dayOfMonth, 0, 0);
        binding.subTitle.setText(year + "-" + month + "-" + dayOfMonth);


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
