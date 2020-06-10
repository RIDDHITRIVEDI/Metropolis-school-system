package com.example.riddhi.m1etropolishtutelage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by riddhi on 23-12-2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    ArrayList<HashMap<String, String>> image = new ArrayList<HashMap<String, String>>();
    private Activity context;


    private static LayoutInflater inflater = null;

    public CardAdapter(Activity context, ArrayList<HashMap<String, String>> image) {
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
        //  Picasso.with(context).load(image.get(position).get("category_image")).error(R.mipmap.ic_launcher).into(holder.imageView);
        holder.textViewName.setText(image.get(position).get("username"));
        holder.textViewName3.setText(image.get(position).get("address"));


        //  holder.textViewUrl.setText(image.get(position).get("category_image"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dash2, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // public ImageView imageView;
        public TextView textViewName;
        public TextView textViewName3;
        // public TextView textViewUrl;
        public LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);

            //  imageView = (ImageView) itemView.findViewById(R.id.imageView2);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewName3 = (TextView) itemView.findViewById(R.id.textViewName3);
            //textViewUrl = (TextView) itemView.findViewById(R.id.textViewUrl);
            ll = (LinearLayout) itemView.findViewById(R.id.lin_dash21);


            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positions = getAdapterPosition();
                    Toast.makeText(context, "u click" + positions, Toast.LENGTH_SHORT).show();
                    //  brif_detail AA = new brif_detail();


                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    brif_detail fragment = new brif_detail();
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.commit();

                    String email = image.get(positions).get("email");
                    String city = image.get(positions).get("city");

                    String contact1 = image.get(positions).get("contact");
                    String address = image.get(positions).get("address");
                    String screen = image.get(positions).get("screen");

                    final Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("city", city);
                    bundle.putString("contact", contact1);
                    bundle.putString("address", address);
                    bundle.putString("screen", screen);
                    Log.d("this issss", email + city + contact1 + address);


                    fragment.setArguments(bundle);
                    return;
                }
            });

        }
    }

}
