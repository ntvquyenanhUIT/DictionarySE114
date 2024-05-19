package com.example.dictionary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dictionary.DataBaseHelper;
import com.example.dictionary.ItemOnclick;
import com.example.dictionary.R;

import java.util.ArrayList;
import java.util.List;

public class AutoSuggestAdapter extends ArrayAdapter {
    private ItemOnclick itemOnclick;
    private Context context;
    private int resource;
    private List<String> items;
    private List<String> tempItems;
    private List<String> suggestions;

    private DataBaseHelper dataBaseHelper;

    public AutoSuggestAdapter(Context context, int resource, List<String> items, DataBaseHelper dataBaseHelper,ItemOnclick itemOnclick) {
        super(context, resource, 0, items);

        this.context = context;
        this.resource = resource;
        this.items = items;
        tempItems = new ArrayList<String>(items);
        suggestions = new ArrayList<String>();
        this.dataBaseHelper = dataBaseHelper;
        this.itemOnclick = itemOnclick;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);


        }

        String item = items.get(position);

        TextView tc = view.findViewById(R.id.drag_item);
        tc.setText(item);

        ImageView imageView = view.findViewById(R.id.right_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemOnclick.DeleteItem(item);
                notifyDataSetChanged();
            }
        });

        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemOnclick.ItemOnClicl(item);
            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = (String) resultValue;
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (String names : tempItems) {
                    if (names.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(names);
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
            List<String> filterList = (ArrayList<String>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (String item : filterList) {
                    add(item);
                    notifyDataSetChanged();
                }
            }
        }
    };

}