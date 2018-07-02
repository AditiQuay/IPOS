package com.quayintech.tasklib.fragment;

import android.app.Activity;
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
import android.widget.CalendarView;

import com.quayintech.tasklib.R;
import com.quayintech.tasklib.databinding.FragmentRepeatUntilBinding;
import com.quayintech.tasklib.interfaces.RepeatUntilPageHandler;
import com.quayintech.tasklib.utils.DateTimeUtils;
import com.quayintech.tasklib.viewmodel.RepeatUntilPageViewModel;

import java.util.Calendar;

public class RepeatUntilFragment extends Fragment implements RepeatUntilPageHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentRepeatUntilBinding binding;
    private RepeatUntilPageViewModel viewModel;

    private String mRepeatFrequency;

    public RepeatUntilFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RepeatUntilFragment newInstance(String param1, String param2) {
        RepeatUntilFragment fragment = new RepeatUntilFragment();
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

        mRepeatFrequency = getActivity().getIntent().getStringExtra("mRepeatFrequency");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_repeat, container, false);
        View view = inflater.inflate(R.layout.fragment_repeat_until, container, false);
        binding = DataBindingUtil.bind(view);
        viewModel = new RepeatUntilPageViewModel(mRepeatFrequency,this, getActivity());
        binding.setHandler(this);
        binding.setViewModel(viewModel);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                viewModel.setStrUntil(DateTimeUtils.getDate(year, month, dayOfMonth));
                //   binding.subTitle.setText("" + year + "-" + month + "-" + dayOfMonth);

            }
        });
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
        intent.putExtra("repeatUntil",viewModel.getRepeatUntil() );
        getActivity().setResult(Activity.RESULT_OK,intent);
        getActivity().finish();

    }

    @Override
    public void onClickDefault(View view) {
        viewModel.setViewCalender(false);
       // viewModel.setStrUntil(getString(R.string.forever));
        viewModel.setStrDefaultTime();
    }

    @Override
    public void onClickSpecific(View view) {
        viewModel.setViewCalender(true);
        Calendar c = Calendar.getInstance();
        binding.mCalendarView.setDate(c.getTime().getTime());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        viewModel.setStrUntil(DateTimeUtils.getDate(year, month, dayOfMonth));


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
