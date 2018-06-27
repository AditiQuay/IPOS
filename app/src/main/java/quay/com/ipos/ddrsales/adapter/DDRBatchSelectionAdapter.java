package quay.com.ipos.ddrsales.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.ddrsales.model.response.DDRBatch;

/**
 * Created by deepa.kumar on 6/25/2018.
 */

public class DDRBatchSelectionAdapter extends RecyclerView.Adapter<DDRBatchSelectionAdapter.ViewHolder> {
    private static final String TAG = DDRBatchSelectionAdapter.class.getSimpleName();
    private Context mContext;
    private List<DDRBatch> list = new ArrayList<>();
    private OnBatchDataChangeListener batchDataChangeListener;
    private boolean onBind;

    public DDRBatchSelectionAdapter(Context mContext, List<DDRBatch> list, OnBatchDataChangeListener batchDataChangeListener) {
        this.mContext = mContext;
        this.list = list;
        this.batchDataChangeListener = batchDataChangeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ddr_adapter_batch_selection, parent, false);
        return new ViewHolder(view, new DDRBatchSelectionAdapter.MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        this.onBind = true;
        final DDRBatch incoTerms = list.get(position);
        holder.tvSNo.setText(position+ 1+"");


        holder.myCustomEditTextListener.updatePosition(position, holder);
        holder.editItemCount.setText(list.get(holder.getAdapterPosition()).batchQty + "");
        holder.editBatchNumber.setText(list.get(holder.getAdapterPosition()).batchNumber + "");


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(incoTerms);
               // notifyItemRemoved(position);
                batchList.remove(incoTerms);
                notifyDataSetChanged();
                onDataChange();


            }
        });

        this.onBind = false;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    List<DDRBatch> batchList = new ArrayList<>();

    public void setMainDataList(List<DDRBatch> batchList) {
        this.batchList = batchList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSNo;
        private EditText editBatchNumber;
        private EditText editItemCount;
        private View btnDelete;


        public MyCustomEditTextListener myCustomEditTextListener;

        public ViewHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            tvSNo = itemView.findViewById(R.id.tvSNo);
            editBatchNumber = itemView.findViewById(R.id.editBatchNumber);
            editItemCount = itemView.findViewById(R.id.editItemCount);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            this.myCustomEditTextListener = myCustomEditTextListener;

            editBatchNumber.addTextChangedListener(this.myCustomEditTextListener);
            editItemCount.addTextChangedListener(this.myCustomEditTextListener);


        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private ViewHolder holder;

        public void updatePosition(int position, ViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            try {

                if (holder.editBatchNumber != null) {
                    if (holder.editBatchNumber.getText().hashCode() == charSequence.hashCode()) {
                        String editStr = charSequence.toString();
                        list.get(position).batchNumber = editStr;
                    }
                }
                if (holder.editItemCount != null) {
                    if (holder.editItemCount.getText().hashCode() == charSequence.hashCode()) {
                        String editStr = charSequence.toString();
                        if (editStr.isEmpty()) {
                            editStr = "0";
                        }
                        list.get(position).batchQty = Integer.parseInt(editStr);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            onDataChange();
            // no op

        }
    }


    private void onDataChange() {
        if (batchDataChangeListener != null) {
             batchDataChangeListener.onDataChange(list);
        }
    }

    public interface OnBatchDataChangeListener {
        void onDataChange(List<DDRBatch> list);
    }

}
