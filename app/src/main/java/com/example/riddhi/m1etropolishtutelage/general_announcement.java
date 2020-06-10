package com.example.riddhi.m1etropolishtutelage;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

import cn.lightsky.infiniteindicator.InfiniteIndicator;

/**
 * Created by RIDDHI on 03-01-2016.
 */
public class general_announcement extends Fragment {

    ArrayList<HashMap<String, String>> usersList = new ArrayList<HashMap<String, String>>();
    // private static String url = "http://hemalleeway.16mb.com/imageget.php";
    private static String url = "http://ridhitrivedi.16mb.com/general_announcement.php";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private InfiniteIndicator mAnimLineIndicator;
    public general_announcement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.general_announcement,container,false);
        recyclerView = (RecyclerView)v. findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        new GetContacts3().execute();


        return  v;
    }


    private class GetContacts3 extends AsyncTask<String, Void, String> {
        private ProgressDialog pDialog1;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            usersList.clear();
            pDialog1 = new ProgressDialog(getActivity());
            pDialog1.setMessage("Please wait...");

            pDialog1.setCancelable(false);
            pDialog1.show();

        }

        @Override
        protected String doInBackground(String... params) {
            int success;
            BufferedReader bufferedReader = null;
            HttpHandler1 sh = new HttpHandler1();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e("viewimage", "Response from url: " + jsonStr);
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("xyz");
                Log.d("jsonis134", "iss" + contacts);


                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);


                    // String category_image = c.getString("image1");
                    // This Line is new for you.....
                    String text = c.getString("title");
                    String discription = c.getString("discription");


                    HashMap<String, String> contact = new HashMap<String, String>();

                    // contact.put("category_image", category_image);
                    // This Line is new for you.....
                    contact.put("title",text );
                    contact.put("discription", discription);


                    Log.d("hashmap", "iss" + contact);
                    usersList.add(contact);

                }
                Log.d("userllistis", "iss" + usersList);


            } catch (Exception e) {
                return null;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Dismiss the progress dialog

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (pDialog1.isShowing())
                        pDialog1.dismiss();

                    Log.d("ordertaking", "is>>>" + usersList);


                    CardAdapter_announcement ad = new CardAdapter_announcement(getActivity(), usersList);
                    recyclerView.setAdapter(ad);


                }
            });
        }
    }}