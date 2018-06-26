package quay.com.ipos.inventory.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.inventory.activity.SimpleDividerItemDecoration;
import quay.com.ipos.inventory.adapter.ActionListAdapter;
import quay.com.ipos.inventory.modal.ActionListModel;
import quay.com.ipos.listeners.ActionListClick;

/**
 * Created by niraj.kumar on 6/23/2018.
 */

public class ActionListFragment extends DialogFragment implements ActionListClick {
    private static final String ARG_PARAM1 = "data";
    private List<ActionListModel> actionListModels;
    private ActionListAdapter actionListAdapter;
    private Context mContext;
    private View main;

    // 1. Defines the listener interface with a method passing back data result.
    public interface ActionListener {
        void onActionListClicked(int actionId, String actionTitle);
    }


    public ActionListFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public static ActionListFragment newInstance(List<ActionListModel> list) {
        ActionListFragment fragment = new ActionListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            actionListModels = (List<ActionListModel>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.view_action_spinner_dialog, container, false);
        mContext = getActivity();
        findViewById();
        return main;
    }

    private void findViewById() {
        RecyclerView rvList = (RecyclerView) main.findViewById(R.id.rvActionList);

        rvList.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        actionListAdapter = new ActionListAdapter(mContext, actionListModels, this);
        rvList.setAdapter(actionListAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void actionListClicked(int position) {
        ActionListModel actionListModel = actionListModels.get(position);
        ActionListener actionListener = (ActionListener) mContext;
        actionListener.onActionListClicked(actionListModel.actionID, actionListModel.actionTitle);
        dismiss();
    }
}
