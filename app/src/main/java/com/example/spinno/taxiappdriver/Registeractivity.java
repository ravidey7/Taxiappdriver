package com.example.spinno.taxiappdriver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Registeractivity extends Activity {
    public static ImageView back;
    TextView next;
    public static Spinner sp22;
    public static View v1,v2,v3,v4,v11;
    EditText email,pass,mob;
    SharedPreferences prefs;
    private static final String[] m_Codes = {          "91",      "376",            "971",
            "93",            "355",            "374",            "599",            "244",            "672",            "54",            "43",            "61",            "297",
            "994",            "387",            "880",            "32",            "226",
            "359",            "973",            "257",            "229",            "590",            "673",            "591",            "55",            "975",            "267",            "375",            "501",
            "1",            "61",            "243",            "236",            "242",            "41",            "225",            "682",            "56",            "237",            "86",            "57",
            "506",            "53",            "238",            "61",            "357",            "420",
            "49",            "253",            "45",            "213",            "593",            "372",            "20",            "291",            "34",            "251",            "358",            "679",
            "500",            "691",            "298",            "33",            "241",            "44",            "995",            "233",            "350",            "299",            "220",            "224",            "240",            "30",            "502",            "245",            "592",            "852",            "504",            "385",            "509",            "36",            "62",            "353",            "972",            "44",               "964",
            "98",            "39",            "962",            "81",            "254",            "996",            "855",            "686",            "269",            "850",            "82",            "965",            "7",            "856",            "961",            "423",            "94",            "231",            "266",            "370",            "352",            "371",            "218",            "212",            "377",          "373",            "382",            "261",            "692",            "389",            "223",            "95",            "976",            "853",            "222",            "356",
            "230",            "960",            "265",            "52",            "60",            "258",            "264",            "687",            "227",            "234",            "505",            "31",            "47",            "977",            "674",            "683",            "64",            "968",            "507",            "51",            "689",            "675",            "63",            "92",            "48",            "508",            "870",            "1",            "351",            "680",            "595",            "974",            "40",            "381",            "7",            "250",            "966",            "677",            "248",            "249",            "46",            "65",            "290",            "386",            "421",            "232",            "378",            "221",            "252",            "597",            "239",            "503",            "963",            "268",            "235",            "228",            "66",            "992",            "690",            "670",            "993",            "216",            "676",            "90",            "688",            "886",            "255",            "380",            "256",            "1",            "598",            "998",            "39",            "58",            "84",            "678",            "681",            "685",            "967",            "262",            "27",            "260",     "263"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);
        sp22 = (Spinner)findViewById(R.id.spinner);
        back=(ImageView)findViewById(R.id.back);
        v1 = (View)findViewById(R.id.view11);
        v2 = (View)findViewById(R.id.view12);
        v3 = (View)findViewById(R.id.view21);
        v4 = (View)findViewById(R.id.view22);
        email=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.password);
        mob = (EditText)findViewById(R.id.mob);
        v11= (View)findViewById(R.id.view61);
        next=(TextView)findViewById(R.id.next);
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        ArrayAdapter adp = new ArrayAdapter(getApplicationContext(),R.layout.itemlayoutformobile,R.id.mobcode,m_Codes);
        sp22.setAdapter(adp);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    v1.setVisibility(View.GONE);
                    v2.setVisibility(View.VISIBLE);
                } else {
                    v1.setVisibility(View.VISIBLE);
                    v2.setVisibility(View.GONE);
                }
            }
        });
        mob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true){
                    v11.setBackgroundColor(Color.parseColor("#bd362b"));
                }
                else{
                    v11.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }
        });

        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    v3.setVisibility(View.GONE);
                    v4.setVisibility(View.VISIBLE);
                } else {
                    v3.setVisibility(View.VISIBLE);
                    v4.setVisibility(View.GONE);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(email.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                imm.hideSoftInputFromWindow(pass.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                imm.hideSoftInputFromWindow(mob.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Signed Up... ", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor edit2 = prefs.edit();
                edit2.putBoolean("pref_previously_started", Boolean.TRUE);
                edit2.commit();
                Intent in = new Intent(Registeractivity.this, Carselectionactivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                finish();
                Splashactivity.contexty.finish();

            }
        });
    }

}
