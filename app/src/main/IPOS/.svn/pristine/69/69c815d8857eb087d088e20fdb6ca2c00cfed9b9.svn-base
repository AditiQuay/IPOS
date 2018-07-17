package quay.com.ipos.dayClosure.dayClosureAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.dayClosure.dayClosureModel.BusinessPlace;
import quay.com.ipos.dayClosure.dayClosureModel.BusinessPlace;

/**
 * Created by niraj.kumar on 7/11/2018.
 */

public class BusinessPlaceAdapter extends ArrayAdapter<BusinessPlace> {

    Context context;
    int resource, textViewResourceId;
    List<BusinessPlace> items, tempItems, suggestions;

    public BusinessPlaceAdapter(Context context, int resource, int textViewResourceId, List<BusinessPlace> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<BusinessPlace>(items); // this makes the difference.
        suggestions = new ArrayList<BusinessPlace>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_category, parent, false);
        }
        BusinessPlace BusinessPlace = items.get(position);
        if (BusinessPlace != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(BusinessPlace.getName());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((BusinessPlace) resultValue).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (BusinessPlace people : tempItems) {
                    if (people.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<BusinessPlace> filterList = (ArrayList<BusinessPlace>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (BusinessPlace people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
