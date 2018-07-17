package com.quayintech.tasklib.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.quayintech.tasklib.R;
import com.quayintech.tasklib.RepeatMonthDayTaskActivity;
import com.quayintech.tasklib.RepeatUnitTaskActivity;
import com.quayintech.tasklib.constants.KeyConstant;
import com.quayintech.tasklib.databinding.FragmentRepeatBinding;
import com.quayintech.tasklib.interfaces.RepeatPageHandler;
import com.quayintech.tasklib.interfaces.WeeksSelectionListener;
import com.quayintech.tasklib.model.Recurrence;
import com.quayintech.tasklib.model.RecurrenceDaily;
import com.quayintech.tasklib.model.RecurrenceMonthly;
import com.quayintech.tasklib.model.RecurrenceNever;
import com.quayintech.tasklib.model.RecurrenceWeekly;
import com.quayintech.tasklib.model.RecurrenceYearly;
import com.quayintech.tasklib.model.Weeks;
import com.quayintech.tasklib.viewmodel.RepeatPageViewModel;

import java.util.List;

public class RepeatFragment extends Fragment implements RepeatPageHandler, RepeatUntilFragment.OnFragmentInteractionListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = RepeatFragment.class.getSimpleName();
    private static final int REQ_REPEAT_UNTIL = 122;
    private static final int REQ_REPEAT_MONTHDAY = 123;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentRepeatBinding binding;
    private RepeatPageViewModel viewModel;

    Recurrence recurrence;

    public RepeatFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static RepeatFragment newInstance(String param1, String param2) {
        RepeatFragment fragment = new RepeatFragment();
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
        recurrence = (Recurrence) getActivity().getIntent().getSerializableExtra("recurrence");
        if (recurrence == null) {
            recurrence = new RecurrenceNever();
            Log.i(TAG, "recurrence is null");
        } else {
            Log.i(TAG, "recurrence is not null");

        }
        Gson gson = new Gson();
        Log.i(TAG, "recurrence:" + gson.toJson(recurrence));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_repeat, container, false);
        View view = inflater.inflate(R.layout.fragment_repeat, container, false);
        binding = DataBindingUtil.bind(view);
        viewModel = new RepeatPageViewModel(recurrence, this, getActivity());
        binding.setHandler(this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
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
        Intent intent = new Intent();
        intent.putExtra("recurrence", recurrence);
        Gson gson = new Gson();
        Log.i(TAG, "recurrence:" + gson.toJson(recurrence));
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();

    }

    @Override
    public void onClickFrequency(View view) {
        showPopup(binding.textFrequency);
    }

    @Override
    public void onClickInterval(View view) {
        if (viewModel.strFrequency.contentEquals(KeyConstant.FREQUENCY_DAILY)) {
            showPopupInterval(binding.textInterval);
        }
        if (viewModel.strFrequency.contentEquals(KeyConstant.FREQUENCY_WEEKLY)) {

            showPopupIntervalWeekly(binding.textInterval);
        }
        if (viewModel.strFrequency.contentEquals(KeyConstant.FREQUENCY_MONTHLY)) {

            showPopupIntervalMonthly(binding.textInterval);
        }
        if (viewModel.strFrequency.contentEquals(KeyConstant.FREQUENCY_YEARLY)) {

            showPopupIntervalYearly(binding.textInterval);
        }
    }

    @Override
    public void onClickOnDays(View view) {

        if (viewModel.strFrequency.contentEquals(KeyConstant.FREQUENCY_WEEKLY)) {

            showPopupOnDaysWeekly(binding.textOnDays);
        }
        if (viewModel.strFrequency.contentEquals(KeyConstant.FREQUENCY_MONTHLY)) {

            showPopupOnDaysMonthly(binding.textOnDays);
        }


    }

    @Override
    public void onClickUntil(View view) {

        Intent intent = new Intent(getActivity(), RepeatUnitTaskActivity.class);
        intent.putExtra("mRepeatFrequency", viewModel.strFrequency);
        startActivityForResult(intent, REQ_REPEAT_UNTIL);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void showPopup(final View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.popup_frequency, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.fqNever) {//  viewModel.setStrFrequency("Never");
                    recurrence = new RecurrenceNever();
                    viewModel.setRecurrence(recurrence);
                    return true;
                } else if (i == R.id.fqDaily) {
                    recurrence = new RecurrenceDaily();
                    viewModel.setRecurrence(recurrence);
                    // viewModel.setStrFrequency("Daily");
                    viewModel.setStrInterval("1 day");
                    viewModel.setStrSummary("Repeat daily");
                    return true;
                } else if (i == R.id.fqWeekly) {
                    recurrence = new RecurrenceWeekly();
                    viewModel.setRecurrence(recurrence);
                    //   viewModel.setStrFrequency("Weekly");
                    viewModel.setStrInterval("1 week");
                    viewModel.setStrOnDays(recurrence.getRepeatOnDays());
                    viewModel.setStrSummary("Repeat weekly");


                    return true;
                } else if (i == R.id.fqMonthly) {
                    recurrence = new RecurrenceMonthly();
                    viewModel.setRecurrence(recurrence);
                    //  viewModel.setStrFrequency("Monthly");
                    viewModel.setStrOnDays(recurrence.getRepeatOnDays());
                    viewModel.setStrInterval("1 month");
                    viewModel.setStrSummary("Repeat monthly");


                    return true;
                } else if (i == R.id.fqYearly) {
                    recurrence = new RecurrenceYearly();
                    viewModel.setRecurrence(recurrence);
                    //   viewModel.setStrFrequency("Yearly");
                    viewModel.setStrInterval("1 year");
                    viewModel.setStrSummary("Repeat yearly");


                    return true;
                } else {
                    return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    private void showPopupInterval(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.popup_interval_daily, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                viewModel.setStrInterval(item.getTitle().toString());
                return true;

            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    private void showPopupIntervalWeekly(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.popup_interval_weekly, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                viewModel.setStrInterval(item.getTitle().toString());
                return true;
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    private void showPopupIntervalMonthly(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.popup_interval_monthly, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                viewModel.setStrInterval(item.getTitle().toString());
                return true;
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    private void showPopupIntervalYearly(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.popup_interval_yearly, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                viewModel.setStrInterval(item.getTitle().toString());
                return true;
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }


    private void showPopupOnDaysMonthly(View v) {

        Intent intent = new Intent(getActivity(), RepeatMonthDayTaskActivity.class);
        startActivityForResult(intent, REQ_REPEAT_MONTHDAY);

    }

    private void showPopupOnDaysWeekly(View v) {
        WeeksPickerDialogFragment dialogFragment = WeeksPickerDialogFragment.showDialog(getChildFragmentManager(), viewModel.strOnDays);
        dialogFragment.setListener(new WeeksSelectionListener() {
            @Override
            public void onWeeksSelected(List<Weeks> list) {
                viewModel.setStrOnDays(list);
            }

        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (recurrence != null && data != null) {
            if (requestCode == REQ_REPEAT_UNTIL) {
                String repeatUntil = data.getStringExtra("repeatUntil");
                recurrence.setRepeatUntil(repeatUntil);
                viewModel.setRecurrence(recurrence);

            }
            if (requestCode == REQ_REPEAT_MONTHDAY) {
                String mOnDaysMonth = data.getStringExtra("mOnDaysMonth");
                recurrence.setRepeatOnDays(mOnDaysMonth);
                viewModel.setRecurrence(recurrence);

            }

        }

    }
}
