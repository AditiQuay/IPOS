package quay.com.ipos.ddrsales.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.ddrsales.model.response.Address;



public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private Context mContext;
    private List<Address> list;
    private OnItemSelecteListener mListener;

    public AddressAdapter(Context mContext, List<Address> list, OnItemSelecteListener mListener) {
        this.mContext = mContext;
        this.list = list;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ddr_adapter_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Address address = list.get(position);

        holder.textAddress.setText(address.name);
        holder.radio.setChecked(address.isSelected);




    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemSelecteListener {
          void onItemSelected(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textAddress;

        private RadioButton radio;
        public ViewHolder(View itemView) {
            super(itemView);
            textAddress = itemView.findViewById(R.id.textAddress);
            radio=itemView.findViewById(R.id.radio);

        }
    }
}
