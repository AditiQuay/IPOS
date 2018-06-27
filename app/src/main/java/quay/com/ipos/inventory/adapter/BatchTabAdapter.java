package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.RealmInventoryTabData;
import quay.com.ipos.listeners.BatchTabButtonClick;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.listeners.TabListenerr;

/**
 * Created by niraj.kumar on 6/22/2018.
 */

public class BatchTabAdapter extends RecyclerView.Adapter<BatchTabAdapter.MyView> {
    public Context mContext;
    public List<RealmInventoryTabData> batchListData;
    MyListener listener;
    TabListenerr tabListenerr;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private BatchTabButtonClick batchTabButtonClick;

    public BatchTabAdapter(Context mContext, List<RealmInventoryTabData> batchListData, MyListener listener, TabListenerr tabListenerr, BatchTabButtonClick batchTabButtonClick) {
        this.mContext = mContext;
        this.batchListData = batchListData;
        this.listener = listener;
        this.tabListenerr = tabListenerr;
        this.batchListData = batchListData;
        this.batchTabButtonClick = batchTabButtonClick;
        mPref = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        mEditor = mPref.edit();
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.batch_items, parent, false);
        return new BatchTabAdapter.MyView(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyView holder, final int position) {
        final RealmInventoryTabData batchLis = batchListData.get(position);

        if (batchLis.getTabId() == 3) {
            holder.btnTab.setText(batchLis.getTabTitle());
            Drawable img = mContext.getResources().getDrawable(R.drawable.ic_down_arrow_white);
            holder.btnTab.setCompoundDrawables(null, null, img, null);
        } else {
            holder.btnTab.setText(batchLis.getTabTitle());
            holder.btnTab.setCompoundDrawables(null, null, null, null);
        }

        Log.e("selection", "" + batchLis.isSelected());

        if (batchLis.isSelected()) {
            holder.btnTab.setSelected(false);
        } else {
            holder.btnTab.setSelected(true);
        }

        holder.btnTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                batchTabButtonClick.onTabClick(position);
                if (batchLis.getTabId() == 3) {
                    listener.onRowClicked(position);
                } else {
                    tabListenerr.tabClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return batchListData.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        private Button btnTab;

        public MyView(View itemView) {
            super(itemView);
            btnTab = itemView.findViewById(R.id.btnTab);

        }
    }
}
