package com.example.riddhi.m1etropolishtutelage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class dash_board extends AppCompatActivity {
    ImageView iv;
    ImageView iv2;
    ImageView iv3;
    ImageView iv4;
    ImageView iv5;
    ImageView iv6;
    ImageView iv7;
    Button logout;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board);
        iv=(ImageView)findViewById(R.id.imageView);
        iv2=(ImageView)findViewById(R.id.imageView2);
        iv3=(ImageView)findViewById(R.id.imageView3);
        iv4=(ImageView)findViewById(R.id.imageView4);
        iv5=(ImageView)findViewById(R.id.imageView5);
        iv6=(ImageView)findViewById(R.id.imageView6);
        iv7=(ImageView)findViewById(R.id.imageView7);
        logout=(Button) findViewById(R.id.logout);


//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(dash_board.this, Form_filling.class);
//                //i.putExtra("r",R.drawable.img1);
//                startActivity(i);
//            }
//        });
//        iv2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i2 = new Intent(dash_board.this, Student_details.class);
//                //i2.putExtra("r",R.drawable.img2);
//                startActivity(i2);
//            }
//        });

        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4 = new Intent(dash_board.this, Proportion.class);
                i4.putExtra("r",R.drawable.img4);
                startActivity(i4);
            }
        });
//        iv5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i5 = new Intent(dash_board.this, Other_details.class);
//                i5.putExtra("r",R.drawable.img6);
//                startActivity(i5);
//            }
//        });
        iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i6= new Intent(dash_board.this, Admin_contact.class);
                i6.putExtra("r",R.drawable.img5);
                startActivity(i6);
            }
        });
        iv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i7= new Intent(dash_board.this, FAQs.class);
                i7.putExtra("r",R.drawable.img3);
                startActivity(i7);
            }
        });
//        iv7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i7= new Intent(dash_board.this, School_details.class);
//                i7.putExtra("r",R.drawable.img3);
//                startActivity(i7);
//            }
//        });
        session = new SessionManager(getApplicationContext());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(dash_board.this, Signup_Login.class);
                // i.putExtra("username",username);
                startActivityForResult(i, 1);
                session.setLogin(false);
                finish();

            }
        });



    }
}
