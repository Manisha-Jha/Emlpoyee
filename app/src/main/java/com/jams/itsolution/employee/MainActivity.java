package com.jams.itsolution.employee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterInterface{



    //https://raw.githubusercontent.com/KalpeshJadvani/EmpData/master/database.json
    ProgressDialog pd;


    private List<ListData> EmpList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataAdapter mAdapter;

    //JSON Node Names
    private static final String TAG_USER = "Employees";
    private static final String TAG_ID = "empId";
    private static final String TAG_NAME = "name";
    private static final String TAG_DES = "designation";
    private static final String TAG_PHOT = "photo";
    private static final String TAG_AGE = "age";
    private static final String TAG_GEN = "gender";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

       //  new JsonTask().execute("https://raw.githubusercontent.com/KalpeshJadvani/EmpData/master/database.json");

        // callApi();
        callApi(); // added by shubham

        mAdapter = new DataAdapter(EmpList,this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }



     public void callApi(){


         pd = new ProgressDialog(MainActivity.this);
         pd.setMessage("Please wait....");
         pd.setCancelable(false);
         pd.show();

         AndroidNetworking.get("https://raw.githubusercontent.com/KalpeshJadvani/EmpData/master/database.json")
                 .setPriority(Priority.LOW)
                 .build()
                 .getAsJSONObject(new JSONObjectRequestListener() {
                     @Override
                     public void onResponse(JSONObject response) {
                         // do anything with response
                         // Log.i("my","response ->"+response);

                         try {


                             setJsonArray(response.getJSONArray(TAG_USER));

                         } catch (JSONException e) {

                             e.printStackTrace();
                         }



                     }
                     @Override
                     public void onError(ANError error) {
                         Log.i("my","error ->"+error);
                     }
                 });


     }


     public void setJsonArray(JSONArray empArr){

         try {

             for(int i = 0 ; i< empArr.length(); i++){

                 // Storing  JSON item in a Variable
                 String id = empArr.getJSONObject(i).getString(TAG_ID);
                 String name = empArr.getJSONObject(i).getString(TAG_NAME);
                 String des = empArr.getJSONObject(i).getString(TAG_DES);
                 String phot = empArr.getJSONObject(i).getString(TAG_PHOT);
                 String age = empArr.getJSONObject(i).getString(TAG_AGE);
                 String gen = empArr.getJSONObject(i).getString(TAG_GEN);

                 ListData empData = new ListData(name, id, phot, des, age, gen);
                 EmpList.add(empData);
             }

             mAdapter.notifyDataSetChanged();

             if (pd.isShowing()){
                 pd.dismiss();
             }

         } catch (JSONException e) {

             e.printStackTrace();
         }

     }

    @Override
    public void itemClick(int pos) {


        ListData empData = EmpList.get(pos);

        Intent intent = new Intent(this, EmpShowSetails.class);
        intent.putExtra("ListData", empData);
        startActivity(intent);

    }



















   //   Extra code for json

    private class JsonTask extends AsyncTask<String, String, String> {

        JSONObject jObj = null;

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();

        }

        protected String doInBackground(String... params) {





            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                   // Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                // try parse the string to a JSON object
                try {
                    jObj = new JSONObject(buffer.toString());
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            try {
             //Getting JSON Array
                JSONObject c;


            for(int i = 0 ; i< jObj.getJSONArray(TAG_USER).length(); i++){

                     c = jObj.getJSONArray(TAG_USER).getJSONObject(i);

                // Storing  JSON item in a Variable
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String des = c.getString(TAG_DES);

                Log.i("my","id  - >"+ id);
                Log.i("my","name  - >"+ name);
                Log.i("my","des  - >"+ des);

            }


        } catch (JSONException e) {

            e.printStackTrace();
        }

        }
    }

}
