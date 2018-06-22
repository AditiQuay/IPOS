package quay.com.ipos.ddrsales.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.ddrsales.model.OrderModel;

public class POAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<OrderModel> list = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public POAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel object = list.get(position);
        holder.textPName.setText(object.sellerName+"");
        holder.textDeliveryReq.setText(object.etaDate+"");
        holder.textRequestCode.setText(object.requestCode+"");
        holder.textModifiedDate.setText(object.modifiedDate+"");
        holder.textItemsCount.setText(object.itemQty+"");
        holder.textQty.setText(object.itemQty+"");
        holder.textOrderValue.setText(object.orderValue+"");
    }


    @Override
    public int getItemCount() {
        return this.list.size();
    }
}

  class ViewHolder extends RecyclerView.ViewHolder {
    public TextView textDeliveryReq, textItemsCount,textQty, textRequestCode, textPName, textOrderValue, textModifiedDate;


    public ViewHolder(View itemView) {
        super(itemView);
        textDeliveryReq = itemView.findViewById(R.id.textDeliveryReq);
        textItemsCount = itemView.findViewById(R.id.textItemsCount);
        textQty = itemView.findViewById(R.id.textQty);
        textRequestCode = itemView.findViewById(R.id.textRequestCode);
        textPName = itemView.findViewById(R.id.textPName);
        textOrderValue = itemView.findViewById(R.id.textOrderValue);
        textModifiedDate = itemView.findViewById(R.id.textModifiedDate);
        textModifiedDate = itemView.findViewById(R.id.textModifiedDate);
    }
}
