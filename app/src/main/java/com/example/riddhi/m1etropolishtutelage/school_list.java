package com.example.riddhi.m1etropolishtutelage;

/**
 * Created by RIDDHI on 26-02-17.
 */

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class school_list extends Fragment {
    Spinner city;
    String cityname;
    TextView t1;
    TextView t2;
    ArrayList<HashMap<String, String>> usersList = new ArrayList<HashMap<String, String>>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    JSONParser jsonParser = new JSONParser();
    private static String REGISTER_URL1 = "http://ridhitrivedi.16mb.com/schools.php";
    private static final String TAG_SUCCESS1 = "success_upload";
    JSONArray ja = null;
    ListView l;
    ArrayList<String> ar = new ArrayList<>();
    String values;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.school_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        city = (Spinner) v.findViewById(R.id.city);

//        t1=(TextView) v.  findViewById(R.id.textView7);
        ar.add("Kalol");
        ar.add("Baroda");
        ar.add("Amedabad");

        final ArrayAdapter<String> dataAdpter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ar);
        city.setAdapter(dataAdpter1);


        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String s1 = parent.getItemAtPosition(position).toString();*/
                cityname = ar.get(position);
                new RegisterUser().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("School");
    }


    class RegisterUser extends AsyncTask<String, Void, String> {
        ProgressDialog loading_register;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            usersList.clear();
            loading_register = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
        }


        @Override
        protected String doInBackground(String... params) {
            String success;
            BufferedReader bufferedReader = null;
            try {
                List<NameValuePair> param = new ArrayList<NameValuePair>();

                param.add(new BasicNameValuePair("cityname", cityname));

                //  param.add(new BasicNameValuePair("id", password));
                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL1, "POST", param);
                Log.d("mainactivity123", "isss" + param);
                success = json.getString("error");
                if (success.equals("false")) {
                    JSONArray contacts = json.getJSONArray("xyz");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        final String username = c.getString("username");
                        final String email = c.getString("email");
                        final String city = c.getString("city");
                        final String contact1 = c.getString("contact");
                        final String address = c.getString("address");

                        HashMap<String, String> contact = new HashMap<String, String>();

                        contact.put("email", email);
                        contact.put("city", city);
                        contact.put("contact", contact1);
                        contact.put("address", address);
                        contact.put("username", username);

                        contact.put("screen", "school");
                        usersList.add(contact);

                        Log.d("jsonis134", "iss" + contacts);
                        // Log.d("User Created!", "isss" + username );

                    }

                    return json.getString("error");
                } else {
                    Log.d("Login Failure!", json.getString("error"));
                    return json.getString("error");
                }
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading_register.dismiss();
            CardAdapter ad = new CardAdapter(getActivity(), usersList);
            recyclerView.setAdapter(ad);
            // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }
}