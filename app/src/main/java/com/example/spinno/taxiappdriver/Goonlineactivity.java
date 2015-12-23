package com.example.spinno.taxiappdriver;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Goonlineactivity extends Activity {
    LinearLayout goonline,selctanthr;
    TextView cn,cno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goonlineactivity);
        goonline = (LinearLayout)findViewById(R.id.llg);
        selctanthr = (LinearLayout)findViewById(R.id.lls);
        cn = (TextView)findViewById(R.id.textView4);
        cno = (TextView)findViewById(R.id.textView5);
        Bundle bundle = getIntent().getExtras();
        cn.setText(bundle.getString("carname",null)+"");
        cno.setText(bundle.getString("carno",null)+"");
        goonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Goonlineactivity.this,MainActivity.class);
                startActivity(in);

            }
        });
        selctanthr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Goonlineactivity.this,Carselectionactivity.class);
                startActivity(in);
                finish();
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = Goonlineactivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Goonlineactivity.this.getResources().getColor(R.color.theme_color));
        } else {
            Window window = Goonlineactivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
