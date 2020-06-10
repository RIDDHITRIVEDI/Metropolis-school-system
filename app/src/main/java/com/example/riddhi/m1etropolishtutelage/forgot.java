package com.example.riddhi.m1etropolishtutelage;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class forgot extends AppCompatActivity {
    Button ok;
    EditText email;
    Button ok2;
    EditText email2;
    Button ok3;
    EditText email3;
    String email_st;
    String password;
    String code;
    private static final String REGISTER_URL = "http://ridhitrivedi.16mb.com/mailotp.php";
    private static final String REGISTER_URL1 = "http://ridhitrivedi.16mb.com/changepassword.php";
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ok = (Button) findViewById(R.id.ok);
        email = (EditText) findViewById(R.id.mail);

        ok2 = (Button) findViewById(R.id.ok2);
        email2 = (EditText) findViewById(R.id.mail2);

        ok3 = (Button) findViewById(R.id.ok3);
        email3 = (EditText) findViewById(R.id.mail3);

        ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = email2.getText().toString();
                new RegisterUser3().execute();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_st = email.getText().toString();
                new RegisterUser2().execute();
            }
        });
ok3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String mail=email3.getText().toString();

        if(code.equals(mail)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ok2.setVisibility(View.VISIBLE);
                    email2.setVisibility(View.VISIBLE);
                }
            });


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

    class RegisterUser2 extends AsyncTask<String, Void, String> {
        ProgressDialog loading_register;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading_register = ProgressDialog.show(forgot.this, "Please Wait", null, true, true);
        }


        @Override
        protected String doInBackground(String... params) {
            int success;
            BufferedReader bufferedReader = null;
            try {
                List<NameValuePair> param = new ArrayList<NameValuePair>();

                //  param.add(new BasicNameValuePair("", password));
                param.add(new BasicNameValuePair("username", email_st));

                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", param);
                String Success = json.getString("error_msg");


                // success = json.getInt(TAG_SUCCESS1);
                if (Success.equals("Success!")) {
                    code=json.getString("otp");


//                    sm.setLogin(true);
//                    Intent i = new Intent(Signup_Login.this, Navgation_Drawer.class);
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("type", 0); // 0 - for private mode
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("type", typename); // Storing string
//                    editor.commit();
//                    i.putExtra("username", username);
//                    startActivity(i);
//                    session.setLogin(true);
//                    finish();
                    Log.d("User Created!", json.toString());
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

            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }

    class RegisterUser3 extends AsyncTask<String, Void, String> {
        ProgressDialog loading_register;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading_register = ProgressDialog.show(forgot.this, "Please Wait", null, true, true);
        }


        @Override
        protected String doInBackground(String... params) {
            int success;
            BufferedReader bufferedReader = null;
            try {
                List<NameValuePair> param = new ArrayList<NameValuePair>();

                //  param.add(new BasicNameValuePair("", password));
                param.add(new BasicNameValuePair("email", email_st));
                param.add(new BasicNameValuePair("password", password));

                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL1, "POST", param);



                 success = json.getInt("success");
                if (success == 1) {


//                    sm.setLogin(true);
//                    Intent i = new Intent(Signup_Login.this, Navgation_Drawer.class);
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("type", 0); // 0 - for private mode
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("type", typename); // Storing string
//                    editor.commit();
//                    i.putExtra("username", username);
//                    startActivity(i);
//                    session.setLogin(true);
//                    finish();
                    Log.d("User Created!", json.toString());
                    return json.getString("message");
                } else {
                    Log.d("Login Failure!", json.getString("message"));
                    return json.getString("message");
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
    }
}
