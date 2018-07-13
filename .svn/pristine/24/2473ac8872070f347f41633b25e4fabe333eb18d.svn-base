package quay.com.ipos.compliance.fragment;


import android.arch.lifecycle.Observer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.adapter.ComplianceAdapter;
import quay.com.ipos.compliance.constants.AnnotationTaskState;
import quay.com.ipos.compliance.data.local.entity.Task;
import quay.com.ipos.compliance.viewModel.ProgressStateViewModel;
import quay.com.ipos.compliance.viewModel.StoreViewModel;
import quay.com.ipos.data.local.AppDatabase;
import quay.com.ipos.utility.DateAndTimeUtil;
import quay.com.ipos.utility.RecyclerViewEmptySupport;
import quay.com.ipos.utility.UiUtils;


public class ComplianceFragmentSingleStoreDetail_ABS extends Fragment implements ComplianceViewFilterSelectionListner {

    private static final String TAG = ComplianceFragmentSingleStoreDetail_ABS.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String store_id;
    private int mParam2;


    private RecyclerViewEmptySupport mRecyclerViewImmediate;
    private RecyclerViewEmptySupport mRecyclerViewUpcoming;
    private RecyclerViewEmptySupport mRecyclerViewPending;
    private RecyclerViewEmptySupport mRecyclerViewDone;
    private StoreViewModel storeViewModel;


    private ComplianceAdapter adapterDone;
    private ComplianceAdapter adapterPending;
    private ComplianceAdapter adapterUpcoming;
    private ComplianceAdapter adapterImmediate;

    private View btnViewAll;
   // private ProgressStateViewModel progressStateViewModel;
    private View txtShowCountContainer;

    private List<Task> taskList = new ArrayList<>();

    private static final int Pending = 0;
    private TextView txtShowCount;


    public ComplianceFragmentSingleStoreDetail_ABS() {
        // Required empty public constructor
    }


    public static ComplianceFragmentSingleStoreDetail_ABS newInstance(String param1, int param2) {
        ComplianceFragmentSingleStoreDetail_ABS fragment = new ComplianceFragmentSingleStoreDetail_ABS();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            store_id = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //storeViewModel = new StoreViewModel(getActivity());


        View view = inflater.inflate(R.layout.c_fragment_compliance_singlestoredetail, container, false);
        //View view = binding.getRoot();
        //progressStateViewModel = new ProgressStateViewModel(null);
        //progressStateViewModel.setAllTrue();
        //binding.setProgressState(progressStateViewModel);
        // binding.setStoreViewModel(storeViewModel);
        storeViewModel = StoreViewModel.getInstance(this);


        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtShowCount = view.findViewById(R.id.txtShowCount);
        txtShowCountContainer = view.findViewById(R.id.txtShowCountContainer);
        txtShowCountContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        btnViewAll = view.findViewById(R.id.btnViewAll);


        TextView textView = view.findViewById(R.id.ipos_block_txt1);
        textView.setText(getTitle());

        mRecyclerViewImmediate = view.findViewById(R.id.mRecyclerViewImmediate);
        mRecyclerViewUpcoming = view.findViewById(R.id.mRecyclerViewUpcoming);
        mRecyclerViewPending = view.findViewById(R.id.mRecyclerViewPending);
        mRecyclerViewDone = view.findViewById(R.id.mRecyclerViewDone);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);

        mRecyclerViewImmediate.addItemDecoration(new UiUtils.SpacesItemDecoration(spacingInPixels));
        mRecyclerViewUpcoming.addItemDecoration(new UiUtils.SpacesItemDecoration(spacingInPixels));
        mRecyclerViewPending.addItemDecoration(new UiUtils.SpacesItemDecoration(spacingInPixels));
        mRecyclerViewDone.addItemDecoration(new UiUtils.SpacesItemDecoration(spacingInPixels));

        mRecyclerViewImmediate.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerViewPending.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerViewDone.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        int backGroundColorDone = getResources().getColor(R.color.color_done);
        int backGroundColorUpcoming = getResources().getColor(R.color.color_upcoming_event);
        int backGroundColorPending = getResources().getColor(R.color.color_pending);
        int backGroundColorImmediate = getResources().getColor(R.color.color_immediate_action);

        adapterDone = new ComplianceAdapter(getActivity(), backGroundColorDone);
        adapterUpcoming = new ComplianceAdapter(getActivity(), backGroundColorUpcoming);
        adapterPending = new ComplianceAdapter(getActivity(), backGroundColorPending);
        adapterImmediate = new ComplianceAdapter(getActivity(), backGroundColorImmediate);

