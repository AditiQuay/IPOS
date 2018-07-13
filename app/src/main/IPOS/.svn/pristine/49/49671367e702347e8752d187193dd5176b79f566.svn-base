package quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInAdapter;

import android.content.Context;
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
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.RealmTransferTabData;
import quay.com.ipos.listeners.BatchTabButtonClick;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.listeners.TabListenerr;

/**
 * Created by niraj.kumar on 7/11/2018.
 */

public class TransferInTabListAdapter extends RecyclerView.Adapter<TransferInTabListAdapter.MyView> {
    public Context mContext;
    public List<RealmTransferTabData> realmTransferTabData;
    MyListener listener;
    TabListenerr tabListenerr;
    private BatchTabButtonClick batchTabButtonClick;

    public TransferInTabListAdapter(Context mContext, List<RealmTransferTabData> realmTransferTabData, MyListener listener, TabListenerr tabListenerr, BatchTabButtonClick batchTabButtonClick) {
        this.mContext = mContext;
        this.realmTransferTabData = realmTransferTabData;
        this.listener = listener;
        this.tabListenerr = tabListenerr;
      /*  if (batchListData.size() > 0)
            batchListData.get(0).isSelected = true;*/

        this.batchTabButtonClick = batchTabButtonClick;
    }

    @NonNull
    @Override
    public TransferInTabListAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.batch_items, parent, false);
        return new TransferInTabListAdapter.MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransferInTabListAdapter.MyView holder, final int position) {
        final RealmTransferTabData batchLis = realmTransferTabData.get(position);
        String btnTitle = batchLis.getTabTitle();
        String count = batchLis.getCount() + "";
        String text = btnTitle + " " + "(" + count + ")";

        if (batchLis.getTabId() == 3) {
            holder.btnTab.setText(text);
            Drawable img = mContext.getResources().getDrawable(R.drawable.ic_down_arrow_white);
            holder.btnTab.setCompoundDrawables(null, null, img, null);
        } else {
            holder.btnTab.setText(text);
            holder.btnTab.setCompoundDrawables(null, null, null, null);
        }

        Log.e("selection", "" + batchLis.isSelected());

        holder.btnTab.setSelected(batchLis.isSelected());
        holder.btnTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (RealmTransferTabData realmTransferTabData : realmTransferTabData) {
                    realmTransferTabData.isSelected = false;

                }
                batchLis.isSelected = true;
                notifyDataSetChanged();
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
        return realmTransferTabData.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        private Button btnTab;

        public MyView(View itemView) {
            super(itemView);
            btnTab = itemView.findViewById(R.id.btnTab);

        }
    }
}
