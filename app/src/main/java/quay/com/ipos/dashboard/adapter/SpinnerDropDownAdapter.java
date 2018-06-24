package quay.com.ipos.dashboard.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import quay.com.ipos.R;

public class SpinnerDropDownAdapter extends BaseAdapter implements
        SpinnerAdapter {
    Context context;
    String[] name;
    boolean isBlack=false;

    public SpinnerDropDownAdapter(Context ctx,String[] name ) {
        context = ctx;
        this.name = name;
    }
    public void setColor(boolean isBlack){
        this.isBlack = isBlack;
    }
//
//    String[] name = { " One", " Two", " Three", " Four", " Five", " Six",
//            " Seven", " Eight" };
//    String[] value = { " 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8" };

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public String getItem(int pos) {
        // TODO Auto-generated method stub
        return name[pos];
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        TextView text1 = view.findViewById(R.id.text1);
        text1.setText(name[position]);
        if (!isBlack)
            text1.setTextColor(context.getResources().getColor(R.color.white));
        else
            text1.setTextColor(context.getResources().getColor(R.color.accent_color));
        parent.setPadding(0, 0, 0, 0);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        TextView text1 = view.findViewById(R.id.text1);
        text1.setText(name[position]);
        text1.setTextColor(context.getResources().getColor(R.color.black));
        return view;
    }
}