        View listEmptyUpcomingEvent = view.findViewById(R.id.listEmptyUpcomingEvent);
        View listEmptyPending = view.findViewById(R.id.listEmptyPending);
        View listEmptyImmediate = view.findViewById(R.id.listEmptyImmediate);
        View listEmptyDone = view.findViewById(R.id.listEmptyDone);
        mRecyclerViewUpcoming.setEmptyView(listEmptyUpcomingEvent);
        mRecyclerViewImmediate.setEmptyView(listEmptyImmediate);
        mRecyclerViewPending.setEmptyView(listEmptyPending);
        mRecyclerViewDone.setEmptyView(listEmptyDone);

        mRecyclerViewImmediate.setAdapter(adapterImmediate);
        mRecyclerViewUpcoming.setAdapter(adapterUpcoming);
        mRecyclerViewPending.setAdapter(adapterPending);
        mRecyclerViewDone.setAdapter(adapterDone);

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fetchDataFromLocal();
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private int getTitle() {

        if (mParam2 == 0) {
            return R.string.ct_statutory_business_compliance;
        }
        if (mParam2 == 1) {
            return R.string.ct_business_compliance;
        }
        if (mParam2 == 2) {
            return R.string.ct_statutory_compliance;
        }
        return R.string.ct_statutory_business_compliance;
    }


    @Override
    public void onViewAllSelected() {

    }

    @Override
    public void onDoneSelected() {

    }

    @Override
    public void onImmediateSelected() {

    }

    @Override
    public void onUpcomingSelected() {

    }

