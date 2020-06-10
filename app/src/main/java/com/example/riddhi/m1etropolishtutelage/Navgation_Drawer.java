package com.example.riddhi.m1etropolishtutelage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.Page;


public class Navgation_Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    JSONParser jsonParser = new JSONParser();
    JSONArray ja = null;
    private SessionManager session;
    TextView title;
    TextView email;
    String email1;
    String email2;

    String email_send;
    private static final String URL_REGISTER_DEVICE = "http://ridhitrivedi.16mb.com/notification/RegisterDevice.php";
    Globalvariable globalVariable;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navgation__drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendTokenToServer();

//        title = (TextView) findViewById(R.id.title);
//        email = (TextView) findViewById(R.id.email);
        globalVariable = (Globalvariable) getApplicationContext();
        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView1.getHeaderView(0);
        title = (TextView) header.findViewById(R.id.title5);
        email = (TextView) header.findViewById(R.id.email5);

        SharedPreferences pref = getSharedPreferences("rid", 0);
        email1 = pref.getString("email", null);
        String title1 = pref.getString("username", null);

        title.setText(title1);
        email.setText(email1);
        globalVariable.setName("else");
//        SharedPreferences pref2 = getSharedPreferences("rid2", 0);
//        email2 = pref2.getString("email", null);
//        String title2 = pref.getString("username", null);
//
//        title.setText(title2);
//        email.setText(email2);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //add this line to display municipality when the activity is loaded
        displaySelectedScreen(R.id.viewpager1);
        session = new SessionManager(getApplicationContext());
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (globalVariable.getName().equals("home")) {
            Intent i = new Intent(Navgation_Drawer.this, Navgation_Drawer.class);
            startActivity(i);
            finish();
        } else if (globalVariable.getName().equals("muncipality")) {
            Fragment fragment = new municipality_list();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        } else if (globalVariable.getName().equals("school")) {
            Fragment fragment = new school_list();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        } else if (globalVariable.getName().equals("ngo")) {
            Fragment fragment = new NGOs();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        } else if (globalVariable.getName().equals("remainstudent")) {
            Fragment fragment = new remaining_student();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        } else if (globalVariable.getName().equals("else")) {   //super.onBackPressed();
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to close this Application?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_municipality:
                fragment = new municipality_list();
                break;
            case R.id.nav_settings:
                fragment = new settings();
                break;

            case R.id.brif:
                fragment = new brif_detail();
                break;
            case R.id.nav_NGOs:
                fragment = new NGOs();
                break;
//                case R.id.nav_proportions:
//                    fragment = new announcements();
//                    break;
            case R.id.nav_schools:
                fragment = new school_list();
                break;
            case R.id.nav_contact_admin:
                fragment = new contact_admin();
                break;
            case R.id.nav_FAQs:
                fragment = new FAQs();
                break;
            case R.id.nav_student_details:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("type", 0);
                String type = pref.getString("type", null);
                if (type.equals("Municipality")) {
                    fragment = new municipality();

                } else {
                    fragment = new student_details();
                }
                break;
            case R.id.nav_announcements:
                fragment = new announcements();
                break;
            case R.id.nav_remain_student:
                fragment = new remaining_student();
                break;
            case R.id.nav_surveys:
                fragment = new com.example.riddhi.m1etropolishtutelage.Map();
                break;
//                case R.id.nav_scholership_details:
//                    fragment = new scholership_details();
//                    break;
            case R.id.viewpager1:
                fragment = new AnimIndicatorActivity();
                break;
            case R.id.nav_admission:
                fragment = new Admission_process();
                break;
            case R.id.nav_your_mail:
                fragment = new Tabhost_mail();
                break;


            case R.id.nav_logout:
                //fragment = new Admission_process();
                Intent i = new Intent(Navgation_Drawer.this, Signup_Login.class);
                // i.putExtra("username",username);
                startActivityForResult(i, 1);
                session.setLogin(false);
                finish();
                break;

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    private void sendTokenToServer() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        // email_send = email.getText().toString();


        if (token == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(Navgation_Drawer.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Navgation_Drawer.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email1);
                params.put("token", token);
                Log.d("this isss", email1 + token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

//    @Override
//    public void onClick(View view) {
//        if (view == buttonRegister) {
//            sendTokenToServer();
//        }
//        if(view == buttonSendPush){
//            startActivity(new Intent(this, Main2Activity.class));
//        }
//    }


}


