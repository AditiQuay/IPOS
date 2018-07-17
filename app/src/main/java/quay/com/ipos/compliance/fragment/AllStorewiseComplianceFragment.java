package quay.com.ipos.compliance.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.adapter.StoreAdapter;
import quay.com.ipos.compliance.data.local.dao.BusinessPlaceDao;
import quay.com.ipos.compliance.data.local.dao.TaskDao;
import quay.com.ipos.compliance.data.local.entity.BusinessPlaceEntity;
import quay.com.ipos.compliance.data.local.entity.Task;
import quay.com.ipos.compliance.viewModel.StoreViewModel;
import quay.com.ipos.data.local.AppDatabase;
import quay.com.ipos.databinding.CFragmentComplianceAllstoreAbsBinding;
import quay.com.ipos.utility.UiUtils;

public class AllStorewiseComplianceFragment extends Fragment {
    private static final String TAG = AllStorewiseComplianceFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int position;
    private StoreViewModel storeViewModel;

    private TextView textProgressDone;

    private RecyclerView recyclerView;
    private StoreAdapter adapter;


    private void loadData() {
        final LiveData<List<BusinessPlaceEntity>> placeLiveData = AppDatabase.getAppDatabase(getActivity()).placeDao().fetchAllData();
        placeLiveData.observe(getActivity(), new Observer<List<BusinessPlaceEntity>>() {
            @Override
            public void onChanged(@Nullable final List<BusinessPlaceEntity> businessPlaceEntities) {

                Gson gson = new Gson();
                Log.d("query", gson.toJson(businessPlaceEntities));
                adapter.setData(businessPlaceEntities, position);

            }
        });

     /*   getStates().observe(getActivity(), new Observer<List<BusinessPlaceEntity>>() {
            @Override
            public void onChanged(@Nullable final List<BusinessPlaceEntity> businessPlaceEntities) {
                Gson gson = new Gson();
                Log.d("query", gson.toJson(businessPlaceEntities));
                adapter.setData(businessPlaceEntities, position);

            }
        });*/
        new DatabaseAsync().execute();
    }

  /*  public LiveData<List<BusinessPlaceEntity>> getStates() {
       final BusinessPlaceDao placeDao = AppDatabase.getAppDatabase(getActivity()).placeDao();
        final TaskDao taskDao = AppDatabase.getAppDatabase(getActivity()).taskDao();
        LiveData<List<BusinessPlaceEntity>> statesLiveData = placeDao.getAllTask();
        statesLiveData = Transformations.map(statesLiveData, new Function<List<BusinessPlaceEntity>, List<BusinessPlaceEntity>>() {

            @Override
            public List<BusinessPlaceEntity> apply(final List<BusinessPlaceEntity> inputStates) {
                for (BusinessPlaceEntity state : inputStates) {
                    state.setCompliances(taskDao.fetchAllDataFilterByPlace(state.id));
                }
                return inputStates;
            }
        });
        return statesLiveData;
    }
*/

    public AllStorewiseComplianceFragment() {
    }


    public static AllStorewiseComplianceFragment newInstance(String param1, int position) {
        AllStorewiseComplianceFragment fragment = new AllStorewiseComplianceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.c_fragment_compliance_allstore_abs, container, false);
        CFragmentComplianceAllstoreAbsBinding binding = DataBindingUtil.bind(view);
        storeViewModel = new StoreViewModel();
        binding.setStoreViewModel(storeViewModel);


        return view;

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // storeViewModel.loadData(this);
        TextView textView = view.findViewById(R.id.ipos_block_txt1);
        textView.setText(getTitle());

        textProgressDone = view.findViewById(R.id.textProgressDone);
        //textProgressDone.setText("Hello");


        recyclerView = view.findViewById(R.id.mRv_storewise_compliance_summary);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);

        recyclerView.addItemDecoration(new UiUtils.SpacesItemDecoration(spacingInPixels));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        adapter = new StoreAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        loadStoreViewModel();

    }

    private void loadStoreViewModel() {
        AppDatabase.getAppDatabase(IPOSApplication.getContext()).taskDao().getAllTask().observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                storeViewModel.updateUI(tasks, position, "");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();

    }

    private int getTitle() {

        if (position == 0) {
            return R.string.ct_statutory_business_compliance;
        }
        if (position == 1) {
            return R.string.ct_business_compliance;
        }
        if (position == 2) {
            return R.string.ct_statutory_compliance;
        }
        return R.string.ct_statutory_business_compliance;
    }


   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStoreSelectionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }


    private class DatabaseAsync extends AsyncTask<Void, Void, List<BusinessPlaceEntity>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected List<BusinessPlaceEntity> doInBackground(Void... voids) {
            final BusinessPlaceDao placeDao = AppDatabase.getAppDatabase(getActivity()).placeDao();
            final TaskDao taskDao = AppDatabase.getAppDatabase(getActivity()).taskDao();
            List<BusinessPlaceEntity> inputStates = placeDao.fetchAllDataWithoutLive();
            for (BusinessPlaceEntity state : inputStates) {
                state.setCompliances(taskDao.fetchAllDataFilterByPlace(state.id));
            }

            return inputStates;
        }

        @Override
        protected void onPostExecute(List<BusinessPlaceEntity> result) {
            super.onPostExecute(result);
            //storeViewModel.updateUI(result, position);
            adapter.setData(result, position);
            //To after addition operation here.
        }
    }

}