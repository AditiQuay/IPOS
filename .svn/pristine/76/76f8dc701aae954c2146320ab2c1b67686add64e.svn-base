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
import quay.com.ipos.inventory.fragment.ListDialogFragment;
import quay.com.ipos.inventory.modal.OthersTabList;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInAdapter.TransferOtherTabListAdapter;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInOtherTabList;
import quay.com.ipos.listeners.OthersTabListner;

/**
 * Created by niraj.kumar on 7/11/2018.
 */

public class TransferInOtherListDialogFragment extends DialogFragment implements OthersTabListner {
    private static final String ARG_PARAM1 = "data";
    private RecyclerView rvList;
    private Context mContext;
    private View main;
    private TransferOtherTabListAdapter transferOtherTabListAdapter;
    private List<TransferInOtherTabList> list;

    // 1. Defines the listener interface with a method passing back data result.
    public interface DialogListener {
        void onFinishListDialog(int tabId, String tabTitle);
    }

    public TransferInOtherListDialogFragment() {

    }

    public static TransferInOtherListDialogFragment newInstance(List<TransferInOtherTabList> list) {
        TransferInOtherListDialogFragment fragment = new TransferInOtherListDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = (List<TransferInOtherTabList>) getArguments().getSerializable(ARG_PARAM1);
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
        transferOtherTabListAdapter = new TransferOtherTabListAdapter(mContext, list, this);
        rvList.setAdapter(transferOtherTabListAdapter);
    }

    @Override
    public void otherTabListner(int position) {
        TransferInOtherTabList transferInOtherTabList = list.get(position);
        // Return input text back to activity through the implemented listener
        TransferInOtherListDialogFragment.DialogListener listener = (TransferInOtherListDialogFragment.DialogListener) getActivity();
        if (listener != null) {
            listener.onFinishListDialog(transferInOtherTabList.tabId, transferInOtherTabList.tabTitle);
            dismiss();
        }
    }
}
