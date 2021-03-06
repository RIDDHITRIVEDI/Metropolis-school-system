package com.example.riddhi.m1etropolishtutelage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by riddhi on 23-12-2016.
 */
public class CardAdapterFrom extends RecyclerView.Adapter<CardAdapterFrom.ViewHolder> {
    ArrayList<HashMap<String, String>> image = new ArrayList<HashMap<String, String>>();
    private Activity context;


    private static LayoutInflater inflater = null;

    public CardAdapterFrom(Activity context, ArrayList<HashMap<String, String>> image) {
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
        holder.textViewName2.setText(image.get(position).get("subject"));


        //  holder.textViewUrl.setText(image.get(position).get("category_image"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashfrom, parent, false);
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
        public TextView textViewName2;
        // public TextView textViewUrl;
        public LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);

            //  imageView = (ImageView) itemView.findViewById(R.id.imageView2);
            textViewName = (TextView) itemView.findViewById(R.id.textViewNamefrom);
            textViewName2 = (TextView) itemView.findViewById(R.id.textViewName2from);
            //textViewUrl = (TextView) itemView.findViewById(R.id.textViewUrl);
            ll = (LinearLayout) itemView.findViewById(R.id.llfrom);


            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positions = getAdapterPosition();
                    Toast.makeText(context, "u click" + positions, Toast.LENGTH_SHORT).show();
                    //  brif_detail AA = new brif_detail();


                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    replyfrom fragment = new replyfrom();
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.commit();

                    String username = image.get(positions).get("username");
                    String id = image.get(positions).get("id");

                    String user_to = image.get(positions).get("user_to");
                    String subject = image.get(positions).get("subject");
                    String replay = image.get(positions).get("replay");
                    final Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putString("id", id);
                    bundle.putString("user_to", user_to);
                    bundle.putString("subject", subject);
                    bundle.putString("replay", replay);
                    //  Log.d("this issss",email+city+contact1+address);


                    fragment.setArguments(bundle);
                    return;
                }
            });

        }
    }

}
