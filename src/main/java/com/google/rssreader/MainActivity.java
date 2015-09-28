package com.google.rssreader;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.Toast;

import com.google.rssreader.model.MyAsyncTask;

/**
 * Created by Roman on 26.09.2015.
 */
public class MainActivity extends Activity {

    ListView list;
    ConnectivityManager cm;
    NetworkInfo networkInfo;
    String noInternetConnection;
    // Insert image URL
    private static final String URL = "http://www.cbc.ca/cmlink/rss-sports";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.listView);
        new MyAsyncTask(MainActivity.this, list).execute(URL);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = cm.getActiveNetworkInfo();

        noInternetConnection = getResources().getString(R.string.noIC);
        //check internet connection
        if (networkInfo == null) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    noInternetConnection,
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();

        } else {
            // Execute DownloadXML AsyncTask
            new MyAsyncTask(MainActivity.this, list).execute(URL);
        }
    }
}