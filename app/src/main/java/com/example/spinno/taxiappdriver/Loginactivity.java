package com.example.spinno.taxiappdriver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Loginactivity extends Activity {
    Button next;
    TextView frgt;
    EditText email,pass;
    ImageView back;
    public static View v1,v2;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        next=(Button)findViewById(R.id.signin222);
        frgt=(TextView)findViewById(R.id.frgtpass2222);
        back=(ImageView)findViewById(R.id.back);
        email=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.password);
        v1 = (View)findViewById(R.id.view11);
        v2 = (View)findViewById(R.id.view1133);

        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true){
                    v1.setBackgroundColor(Color.parseColor("#a61c1e"));
                }
                else{
                    v1.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }
        });


        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true){
                    v2.setBackgroundColor(Color.parseColor("#a61c1e"));
                }
                else{
                    v2.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(email.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                imm.hideSoftInputFromWindow(pass.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sign In... ", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor edit2 = prefs.edit();
                edit2.putBoolean("pref_previously_started", Boolean.TRUE);
                edit2.commit();
                Intent in = new Intent(Loginactivity.this, Carselectionactivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                finish();
                Splashactivity.contexty.finish();
            }
        });
    }

}
