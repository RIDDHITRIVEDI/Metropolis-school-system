package com.example.riddhi.m1etropolishtutelage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RIDDHI on 10-09-2016.
 */
public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    ArrayList<Integer> img = new ArrayList();
   // private final Integer[] imageId;
    public CustomList(Activity context,
                      String[] web, ArrayList<Integer> img) {
        super(context, R.layout.layout, web);
        this.context = context;
        this.web = web;
        this.img = img;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.layout, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView2);
        txtTitle.setText(web[position]);

        imageView.setImageResource(img.get(position));
        return rowView;
    }
}
