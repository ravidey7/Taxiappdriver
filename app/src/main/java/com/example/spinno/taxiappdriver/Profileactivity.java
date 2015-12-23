package com.example.spinno.taxiappdriver;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Profileactivity extends Activity {
    ImageView back,back2;
    TextView passalert,done,logout;
    LinearLayout llforpassword;
    EditText oldp,conp,newp,name,mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileactivity);
        back=(ImageView)findViewById(R.id.bck);
        name = (EditText)findViewById(R.id.name);
        mobile = (EditText)findViewById(R.id.mob);
        logout = (TextView)findViewById(R.id.logout);
        done = (TextView)findViewById(R.id.done);
        llforpassword=(LinearLayout)findViewById(R.id.ll22);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(name.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                imm.hideSoftInputFromWindow(mobile.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });
        llforpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogforpassword();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showlogoutdialog();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goonlineactivity2.NAME= name.getText().toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(name.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                imm.hideSoftInputFromWindow(mobile.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });
    }
    public  void showdialogforpassword() {

        final Dialog dialog = new Dialog(Profileactivity.this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=dialog.getWindow();
        dialog.setCancelable(false);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setContentView(R.layout.dialogforchangepassword);
       done = (TextView)dialog.findViewById(R.id.done);
       passalert = (TextView)dialog.findViewById(R.id.passalert);
        back2 = (ImageView)dialog.findViewById(R.id.back);
         oldp = (EditText)dialog.findViewById(R.id.oldp);
         conp = (EditText)dialog.findViewById(R.id.conp);
        newp = (EditText)dialog.findViewById(R.id.newp);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(oldp.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                imm.hideSoftInputFromWindow(conp.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                imm.hideSoftInputFromWindow(newp.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (!newp.getText().toString().equals(conp.getText().toString())) {
                    passalert.setVisibility(View.VISIBLE);
                } else {
                    passalert.setVisibility(View.GONE);
                    Toast.makeText(Profileactivity.this, "Password has been changed !!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

       oldp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if (hasFocus == true) {
                   passalert.setVisibility(View.GONE);
               }
           }
       });
        newp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    passalert.setVisibility(View.GONE);
                }
            }
        });
        conp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    passalert.setVisibility(View.GONE);
                }
            }
        });
        back2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(oldp.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                imm.hideSoftInputFromWindow(conp.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                imm.hideSoftInputFromWindow(newp.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public  void showlogoutdialog() {

        final Dialog dialog = new Dialog(Profileactivity.this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=dialog.getWindow();
        dialog.setCancelable(false);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialogforlogout);
        TextView yes = (TextView)dialog.findViewById(R.id.yes);
        TextView no = (TextView)dialog.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                SharedPreferences.Editor edit2 = prefs.edit();
                edit2.putBoolean("pref_previously_started", Boolean.FALSE);
                edit2.commit();
                Intent in = new Intent(Profileactivity.this, Splashactivity.class);
                startActivity(in);
                Profileactivity.this.finish();
                Goonlineactivity2.mainact.finish();
                dialog.dismiss();


            }
        });

        no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = Profileactivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Profileactivity.this.getResources().getColor(R.color.theme_color));
        } else {
            Window window = Profileactivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
