package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecast extends AppCompatActivity {
    String TAG ="WeatherForcast";
    ProgressBar progressBar;
    ImageView imageView;
    TextView currentT;
    TextView minT;
    TextView maxT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        ForecastQuery forecast = new ForecastQuery();
        //Get the variable called url that was passed into the intent coming in
        String url = getIntent().getStringExtra("url");
        forecast.execute(url);
    }



    protected class ForecastQuery extends AsyncTask<String, Integer, String>{
        String min;
        String max;
        String current;
        Bitmap weatherbit;
        String icon;

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            currentT = findViewById(R.id.textView3);
            currentT.setText("Current: "+current);
            minT = findViewById(R.id.textView4);
            minT.setText("Min: "+min);
            maxT = findViewById(R.id.textView5);
            maxT.setText("Max: "+max);
            imageView = findViewById(R.id.imageView3);
            imageView.setImageBitmap(weatherbit);
            progressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        protected String doInBackground(String... strings)  {
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                parse(conn.getInputStream());
                String fileName = icon+".png";
                if (fileExistance(fileName)){
                    FileInputStream fis = null;
                    try {    fis = openFileInput(fileName);   }
                    catch (FileNotFoundException e) {    e.printStackTrace();  }
                    weatherbit = BitmapFactory.decodeStream(fis);
                    Log.i(TAG, fileName+": Found Locally ");
                }
                else{
                    String iconURL = "http://openweathermap.org/img/w/"+fileName;
                    weatherbit = new HttpUtils().getImage(iconURL);
                    publishProgress(100);
                    FileOutputStream outputStream = openFileOutput( icon + ".png", Context.MODE_PRIVATE);
                    weatherbit.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Log.i(TAG, fileName+": Downloaded ");
                }

            }catch (IOException e){
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

//            return conn.getInputStream();

            return "hi";
        }

        public List parse(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(in, null);
                parser.nextTag();
                String name;
                parser.require(XmlPullParser.START_TAG, null, "current");
                while (parser.getEventType() != XmlPullParser.END_DOCUMENT){
                    name = parser.getName();
                    if (parser.getEventType() == XmlPullParser.START_TAG){
                        if (name.equals("temperature")){
                            current = parser.getAttributeValue(null,"value");
                            publishProgress(25);
                            min = parser.getAttributeValue(null,"min");
                            publishProgress(50);
                            max = parser.getAttributeValue(null, "max");
                            publishProgress(75);
                        }
                        else if (name.equals("weather")){
                            icon = parser.getAttributeValue(null,"icon");
                        }
                    }

                    parser.next();
                }

            } finally {
                in.close();
            }
            return null;
        }

    }
    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();   }
    private class HttpUtils {
        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

    }
}
