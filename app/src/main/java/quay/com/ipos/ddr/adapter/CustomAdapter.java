package quay.com.ipos.ddr.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.ddr.modal.NoGetEntityResultModal;

/**
 * Created by ankush.bansal on 04-06-2018.
 */

public class CustomAdapter extends ArrayAdapter<NoGetEntityResultModal.BuisnessPlacesBean> {

    LayoutInflater flater;

    public CustomAdapter(Context context, int resouceId, int textviewId, List<NoGetEntityResultModal.BuisnessPlacesBean> list){

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

        NoGetEntityResultModal.BuisnessPlacesBean buisnessPlacesBean = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.spinner_item_pss, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.text1);

            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }

        if (buisnessPlacesBean!=null)
        holder.txtTitle.setText(buisnessPlacesBean.getBuisnessPlaceName());

        return rowview;
    }

    private class viewHolder{
        TextView txtTitle;

    }
}