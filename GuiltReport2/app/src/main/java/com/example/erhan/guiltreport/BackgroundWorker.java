package com.example.erhan.guiltreport;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class BackgroundWorker extends AsyncTask<String,String,String>{
    Context context;
    AlertDialog alertDialog;
    String username;    //name of user
    BackgroundWorker(Context ctx){
        context = ctx;
    }

    //URL's for comminication between server and android
    String upload_url = "http://10.0.2.2/upload.php";
    String login_url = "http://10.0.2.2/login.php";
    String register_url = "http://10.0.2.2/register.php";
    String insert_url = "http://10.0.2.2/addnewreport.php";
    String update_url = "http://10.0.2.2/update.php";
    String delete_url = "http://10.0.2.2/delete.php";
    String deleteImage_url = "http://10.0.2.2/deleteImage.php";

    @Override
    protected void onPreExecute() {
        //create alertdialog
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Operation Status");
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if(type.equals("login")){           //LOGIN REQUEST
            try {
                //GET PARAMS
                username = params[1];
                String password = params[2];

                //create the url
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //POST THE DATA to php file
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();;
                bufferedWriter.close();
                outputStream.close();

                //get result from php
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                //close all connections
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("register")){       //creates a new row of user_info database
            try {
                //GET PARAMS
                username = params[1];
                String password = params[2];
                String name = params[3];
                String surname = params[4];

                //create the url
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //POST THE DATA to php file
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                        +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("surname","UTF-8")+"="+URLEncoder.encode(surname,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();;
                bufferedWriter.close();
                outputStream.close();

                //get result of php file
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                //close connecitons
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("insert")){         //creates a new row of report_list database
            try {
                //get params
                username = params[1];
                String header = params[2];
                String text = params[3];
                String loc_x = params[4];
                String loc_y = params[5];

                //create the url
                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //post the data to php file
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("header","UTF-8")+"="+URLEncoder.encode(header,"UTF-8")+"&"
                        +URLEncoder.encode("text","UTF-8")+"="+URLEncoder.encode(text,"UTF-8")+"&"
                        +URLEncoder.encode("location_x","UTF-8")+"="+URLEncoder.encode(loc_x,"UTF-8")+"&"
                        +URLEncoder.encode("location_y","UTF-8")+"="+URLEncoder.encode(loc_y,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();;
                bufferedWriter.close();
                outputStream.close();

                //get result of php
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                //close connections
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("update")){        //updates row of report_list
            try {
                //get params
                String row_ID = params[1];
                String header = params[2];
                String text = params[3];

                //create the url
                URL url = new URL(update_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //post the data to php
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("row_ID","UTF-8")+"="+URLEncoder.encode(row_ID,"UTF-8")+"&"
                        +URLEncoder.encode("header","UTF-8")+"="+URLEncoder.encode(header,"UTF-8")+"&"
                        +URLEncoder.encode("text","UTF-8")+"="+URLEncoder.encode(text,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();;
                bufferedWriter.close();
                outputStream.close();

                //get result from php
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                //close connections
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("delete")){                //delete row from report_list, also deletes a row of photos
            try {
                //get params
                String row_ID = params[1];

                //create the url
                URL url = new URL(delete_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //post the data to php
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("row_ID","UTF-8")+"="+URLEncoder.encode(row_ID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();;
                bufferedWriter.close();
                outputStream.close();

                //get result of php
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                //close connections
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                //If a row of report_list deleted, delete the row of photos table that has same row_ID
                String delete_result = "";
                if(result.equals("Delete completed!"))
                    delete_result = deleteImage(row_ID);

                return delete_result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
        String temp = "Welcome "+username+"!";
        if(result.equals("Registiration completed!")){
            Intent intent = new Intent(context,LoginScreen.class);  //IF REGISTIRATION SUCCESS RUN LOGIN ACTIVITY
            context.startActivity(intent);
        }else if(result.equals(temp)){
            Intent intent = new Intent(context,MainScreen.class);   //IF LOGIN SUCCESS RUN MAIN ACTIVITY
            intent.putExtra("username",username);
            context.startActivity(intent);
        }else if(result.matches("Insert completed! Report id is : (.*)")){      //IF INSERT SUCCESS UPLOAD IMAGE TO DB AND RUN MAIN ACTIVITY
            String last_id = result.substring(33);
            uploadImage(last_id);
            Intent intent = new Intent(context,MainScreen.class);
            context.startActivity(intent);
        }else if(result.equals("Delete image completed!")){             //IF DELETE COMPELETED RUN THE MAIN ACTIVITY
            Intent intent = new Intent(context,MainScreen.class);
            context.startActivity(intent);
        }

    }

    private String deleteImage(String row_ID) {
        try {
            //create the url
            URL url = new URL(deleteImage_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            //post the data to php
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data = URLEncoder.encode("row_ID","UTF-8")+"="+URLEncoder.encode(row_ID,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();;
            bufferedWriter.close();
            outputStream.close();

            //get result of php
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line="";
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }

            //close connections
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();


            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //uploads the image to photos table
    public void uploadImage(final String r_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url,        //create a request
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");

                            //create alertdialog
                            AlertDialog alertDialog;
                            alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle("Operation Status");
                            alertDialog.setMessage(Response);
                            alertDialog.show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("row_id",r_id.trim());
                params.put("image",imageToString(AddNewReport.getImageBitmap()));
                return params;
            }
        };

        //send the request
        MySingleton.getmInstance(context).addToRequestQue(stringRequest);
    }

    //get encoded image
    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

}
