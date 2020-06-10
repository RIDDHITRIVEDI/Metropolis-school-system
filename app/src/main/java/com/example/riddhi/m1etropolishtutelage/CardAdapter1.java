package com.example.riddhi.m1etropolishtutelage;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ADMIN on 23-12-2016.
 */
public class CardAdapter1 extends RecyclerView.Adapter<CardAdapter1.ViewHolder> {
    ArrayList<HashMap<String, String>> image = new ArrayList<HashMap<String, String>>();
    private Activity context;
    private static LayoutInflater inflater = null;

    public CardAdapter1(Activity context, ArrayList<HashMap<String, String>> image) {
        super();

        this.context = context;

        //  this.desc = desc;
        this.image = image;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //holder.imageView.setImageBitmap(list.getImage());
        if(image.get(position).get("image").isEmpty()){

        }
        else
        {
            Picasso.with(context).load(image.get(position).get("image")).error(R.mipmap.ic_launcher).into(holder.imageView);
        }
      //  Picasso.with(context).load(image.get(position).get("category_image")).error(R.mipmap.ic_launcher).into(holder.imageView);
        holder.textViewName.setText(image.get(position).get("title"));
        holder.textViewName1.setText(image.get(position).get("type"));
        holder.textViewName2.setText(image.get(position).get("date"));
        holder.textViewName3.setText(image.get(position).get("discription"));
        //holder.textViewUrl.setText(image.get(position).get("category_image"));

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dash21, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewName1;
        public TextView textViewName2;
        public TextView textViewName3;

        //public TextView textViewUrl;
        public LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);

             imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewName1 = (TextView) itemView.findViewById(R.id.textViewName1);
            textViewName2 = (TextView) itemView.findViewById(R.id.textViewName2);
            textViewName3 = (TextView) itemView.findViewById(R.id.textViewName3);
            //  textViewUrl = (TextView) itemView.findViewById(R.id.textViewUrl);


            ll = (LinearLayout) itemView.findViewById(R.id.lin_dash21);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positions=getAdapterPosition();
                    Toast.makeText(context,"u click"+positions, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