    @Override
    public void onPendingSelected() {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.next7:
                        txtShowCount.setText("Next 7 days");
                        try {
                            loadUpcomingTaskNew(7);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return true;
                    case R.id.next15:
                         txtShowCount.setText("Next 15 days");
                        try {
                            loadUpcomingTaskNew(15);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        return true;
                    case R.id.next30:
                        txtShowCount.setText("Next 30 days");
                        try {
                            loadUpcomingTaskNew(30);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        return true;
                    default:
                        return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    private void loadUpcomingTaskNew(int uptoNextUpcomingDay) {
        Calendar calAftertomorrow = Calendar.getInstance();
        calAftertomorrow.add(Calendar.DAY_OF_YEAR, 1);
        // long aftertomorrow = calAftertomorrow.getTimeInMillis();
        String aftertomorrow = DateAndTimeUtil.toStringDateAndTime(calAftertomorrow);

        Calendar calendarNext = Calendar.getInstance();
        calendarNext.add(Calendar.DAY_OF_YEAR, uptoNextUpcomingDay);
        List<Task> upcomingList = new ArrayList<>();

        for (Task task : taskList) {
            Calendar dueDate = DateAndTimeUtil.parseDateAndTime(task.getDateAndTime());
            if (dueDate.before(calendarNext) && dueDate.after(aftertomorrow)) {
                upcomingList.add(task);
            }
        }

        adapterUpcoming.setData(upcomingList, mParam2);


    }

    private void fetchDataFromLocal() {
        try {

           /* new RushSearch()
                    .whereEqual("store_id", store_id)
                    .find(TaskData.class, new RushSearchCallback<TaskData>() {
                        @Override
                        public void complete(List<TaskData> list) {
                            int size = list.size();
                            Log.i(TAG, "Size of save list in db : " + size);
                            storeViewModel.updateUI(list, mParam2, store_id);
                        }
                    });*/
            AppDatabase.getAppDatabase(IPOSApplication.getContext()).taskDao().getTaskByPlace(Long.parseLong(store_id)).observe(getActivity(),
                    new Observer<List<Task>>() {
                        @Override
                        public void onChanged(@Nullable List<Task> tasks) {
                            int size = tasks.size();
                            Log.i(TAG, "Size of save list in db : " + size);
                            storeViewModel.updateUI(tasks, mParam2, store_id);
                            taskList = tasks;
                            loadDataInAdapter(tasks);

                        }
                    });


           /* new RushSearch()
                    .whereEqual("store_id", store_id)
                    .and()
                    .whereEqual("progress_state", 1)
                    .find(TaskData.class, new RushSearchCallback<TaskData>() {
                        @Override
                        public void complete(final List<TaskData> list) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int size = list.size();
                                    Log.i(TAG, "Size of save list in db : " + size);
                                    //   if (size > 0) {
                                    adapterDone.setData(list, mParam2);

                                    // }
                                }
                            });

                        }
                    });*/

          /*  new RushSearch()
                    .whereEqual("store_id", store_id)
                    .and()
                    .whereEqual("progress_state", Pending)
                    .find(TaskData.class, new RushSearchCallback<TaskData>() {
                        @Override
                        public void complete(final List<TaskData> list) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    {
                                        int size = list.size();
                                        Log.i(TAG, "Size of save list in db : " + size);
                                        //if (size > 0) {
                                        adapterPending.setData(list, mParam2);

                                        //}
                                    }
                                }
                            });
                        }
                    });
*/

            //   loadImmediateTask();

          /*  new RushSearch()
                    .whereEqual("store_id", store_id)
                    .and()
                    .whereEqual("is_immediate", true)
                    .find(Task.class, new RushSearchCallback<Task>() {
                        @Override
                        public void complete(final List<Task> list) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    int size = list.size();
                                    Log.i(TAG, "Size of save list in db : " + size);
                                    adapterImmediate.setData(list, mParam2);

                                }
                            });
                        }
                    });
*/


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadDataInAdapter(List<Task> tasks) {
        List<Task> taskDoneList = new ArrayList<>();
        List<Task> taskPendingList = new ArrayList<>();
        List<Task> taskImmediateList = new ArrayList<>();
        List<Task> taskUpcomingList = new ArrayList<>();


        //for immediate task
        Calendar calendarImm = Calendar.getInstance();
        calendarImm.add(Calendar.DAY_OF_YEAR, 3);
        calendarImm.set(Calendar.HOUR_OF_DAY, 0);
        calendarImm.set(Calendar.MINUTE, 0);
        calendarImm.set(Calendar.SECOND, 0);

        for (Task task : tasks) {
            Calendar dueDate = DateAndTimeUtil.parseDateAndTime(task.getDateAndTime());

            //for Done task
            if (task.progress_state == AnnotationTaskState.DONE) {
                taskDoneList.add(task);
            }

            //for Pending task
            if (task.progress_state == AnnotationTaskState.PENDING) {
                taskPendingList.add(task);

                //for Immediate task
                if (dueDate.before(calendarImm)) {
                    taskImmediateList.add(task);
                }
            }


        }

        adapterDone.setData(taskDoneList, mParam2);
        adapterPending.setData(taskPendingList, mParam2);
        adapterImmediate.setData(taskImmediateList, mParam2);

        loadUpcomingTaskNew(7);

    }

    /* private void loadImmediateTask() {
        DatabaseHelper.getInstance(getActivity()).getImmediateTaskListAsync(store_id, new RushSearchCallback<TaskData>() {
            @Override
            public void complete(final List<TaskData> list) {
                int size = list.size();
                Log.i(TAG, "Size loadImmediateTask in db : " + size);
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        adapterImmediate.setData(list, mParam2);
                        // Stuff that updates the UI

                    }
                });

            }
        });
    }*/

  /*  private void loadUpcomingTask(int uptoNextUpcomingDay) {
        Calendar calAftertomorrow = Calendar.getInstance();
        calAftertomorrow.add(Calendar.DAY_OF_YEAR, 1);
        // long aftertomorrow = calAftertomorrow.getTimeInMillis();
        String aftertomorrow = DateAndTimeUtil.toStringDateAndTime(calAftertomorrow);

        Calendar calendarNext = Calendar.getInstance();
        calendarNext.add(Calendar.DAY_OF_YEAR, uptoNextUpcomingDay);
        // long upcomingDay = calendarNext.getTimeInMillis();
        String upcomingDay = DateAndTimeUtil.toStringDateAndTime(calendarNext);

        new RushSearch()
                .whereEqual("store_id", store_id)
                .and()
                .whereGreaterThan("task_due_date", Long.parseLong(aftertomorrow))
                .and()
                .whereLessThan("task_due_date", Long.parseLong(upcomingDay))


                //.whereEqual("progress_state", Pending)
                .find(TaskData.class, new RushSearchCallback<TaskData>() {
                    @Override
                    public void complete(final List<TaskData> list) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                int size = list.size();
                                Log.i(TAG, "Size of save list in db : " + size);
                                adapterUpcoming.setData(list, mParam2);

                            }
                        });
                    }
                });
    }*/

}
