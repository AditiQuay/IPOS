package quay.com.ipos.ddrsales.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.ddrsales.DDRApproveInvoiceActivity;
import quay.com.ipos.ddrsales.model.OrderModel;
import quay.com.ipos.utility.NumberFormatEditText;

public class POAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<OrderModel> list = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    private String rs;
    private OnSelectPOAdapterListener listener;

    public POAdapter(Context context, OnSelectPOAdapterListener listener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.rs = context.getString(R.string.Rs) + " ";
        this.listener = listener;
    }

    public interface OnSelectPOAdapterListener{
        void onSelectPO(int pos, OrderModel orderModel);
    }
    public void setList(List<OrderModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_posummary, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final OrderModel object = list.get(position);
        holder.textPName.setText(object.sellerName + "");
        holder.textDeliveryReqDate.setText(object.etaDate + "");
        holder.textRequestCode.setText(object.requestCode + "");
        holder.textModifiedDate.setText(object.modifiedDate + "");
        holder.textItemsCount.setText(object.itemQty + "");
        holder.textQty.setText(object.itemQty + "");
        holder.textOrderValue.setText(rs + NumberFormatEditText.getText(object.orderValue + ""));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onSelectPO(position, object);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return this.list.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    public TextView textDeliveryReqDate, textItemsCount, textQty, textRequestCode, textPName, textOrderValue, textModifiedDate;


    public ViewHolder(View itemView) {
        super(itemView);
        textDeliveryReqDate = itemView.findViewById(R.id.textDeliveryReqDate);
        textItemsCount = itemView.findViewById(R.id.textItemsCount);
        textQty = itemView.findViewById(R.id.textQty);
        textRequestCode = itemView.findViewById(R.id.textRequestCode);
        textPName = itemView.findViewById(R.id.textPName);
        textOrderValue = itemView.findViewById(R.id.textOrderValue);
        textModifiedDate = itemView.findViewById(R.id.textModifiedDate);
        textModifiedDate = itemView.findViewById(R.id.textModifiedDate);
    }




}
