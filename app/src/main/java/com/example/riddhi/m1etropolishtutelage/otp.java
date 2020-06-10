package com.example.riddhi.m1etropolishtutelage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class otp extends AppCompatActivity {
    Button Submit;
    EditText otp;
    SessionManager sm;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS1 = "success";
    private static final String TAG_MESSAGE1 = "message";

    private static final String REGISTER_URL = "http://ridhitrivedi.16mb.com/metropolis/m-signup.php";
    String username;
    String password;
    String email;
    String typename;
    String cityname;
    String contact1;
    String address1;
    String code;
    String otp1;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Submit = (Button) findViewById(R.id.button);
        otp = (EditText) findViewById(R.id.et_OTP);
        sm = new SessionManager(otp.this);
        username=getIntent().getStringExtra("username");
        password=getIntent().getStringExtra("password");
        email=getIntent().getStringExtra("email");
        typename=getIntent().getStringExtra("type");
        cityname=getIntent().getStringExtra("city");
        contact1=getIntent().getStringExtra("contact");
        address1=getIntent().getStringExtra("address");
        code=getIntent().getStringExtra("code");
Log.d("xyz1",username+password+email+typename+cityname+contact1+address1+code);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            otp1=otp.getText().toString();
                if(otp1.equals(code)){
                    new RegisterUser().execute();


                }

            }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    class RegisterUser extends AsyncTask<String, Void, String> {
        ProgressDialog loading_register;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading_register = ProgressDialog.show(otp.this, "Please Wait", null, true, true);
        }


        @Override
        protected String doInBackground(String... params) {
            int success;
            BufferedReader bufferedReader = null;
            try {
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                param.add(new BasicNameValuePair("username", username));
                param.add(new BasicNameValuePair("password", password));
                //  param.add(new BasicNameValuePair("", password));
                param.add(new BasicNameValuePair("email", email));
                param.add(new BasicNameValuePair("type", typename));
                param.add(new BasicNameValuePair("city", cityname));
                param.add(new BasicNameValuePair("contact", contact1));
               param.add(new BasicNameValuePair("address", address1));


                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", param);

                success = json.getInt(TAG_SUCCESS1);
                if (success == 1) {
                    sm.setLogin(true);
                    Intent i = new Intent(otp.this, Navgation_Drawer.class);

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("type", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("type", typename); // Storing string
                    editor.commit();

                    SharedPreferences pref2 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
                    SharedPreferences.Editor editor2 = pref2.edit();
                    editor2.putString("email", email);
                    editor2.putString("password", password); // Storing string
                    editor2.commit();

                    SharedPreferences pref1 = getApplicationContext().getSharedPreferences("rid", 0); // 0 - for private mode
                    SharedPreferences.Editor editor1 = pref1.edit();
                    editor1.putString("username", username);
                    editor1.putString("email", email);// Storing string
                    editor1.commit();

                    i.putExtra("username", username);
                    startActivity(i);
                    session.setLogin(true);
                    finish();
                    Log.d("User Created!", json.toString());
                    return json.getString(TAG_MESSAGE1);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE1));
                    return json.getString(TAG_MESSAGE1);
                }
            } catch (Exception e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading_register.dismiss();

            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }

}}
