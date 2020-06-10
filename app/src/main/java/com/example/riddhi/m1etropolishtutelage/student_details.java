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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class student_details extends Fragment {
    EditText unique_id1;
    String randomnum, success;
    ArrayList<String> ar = new ArrayList<>();

    LinearLayout linear1, linearLayout2;
    Button submit1, ok, cancel;
    TextView firstname, lastname, mothersname, gender, address, contact, middlename, randomstring;
    ImageView image;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS1 = "success_upload";
    private static String REGISTER_URL = "http://ridhitrivedi.16mb.com/get_child_info.php";
    private static String REGISTER_URL1 = "http://ridhitrivedi.16mb.com/updateadmission.php";
    JSONArray ja = null;
    String randomString_st;
    Globalvariable globalVariable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        View v = inflater.inflate(R.layout.get_child_info, container, false);

        globalVariable = (Globalvariable) getApplicationContext();
        globalVariable.setName("home");
        submit1 = (Button) v.findViewById(R.id.submit);
        linear1 = (LinearLayout) v.findViewById(R.id.linear1_getchild);

        linearLayout2 = (LinearLayout) v.findViewById(R.id.linear2_getchild);
        unique_id1 = (EditText) v.findViewById(R.id.unique_id);
        firstname = (TextView) v.findViewById(R.id.firstname);
        lastname = (TextView) v.findViewById(R.id.lastname);
        mothersname = (TextView) v.findViewById(R.id.mothers_name);
        gender = (TextView) v.findViewById(R.id.gender);
        address = (TextView) v.findViewById(R.id.address);
        contact = (TextView) v.findViewById(R.id.contact);
        middlename = (TextView) v.findViewById(R.id.middlename);
        randomstring = (TextView) v.findViewById(R.id.randomstring);
        image = (ImageView) v.findViewById(R.id.imageView);
        ok = (Button) v.findViewById(R.id.ok);
        cancel = (Button) v.findViewById(R.id.cancel);
        linearLayout2.setVisibility(View.GONE);
        SharedPreferences pref = getActivity().getSharedPreferences("type", 0);
        String type = pref.getString("type", null);
        if (type.equals("NGO")) {
            ok.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        }
        //change R.layout.yourlayoutfilename for each of your fragments
        return v;//inflater.inflate(R.layout.get_child_info, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Upload");
        ok.setVisibility(View.GONE);
        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomnum = unique_id1.getText().toString();
                new RegisterUser().execute();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegisterUser1().execute();
            }
        });

    }


    class RegisterUser extends AsyncTask<String, Void, String> {
        ProgressDialog loading_register;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading_register = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
        }


        @Override
        protected String doInBackground(String... params) {
            String success;
            BufferedReader bufferedReader = null;
            try {
                List<NameValuePair> param = new ArrayList<NameValuePair>();

                param.add(new BasicNameValuePair("randomnum", randomnum));

                //  param.add(new BasicNameValuePair("id", password));
                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", param);
                Log.d("mainactivity123", "isss" + param);
                success = json.getString("error");
                if (success.equals("false")) {


                    ja = json.getJSONArray("xyz");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject c = ja.getJSONObject(i);
                        final String firstname_st = c.getString("firstname1");
                        final String lastname1_st = c.getString("lastname1");
                        final String middlename1_st = c.getString("middlename1");
                        final String mothersname1_st = c.getString("mothersname1");
                        final String address1_st = c.getString("address1");
                        final String contact1_st = c.getString("contact1");
                        final String image_st = c.getString("image");
                        final String gender_st = c.getString("gender");
                        randomString_st = c.getString("randomString");
                        // Log.d("User Created!", "isss" + firstname + ">>>>" + lastname1 + middlename1 + mothersname1 + address1 + contact1 + image + gender + randomString);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ok.setVisibility(View.VISIBLE);
                                linear1.setVisibility(View.GONE);
                                linearLayout2.setVisibility(View.VISIBLE);
                                firstname.setText(firstname_st);
                                lastname.setText(lastname1_st);
                                middlename.setText(middlename1_st);
                                contact.setText(contact1_st);
                                mothersname.setText(mothersname1_st);
                                gender.setText(gender_st);
                                address.setText(address1_st);
                                randomstring.setText(randomString_st);
                                Picasso.with(getActivity()).load(image_st).error(R.drawable.ic_user).into(image);
                                SharedPreferences pref = getActivity().getSharedPreferences("type", 0);
                                String type = pref.getString("type", null);
                                if (type.equals("NGO")) {
                                    ok.setVisibility(View.GONE);
                                    cancel.setVisibility(View.GONE);
                                }
                            }
                        });
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

            // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }

    class RegisterUser1 extends AsyncTask<String, Void, String> {
        ProgressDialog loading_register;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // loading_register = ProgressDialog.show(Signup_Login.this, "Please Wait", null, true, true);
        }


        @Override
        protected String doInBackground(String... params) {
            int success;
            BufferedReader bufferedReader = null;
            try {
                List<NameValuePair> param = new ArrayList<NameValuePair>();

                param.add(new BasicNameValuePair("id", randomString_st));

                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL1, "POST", param);

                success = json.getInt(TAG_SUCCESS1);
                if (success == 1) {
//                    Intent i = new Intent(Signup_Login.this, Navgation_Drawer.class);
//                    i.putExtra("username", username);
//                    startActivity(i);
//                    session.setLogin(true);
//                    finish();
//                    Log.d("User Created!", json.toString());
                    return json.getString("error_message");
                } else {
                    // Log.d("Login Failure!", json.getString(TAG_MESSAGE1));
                    return json.getString("error_message");
                }
            } catch (Exception e) {
                return null;
            }
        }
    }
}