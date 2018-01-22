package com.example.erhan.guiltreport;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainScreen extends AppCompatActivity {
    String get_report_url = "http://10.0.2.2/getreport.php";
    static String username;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");   //GET USERNAME FROM LOGIN

        lv = (ListView) findViewById(R.id.lv);   //LIST OF HEADERS

        //call d.execute() for download data
        final Downloader d = new Downloader(MainScreen.this,get_report_url,lv);
        d.execute();

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainScreen.this,AddNewReport.class);      //ADD NEW REPORT BUTTON LİSTENER
                startActivity(i);
            }
        });
    }
}
