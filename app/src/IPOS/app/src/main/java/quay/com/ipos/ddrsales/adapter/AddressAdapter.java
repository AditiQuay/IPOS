package quay.com.ipos.ddrsales.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.ddrsales.model.response.Address;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private Context mContext;
    private List<Address> list;
    private OnItemSelectionListener mListener;
    private int addressType;

    public AddressAdapter(Context mContext, List<Address> list, OnItemSelectionListener mListener, int addressType) {
        this.mContext = mContext;
        this.list = list;
        if (list != null && list.size() > 0) {
            list.get(0).setSelected(true);
        }
        this.mListener = mListener;
        this.addressType = addressType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ddr_adapter_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final Address address = list.get(position);
        holder.textAddress.setText(address.name);
        holder.radio.setChecked(address.isSelected);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Address address1 :
                        list) {
                    address1.setSelected(false);
                }
                address.setSelected(true);
                notifyDataSetChanged();
                if (mListener != null) {
                    mListener.onItemSelected(position, addressType);
                }
            }
        });


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemSelectionListener {
          void onItemSelected(int position,int addressType);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textAddress;

        public   RadioButton radio;
        public ViewHolder(View itemView ) {
            super(itemView);
            textAddress = itemView.findViewById(R.id.textAddress);
            radio=itemView.findViewById(R.id.radio);
            radio.setFocusable(false);
            radio.setClickable(false);

        }
    }

}
