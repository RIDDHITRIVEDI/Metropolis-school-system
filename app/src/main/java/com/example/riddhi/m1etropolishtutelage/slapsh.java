package com.example.riddhi.m1etropolishtutelage;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.view.animation.Animation.AnimationListener;
import android.widget.Toast;

public class slapsh extends AppCompatActivity {
    ImageView iv;
    Animation animFadein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
//        iv = (ImageView) findViewById(R.id.imageView8);
//        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.zoomin);
//        animFadein.setAnimationListener(this);
//        iv.setVisibility(View.VISIBLE);
//        iv.startAnimation(animFadein);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //  Intent i = new Intent(slapsh.this,Signup_Login .class);
                Intent i = new Intent(slapsh.this, Signup_Login.class);
                startActivity(i);
                finish();

            }
        }, 3000);

    }
//    @Override
////    public void onAnimationEnd(Animation animation) {
////        // Take any action after completing the animation
////
////        // check for fade in animation
////        if (animation == animFadein) {
////            Toast.makeText(getApplicationContext(), " ",
////
////                    Toast.LENGTH_SHORT).show();
////            Intent i = new Intent(slapsh.this, Signup_Login.class);
////           startActivity(i);
////            finish();
////        }
////
////    }
//
//    @Override
//    public void onAnimationRepeat(Animation animation) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void onAnimationStart(Animation animation) {
//        // TODO Auto-generated method stub
//
//
//    }
}

