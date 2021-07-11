package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText text1;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1=findViewById(R.id.editText);
        result=findViewById(R.id.textView4);
    }
    public void getData(View view){
        Download  a1=new Download();
        a1.execute("http://api.openweathermap.org/data/2.5/weather?q="+text1.getText().toString()+"&appid=0085d8a1e15b553a10adbd6eb69ea777");
//        result.setText();
        InputMethodManager mgr=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(text1.getWindowToken(),0);
    }

    public class Download extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {

            try {
                URL url=new URL(urls[0]);
                HttpURLConnection urlConnection;
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                String result="";
                int data=reader.read();
                while(data!=-1){
                    result+=(char)data;
                    data=reader.read();
                }
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return "failed";
            }

        }
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try {
                JSONObject object=new JSONObject(s);
//                String value=object.getString("weather");
                String value=object.getString("main");
                JSONObject object1=new JSONObject(value);
//                Log.i("Weather info",value);
//                JSONArray arr=new JSONArray(value);
//                String results="";
                String result1="";
                String maxtemp=object1.getString("temp_max");
                String mintemp=object1.getString("temp_min");
                String humidity=object1.getString("humidity");
                String pressure=object1.getString("pressure");
                result1="Max-Temp  "+maxtemp  +"\nMin-Temp "+mintemp +"\nHumidity"+humidity;
//                for(int i=0;i<arr.length();i++){
//                    JSONObject ob1=arr.getJSONObject(i);
//                    String main=ob1.getString("main");
//                    Log.i("Main",main);
//                    String desc=ob1.getString("description");
//                    Log.i("Description",desc);
//                    String result1 = "Main:" + main + "\r\n Description:" + desc;
                result.setText(result1);
//
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}