package com.example.riddhi.m1etropolishtutelage;

/**
 * Created by RIDDHI on 26-02-17.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;

import android.view.Menu;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;

public class upload_announcement extends Fragment {
    private DatePicker datePicker;
    Button dob;
    TextView dob2;
    EditText title;
    EditText discription;
    Spinner type;
    Button submit;
    ImageView photo;
    String image;
   private EditText editTextImage;
    JSONParser jsonParser = new JSONParser();
    private static final String REGISTER_URL = "http://ridhitrivedi.16mb.com/announcement.php";

    private Button buttonUpload, buttonView;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private ImageView imageView;
    private Bitmap bitmap;
    private ProgressDialog progressDialog;
    private boolean isSendAllChecked;
    private List<String> devices;

    private RadioGroup radioGroup;
    private Spinner spinner;

    public static final String UPLOAD_KEY = "image";
    private HTTPURLConnection service;

    ArrayList<String> arrayList = new ArrayList<>();

    String title1;
    String discription1;
    String type1;
    String dob1;


    private static final String TAG_SUCCESS1 = "success";
    private static final String TAG_MESSAGE1 = "message";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.upload_announcement, container, false);
        dob = (Button) v.findViewById(R.id.dob);
        dob2 = (TextView) v.findViewById(R.id.dob2);
        type = (Spinner) v.findViewById(R.id.type_announcement);
        title = (EditText) v.findViewById(R.id.title);
        submit = (Button) v.findViewById(R.id.submit);
        discription = (EditText) v.findViewById(R.id.discription);
        devices = new ArrayList<>();

        radioGroup = (RadioGroup)v. findViewById(R.id.radioGroup);
        spinner = (Spinner)v. findViewById(R.id.spinnerDevices);
      //  buttonSendPush = (Button)v. findViewById(R.id.buttonSendPush);
        editTextImage = (EditText)v. findViewById(R.id.editTextImageUrl);

        arrayList.add("type of Announcement");
        arrayList.add("SPORTS");
        arrayList.add("GENERAL");
        arrayList.add("INITIATIVES");
        arrayList.add("SCHOLERSHIP");
        final ArrayAdapter<String> dataAdpter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        type.setAdapter(dataAdpter);


        // buttonUpload = (Button) findViewById(R.id.buttonUpload);
        service = new HTTPURLConnection();

        loadRegisteredDevices();
        imageView = (ImageView) v.findViewById(R.id.photo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });


radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radioButtonSendAll:
                isSendAllChecked = true;
                spinner.setEnabled(false);
                break;

            case R.id.radioButtonSendOne:
                isSendAllChecked = false;
                spinner.setEnabled(true);
                break;

        }

    }
});

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String s1 = parent.getItemAtPosition(position).toString();*/
                type1 = arrayList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPush();
                registerUser();
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");
    }

    private void registerUser() {
        //name = editTextName.getText().toString().trim().toLowerCase();
        //username = etusername.getText().toString().trim().toLowerCase();
        title1 = title.getText().toString().trim().toLowerCase();
        discription1 = discription.getText().toString().trim().toLowerCase();
        dob1 = dob2.getText().toString();
        // type1 = type.getText().toString().trim().toLowerCase();
        new RegisterUser().execute(REGISTER_URL);
        /*register(name,username,password,email);*/
    }

    class RegisterUser extends AsyncTask<String, Void, String> {
        ProgressDialog loading_register;
        RequestHandler rh = new RequestHandler();
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading_register = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
        }


        @Override
        protected String doInBackground(String... params) {
            int success;
            BufferedReader bufferedReader = null;
            try {
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                Log.d("main activity",title1+discription1+dob1+type1);
               Log.d("main activity",getStringImage(bitmap));

                param.add(new BasicNameValuePair("title", title1));
                param.add(new BasicNameValuePair("discription", discription1));
                // param.add(new BasicNameValuePair("", password));
                param.add(new BasicNameValuePair("date", dob1));
                param.add(new BasicNameValuePair("type", type1));

                param.add(new BasicNameValuePair("image", getStringImage(bitmap)));
                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", param);
                success = json.getInt(TAG_SUCCESS1);
                if (success == 1) {
                    Intent i = new Intent(getActivity(), Navgation_Drawer.class);
                   // SharedPreferences pref = getActivity().getSharedPreferences("type", 0); // 0 - for private mode
                    //SharedPreferences.Editor editor = pref.edit();

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
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //method to load all the devices from database
    private void loadRegisteredDevices() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Devices...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.URL_FETCH_DEVICES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                JSONArray jsonDevices = obj.getJSONArray("devices");

                                for (int i = 0; i < jsonDevices.length(); i++) {
                                    JSONObject d = jsonDevices.getJSONObject(i);
                                    devices.add(d.getString("email"));
                                }

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                        getActivity(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        devices);

                                spinner.setAdapter(arrayAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

        };
        MyVolley.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    //this method will send the push
    //from here we will call sendMultiple() or sendSingle() push method
    //depending on the selection
    private void sendPush() {
        if (isSendAllChecked) {
            sendMultiplePush();
        } else {
            sendSinglePush();
        }
    }

    private void sendMultiplePush() {

        final String image = editTextImage.getText().toString();

        progressDialog.setMessage("Sending Push");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_SEND_MULTIPLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title1);
                params.put("message", discription1);

                if (!TextUtils.isEmpty(image))
                    params.put("image", image);
                return params;
            }
        };

        MyVolley.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void sendSinglePush() {
//        final String title = editTextTitle.getText().toString();
//        final String message = editTextMessage.getText().toString();
        final String image = editTextImage.getText().toString();
        final String email = spinner.getSelectedItem().toString();

        progressDialog.setMessage("Sending Push");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_SEND_SINGLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title1);
                params.put("message", discription1);

                if (!TextUtils.isEmpty(image))
                    params.put("image", image);

                params.put("email", email);
                return params;
            }
        };

        MyVolley.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }




}