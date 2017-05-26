package com.example.pranijareddy.knowyourgovernment;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Pranijareddy on 4/7/2017.
 */

public class CivicInfoDownloader extends AsyncTask<String, Void, String> {
    private MainActivity mainActivity;
    private int count;
    private String API="AIzaSyDCSAdgJX4pzWf9Tu3nvkBR7lcOiq3w0FI";


    private final String dataURL="https://www.googleapis.com/civicinfo/v2/representatives";
    public CivicInfoDownloader(MainActivity ma) {
        mainActivity = ma;
    }
    @Override
    protected void onPreExecute() {

    }
    @Override
    protected void onPostExecute(String s) {
        parseData p=new parseData();
        String loc= p.loc(s);
       ArrayList<Official> infoList = p.parse(s) ;
        mainActivity.loadData(infoList,loc);

    }
    @Override
    protected String doInBackground(String... params) {
       // String location=params[1];

        Uri.Builder buildURL = Uri.parse(dataURL).buildUpon();
        buildURL.appendQueryParameter("key",API);
        buildURL.appendQueryParameter("address", params[0]);

        String urlToUse = buildURL.build().toString();
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            //sb.append(location);
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }



        } catch (Exception e) {

            return null;
        }
        return sb.toString();
    }
}
