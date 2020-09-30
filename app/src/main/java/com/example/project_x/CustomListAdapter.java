package com.example.project_x;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Olimp> olimpItems;

    public CustomListAdapter(Activity activity, List<Olimp> olimpItems) {
        this.activity = activity;
        this.olimpItems = olimpItems;
    }

    @Override
    public int getCount() {
        return olimpItems.size();
    }

    @Override
    public Olimp getItem(int location) {
        return olimpItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            try {
                convertView = inflater.inflate(R.layout.list_row, null);
            }catch (NullPointerException e){
                Log.i("Exception","True");
            }

        }
        TextView title = convertView.findViewById(R.id.title);
        TextView rating = convertView.findViewById(R.id.rating);
        TextView description = convertView.findViewById(R.id.description);
        // getting olimp data for the row
        Olimp m = olimpItems.get(position);
        // title
        title.setText(m.getTitle());

        // rating
        rating.setText(String.valueOf(m.getRating()));

        //description
        description.setText(m.getDescription());

        return convertView;
    }

}