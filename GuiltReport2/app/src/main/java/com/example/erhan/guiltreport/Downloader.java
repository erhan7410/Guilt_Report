package com.example.erhan.guiltreport;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader extends AsyncTask<Void,Integer,String> {

    Context c;
    String address;
    ListView lv;
    String data;
    ProgressDialog pd;

    public Downloader(Context c, String adress, ListView lv){
        this.c = c;
        this.address = adress;
        this.lv = lv;
    }

    @Override
    protected String doInBackground(Void... params) {
        data = downloadData();

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //CREATE PROGRESS DIALOG
        pd = new ProgressDialog(c);
        pd.setTitle("Fetch Data");
        pd.setMessage("Fethcing Data, Please wait");
        pd.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        s = data;
        super.onPostExecute(s);

        pd.dismiss();

        if(s != null){
            Parser p = new Parser(c,s,lv);  //CALL parser if download success
            p.execute();
        }else{
            Toast.makeText(c,"Unable to download data",Toast.LENGTH_SHORT).show();
        }
    }

    //DOWNLOAD THE DATA FROM SERVER
    private String downloadData() {
        InputStream is = null;
        String line = null;

        try{
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(con.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();

            if(br != null){
                while((line=br.readLine()) != null){
                    sb.append(line+"\n");
                }
            }else{
                return null;
            }

            return sb.toString();
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
