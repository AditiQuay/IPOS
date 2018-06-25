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
import android.view.WindowManager;

import java.io.Serializable;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.inventory.adapter.ActionListAdapter;
import quay.com.ipos.inventory.modal.ActionListModel;
import quay.com.ipos.listeners.ActionListClick;
import quay.com.ipos.utility.DividerItemDecoration;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by niraj.kumar on 6/23/2018.
 */

public class ActionListFragment extends DialogFragment implements ActionListClick {
    private static final String ARG_PARAM1 = "data";
    private RecyclerView rvList;
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
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        actionListAdapter = new ActionListAdapter(mContext, actionListModels, this);
        rvList.setAdapter(actionListAdapter);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void actionListClicked(int position) {
        ActionListModel actionListModel = actionListModels.get(position);
        ActionListener actionListener = (ActionListener) mContext;
        actionListener.onActionListClicked(actionListModel.actionID, actionListModel.actionTitle);
    }
}
