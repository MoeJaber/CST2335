package com.example.mrarabwarfare.lab1;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.content.*;
import android.graphics.*;

import java.io.*;
import java.net.*;
import java.net.URL;
import android.util.*;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.*;
import org.xmlpull.v1.*;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class WeatherForecast extends AppCompatActivity {

    ProgressBar mProgress;
    RelativeLayout pBar;
    // ForecastQuery forecast;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setVisibility(View.VISIBLE);
        new ForecastQuery().execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
        Log.i("WeatherForecast","In onCreate Weather Forecast");


        // WeatherForecast.ForecastQuery forecast = new WeatherForecast.ForecastQuery();
        //forecast = new ForecastQuery(this);
        // forecast.execute();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "WeatherForecast Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.mrarabwarfare.lab1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        Bitmap image;
        Bitmap bm;
        String value ;
        String min ;
        String max ;
        String icon ;

        @Override
        protected String doInBackground(String... args) {

            try {

                String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
                Log.i("WeatherForecast","doInBackground");
                URL url = new URL(urlString);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                parser.nextTag();

                int event;
                String TAG_ITEM = "temperature";
                String TAG_ITEM2 = "weather";
                while ((event = parser.next()) != XmlPullParser.END_DOCUMENT) {
                    if (event == XmlPullParser.START_TAG) {
                        String tag = parser.getName();
                        if (TAG_ITEM.equals(tag)) {
                            value = parser.getAttributeValue(null, "value");
                            try {
                                Thread.sleep(1000);
                                publishProgress(25);
                            }
                            catch (InterruptedException e){

                            }
                            min = parser.getAttributeValue(null, "min");
                            try {
                                Thread.sleep(1000);
                                publishProgress(50);
                            }
                            catch (InterruptedException e){

                            }
                            max = parser.getAttributeValue(null, "max");
                            try {
                                Thread.sleep(1000);
                                publishProgress(75);
                            }
                            catch (InterruptedException e){

                            }
                            Log.d("WeatherForecast", parser.getAttributeValue(null, "value"));
                            Log.d("WeatherForecast", parser.getAttributeValue(null, "min"));
                            Log.d("WeatherForecast", parser.getAttributeValue(null, "max"));
                            conn.getInputStream().close();
                        }

                        else if (TAG_ITEM2.equals(tag)){
                            icon = parser.getAttributeValue(null, "icon");
                            Log.d("WeatherForecast", parser.getAttributeValue(null, "icon"));
                            downloadImage();

                        }
                    }
                }

            } catch (IOException a) {


            }
            catch (XmlPullParserException e){

            }

            return icon;
        }
        @Override
        protected void onProgressUpdate(Integer... progress)  {

            mProgress.setVisibility(View.VISIBLE);
            mProgress.setProgress(progress[0]);


            Log.i("WeatherForecast","Progressbar status:   " + progress[0]);

            Log.i("WeatherForecast","ProgressUpdate " + icon);


        }
        public void downloadImage(){

            try {
                String iconURLString = "http://openweathermap.org/img/w/" + icon + ".png";
                URL urlImg = new URL(iconURLString);

                FileOutputStream outputStream = openFileOutput(icon + ".png",  Context.MODE_PRIVATE);


                    image = getImage(urlImg);

                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();


                    publishProgress(100);
                    //Log.i("WeatherForecast", getFileStreamPath(icon + ".png"));

                    Log.i("WeatherForecast", "http://openweathermap.org/img/w/" + icon + ".png");


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Bitmap getImage(URL url) {

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    Log.i("WeatherForecast","NULLL BIT IMG" );
                return null;
            } catch (Exception e) {
                Log.i("WeatherForecast","bit IMAGE EXCEPTION" );
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        @Override
        protected void onPostExecute(String result){

            TextView minText;
            TextView maxText;
            TextView curText;
            ImageView imageForWeather;

            mProgress.setVisibility(View.INVISIBLE);
            minText = (TextView)findViewById(R.id.minTempText);
            curText = (TextView)findViewById(R.id.maxTempText);
            maxText = (TextView)findViewById(R.id.curTempText);

            imageForWeather = (ImageView) findViewById(R.id.imageViewWeather);


            if(fileExistance(icon +".png") ) {
                //if(fileExistance(outputStream.toString())) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(icon + ".png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // ImageView imageForWeather;
                // imageForWeather = (ImageView) findViewById(R.id.imageViewWeather);
                bm = BitmapFactory.decodeStream(fis);
                 imageForWeather.setImageBitmap(bm);
                imageForWeather.setImageBitmap(image);

                Log.i("WeatherForecast", "Local Image");
                Log.i("WeatherForecast", "http://openweathermap.org/img/w/" + icon + ".png");


            }
            else {
                imageForWeather.setImageBitmap(image);
                Log.i("WeatherForecast", "Download Image");
            }

            //imageForWeather.setImageBitmap(bm);

            minText.setText(min);
            maxText.setText(max);
            curText.setText(value);



            Log.i("WeatherForecast","PostExecute" );

        }



        public boolean fileExistance(String fname){

            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();

        }



    }
    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "WeatherForecast Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.mrarabwarfare.lab1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}
