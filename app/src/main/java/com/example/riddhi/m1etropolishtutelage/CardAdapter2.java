package com.example.riddhi.m1etropolishtutelage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ADMIN on 23-12-2016.
 */
public class CardAdapter2 extends RecyclerView.Adapter<CardAdapter2.ViewHolder> {
    ArrayList<HashMap<String, String>> image = new ArrayList<HashMap<String, String>>();
    private Activity context;
    String username;

     String middlename ;
     String gender ;

     String contact ;
     String address;
    private static LayoutInflater inflater = null;

    public CardAdapter2(Activity context, ArrayList<HashMap<String, String>> image) {
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
        holder.textViewName.setText(image.get(position).get("middlename1"));





      //  holder.textViewUrl.setText(image.get(position).get("category_image"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dash22, parent, false);
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
       // public TextView textViewUrl;
        public LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);

          //  imageView = (ImageView) itemView.findViewById(R.id.imageView2);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            //textViewUrl = (TextView) itemView.findViewById(R.id.textViewUrl);
            ll = (LinearLayout) itemView.findViewById(R.id.ll);



            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positions=getAdapterPosition();
                    Toast.makeText(context,"u click"+positions, Toast.LENGTH_SHORT).show();
                    brif_detail AA = new brif_detail();

                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    brif_detail fragment = new brif_detail();
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.commit();
                    middlename= image.get(positions).get("middlename1");

                    address= image.get(positions).get("address1");
                    contact= image.get(positions).get("contact1");

                    gender= image.get(positions).get("gender");
                    String screen = image.get(positions).get("screen");

                    final Bundle bundle = new Bundle();
                    bundle.putString("email", middlename);
                    bundle.putString("city", address);
                    bundle.putString("contact", contact);
                    bundle.putString("address", gender);
                    bundle.putString("screen", screen);
                    Log.d("this issss",middlename+address+contact+gender);


                    fragment.setArguments(bundle);
                   return;
                }
            });

        }
    }

}
