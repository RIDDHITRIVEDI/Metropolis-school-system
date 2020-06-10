package com.example.riddhi.m1etropolishtutelage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class forgetpassword extends AppCompatActivity {
    Spinner s2;
    EditText e, e1, e2;
    String s;
    Button b3;
    String f = "xyz";
    ArrayList<String> ar1 = new ArrayList<>();

    //ArrayList<String> ar2=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        s2 = (Spinner) findViewById(R.id.spinner2);
        b3 = (Button) findViewById(R.id.button5);
        e = (EditText) findViewById(R.id.editText7);
        e1 = (EditText) findViewById(R.id.editText_mob);
        e2 = (EditText) findViewById(R.id.editText_acnum);
        ar1.add("what is your nick name? ");
        ar1.add("what is first mobile number ");
        ar1.add("what is your account number");

//final int i=3000;
        final ArrayAdapter<String> dataAdpter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ar1);
        s2.setAdapter(dataAdpter);


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = e.getText().toString();


                if (s.equals(f)) {

                    Toast.makeText(getApplicationContext(), "yes,name is correct", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(), "no,name is incorrect", Toast.LENGTH_SHORT).show();
                }


            }
        });
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    e.setVisibility(View.VISIBLE);
                    e1.setVisibility(View.GONE);
                    e2.setVisibility(View.GONE);
                } else if (position == 1) {
                    e.setVisibility(View.GONE);
                    e1.setVisibility(View.VISIBLE);
                    e2.setVisibility(View.GONE);
                } else if (position == 2) {
                    e.setVisibility(View.GONE);
                    e1.setVisibility(View.GONE);
                    e2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
