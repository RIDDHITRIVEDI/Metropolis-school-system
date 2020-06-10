package com.example.riddhi.m1etropolishtutelage;

/**
 * Created by RIDDHI on 26-02-17.
 */

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Your_mailFrom extends Fragment {
TextView message;
    TextView from;
String cityname;

    String me;
    ArrayList<HashMap<String, String>> usersList = new ArrayList<HashMap<String, String>>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    JSONParser jsonParser = new JSONParser();
    private static String REGISTER_URL1 = "http://ridhitrivedi.16mb.com/getmesfrom.php";
    private static final String TAG_SUCCESS1 = "success_upload";
    JSONArray ja = null;
    ListView l;
    ArrayList<String> ar = new ArrayList<>();
    String values;
    Globalvariable globalVariable;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
       View v= inflater.inflate(R.layout.your_mail, container, false);
        message = (TextView) v.findViewById(R.id.message);
        from = (TextView) v.findViewById(R.id.from);
        recyclerView = (RecyclerView)v. findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        SharedPreferences pref =getActivity(). getSharedPreferences("rid", 0);
        me = pref.getString("email", null);

        layoutManager = new LinearLayoutManager(getActivity());
        new RegisterUser().execute();
        recyclerView.setLayoutManager(layoutManager);
        globalVariable = (Globalvariable) getApplicationContext();
        return v;

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Mail");
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

                param.add(new BasicNameValuePair("me", me));

                //  param.add(new BasicNameValuePair("id", password));
                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL1, "POST", param);
                Log.d("mainactivity123", "isss" + param);
                success = json.getString("error");
                if (success.equals("false")) {
                    JSONArray contacts= json.getJSONArray("xyz");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);


                        final String subject = c.getString("subject");
                        final String username = c.getString("user_from");
                        final String to = c.getString("user_to");
                        final String id = c.getString("id");

String rply = c.getString("reply");

                        HashMap<String, String> contact = new HashMap<String, String>();

                        contact.put("username", username);
                        contact.put("user_to", to);
                        contact.put("id", id);
                        contact.put("subject", subject);
                        contact.put("replay", rply);
                        usersList.add(contact);

                        Log.d("jsonis134", "iss" + username);
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
            CardAdapterFrom ad = new CardAdapterFrom(getActivity(), usersList);
            recyclerView.setAdapter(ad);
            // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }
}