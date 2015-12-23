package com.example.spinno.taxiappdriver;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity {
    public static int distance;
    public static Marker now;
    public static MapView mapView;
    public static GoogleMap map;
    public static Circle mapCircle;
    public static TextView addressdriver,gooffline,carname;
    public static ProgressBar pb ;
    public static Button bfrwd;
    public static Boolean isInternetPresent = false;
    public static ConnectionDetector cd;
    public static ImageView back;
    public static Double currentlat, currentlong;
    public  static ArrayList<String> name = new ArrayList<String>();
    public  static ArrayList<String[]> latlng = new ArrayList<>();
    public  static  ArrayList<String> latitude = new ArrayList<>();
    public  static ArrayList<String> longitude = new ArrayList<>();
    public static  String name1 []={"1","2","3","4","5"};
    public static  String lat2 []={"28.595520","28.5465205","28.5655205","28.5555205","28.5455205"};
    public static  String long2 []={"77.23195220000001","77.25195220000001","77.26195220000001","77.22195220000001","77.27195220000001"};
    public static GPStracker gps;
    private Geocoder geocoder;
    private List<android.location.Address> addresses;

    StringBuilder str;


    public static Context ctc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctc=getApplicationContext();
        pb = (ProgressBar)findViewById(R.id.progressBarinsplash);
        addressdriver = (TextView)findViewById(R.id.add);
        gooffline = (TextView)findViewById(R.id.gooff);
        carname = (TextView)findViewById(R.id.carname);
        bfrwd = (Button)findViewById(R.id.button);
        cd = new ConnectionDetector(ctc);
        gps= new GPStracker(ctc);
       // back=(ImageView)findViewById(R.id.back);
        mapView = (MapView)findViewById(R.id.mapgh);
        mapView.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        carname.setText(bundle.getString("carname",null)+" - "+bundle.getString("carno",null));
        for(int i =0;i<name1.length;i++){
            name.add(name1[i]);
            latitude.add(lat2[i]);
            longitude.add(long2[i]);

        }
        bfrwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this,ReceivePassengerActivity.class);
                startActivity(in);
            }
        });
        gooffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        currentlat= gps.getLatitude();
        currentlong= gps.getLongitude();
        getthetracker();
    }

    public void getthetracker() {

        MapsInitializer.initialize(ctc);
        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctc)) {
            case ConnectionResult.SUCCESS:

               //Toast.makeText(ctc, "tracker", Toast.LENGTH_SHORT).show();
                // Gets to GoogleMap from the MapView and does initialization stuff
                if (mapView != null) {

                    map = mapView.getMap();
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                    map.setMyLocationEnabled(true);
                    gps = new GPStracker(ctc, map);
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(currentlat,
                                    currentlong));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);
                    map.moveCamera(center);
                    map.animateCamera(zoom);
                    map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                        @Override
                        public void onMyLocationChange(Location location) {
                            drawMarker(location);
//
                        }
                    });
                    Double x = gps.getLatitude();;
                    Double y = gps.getLongitude();


                    try {
                        geocoder = new Geocoder(MainActivity.this, Locale.ENGLISH);
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


                        } else {
                        }
                    } catch (Exception e) {
                        Log.e("tag", e.getMessage());
                    }
                    addressdriver.setText(""+addresses.get(0).getAddressLine(0) + ", "
                            + addresses.get(0).getAddressLine(1) + ", "+ addresses.get(0).getAddressLine(2) + " ");

                        now = map.addMarker(new MarkerOptions()
                                .position(new LatLng(currentlat, currentlong))
                                .title("You are here !!!").icon(BitmapDescriptorFactory.fromResource(R.drawable.carmarker)));
                        map.setMyLocationEnabled(true);

                    now.showInfoWindow();

                    isInternetPresent =cd.isConnectingToInternet();

                    // check for Internet status
                    if (isInternetPresent) {

                    } else {
                        // Internet connection is not present
                        // Ask user to connect to Internet
                        Toast.makeText(ctc, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }

                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(ctc, "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(ctc, "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(ctc, GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctc), Toast.LENGTH_SHORT).show();
        }




    }
    @Override
    public void onResume() {
        //Toast.makeText(getActivity(), "resume", Toast.LENGTH_SHORT).show();
        mapView.onResume();
        super.onResume();
        map.clear();
        getthetracker();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
         mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = MainActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(MainActivity.this.getResources().getColor(R.color.theme_color));
        } else {
            Window window = MainActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }



    private void drawMarker(Location location)
    {
        map.clear();
        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        Double x = location.getLatitude();;
        Double y = location.getLongitude();


        try {
            geocoder = new Geocoder(MainActivity.this, Locale.ENGLISH);
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


            } else {
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        addressdriver.setText("" + addresses.get(0).getAddressLine(0) + ", "
                + addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2) + " ");

        now = map.addMarker(new MarkerOptions()
                .position(currentPosition)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.carmarker))
                .title("You are here !!!"));
        now.showInfoWindow();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude,longitude)).zoom(20).tilt(45).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}



