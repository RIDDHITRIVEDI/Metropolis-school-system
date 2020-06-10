package com.example.riddhi.m1etropolishtutelage;

/**
 * Created by RIDDHI on 26-02-17.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class message extends Fragment {
EditText subject;
    Button submit;

    String from;
    String to;
    String subject_of_message;


    private static final String LOGIN_URL = "http://ridhitrivedi.16mb.com/message.php";
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";

    private static final String TAG_MESSAGE = "message";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
       View v= inflater.inflate(R.layout.message, container, false);
        subject = (EditText) v. findViewById(R.id.subject);
        submit = (Button) v. findViewById(R.id.submit);

        SharedPreferences pref =getActivity(). getSharedPreferences("rid", 0);
        from = pref.getString("email", null);
        //String title1 = pref.getString("username", null);
        SharedPreferences pref1 =getActivity(). getSharedPreferences("to", 0);
        to = pref1.getString("email", null);


        return v;


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Message");

submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        subject_of_message=subject.getText().toString();
       new  RegisterUser1().execute();

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

                param.add(new BasicNameValuePair("from", from));
                param.add(new BasicNameValuePair("subject", subject_of_message));
                param.add(new BasicNameValuePair("to",to));

                Log.d("isssssssssss",from+to+subject_of_message);

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", param);
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // final String password = c.getString("password");
                    Log.d("Message sent", json.toString());



                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (Exception e) {
                return null;
            }
        }
        /*RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);*/
    }
}