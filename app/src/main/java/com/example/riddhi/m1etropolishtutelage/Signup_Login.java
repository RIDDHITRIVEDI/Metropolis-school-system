package com.example.riddhi.m1etropolishtutelage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Signup_Login extends AppCompatActivity {
    LoginButton loginButton;
    CallbackManager callbackManager;
    Uri imageuri;
    AccessTokenTracker accessTokenTracker;
    String username1, firstname, lastname;
    TextView tv_profile_name;
    ImageView iv_profile_pic;
    JSONParser jsonParser = new JSONParser();
    EditText etemail, etpassword;
    String password;
    String email;
    String username;
    private SessionManager session;
    SharedPreferences sharedpreferences;
    Spinner city;
    Spinner Type;
    ArrayList<String> city1 = new ArrayList<>();
    ArrayList<String> Type1 = new ArrayList<>();
    private static final String LOGIN_URL = "http://ridhitrivedi.16mb.com/metropolis/m-login.php";
    SessionManager sm;
    private static final String TAG_SUCCESS = "success";
    public static final String MyPREFERENCES = "username";
    private static final String TAG_MESSAGE = "message";

    String cityname;
    String typename;
    String conform;
    Button forgot;
    private boolean uMail;
    private boolean uPass;
    private boolean uname;
    private boolean uconform;

    private static final String TAG_SUCCESS1 = "success";
    private static final String TAG_MESSAGE1 = "message";
    private EditText etemails;
    private EditText etpasswords;
    private EditText etusername;
    private EditText etconforms;
    private TextView already_accnt_link;
    ImageView facebook;
    EditText contact;
    EditText address;
    EditText etconform_real;
    String contact1;
    String address1;
    private static final String REGISTER_URL = "http://ridhitrivedi.16mb.com/mailotp.php";
    private static final String REGISTER_URL2 = "http://ridhitrivedi.16mb.com/otp_file.php";
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button btnSignup;
    private Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.sign_login_new);
        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);
        sm = new SessionManager(Signup_Login.this);
        if (sm.isLoggedIn()) {

            Intent i = new Intent(Signup_Login.this, Navgation_Drawer.class);
            startActivity(i);
            finish();
        }

        btnSignup = (Button) findViewById(R.id.submitbuttuon);
        btnSignin = (Button) findViewById(R.id.buttonsubmit);
        forgot = (Button) findViewById(R.id.forgot);

        contact = (EditText) findViewById(R.id.etcontact);
        address = (EditText) findViewById(R.id.etadrress);


        etemail = (EditText) findViewById(R.id.etemail);
        etpassword = (EditText) findViewById(R.id.etpassword);

        city = (Spinner) findViewById(R.id.city);
        Type = (Spinner) findViewById(R.id.type);

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);

        etemails = (EditText) findViewById(R.id.etemailS);
        etpasswords = (EditText) findViewById(R.id.etpasswordS);
        etconforms = (EditText) findViewById(R.id.ettitle);
        etconform_real = (EditText) findViewById(R.id.etconformS);
        facebook = (ImageView) findViewById(R.id.img_facbook);
        test();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        session = new SessionManager(getApplicationContext());


        uMail = false;
        uPass = false;
        uname = false;
        uconform = false;
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String s1 = parent.getItemAtPosition(position).toString();*/
                cityname = city1.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup_Login.this, forgot.class);
                startActivity(i);
            }
        });

        Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String s1 = parent.getItemAtPosition(position).toString();*/
                typename = Type1.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        city1.add("kalol");
        city1.add("amedabad");
        city1.add("baroda");
        city1.add("amreli");
        final ArrayAdapter<String> dataAdpter = new ArrayAdapter<String>(Signup_Login.this, android.R.layout.simple_list_item_1, city1);
        city.setAdapter(dataAdpter);


        Type1.add("Municipality");
        Type1.add("NGO");
        Type1.add("School");

        final ArrayAdapter<String> dataAdpter1 = new ArrayAdapter<String>(Signup_Login.this, android.R.layout.simple_list_item_1, Type1);
        Type.setAdapter(dataAdpter1);


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loginuser();
            }
        });

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSigninForm();
            }
        });
        showSigninForm();


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
                btnSignup.startAnimation(clockwise);
                //registerUser();
                email = etemails.getText().toString();
                password = etpasswords.getText().toString();
                username = etconforms.getText().toString();
                conform = etconform_real.getText().toString();

                uMail = emailValidator();
                uPass = passValidator(password);
                uconform = conformvalidator(username);

                // uname = usernamevalidator(username);
                // uconform = conformvalidator(username);
                if (uMail && uPass && uconform) {
                    if (password.equals(conform)) {
                        registerUser();
                    } else {
                        Toast.makeText(Signup_Login.this, "Not Correct Password And Confirm Password", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    //finish();
                } else {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
    }

    public void Loginuser() {
        //name = editTextName.getText().toString().trim().toLowerCase();
        email = etemail.getText().toString().trim().toLowerCase();
        password = etpassword.getText().toString().trim().toLowerCase();
        //email = editTextEmail.getText().toString().trim().toLowerCase();
        new RegisterUser1().execute();
        /*register(name,username,password,email);*/
    }


    class RegisterUser1 extends AsyncTask<String, Void, String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(Signup_Login.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
//            Intent i = new Intent(Login.this,Main3.class);
//            startActivity(i);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {
            int success;
            BufferedReader bufferedReader = null;
            try {
                List<NameValuePair> param = new ArrayList<NameValuePair>();

                param.add(new BasicNameValuePair("email", email));
                param.add(new BasicNameValuePair("password", password));

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", param);
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                   // final String password = c.getString("password");
                    Log.d("User Created!", json.toString());
                    sm.setLogin(true);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("type", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("type", json.getString("type")); // Storing string
                    editor.commit();

                    SharedPreferences pref2 = getApplicationContext().getSharedPreferences("rid", 0); // 0 - for private mode
                    SharedPreferences.Editor editor2 = pref2.edit();
                    editor2.putString("username", json.getString("username"));
                    editor2.putString("email",email );
                    editor2.commit();


                    SharedPreferences pref1 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
                    SharedPreferences.Editor editor1 = pref1.edit();
                    editor1.putString("email", email);
                    editor1.putString("password", password); // for announcements
                    editor1.commit();


                    Intent i = new Intent(Signup_Login.this, Navgation_Drawer.class);
                    // i.putExtra("username",username);
                    startActivityForResult(i, 1);
                    session.setLogin(true);
                    finish();
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

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);
    }


    private boolean usernamevalidator(String s) {
        if (s.isEmpty()) {
            etusername.setError("Empty Email");
            return false;
        } else {
            Toast.makeText(Signup_Login.this, "OK Enroll", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private boolean conformvalidator(String s) {
        if (s.isEmpty()) {
            etconform_real.setError("Empty Password");
            Toast.makeText(Signup_Login.this, "not correct", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(Signup_Login.this, "correct", Toast.LENGTH_SHORT).show();

        }
        return true;


    }


    //validation of email id
    private boolean emailValidator() {
        String email1 = etemails.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email1.isEmpty()) {
            etemails.setError("Empty Email");
            return false;
        } else if (email1.matches(emailPattern)) {
            return true;
            // Log.e("ok", "ok mail");
            // Toast.makeText(sign_up.this, "OK Enroll", Toast.LENGTH_SHORT).show();
        } else {
            etemails.setError("invalid email");
            etemails.getText().clear();

        }

        return false;
    }


    //validation of password
    private boolean passValidator(String s) {
        if (s.isEmpty()) {
            etpasswords.setError("Empty Password");
            etpasswords.getText().clear();
            return false;
        } else if (etpasswords.length() < 4) {
            etpasswords.getText().clear();
            etpasswords.setError("Password too short");
            return false;
        } else {
            // Log.e("right", "password correct");
            return true;
        }
    }

    private void registerUser() {

        password = etpasswords.getText().toString().trim().toLowerCase();
        email = etemails.getText().toString().trim().toLowerCase();
        username = etconforms.getText().toString().trim().toLowerCase();
        contact1 = contact.getText().toString().trim().toLowerCase();
        address1 = address.getText().toString().trim().toLowerCase();
        new RegisterUser().execute(REGISTER_URL);

        /*register(name,username,password,email);*/
    }

    class RegisterUser extends AsyncTask<String, Void, String> {
        ProgressDialog loading_register;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading_register = ProgressDialog.show(Signup_Login.this, "Please Wait", null, true, true);
        }


        @Override
        protected String doInBackground(String... params) {
            int success;
            BufferedReader bufferedReader = null;
            try {
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                Log.d("xyz", email);

                //  param.add(new BasicNameValuePair("", password));
                param.add(new BasicNameValuePair("email", email));

                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL2, "POST", param);

                success = json.getInt(TAG_SUCCESS1);
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new RegisterUser2().execute();
                        }
                    });

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
    }

    class RegisterUser2 extends AsyncTask<String, Void, String> {
        ProgressDialog loading_register;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading_register = ProgressDialog.show(Signup_Login.this, "Please Wait", null, true, true);
        }


        @Override
        protected String doInBackground(String... params) {
            int success;
            BufferedReader bufferedReader = null;
            try {
                List<NameValuePair> param = new ArrayList<NameValuePair>();

                //  param.add(new BasicNameValuePair("", password));
                param.add(new BasicNameValuePair("username", email));

                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", param);
                String Success = json.getString("error_msg");


                // success = json.getInt(TAG_SUCCESS1);
                if (Success.equals("Success!")) {
                    Intent i = new Intent(Signup_Login.this, otp.class);
                    i.putExtra("code", json.getString("otp"));
                    i.putExtra("username", username);
                    i.putExtra("password", password);
                    i.putExtra("type", typename);
                    i.putExtra("city", cityname);
                    i.putExtra("contact", contact1);
                    i.putExtra("address", address1);
                    i.putExtra("email", email);


                    Log.d("xyz1", username + password + email + typename + cityname + contact1 + address1);

                    startActivity(i);
//                   sm.setLogin(true);
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

    public void test() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {


                                try {
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday");
                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    // tv_profile_name.setText(name);


                                    String imageurl = "https://graph.facebook.com/" + id + "/picture?type=large";

                                    //  Picasso.with(Signup_Login.this).load(imageurl).into(iv_profile_pic);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();


/**
 * AccessTokenTracker to manage logout
 */
                accessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                               AccessToken currentAccessToken) {
                        if (currentAccessToken == null) {

                        }
                    }
                };
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
