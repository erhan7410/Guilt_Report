package com.example.erhan.guiltreport;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;



public class ViewReport extends AppCompatActivity {
    TextView textView1;     //header
    TextView textView2;     //locaiton
    TextView textView3;     //explanation
    static ImageView imageView;   //report photo
    private int row_ID;
    private String download_url = "http://10.0.2.2/uploaded_images/";
    String loc_x, loc_y, username,r_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        imageView = (ImageView) findViewById(R.id.imageview1);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView2.setOnClickListener(new OnClickListener() {        //SHOWS THE LOCATION OF REPORT!!
            public void onClick(View v) {
                String url = "http://www.google.com/maps?q="+ loc_x + "+" + loc_y;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        Intent intent = getIntent();
        r_id = intent.getStringExtra("row_ID");
        row_ID = Integer.parseInt(r_id);
        username = intent.getStringExtra("username");
        String header = intent.getStringExtra("header");
        String text = intent.getStringExtra("text");
        loc_x = intent.getStringExtra("location_x");
        loc_y = intent.getStringExtra("location_y");

        download_url = download_url+r_id+".jpg";
        downloadImage();
        textView1.setText(header);
        textView2.setText("Click here to show report location\n" + loc_x + "," + loc_y);
        textView3.setText(text);
    }

    //CREATE THE MENU OF VIEW REPORTS ACTIVITY
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_report_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) // switch based on selected MenuItem's ID
        {
            case R.id.editItem:
                // create an Intent to launch the AddNewReport Activity
                Intent addNewReport = new Intent(this, AddNewReport.class);
                Bundle extras = new Bundle();

                extras.putString("header",textView1.getText().toString());
                extras.putString("loc_x",loc_x);
                extras.putString("loc_y",loc_y);
                extras.putString("explanation",textView3.getText().toString());
                extras.putInt("row_ID",row_ID);
                extras.putString("username",username);
                addNewReport.putExtras(extras);
                startActivity(addNewReport); // start the Activity
                return true;
            case R.id.deleteItem:
                String type = "delete";
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.execute(type,r_id);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // end switch
    } // end method onOptionsItemSelected

    //Download the image from server
    private void downloadImage(){
        ImageRequest imageRequest = new ImageRequest(download_url,
                new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //CREATE ALERT DIALOG
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(ViewReport.this).create();
                alertDialog.setTitle("Operation Status");
                alertDialog.setMessage("Error");
                alertDialog.show();
            }
        });

        //send image request
        MySingleton.getmInstance(ViewReport.this).addToRequestQue(imageRequest);
    }

    //returns the imageview of report
    public static ImageView getImageView(){
        return imageView;
    }

}
