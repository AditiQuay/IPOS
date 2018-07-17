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
import quay.com.ipos.inventory.adapter.OthersListAdapter;
import quay.com.ipos.inventory.modal.OthersTabList;
import quay.com.ipos.listeners.OthersTabListner;
import quay.com.ipos.utility.DividerItemDecoration;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by niraj.kumar on 6/23/2018.
 */

public class ListDialogFragment extends DialogFragment implements OthersTabListner {
    private static final String ARG_PARAM1 = "data";
    private RecyclerView rvList;
    private Context mContext;
    private View main;
    private OthersListAdapter othersListAdapter;
    private List<OthersTabList> list;

    // 1. Defines the listener interface with a method passing back data result.
    public interface DialogListener {
        void onFinishListDialog(int tabId, String tabTitle);
    }

    public ListDialogFragment() {

    }

    public static ListDialogFragment newInstance(List<OthersTabList> list) {
        ListDialogFragment fragment = new ListDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = (List<OthersTabList>) getArguments().getSerializable(ARG_PARAM1);
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
        othersListAdapter = new OthersListAdapter(mContext, list, this);
        rvList.setAdapter(othersListAdapter);
    }

    @Override
    public void otherTabListner(int position) {
        OthersTabList othersTabList = list.get(position);
        // Return input text back to activity through the implemented listener
        DialogListener listener = (DialogListener) getActivity();
        if (listener != null) {
            listener.onFinishListDialog(othersTabList.tabId, othersTabList.tabTitle);
            dismiss();
        }
    }
}
