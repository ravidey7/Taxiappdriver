package com.example.spinno.taxiappdriver;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Pricefareactivity extends Activity {
    RatingBar rb;
    public static TextView Dp,done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricefareactivity);
        rb = (RatingBar) findViewById(R.id.ratingBar2);
        done=(TextView)findViewById(R.id.done);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        rb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = rb.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int) starsf + 1;
                    rb.setRating(stars);
                    LayerDrawable layerDrawable = (LayerDrawable) rb.getProgressDrawable();
                    DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(0)), Color.GRAY);   // Empty star
                    DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(1)), Color.GREEN); // Partial star
                    DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), Color.parseColor("#edc244"));

                    final Toast toast = Toast.makeText(getApplicationContext(), "Rating " + stars, Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                    v.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }


                return true;
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = Pricefareactivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Pricefareactivity.this.getResources().getColor(R.color.theme_color));
        } else {
            Window window = Pricefareactivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
