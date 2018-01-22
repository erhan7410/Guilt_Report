package com.example.erhan.guiltreport;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parser extends AsyncTask<Void,Integer,Integer> {

    Context c;          //mainscreen context
    ListView lv;        //lv of mainscreen
    String data;        //downloadad data(json)
    private List<List<String>> all_reports = new ArrayList<List<String>>();         //list of all reports
    ArrayList<String> reports = new ArrayList<>();      //just holds the headers
    ProgressDialog pd;      //progress dialog

    public Parser(Context c, String data, ListView lv) {
        this.c = c;
        this.lv = lv;
        this.data = data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //CREATE PROGRESS DIALOG
        pd = new ProgressDialog(c);
        pd.setTitle("Parser");
        pd.setMessage("Parsing .... Please wait");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parse();       //parse the data
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        pd.dismiss();       //dissmiss the progress dialog

        if(integer == 1){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,reports);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<String>clicked_row = findRow(reports.get(position));
                    Intent intent = new Intent(c,ViewReport.class);     //CREATE NEW INTENT
                    intent.putExtra("row_ID",clicked_row.get(0));       //ADD row_id,username,header,text and location to intent
                    intent.putExtra("username",clicked_row.get(1));
                    intent.putExtra("header",clicked_row.get(2));
                    intent.putExtra("text",clicked_row.get(3));
                    intent.putExtra("location_x",clicked_row.get(4));
                    intent.putExtra("location_y",clicked_row.get(5));
                    c.startActivity(intent);                            //RUN VIEW REPORT ACTIVITY

                }
            });
        }else{
            Toast.makeText(c,"Unable to parse data",Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> findRow(String s){     //returns the clicked row's all informations
        for(List<String> l : all_reports) {
            for(String str : l) {
                if(str.equals(s)) {
                    return l;
                }
            }
        }
        return null;
    }

    //PARSE THE json data
    private int parse(){
        try {
            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            reports.clear();
            ArrayList<String> one_report;
            for (int i=0;i<ja.length();i++){
                one_report  = new ArrayList<>();
                jo = ja.getJSONObject(i);

                String rowid = jo.getString("row_id");
                String username = jo.getString("username");
                String header = jo.getString("header");
                String text = jo.getString("text");
                String loc_x = jo.getString("location_x");
                String loc_y = jo.getString("location_y");

                one_report.add(rowid);
                one_report.add(username);
                one_report.add(header);
                one_report.add(text);
                one_report.add(loc_x);
                one_report.add(loc_y);

                all_reports.add(one_report);
                reports.add(header);    //add headers of reports to listview
            }

            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
