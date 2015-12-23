package com.example.spinno.taxiappdriver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spinno.taxiappdriver.parsingforapi.parsingfordest;
import com.example.spinno.taxiappdriver.parsingforapi.parsingforpathdest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Directionactivity extends FragmentActivity implements LocationListener {
    public static Marker now;
    GoogleMap mGoogleMap;
    public static int i =0;
    public static AutoCompleteTextView atvPlaces;
    ArrayList<LatLng> mMarkerPoints;
    double mLatitude;
    double mLongitude;
    private List<android.location.Address> addresses;
    private Geocoder geocoder;

    double x, y;
    StringBuilder str;
    LatLng startPoint;
    public static String placeid="null";
    public static GPStracker gps;
    public static ImageView back;
    public static EditText enddest;
    public static  TextView info,arrive,address,startadr;
    public static LinearLayout arrived,layoutforride,endtrip;
    public static LatLng point11 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directionactivity);
        back=(ImageView)findViewById(R.id.back);
        info=(TextView)findViewById(R.id.info);
        arrive=(TextView)findViewById(R.id.arrive);
        arrived=(LinearLayout)findViewById(R.id.arr);
        address=(TextView)findViewById(R.id.address);
        startadr=(TextView)findViewById(R.id.startaddress);
        atvPlaces=(AutoCompleteTextView)findViewById(R.id.destination);
        layoutforride=(LinearLayout)findViewById(R.id.layoutforride);
        endtrip=(LinearLayout)findViewById(R.id.arr22);
        gps= new GPStracker(Directionactivity.this);
        // Getting Google Play availability status


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(atvPlaces.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                Intent in = new Intent(Directionactivity.this,InfoActivity.class);
                startActivity(in);

            }
        });
        endtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(atvPlaces.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                Intent in = new Intent(Directionactivity.this,Pricefareactivity.class);
                startActivity(in);
                finish();
                //Toast.makeText(Directionactivity.this, "Trip will get end", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(atvPlaces.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

      }else { // Google Play Services are available

            // Initializing
            mMarkerPoints = new ArrayList<LatLng>();

            // Getting reference to SupportMapFragment of the activity_main
            SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting Map for the SupportMapFragment
            mGoogleMap = fm.getMap();
            mLatitude = gps.getLatitude();
            mLongitude= gps.getLongitude();
            // Enable MyLocation Button in the Map
            mGoogleMap.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location From GPS
            final Location location = locationManager.getLastKnownLocation(provider);

            if(location!=null){
                onLocationChanged(location);
            }

            locationManager.requestLocationUpdates(provider, 20000, 0, this);
            point11 = new LatLng(28.556358214591647, 77.10552155971527);
            if(mMarkerPoints.size()>1){

                //FragmentManager fm = getSupportFragmentManager();
                mMarkerPoints.clear();
                mGoogleMap.clear();
                LatLng startPoint = new LatLng(mLatitude, mLongitude);

                // draw the marker at the current position
                drawMarker(startPoint);
            }

            // draws the marker at the currently touched location
            drawMarker(point11);

            // Checks, whether start and end locations are captured
            if(mMarkerPoints.size() >= 2){
                LatLng origin = mMarkerPoints.get(0);
                LatLng dest = mMarkerPoints.get(1);

                // Getting URL to the Google Directions API
                String url = getDirectionsUrl(origin, dest);

                DownloadTask downloadTask = new DownloadTask();

                // Start downloading json data from Google Directions API
                downloadTask.execute(url);
            }
            mGoogleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {


                        //FragmentManager fm = getSupportFragmentManager();
                        mMarkerPoints.clear();
                        mGoogleMap.clear();


                        startPoint = new LatLng(location.getLatitude(), location.getLongitude());

                        // draw the marker at the current position
                        drawMarker(startPoint);


                        // draws the marker at the currently touched location
                        drawMarker(point11);

                        if (mMarkerPoints.size() >= 2) {
                            LatLng origin = mMarkerPoints.get(0);
                            LatLng dest = mMarkerPoints.get(1);

                            // Getting URL to the Google Directions API
                            String url = getDirectionsUrl(origin, dest);

                            DownloadTask downloadTask = new DownloadTask();

                            // Start downloading json data from Google Directions API
                            downloadTask.execute(url);
                        }

//
                }
            });
            // Setting onclick event listener for the map
//            mGoogleMap.setOnMapClickListener(new OnMapClickListener() {
//
//                @Override
//                public void onMapClick(LatLng point) {
//                    Log.d("Bgggg", "gg " + point);
//                    // Already map contain destination location
//
//                }
//            });

            atvPlaces.setThreshold(1);

            atvPlaces.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    parsingfordest.parsing(Directionactivity.this, atvPlaces.getText().toString().trim());
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub
                }
            });

            atvPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    placeid = parsingfordest.placeid.get(position);
                    //Toast.makeText(Directionactivity.this, "textview"+parsingfordest.placeid.get(position), Toast.LENGTH_SHORT).show();
                }
            });
            arrived.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(i==0) {
                        i=1;
                        address.setVisibility(View.GONE);
                        arrive.setText("Begin Trip");
                        layoutforride.setVisibility(View.VISIBLE);
                        mMarkerPoints.clear();
                        mGoogleMap.clear();
                        x = gps.getLatitude();
                        y = gps.getLongitude();

                        try {
                            geocoder = new Geocoder(Directionactivity.this, Locale.ENGLISH);
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
                                    startadr.setText(addresses.get(0).getAddressLine(0)+ ", "
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
                    else if(placeid.equals("null")){
                        Toast.makeText(Directionactivity.this, "Select Destination First", Toast.LENGTH_SHORT).show();
                    }
                    else{
                       // Toast.makeText(Directionactivity.this, "arr"+placeid, Toast.LENGTH_SHORT).show();
                        parsingforpathdest.parsing(Directionactivity.this,placeid);

                    }
                    //
                }
            });
        }
    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+mLatitude+","+mLongitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.e("Exception", e+"");
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /** A class to download data from Google Directions URL */
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Directions in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.parseColor("#3498db"));
            }

            // Drawing polyline in the Google Map for the i-th route
            mGoogleMap.addPolyline(lineOptions);
        }
    }



    private void drawMarker(LatLng point){
        if(i==0) {
            mMarkerPoints.add(point);

            // Creating MarkerOptions
            MarkerOptions options = new MarkerOptions();

            // Setting the position of the marker
            options.position(point);

            /**
             * For the start location, the color of marker is GREEN and
             * for the end location, the color of marker is RED.
             */
            if (mMarkerPoints.size() == 1) {
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.carmarker));
            } else if (mMarkerPoints.size() == 2) {
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.target));
            }

            // Add new marker to the Google Map Android API V2
            mGoogleMap.addMarker(options);
        }
        else{
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(point)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.carmarker))
                    .title("You are here !!!"));
        }
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude,longitude)).zoom(20).tilt(45).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onLocationChanged(Location location) {
        // Draw the marker, if destination location is not set
//        if(mMarkerPoints.size() < 2){
//
//            mLatitude = gps.getLatitude();
//            mLongitude = gps.getLongitude();
//            LatLng point = new LatLng(mLatitude, mLongitude);
//
//            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
//            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(20));
//
//            drawMarker(point);
//        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = Directionactivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Directionactivity.this.getResources().getColor(R.color.theme_color));
        } else {
            Window window = Directionactivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}