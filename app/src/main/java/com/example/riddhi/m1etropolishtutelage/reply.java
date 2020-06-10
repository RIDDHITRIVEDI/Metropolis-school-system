package com.example.riddhi.m1etropolishtutelage;

/**
 * Created by RIDDHI on 26-02-17.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class reply extends Fragment {
    TextView sub_reply;
    EditText reply;
    Button submit;

    String user_from;
    String user_to;
    String id;
    String subject;
    String re;
    private static final String LOGIN_URL = "http://ridhitrivedi.16mb.com/reply.php";
    JSONParser jsonParser = new JSONParser();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.reply, container, false);

        sub_reply = (TextView) v.findViewById(R.id.sub_reply);
        reply = (EditText) v.findViewById(R.id.edit_rply);
        submit = (Button) v.findViewById(R.id.btn_post);
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Menu 1");
        Bundle args = getArguments();
        user_from = args.getString("username");
        user_to = args.getString("user_to");
        id = args.getString("id");
        subject = args.getString("subject");

        sub_reply.setText(subject);
       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             re   = reply.getText().toString();

               new RegisterUser1().execute();

           }
       });
    }
    class RegisterUser1 extends AsyncTask<String, Void, String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
//            Intent i = new Intent(Login.this,Main3.class);
//            startActivity(i);
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {
            int success;
            BufferedReader bufferedReader = null;
            try {

                List<NameValuePair> param = new ArrayList<NameValuePair>();


                param.add(new BasicNameValuePair("id", id));
                param.add(new BasicNameValuePair("reply",re));

              //  Log.d("isssssssssss",from+to+subject_of_message);

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", param);
                success = json.getInt("success");
                if (success == 1) {
                    // final String password = c.getString("password");
                    Log.d("Message sent", json.toString());



                    return json.getString("message");
                } else {
                    Log.d("Login Failure!", json.getString("message"));
                    return json.getString("message");
                }
            } catch (Exception e) {
                return null;
            }
        }
        /*RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);*/
    }
}