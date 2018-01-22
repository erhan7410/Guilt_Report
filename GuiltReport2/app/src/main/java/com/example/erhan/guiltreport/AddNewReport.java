package com.example.erhan.guiltreport;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class AddNewReport extends AppCompatActivity {
    static ImageView imageView;    //photo taken by user
    EditText editText1;     //header
    EditText editText2;     //explanation of guilt
    Button cam_button;      //camera button
    Button loc_button;      //locaiton button
    private int row_ID;    //row id of report
    String loc_x ="", loc_y="", text="", header="", username;
    private LocationManager locationManager;
    private LocationListener locationListener;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_report);

        imageView = (ImageView) findViewById(R.id.imageview1);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        cam_button = (Button) findViewById(R.id.add_photo_button);
        loc_button = (Button) findViewById(R.id.add_location_button);

        extras = getIntent().getExtras();

        if(extras != null){
            loc_button.setVisibility(View.GONE);        //IF EXTRAS IS NULL DO NOT SHOW LOCATION BUTTON
            cam_button.setVisibility(View.GONE);        //IF EXTRAS IS NULL DO NOT SHOW CAMERA BUTTON

            row_ID = extras.getInt("row_ID");           //GET ALL VARIABLES FROM INTENT
            username = extras.getString("username");
            header = extras.getString("header");
            text = extras.getString("explanation");
            loc_x = extras.getString("loc_x");
            loc_y = extras.getString("loc_y");

            imageView.setImageDrawable(ViewReport.getImageView().getDrawable());        //SET VARIABLES
            editText1.setText(header);
            editText2.setText(text);

        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {              //GET LONG AND LAT VALUES OF LOCATION
                loc_x = String.valueOf(location.getLatitude());
                loc_y = String.valueOf(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        //PERMISSIONS FOR LOCATION LISTENER
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET
            }, 10);
            return;
        }else{
            configureButton();
        }

    }

    //SAVE BUTTON LISTENER
    public void saveReport(View view){
        if(extras != null){         //FOR EDIT
            int check = 0;
            String type = "update";
            if (!String.valueOf(row_ID).equals("") && !editText1.getText().toString().equals("") && !editText2.getText().toString().equals("")){
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.execute(type,String.valueOf(row_ID),editText1.getText().toString(),editText2.getText().toString());
                check++;
            }else{
                Toast.makeText(this,"All info's must be entered!",Toast.LENGTH_SHORT).show();
            }

            if(check == 1){
                Intent intent = new Intent(AddNewReport.this,ViewReport.class);
                intent.putExtra("row_ID",String.valueOf(row_ID));
                intent.putExtra("username",username);
                intent.putExtra("header",editText1.getText().toString());
                intent.putExtra("text",editText2.getText().toString());
                intent.putExtra("location_x",loc_x);
                intent.putExtra("location_y",loc_y);
                startActivity(intent);
            }
        }else{      //FOR INSERT
            username = MainScreen.username;
            header = editText1.getText().toString();
            text = editText2.getText().toString();

            String type = "insert";
            if (!username.equals("") && !header.equals("") && !text.equals("") && !loc_x.equals("") && !loc_y.equals("") && imageView.getDrawable()!= null){
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.execute(type,username,header,text,loc_x,loc_y);
            }else{
                Toast.makeText(this,"All info's must be entered!",Toast.LENGTH_SHORT).show();
           }

        }
    }

    //CAMERA BUTTON LISTENER
    public void addPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    //GETS THE TAKEN PHOTO AND SET IMAGEVIEW
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    //LOCATION BUTTON LISTENER
    public void configureButton() {
        loc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location != null){
                    loc_x = String.valueOf(location.getLatitude());
                    loc_y = String.valueOf(location.getLongitude());
                }else{
                    location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    loc_x = String.valueOf(location.getLatitude());
                    loc_y = String.valueOf(location.getLongitude());
                }
                Toast.makeText(AddNewReport.this,"Your location added to report!"+"locx="+loc_x+" locy="+loc_y,Toast.LENGTH_SHORT).show();
            }
        });

    }

    //RETURNS THE BITMAP OF IMAGEVIEW
    public static  Bitmap getImageBitmap(){
        imageView.buildDrawingCache();
        return imageView.getDrawingCache();
    }

}
