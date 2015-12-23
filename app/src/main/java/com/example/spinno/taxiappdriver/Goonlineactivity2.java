package com.example.spinno.taxiappdriver;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Goonlineactivity2 extends ActionBarActivity {



    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;

    public static String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java

    private String name;        //String Resource for header View Name
    private int profile;        //int Resource for header view profile picture
    private String email;       //String Resource for header view email

    public static Context ctc2;

    boolean mUpdatesRequested = false;
    //private TextView markerText;

    public static Boolean isInternetPresent = false;
    // public static ConnectionDetector cd;
    public static ImageView back;
    public static Double currentlat, currentlong;
    public  static ArrayList<String> namemark = new ArrayList<String>();
    public  static ArrayList<String[]> latlngmark = new ArrayList<>();
    public  static  ArrayList<String> latitudemark = new ArrayList<>();
    public  static ArrayList<String> longitudemark = new ArrayList<>();
    public static  String name12 []={"1","2"};
    public static  String lat22 []={"28.4670621","28.4670621"};
    public static  String long22 []={"77.0584252","77.0684252"};

    public static int positionforhori=0;
    public static Context ctc;
    private static String mCurrentPhotoPath;
    public static AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    public static final String JPEG_FILE_PREFIX = "IMG_";
    public static final String JPEG_FILE_SUFFIX = ".jpg";
    public static String imgDecodableString;
    public static Goonlineactivity2 mainact;

    public static String TITLES[] = {"Emergency Contact","Report Issue","Call Support","About"};
    public static int ICONS[] = {
            R.drawable.emergency,R.drawable.report,
            R.drawable.call,R.drawable.info};
   // int ICONS22[] = {R.drawable.start,R.drawable.start,R.drawable.start,R.drawable.start,R.drawable.start,R.drawable.start };
    String mins[] = {"3 mins","5 mins","14 mins","30 mins","no autos","15 mins"};
    String titles11[] = {"MINI","SEDAN","PRIME","SHUTTLE","AUTO","CAFE"};
    int count11[] = {1,0,0,0,0,0};
    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view
    public static String NAME = "Spinno Singh";
    public static String EMAIL = "spinno@abc.com";
    public static int PROFILE = R.drawable.men;
    private Toolbar toolbar;                              // Declaring the Toolbar Object

    public static RecyclerView mRecyclerView;                           // Declaring RecyclerView
    public static  RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    public static  RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    public static DrawerLayout Drawer;                                  // Declaring DrawerLayout
    private static int RESULT_LOAD_IMG = 1;
    public  static Bitmap bitmap1;
    LinearLayout goonline,selctanthr;
    TextView cn,cno;
    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    public static Button cancel2;

    View layout,layout2;
    public static TextView text,textforschedule,cancel,confirm,text22,cabtype,couponsavailable;
    Typeface font;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newmain);

    /* Assinging the toolbar object ot the view
    and setting the the Action bar to our toolbar
     */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
            toolbar.setTitle("Go Online");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.drawable.icon);
        setSupportActionBar(toolbar);


        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.men);



        // settoolbarpadding();
        ctc=getApplicationContext();
        mainact = Goonlineactivity2.this;

        //cd = new ConnectionDetector(ctc);
        //gps= new GPStracker(ctc);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }


        font =Typeface.createFromAsset(ctc.getAssets(), "fonts/AllerDisplay.ttf");

        // Getting Google Play availability status









        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new MyAdapter(Goonlineactivity2.this,TITLES,ICONS,NAME,EMAIL,PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager



        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(Goonlineactivity2.this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        };
        goonline = (LinearLayout)findViewById(R.id.llg);
        selctanthr = (LinearLayout)findViewById(R.id.lls);
        cn = (TextView)findViewById(R.id.textView4);
        cno = (TextView)findViewById(R.id.textView5);
        Bundle bundle = getIntent().getExtras();
        cn.setText(bundle.getString("carname",null)+"");
        cno.setText(bundle.getString("carno", null) + "");
        goonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Goonlineactivity2.this,MainActivity.class);
                in.putExtra("carname",cn.getText().toString());
                in.putExtra("carno",cno.getText().toString());
                startActivity(in);

            }
        });
        selctanthr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Goonlineactivity2.this,Carselectionactivity.class);
                startActivity(in);
                finish();
            }
        });
        // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

    }

    public void showcamerdialog() {

        final Dialog dialog = new Dialog(Goonlineactivity2.this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=dialog.getWindow();
        dialog.setCancelable(false);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.camerdialog);
        cancel2 = (Button)dialog.findViewById(R.id.button1);
        LinearLayout button = (LinearLayout)dialog.findViewById(R.id.layout12);
        LinearLayout button1 = (LinearLayout)dialog.findViewById(R.id.layout13);
        Typeface font=Typeface.createFromAsset(Goonlineactivity2.this.getAssets(), "fonts/AllerDisplay.ttf");
        cancel2.setTypeface(font);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dispatchTakePictureIntent(11);



            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

            }
        });
        cancel2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch(actionCode) {
            case 11:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, 11);
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        ctc.sendBroadcast(mediaScanIntent);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //Toast.makeText(getActivity(),""+requestCode+" "+resultCode,Toast.LENGTH_SHORT).show();
            switch(requestCode){
                case 11:
                    if(resultCode!=0){


                        handleBigCameraPhoto();
                    }
                case 1:
                    if (requestCode == RESULT_LOAD_IMG && resultCode != 0
                            && null != data) {
                        // Get the Image from data

                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        // Get the cursor
                        Cursor cursor = ctc.getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgDecodableString = cursor.getString(columnIndex);
                        cursor.close();
                        Log.e("ggggg222", "" + imgDecodableString);
                        //img.setImageResource(android.R.color.transparent);
                        // Set the Image in ImageView after decoding the String
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(imgDecodableString, options);
                        final int REQUIRED_SIZE = 400;
                        int scale = 1;
                        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                            scale *= 2;
                        options.inSampleSize = scale;
                        options.inJustDecodeBounds = false;
                        bitmap1 = BitmapFactory.decodeFile(imgDecodableString, options);

                        mAdapter = new MyAdapter(Goonlineactivity2.this,TITLES,ICONS,NAME,EMAIL,PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
                        // And passing the titles,icons,header view name, header view email,
                        // and header view profile picture

                        mRecyclerView.setAdapter(mAdapter);
                    }
                default:{
                    // Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();
                }
            }

            // When an Image is picked
            // Toast.makeText(getActivity(),""+requestCode+" "+resultCode,Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(ctc, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private static String getAlbumName() {
        return "CameraSample";
    }
    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            galleryAddPic();
            setPic();

            mCurrentPhotoPath = null;
        }

    }

    private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW =200;
        int targetH = 300;


		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        bitmap1 = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        Log.e("bitmap", "" + bitmap1);
        mAdapter = new MyAdapter(Goonlineactivity2.this,TITLES,ICONS,NAME,EMAIL,PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);
        //profile11.setImageBitmap(bitmap1);

       		/* Associate the Bitmap to the ImageView */

    }


    private static File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            //Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private static File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }


    @Override
    public void onResume() {
        // Toast.makeText(MainActivity.this, "resume", Toast.LENGTH_SHORT).show();
        // mapView.onResume();
        super.onResume();
        mAdapter = new MyAdapter(Goonlineactivity2.this,TITLES,ICONS,NAME,EMAIL,PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);
        // getthetracker();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //  mapView.onLowMemory();
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
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = Goonlineactivity2.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Goonlineactivity2.this.getResources().getColor(R.color.theme_color));
        } else {
            Window window = Goonlineactivity2.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void settoolbarpadding(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            toolbar.setPadding(0, 4, 0, 0);
        } else {
            toolbar.setPadding(0, 4, 0, 0);
        }
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        // Creating a ViewHolder which extends the RecyclerView View Holder
        // ViewHolder are used to to store the inflated views in order to recycle them

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            int Holderid;

            TextView textView;
            ImageView imageView;
            public ImageView profile11;
            TextView Name;
            TextView email;
            LinearLayout itemll,llforprof;

            public ViewHolder(View itemView,int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
                super(itemView);


                // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

                if(ViewType == TYPE_ITEM) {
                    textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
                    imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                    itemll=(LinearLayout)itemView.findViewById(R.id.llfornavi);
                    itemll.setOnClickListener(this);
                    // Creating ImageView object with the id of ImageView from item_row.xml
                    Holderid = 1;

                    // setting holder id as 1 as the object being populated are of type item row
                }
                else{
                    Typeface font=Typeface.createFromAsset(ctc.getAssets(), "fonts/AllerDisplay.ttf");

                    Name = (TextView) itemView.findViewById(R.id.name);         // Creating Text View object from header.xml for name
                    email = (TextView) itemView.findViewById(R.id.email);       // Creating Text View object from header.xml for email
                    Name.setTypeface(font);
                    email.setTypeface(font);
                    profile11 = (ImageView) itemView.findViewById(R.id.circleView);
                    llforprof=(LinearLayout)itemView.findViewById(R.id.llforprofile);
                    profile11.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //MainActivity.Drawer.closeDrawer(Gravity.LEFT);
                            showcamerdialog();
                        }
                    });
                    llforprof.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent in = new Intent(ctc2, Profileactivity.class);
                            ctc2.startActivity(in);
                        }
                    });

                    // Creating Image view object from header.xml for profile pic
                    Holderid = 0;
                }
            }


            @Override
            public void onClick(View v) {

                 if(mNavTitles[getPosition()-1].equals("Emergency Contact")) {
                     Intent in = new Intent(ctc2, Emergencyactivity.class);
                     ctc2.startActivity(in);
                }
                else if(mNavTitles[getPosition()-1].equals("About")) {
                     Intent in = new Intent(ctc2, Aboutactivity.class);
                     ctc2.startActivity(in);
                }
                else if(mNavTitles[getPosition()-1].equals("Report Issue")) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto","abc@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Report Issue Regarding Taxi App");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));

                    emailIntent.setType("text/plain");


                }
                else if(mNavTitles[getPosition()-1].equals("Call Support")) {
                    String posted_by = "0000000000";
                    String uri = "tel:" + posted_by.trim() ;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }
                else{
                    //
                }
            }
        }



        MyAdapter(Goonlineactivity2 mainActivity, String Titles[], int Icons[], String Name, String Email, int Profile){ // MyAdapter Constructor with titles and icons parameter
            // titles, icons, name, email, profile pic are passed from the main activity as we
            mNavTitles = Titles;                //have seen earlier
            mIcons = Icons;
            name = Name;
            email = Email;
            profile = Profile;
            ctc2= mainActivity;
            //here we assign those passed values to the values we declared here
            //in adapter


        }



        //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
        //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
        // if the viewType is TYPE_HEADER
        // and pass it to the view holder

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false); //Inflating the layout

                ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

                return vhItem; // Returning the created object

                //inflate your layout and pass it to view holder

            } else if (viewType == TYPE_HEADER) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false); //Inflating the layout

                ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

                return vhHeader; //returning the object created


            }
            return null;

        }

        //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
        // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
        // which view type is being created 1 for item row
        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
            if(holder.Holderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
                // position by 1 and pass it to the holder while setting the text and image
                holder.textView.setText(mNavTitles[position - 1]); // Setting the Text with the array of our Titles
                holder.imageView.setImageResource(mIcons[position -1]);// Settimg the image with array of our icons
            }
            else{

                holder.profile11.setImageBitmap(bitmap1);           // Similarly we set the resources for header view
                holder.Name.setText(name);
                holder.email.setText(email);

            }

        }

        // This method returns the number of items present in the list
        @Override
        public int getItemCount() {
            return mNavTitles.length+1; // the number of items in the list will be +1 the titles including the header view.
        }


        // Witht the following method we check what type of view is being passed
        @Override
        public int getItemViewType(int position) {
            if (isPositionHeader(position))
                return TYPE_HEADER;

            return TYPE_ITEM;
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }

    }
}


////https://youtu.be/HPWYorS68WE