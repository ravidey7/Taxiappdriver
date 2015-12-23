package com.example.spinno.taxiappdriver;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class Splashactivity extends Activity {
    LinearLayout LoginBox;
    TextView signin2,signup2;
    public  static   Splashactivity contexty;
    public  static  int gallery_grid_Images[]={R.drawable.txi,R.drawable.ttctc,R.drawable.rrttttt,R.drawable.ttytt};
    public  static ViewFlipper viewFlipper;
    boolean previouslyStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contexty=Splashactivity.this;
        setContentView(R.layout.activity_splashactivity);
        LoginBox = (LinearLayout) findViewById(R.id.LoginBox);
        signin2=(TextView)findViewById(R.id.signin2);
        signup2=(TextView)findViewById(R.id.signup);
        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        previouslyStarted = prefs.getBoolean("pref_previously_started", false);
        for(int i=0;i<gallery_grid_Images.length;i++)
        {
            //  This will create dynamic image view and add them to ViewFlipper
            setFlipperImage(gallery_grid_Images[i]);

        }
        viewFlipper.startFlipping();
        signin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(getApplicationContext(),"Sign In... ",Toast.LENGTH_SHORT).show();
                Intent in = new Intent(Splashactivity.this, Loginactivity.class);
                startActivity(in);

                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        });
        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Splashactivity.this, Registeractivity.class);
                startActivity(in);

                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);

            }
        });
        signin2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // do other stuff
                    signin2.setTextColor(Color.parseColor("#000000"));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    signin2.setTextColor(Color.parseColor("#F2CF01"));
                }
                return false;
            }
        });
        signup2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // do other stuff
                    signup2.setTextColor(Color.parseColor("#000000"));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    signup2.setTextColor(Color.parseColor("#F2CF01"));
                }
                return false;
            }
        });

        LoginBox.setVisibility(View.GONE);
        if (!previouslyStarted) {
            Animation animTranslate  = AnimationUtils.loadAnimation(Splashactivity.this, R.anim.anim);
            animTranslate.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation arg0) { }

                @Override
                public void onAnimationRepeat(Animation arg0) { }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    LoginBox.setVisibility(View.VISIBLE);

                    YoYo.with(Techniques.FadeInDown)
                            .duration(3000)
                            .playOn(findViewById(R.id.LoginBox));


                }
            });
            ImageView imgLogo = (ImageView) findViewById(R.id.imageView1);
            imgLogo.startAnimation(animTranslate);


        } else if (previouslyStarted) {
            Animation animTranslate  = AnimationUtils.loadAnimation(Splashactivity.this, R.anim.anim);
            animTranslate.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation arg0) {
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {

                    Intent i = new Intent(Splashactivity.this, Carselectionactivity.class);
                    startActivity(i);
                    finish();
                }
            });
            ImageView imgLogo = (ImageView) findViewById(R.id.imageView1);
            imgLogo.startAnimation(animTranslate);


        }

    }
    private void setFlipperImage(int res) {
        Log.i("Set Filpper Called", res + "");
        ImageView image = new ImageView(getApplicationContext());
        image.setBackgroundResource(res);

        viewFlipper.addView(image);
    }
}
