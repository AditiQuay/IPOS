package quay.com.ipos.compliance.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import quay.com.ipos.OnStoreSelectionListener;
import quay.com.ipos.R;
import quay.com.ipos.compliance.data.local.entity.BusinessPlaceEntity;
import quay.com.ipos.data.local.AppDatabase;
import quay.com.ipos.utility.UiUtils;

public class StoreListDialogFragment extends DialogFragment {
    private static final String TAG = StoreListDialogFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private int position;

    private OnStoreSelectionListener mListener;

    private RecyclerView recyclerView;
    private PickStoreDialogAdapter adapter;
    private AppDatabase appDatabase;

    public StoreListDialogFragment() {
    }


    public static StoreListDialogFragment newInstance(String param1, int param2) {

        StoreListDialogFragment fragment = new StoreListDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   appDatabase = Room.databaseBuilder(getActivity().getApplicationContext(),
                AppDatabase.class, "sample-db").build();*/
        appDatabase = AppDatabase.getAppDatabase(getActivity());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate( R.layout.dialog_fragment_storelist, container, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.mRv_storewise_compliance_summary);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new UiUtils.SpacesItemDecoration(spacingInPixels));

        adapter = new PickStoreDialogAdapter(getActivity(), mListener);
        recyclerView.setAdapter(adapter);





        queryData();


    }


    private void queryData() {
        LiveData<List<BusinessPlaceEntity>> universityLiveData = appDatabase.placeDao().fetchAllData();
        universityLiveData.observe(this, new Observer<List<BusinessPlaceEntity>>() {
            @Override
            public void onChanged(@Nullable List<BusinessPlaceEntity> placeEntities) {
                //Update your UI here.
                Log.i(TAG, "Size of save list in db : " + placeEntities.size());
                adapter.setData(placeEntities, position);
                /*Gson gson = new Gson();
                Log.d("query", gson.toJson(placeEntities));*/
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStoreSelectionListener) {
            mListener = (OnStoreSelectionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStoreSelectionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    class PickStoreDialogAdapter extends RecyclerView.Adapter<StoreVH> {
        private final String TAG = PickStoreDialogAdapter.class.getSimpleName();
        private final Context context;
        private List<BusinessPlaceEntity> list = Collections.emptyList();
        private int pagerPosition = 0;
        private OnStoreSelectionListener mListener;


        public PickStoreDialogAdapter(Context context, OnStoreSelectionListener mListener) {
            this.context = context;
            this.mListener = mListener;
        }


        @Override
        public StoreVH onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater =
                    LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.adapter_dialog_pickstore, parent, false);
            return new StoreVH(view);
        }

        @Override
        public void onBindViewHolder(final StoreVH holder, int position) {
            final BusinessPlaceEntity store = list.get(position);
            holder.txtStoreName.setText(store.name);
            holder.txtStoreName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onStoreSelected(store.name, store.id + "");

                    }
                    dismiss();


                }
            });

        }

        @Override
        public int getItemCount() {

            Log.i("Tas", this.list.size() + "");
            return this.list.size();
        }

        public void setData(List<BusinessPlaceEntity> stores, int pagerPosition) {
            this.list = stores;
            this.pagerPosition = pagerPosition;
            notifyDataSetChanged();
        }


    }


    static class StoreVH extends RecyclerView.ViewHolder {

        public TextView txtStoreName;

        public StoreVH(View view) {
            super(view);
            this.txtStoreName = view.findViewById(R.id.txtStoreName);
        }


    }



}
