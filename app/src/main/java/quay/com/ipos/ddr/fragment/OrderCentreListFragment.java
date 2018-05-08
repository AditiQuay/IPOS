package quay.com.ipos.ddr.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.dashboard.modal.LowInventoryModal;
import quay.com.ipos.ddr.adapter.OrderCentreListAdapter;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.SpacesItemDecoration;

/**
 * Created by aditi.bhuranda on 08-05-2018.
 */

public class OrderCentreListFragment extends BaseFragment implements View.OnClickListener {

    private final String TAG = OrderCentreListFragment.class.getSimpleName();
    String[] inventory = {"KGM Traders", "KGM Traders"};

    private RecyclerView recyclerView;
    private LinearLayout llNew, llAccepted, llDispatched, llDelivered, llCancelled;
    private View vNew, vAccepted, vDispatched, vDelivered, vCancelled;
    OrderCentreListAdapter mOrderCentreListAdapter;
//    private ArrayList<LowInventoryModal> responseList = new ArrayList<>();
    private ArrayList<OrderList.Datum> orderList= new ArrayList<>();
    private int mSelectedpos;

    // newInstance constructor for creating fragment with arguments
    public OrderCentreListFragment newInstance(int position) {
        OrderCentreListFragment fragmentFirst = new OrderCentreListFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_centre, container, false);

        initializeComponent(view);
        setAdapter();
        return view;
    }

    private void setAdapter() {
        mOrderCentreListAdapter = new OrderCentreListAdapter(getActivity(),this,recyclerView, orderList);
        recyclerView.setAdapter(mOrderCentreListAdapter);
        orderList.addAll(IPOSApplication.mOrderList);
        mOrderCentreListAdapter.notifyDataSetChanged();
    }

    private void initializeComponent(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager2);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));

        llNew = view.findViewById(R.id.llNew);
        llAccepted = view.findViewById(R.id.llAccepted);
        llDispatched = view.findViewById(R.id.llDispatched);
        llDelivered = view.findViewById(R.id.llDelivered);
        llCancelled = view.findViewById(R.id.llCancelled);
        vNew = view.findViewById(R.id.vNew);
        vAccepted = view.findViewById(R.id.vAccepted);
        vDispatched = view.findViewById(R.id.vDispatched);
        vDelivered = view.findViewById(R.id.vDelivered);
        vCancelled = view.findViewById(R.id.vCancelled);
        llNew.setOnClickListener(this);
        llAccepted.setOnClickListener(this);
        llDispatched.setOnClickListener(this);
        llDelivered.setOnClickListener(this);
        llCancelled.setOnClickListener(this);
        final GestureDetector mGestureDetector = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public void onTouchEvent(RecyclerView arg0, MotionEvent arg1) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean arg0) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean onInterceptTouchEvent(RecyclerView arg0, MotionEvent motionEvent) {
                View child = arg0.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    mSelectedpos = arg0.getChildPosition(child);
//                    AppLog.e(TAG, "item:: " + arg0.getChildPosition(child));
//                    selectPinned(arg0.getChildPosition(child));
                    return true;

                }

                return false;
            }

        });
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();


    }
}
