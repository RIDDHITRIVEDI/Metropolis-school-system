package com.example.riddhi.m1etropolishtutelage;

/**
 * Created by RIDDHI on 26-02-17.
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.riddhi.m1etropolishtutelage.R.id.child_first_name;
import static com.example.riddhi.m1etropolishtutelage.R.id.content_frame;
import static com.facebook.FacebookSdk.getApplicationContext;

public class municipality extends Fragment {
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();
    ArrayList<String> ar = new ArrayList<>();
Spinner year;
    EditText city;
    EditText firstname;

    EditText mothersname;
    EditText address;
    EditText contact;
    ImageView image;
    RadioButton m;
    RadioButton f;
    Button save;
    Button clear;

    String year1;
    String firstname1;

    String mothersname1;
    String address1;
    String contact1;
    String random;
    String image1;
    String m1;
    String f1;
    String save1;
    String clear1;
    String gender;
    String city1;

    private static final String TAG_SUCCESS1 = "success";
    public static final String MyPREFERENCES = "username";
    private static final String TAG_MESSAGE1 = "message";
    JSONParser jsonParser = new JSONParser();


    private Button buttonChoose;
    private Button buttonUpload, buttonView;
    private ImageView imageView1;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private ImageView imageView;
    private Bitmap bitmap;
    public static final String UPLOAD_KEY = "image";
    private HTTPURLConnection service;
   // private static final String REGISTER_URL = "http://ridhitrivedi.16mb.com/imageupload.php";
    private static final String REGISTER_URL1 = "http://ridhitrivedi.16mb.com/municipality_upload/municipality.php";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.menu1, container, false);
        firstname = (EditText) v.findViewById(child_first_name);
        Log.d("main2activity123", "isss" + randomString(8));


        // View v= inflater.inflate(R.layout.municipality, container, false);

        mothersname = (EditText) v.findViewById(R.id.mothers_name);
        city = (EditText) v.findViewById(R.id.city);
        year = (Spinner) v.findViewById(R.id.year);

        address = (EditText) v.findViewById(R.id.address);

        contact = (EditText) v.findViewById(R.id.contact);
        image = (ImageView) v.findViewById(R.id.imageView10);
        m = (RadioButton) v.findViewById(R.id.male);

        f = (RadioButton) v.findViewById(R.id.female);

        save = (Button) v.findViewById(R.id.save);

        clear = (Button) v.findViewById(R.id.clear);


        imageView1 = (ImageView) v.findViewById(R.id.imageView10);

        buttonUpload = (Button) v.findViewById(R.id.save);
        service = new HTTPURLConnection();
        ar.add("2015");
        ar.add("2016");
        ar.add("2017");
        ar.add("2018");
        final ArrayAdapter<String> dataAdpter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ar);
        year.setAdapter(dataAdpter);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
//        buttonUpload.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                registerUser();
//            }
//        });
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String s1 = parent.getItemAtPosition(position).toString();*/
               year1 = ar.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        m.setChecked(true);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname.setText("");

                mothersname.setText("");
                address.setText("");
                contact.setText("");

                imageView1.setImageResource(R.drawable.ic_menu_camera);

            }
        });
//        firstname1 = firstname.getText().toString();
//        lastname1 = lastname.getText().toString();
//        middlename1= middlename.getText().toString();
//        mothersname1 = mothersname.getText().toString();
//        address1 = address.getText().toString();
//        contact1 = contact.getText().toString();
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        m.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    f.setChecked(false);
                    gender = "male";
                }
            }
        });
        f.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    m.setChecked(false);
                    gender = "female";
                }
            }
        });

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
                imageView1.setImageBitmap(bitmap);
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

//    private void uploadImage() {
//        class UploadImage extends AsyncTask<Bitmap, Void, String> {
//            RequestHandler rh = new RequestHandler();
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(getActivity(), "Uploading...", null, true, true);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            protected String doInBackground(Bitmap... params) {
//                int success;
//                BufferedReader bufferedReader = null;
//                try {
//                    Bitmap bitmap = params[0];
//                    String uploadImage = getStringImage(bitmap);
//
//
//                    HashMap<String, String> data = new HashMap<>();
//
//                    data.put(UPLOAD_KEY, uploadImage);
//
//                    String result = rh.sendPostRequest(REGISTER_URL, data);
//
//                    return result;
////                    param.add(new BasicNameValuePair("title",title));
////                    param.add(new BasicNameValuePair("description",description));
////                    param.add(new BasicNameValuePair("image",uploadImage));
//
//                    //JSONObject json = jsonParser.makeHttpRequest(UPLOAD_URL, "POST", param);
//                    //success = json.getInt(TAG_SUCCESS);
////                    if (success == 1)
////                    {
////                      //  Log.d("User Created!", json.toString());
////
////                        //return json.getString(TAG_MESSAGE);
////                    }
////                    else
////                    {
////                        //Log.d("Login Failure!", json.getString(TAG_MESSAGE));
////                        //return json.getString(TAG_MESSAGE);
////                    }
//                } catch (Exception e) {
//                    return null;
//                }
//
//
//            }
//        }
//
//        UploadImage ui = new UploadImage();
//        ui.execute(bitmap);
//    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");


    }

    private void registerUser() {


        firstname1 = firstname.getText().toString().trim().toLowerCase();
       // lastname1 = lastname.getText().toString().trim().toLowerCase();
       // middlename1 = middlename.getText().toString().trim().toLowerCase();
        mothersname1 = mothersname.getText().toString().trim().toLowerCase();
        address1 = address.getText().toString().trim().toLowerCase();
        contact1 = contact.getText().toString().trim().toLowerCase();
        city1 = city.getText().toString().trim().toLowerCase();


        new RegisterUser().execute(REGISTER_URL1);
        /*register(name,username,password,email);*/
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
            int success;
            BufferedReader bufferedReader = null;
            random = randomString(8);
            try {
                String uploadImage = getStringImage(bitmap);
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                param.add(new BasicNameValuePair("firstname1", firstname1));
//                param.add(new BasicNameValuePair("lastname1", lastname1));
//                param.add(new BasicNameValuePair("middlename1", middlename1));
                param.add(new BasicNameValuePair("mothersname1", mothersname1));
                param.add(new BasicNameValuePair("address1", address1));
                param.add(new BasicNameValuePair("contact1", contact1));
                param.add(new BasicNameValuePair("image", uploadImage));
                param.add(new BasicNameValuePair("gender", gender));
                param.add(new BasicNameValuePair("randomString", random));
                param.add(new BasicNameValuePair("city", city1));
                param.add(new BasicNameValuePair("admissionyear", year1));
Log.d("xyz","xyz1"+firstname1+mothersname1+address1+contact1+uploadImage+gender+random);

                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL1, "POST", param);
                success = json.getInt(TAG_SUCCESS1);
                if (success == 1) {


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


            if (s.equals("Username Successfully Added!")) {

                alertdialogbox(random);

            } else {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        }
    }

    String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }


    public void alertdialogbox(String num) {

        new AlertDialog.Builder(getActivity())
                .setTitle("metropolis tutelage")
                .setMessage("your unique id is " + num + " please note it over birth-certificate")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }
}