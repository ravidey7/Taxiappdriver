package com.example.spinno.taxiappdriver.parsingforapi;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.spinno.taxiappdriver.Directionactivity;
import com.example.spinno.taxiappdriver.R;
import com.example.spinno.taxiappdriver.settergetter.Innerplaces;
import com.example.spinno.taxiappdriver.settergetter.placessettergetter;
import com.example.spinno.taxiappdriver.settergetter.ridedestsettergetter;
import com.example.spinno.taxiappdriver.singleton.VolleySingleton;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saifi45 on 10/23/2015.
 */
public class parsingforpathdest {
    public static String[] from = new String[] { "description"};
    public static int[] to = new int[] { android.R.id.text1 };
    public static RequestQueue queue;
    public static StringRequest sr1,sr2;
    public static List<Innerplaces> data_list1;
    public static Double lat;
    public static ArrayList<String> tagline = new ArrayList<String>();
    public static ArrayList<String> imagesss = new ArrayList<String>();
    public static Double lng ;

    public static void parsing(final Context activity, String s){



        String locationurl2 = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+s+"&key=AIzaSyCC3Ci--XByh-o-ukFw0IBOGD1of7hglA4";
       // Toast.makeText(activity, ""+s, Toast.LENGTH_SHORT).show();
        locationurl2= locationurl2.replace(" ","%20");
        // Log.e("url", "" + locationurl2);
        queue = VolleySingleton.getInstance(activity).getRequestQueue();
        sr2 = new StringRequest(Request.Method.GET, locationurl2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




                try {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    final Gson gson = gsonBuilder.create();
                    ridedestsettergetter received2 = new ridedestsettergetter();
                    received2 = gson.fromJson(response, ridedestsettergetter.class);


                    lat=Double.parseDouble(received2.innerdestination.geometry.location11.lat);
                    lng=Double.parseDouble(received2.innerdestination.geometry.location11.lng);

                    Directionactivity.point11=new LatLng(lat,lng);
                    Directionactivity.i=0;
                    Directionactivity.arrive.setVisibility(View.GONE);
                    Directionactivity.endtrip.setVisibility(View.VISIBLE);
                    //  List<HashMap<String, String>> result =descp;



                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    Log.e("exception", "" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        sr2.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr2);



    }
}
