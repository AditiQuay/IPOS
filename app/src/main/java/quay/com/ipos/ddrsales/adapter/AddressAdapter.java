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
    private OnItemSelecteListener mListener;


    private  RadioButton lastChecked = null;
    private  int lastCheckedPos = 0;
    private int addressType;


    public AddressAdapter(Context mContext, List<Address> list, OnItemSelecteListener mListener,int addressType) {
        this.mContext = mContext;
        this.list = list;
        this.mListener = mListener;
        this.addressType = addressType;
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
       /* holder.radio.setChecked(address.isSelected);
        holder.radio.setChecked(list.get(holder.getAdapterPosition()).isSelected);
*/       holder.radio.setChecked(list.get(position).isSelected);
        holder.radio.setTag(new Integer(position));

        //for default check in first item
        if(position == 0 && list.get(0).isSelected && holder.radio.isChecked())
        {
            lastChecked = holder.radio;
            lastCheckedPos = 0;
        }

        holder.radio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RadioButton  cb = (RadioButton) v;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                        list.get(lastCheckedPos).setSelected(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;

                list.get(clickedPos).setSelected(cb.isSelected());
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemSelecteListener {
          void onItemSelected(View v, int position,int addressType);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textAddress;

        public   RadioButton radio;
        public ViewHolder(View itemView ) {
            super(itemView);
            textAddress = itemView.findViewById(R.id.textAddress);
            radio=itemView.findViewById(R.id.radio);


        }
    }

}
