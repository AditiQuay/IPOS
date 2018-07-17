package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.CommonModal;
import quay.com.ipos.pss_order.modal.NoGetEntityResultModal;

/**
 * Created by ankush.bansal on 04-06-2018.
 */

public class CustomAdapterAddress extends ArrayAdapter<CommonModal> {

    LayoutInflater flater;

    public CustomAdapterAddress(Context context, int resouceId, int textviewId, List<CommonModal> list){

        super(context,resouceId,textviewId, list);
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        CommonModal buisnessPlacesBean = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.spinner_item_pss_ipos, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.text1);

            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }

        if (buisnessPlacesBean!=null)
        holder.txtTitle.setText(buisnessPlacesBean.getAddress());

        return rowview;
    }

    private class viewHolder{
        TextView txtTitle;

    }
}