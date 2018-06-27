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
import quay.com.ipos.inventory.adapter.ActionListAdapter;
import quay.com.ipos.inventory.adapter.OthersListAdapter;
import quay.com.ipos.inventory.modal.ActionListModel;
import quay.com.ipos.inventory.modal.OthersTabList;
import quay.com.ipos.listeners.ActionListClick;
import quay.com.ipos.listeners.OthersTabListner;

/**
 * Created by niraj.kumar on 6/23/2018.
 */

public class ActionDialogFragment extends DialogFragment implements ActionListClick {
    private static final String ARG_PARAM1 = "data";
    private RecyclerView rvList;
    private Context mContext;
    private View main;
    private List<ActionListModel> list;

    // 1. Defines the listener interface with a method passing back data result.
    public interface ActionListener {
        void onActionListClicked(int actionId, String actionTitle);
    }
    public ActionDialogFragment() {

    }

    public static ActionDialogFragment newInstance(List<ActionListModel> list) {
        ActionDialogFragment fragment = new ActionDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = (List<ActionListModel>) getArguments().getSerializable(ARG_PARAM1);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.view_spinner_dialog, container, false);
        mContext = getActivity();


        return main;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        rvList = (RecyclerView) view.findViewById(R.id.rvList);
        // Show soft keyboard automatically and request focus to field
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        ActionListAdapter actionListAdapter = new ActionListAdapter(mContext, list, this);
        rvList.setAdapter(actionListAdapter);
    }


    @Override
    public void actionListClicked(int position) {
        ActionListModel actionListModel = list.get(position);
        ActionListener actionListener = (ActionListener) mContext;
        actionListener.onActionListClicked(actionListModel.actionID, actionListModel.actionTitle);
        dismiss();
    }
}
