package quay.com.ipos.managebusiness.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.FieldModel;

public class SpinnerDropDownAdapter extends BaseAdapter implements
        SpinnerAdapter {
    Context context;
    ArrayList<FieldModel> name = new ArrayList<>();

    public SpinnerDropDownAdapter(Context ctx,ArrayList<FieldModel> name ) {
        context = ctx;
        this.name = name;
    }
//
//    String[] name = { " One", " Two", " Three", " Four", " Five", " Six",
//            " Seven", " Eight" };
//    String[] value = { " 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8" };

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public String getItem(int pos) {
        // TODO Auto-generated method stub
        return name.get(pos).getFrontField();
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
        text1.setText(name.get(position).getFrontField());
            text1.setTextColor(context.getResources().getColor(R.color.accent_color));
        parent.setPadding(0, 0, 0, 0);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        TextView text1 = view.findViewById(R.id.text1);
        text1.setText(name.get(position).getFrontField());
        text1.setTextColor(context.getResources().getColor(R.color.accent_color));
        return view;
    }
}