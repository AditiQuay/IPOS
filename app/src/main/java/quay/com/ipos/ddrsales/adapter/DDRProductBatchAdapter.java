package quay.com.ipos.ddrsales.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.ddrsales.model.response.DDRBatch;
import quay.com.ipos.ddrsales.model.response.DDTProductBatch;

/**
 * Created by deepa.kumar on 6/25/2018.
 */

public class DDRProductBatchAdapter extends RecyclerView.Adapter<DDRProductBatchAdapter.ViewHolder> {
    private static final String TAG = DDRProductBatchAdapter.class.getSimpleName();
    private Context mContext;
    private List<DDTProductBatch> list;



    private boolean onBind;

    public DDRProductBatchAdapter(Context mContext, List<DDTProductBatch> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ddr_adapter_product_batch, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        this.onBind = true;
        final DDTProductBatch productBatch = list.get(position);
        holder.editItemName.setText(productBatch.itemName);

        holder.textItemCount.setText(productBatch.batchQty+"/"+productBatch.totalQty);
        holder.btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewBatch(position, productBatch,holder.recyclerViewBatch);
            }
        });

        holder.onBind(position, productBatch.batchList);




        this.onBind = false;
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView editItemName;
        private TextView textItemCount;
        private View btnAddItem;
        private List<DDRBatch> list = new ArrayList<>();

        private RecyclerView recyclerViewBatch;
        private DDRBatchSelectionAdapter adapterBatchSelection;




        public ViewHolder(View itemView) {
            super(itemView);
            editItemName = itemView.findViewById(R.id.editItemName);
            textItemCount = itemView.findViewById(R.id.textItemCount);
            btnAddItem = itemView.findViewById(R.id.btnAddItem);

            recyclerViewBatch = itemView.findViewById(R.id.recyclerViewBatch);


            recyclerViewBatch.setLayoutManager(new LinearLayoutManager(mContext));
            adapterBatchSelection = new DDRBatchSelectionAdapter(mContext, list, new DDRBatchSelectionAdapter.OnBatchDataChangeListener() {
                @Override
                public void onDataChange(List<DDRBatch> changeList) {
                  /*  list.clear();
                    list.addAll(changeList);*/
                }
            });
            recyclerViewBatch.setAdapter(adapterBatchSelection);


        }

        public void onBind(int position, List<DDRBatch> batchList) {
            list.clear();
            list.addAll(batchList);
            adapterBatchSelection.notifyItemChanged(position);
        }
    }


    private void addNewBatch(int position, DDTProductBatch productBatch, RecyclerView recyclerViewBatch) {
        if (productBatch.batchList == null) {
            productBatch.batchList = new ArrayList<>();
        }
        DDRBatch ddrBatch = new DDRBatch(0,"");
        productBatch.batchList.add(ddrBatch);
        DDRBatchSelectionAdapter batchAdapter = (DDRBatchSelectionAdapter) recyclerViewBatch.getAdapter();
        batchAdapter.setMainDataList(productBatch.batchList);
        notifyItemChanged(position);

       // recyclerViewBatch.getAdapter().notifyDataSetChanged();


    }




}
