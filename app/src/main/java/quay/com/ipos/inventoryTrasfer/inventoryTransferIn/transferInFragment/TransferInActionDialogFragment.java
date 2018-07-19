package quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInFragment;

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
import quay.com.ipos.inventory.fragment.ActionDialogFragment;
import quay.com.ipos.inventory.modal.ActionListModel;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInAdapter.TransferInActionListAdapter;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInActionList;
import quay.com.ipos.listeners.ActionListClick;

/**
 * Created by niraj.kumar on 7/11/2018.
 */

public class TransferInActionDialogFragment extends DialogFragment implements ActionListClick {
    private static final String ARG_PARAM1 = "data";
    private RecyclerView rvList;
    private Context mContext;
    private View main;
    private List<TransferInActionList> list;

    // 1. Defines the listener interface with a method passing back data result.
    public interface ActionListener {
        void onActionListClicked(int actionId, String actionTitle);
    }

    public TransferInActionDialogFragment() {

    }

    public static TransferInActionDialogFragment newInstance(List<TransferInActionList> list) {
        TransferInActionDialogFragment fragment = new TransferInActionDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = (List<TransferInActionList>) getArguments().getSerializable(ARG_PARAM1);
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
        TransferInActionListAdapter transferInActionListAdapter = new TransferInActionListAdapter(mContext, list, this);
        rvList.setAdapter(transferInActionListAdapter);
    }
    @Override
    public void actionListClicked(int position) {
        TransferInActionList actionListModel = list.get(position);
        TransferInActionDialogFragment.ActionListener actionListener = (TransferInActionDialogFragment.ActionListener) mContext;
        actionListener.onActionListClicked(actionListModel.actionID, actionListModel.actionTitle);
        dismiss();
    }
}
