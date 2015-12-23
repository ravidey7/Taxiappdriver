package com.example.spinno.taxiappdriver;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.spinno.taxiappdriver.adapter.Mycarselection;

import java.util.ArrayList;

public class Carselectionactivity extends Activity {
    public  static ListView lv;

    String ds[]={"Toyota Innova","Chevrolet beat","Toyota Prius"};
    ArrayList<String> ds2 = new ArrayList<>();
    public static ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carselectionactivity);

        lv=(ListView)findViewById(R.id.listView);

        //back =(ImageView)findViewById(R.id.back);
        for(int h=0;h<ds.length;h++){
            ds2.add(ds[h]);
        }

         Mycarselection adp = new Mycarselection(Carselectionactivity.this,ds2);
        lv.setAdapter(adp);

       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent in = new Intent(Carselectionactivity.this,Goonlineactivity2.class);
               in.putExtra("carname",""+ds[i]);
               in.putExtra("carno","UP 14 BH 2322");
               startActivity(in);
               finish();
           }
       });

//        back.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//
//                finish();
//            }
//        });

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = Carselectionactivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Carselectionactivity.this.getResources().getColor(R.color.theme_color));
        } else {
            Window window = Carselectionactivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
