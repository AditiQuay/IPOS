package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.modal.BillingSync;
import quay.com.ipos.modal.FieldModel;
import quay.com.ipos.modal.Name;

public class NameAdapter extends BaseAdapter {

    //storing all the names in the list
    private ArrayList<FieldModel> names;

    //context object
    private Context context;

    //constructor 
    public NameAdapter(Context context, ArrayList<FieldModel> names) {
//        super(context, resource, names);
        this.context = context;
        this.names = names;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater 
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview itmes
        View listViewItem = inflater.inflate(R.layout.outbox_list_item, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        ImageView imageViewStatus = (ImageView) listViewItem.findViewById(R.id.imageViewStatus);

        //getting the current name 
        FieldModel name = names.get(position);

        //setting the name to textview
        textViewName.setText("Order No. : "+name.getFrontField());

        //if the synced status is 0 displaying 
        //queued icon 
        //else displaying synced icon 
//        if (name.getSync() == 0)
//            imageViewStatus.setBackgroundResource(R.drawable.ic_action_cloud_off_white);
//        else
//            imageViewStatus.setBackgroundResource(R.drawable.ic_action_check);

        return listViewItem;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}