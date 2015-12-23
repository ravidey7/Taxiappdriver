package com.example.spinno.taxiappdriver;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ReceivePassengerActivity extends Activity {
private DonutProgress donutProgress;
    int j=0;

    private List<android.location.Address> addresses;
    private TextView Address,toaccept;
    public static GPStracker gps;
    private Geocoder geocoder;
    double x, y;
    StringBuilder str;
    public static ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_passenger);
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        Address = (TextView) findViewById(R.id.addressss);
        toaccept = (TextView) findViewById(R.id.txter);
        back=(ImageView)findViewById(R.id.back);
        gps= new GPStracker(ReceivePassengerActivity.this);
        donutProgress.setProgress(j);
        donutProgress.setText("01:00");
        donutProgress.setTextColor(Color.parseColor("#edc244"));
         final CounterClass timer = new CounterClass(1000*60*1, 1000);
       // textViewTime.setText("03:00");
        timer.start();
        donutProgress.setFinishedStrokeColor(Color.parseColor("#edc244"));
        donutProgress.setUnfinishedStrokeColor(Color.parseColor("#ffffff"));
        donutProgress.setMax(60 * 1);




            x = gps.getLatitude();;
            y = gps.getLongitude();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        donutProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(ReceivePassengerActivity.this,Directionactivity.class);
                startActivity(in);
                finish();

            }
        });
        try {
            geocoder = new Geocoder(ReceivePassengerActivity.this, Locale.ENGLISH);
            addresses = geocoder.getFromLocation(x, y, 1);
            str = new StringBuilder();
            if (geocoder.isPresent()) {
                Address returnAddress = addresses.get(0);

                String localityString = returnAddress.getLocality();
                String city = returnAddress.getCountryName();
                String region_code = returnAddress.getCountryCode();
                String zipcode = returnAddress.getPostalCode();

                str.append(localityString + ",");
                str.append(city + "" + region_code + "");
                str.append(zipcode + "");
                try {
                    Address.setText(addresses.get(0).getAddressLine(0)+ ", "
                            + addresses.get(0).getAddressLine(1) + ", "+ addresses.get(0).getAddressLine(2) + " ");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
     @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

            long millis = millisUntilFinished;

            String hms = String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);

            donutProgress.setText(hms);

            j++;

            donutProgress.setProgress(j);
        }

        @Override
        public void onFinish() {

            donutProgress.setText("Time Out");
            toaccept.setText("Sorry !!!");
            donutProgress.setClickable(false);
            j++;

            donutProgress.setProgress(j);
        }



    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = ReceivePassengerActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ReceivePassengerActivity.this.getResources().getColor(R.color.action_bar));
        } else {
            Window window = ReceivePassengerActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
