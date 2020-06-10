package com.example.riddhi.m1etropolishtutelage;

/**
 * Created by RIDDHI on 26-02-17.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Riddhi on 18/09/16.
 */


public class settings extends Fragment {
    String password_new;
    String password;
    String email;
    Button change;
    EditText currunt;
    EditText new_password;
    EditText conform;
    Button submit;
    String email1;
    String password1;
    Button ok;
    private static final String TAG_MESSAGE = "message";
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "http://ridhitrivedi.16mb.com/changepassword.php";
    private static final String LOGIN_URL1 ="http://ridhitrivedi.16mb.com/metropolis/m-login.php";
    private static final String TAG_SUCCESS = "success";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.settings, container, false);
        change = (Button) v.findViewById(R.id.change_password);
        submit = (Button) v.findViewById(R.id.submit);
        ok = (Button) v.findViewById(R.id.ok);
        currunt = (EditText) v.findViewById(R.id.currunt_password);
        new_password = (EditText) v.findViewById(R.id.new_password);
        conform = (EditText) v.findViewById(R.id.conform_password);

        SharedPreferences pref = getActivity().getSharedPreferences("login", 0);
        email = pref.getString("email", null);
        password = pref.getString("password", null);


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currunt.setVisibility(View.VISIBLE);

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change.setVisibility(View.GONE);


                password1 = currunt.getText().toString();
                new RegisterUser2().execute();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_new = new_password.getText().toString();
                new RegisterUser1().execute();
            }
        });

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Setting");
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

                param.add(new BasicNameValuePair("email", email));
                param.add(new BasicNameValuePair("password", password_new));

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", param);
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());

                    Intent i = new Intent(getActivity(), Navgation_Drawer.class);
                    startActivity(i);

                    // i.putExtra("username",username);

                    //new UploadImage1().execute();
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

    class RegisterUser2 extends AsyncTask<String, Void, String> {
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

                param.add(new BasicNameValuePair("email", email));
                param.add(new BasicNameValuePair("password", password1));

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL1, "POST", param);
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new_password.setVisibility(View.VISIBLE);
                            conform.setVisibility(View.VISIBLE);
                            submit.setVisibility(View.VISIBLE);
                        }
                    });

                    //new UploadImage1().execute();
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