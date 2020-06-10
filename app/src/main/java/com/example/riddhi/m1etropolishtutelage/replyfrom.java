package com.example.riddhi.m1etropolishtutelage;

/**
 * Created by RIDDHI on 26-02-17.
 */

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class replyfrom extends Fragment {
    TextView sub_reply, sub_reply1;
    EditText reply;
    Button submit;

    String user_from;
    String user_to;
    String id;
    String subject;
    String replay;
    private static final String LOGIN_URL = "http://ridhitrivedi.16mb.com/reply.php";
    JSONParser jsonParser = new JSONParser();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.replyfrom, container, false);

        sub_reply = (TextView) v.findViewById(R.id.sub_reply);
        sub_reply1 = (TextView) v.findViewById(R.id.sub_reply1);

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
        replay = args.getString("replay");
        sub_reply.setText(subject);
        sub_reply1.setText(replay);

    }

}