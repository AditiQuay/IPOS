package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.modal.FieldModel;

public class AutocompleteCustomArrayAdapter extends ArrayAdapter<String> {
 
    final String TAG = "AutocompleteCustomArrayAdapter.java";
         
    Context mContext;
    int layoutResourceId;
    List<String> mDataset;
    private ListFilter listFilter = new ListFilter();
    private List<String> dataListAllItems;
    public AutocompleteCustomArrayAdapter(Context mContext, int layoutResourceId, List<String> data) {
 
        super(mContext, layoutResourceId,data);
         
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mDataset = data;
    }
 
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
         
        try{
             
            /*
             * The convertView argument is essentially a "ScrapView" as described is Lucas post 
             * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
             * It will have a non-null value when ListView is asking you recycle the row layout. 
             * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
             */
            if(convertView==null){
                // inflate the layout
                LayoutInflater inflater = ((MainActivity) mContext).getLayoutInflater();
                convertView = inflater.inflate(layoutResourceId, parent, false);
            }
             
            // object item based on the position
            String objectItem = mDataset.get(position);
 
            // get the TextView and then set the text (item name) and tag (item ID) values
            TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewName);
            textViewItem.setText(objectItem);
             
            // in case you want to add some style, you can do something like:
            textViewItem.setBackgroundColor(Color.CYAN);
             
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return convertView;
         
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<String>(mDataset);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<String> matchValues = new ArrayList<String>();

                for (String dataItem : dataListAllItems) {
                    if (dataItem.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
//                mDataset.clear();
                mDataset = (ArrayList<String>)results.values;
            } else {
                mDataset = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}