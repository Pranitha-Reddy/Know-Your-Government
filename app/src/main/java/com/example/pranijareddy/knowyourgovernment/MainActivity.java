package com.example.pranijareddy.knowyourgovernment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    private boolean network=true;
    private List<Official> offList = new ArrayList<>();  // Main content is here
    private RecyclerView recyclerView; // Layout's recyclerview
    private OfficialAdapter mAdapter; // Data to recyclerview adapter
    // private StockDataDownloader stockDataDownloader=new StockDataDownloader();
    private static final int ADD_CODE = 1;
    private TextView location;
   // private String zip="60616";
    private String zipcode="";
    private Locator locator;
    String loca="";
    String locat="";
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(networkCheck()){
        locator = new Locator(this);
        location=(TextView)findViewById(R.id.location);
        recyclerView = (RecyclerView) findViewById(R.id.RecycleList);
        mAdapter = new OfficialAdapter(offList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadCivicData(zipcode);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Data cannot be accesses/loaded without an internet connection");
            builder.setTitle("No Network Connection");
            AlertDialog dialog = builder.create();
            dialog.show();
           }
           flag=false;
    }
    public boolean networkCheck() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    public void loadCivicData(String z) {
        if(networkCheck()){
        if(zipcode.isEmpty()){AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No Location is entered");
            builder.setTitle("No Location");
            AlertDialog dialog = builder.create();
            dialog.show();}
        else {
           offList.clear();
        new CivicInfoDownloader(this).execute(z);}}
        else{//location.setText("No Data for Location");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Data cannot be accesses/loaded without an internet connection");
            builder.setTitle("No Network Connection");
            AlertDialog dialog = builder.create();
            dialog.show();}
    }
    public void loadData(ArrayList<Official> cList,String loc) {
        loca=loc;
      location.setText(loc);
        offList.addAll(cList);
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about:
                Intent inten = new Intent(this, About.class);
                //inten.putExtra(Intent.EXTRA_TEXT, MainActivity.class);
                startActivity(inten);
                return true;
            case R.id.location:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final EditText et = new EditText(this);
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                et.setGravity(Gravity.CENTER_HORIZONTAL);
                builder.setView(et);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        locat=et.getText().toString();
                        doLocationName();

                        loadCivicData(zipcode);
                    }
                });
                builder.setNegativeButton("NO WAY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                builder.setTitle("Enter a City, State or a Zip Code ?");

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks
        int pos = recyclerView.getChildLayoutPosition(v);
        Official c = offList.get(pos);
        Intent intent = new Intent(MainActivity.this, OfficialActivity.class);
        intent.putExtra(Official.class.getName(), c);
        intent.putExtra("location",loca);
        startActivity(intent);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 5) {

            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        locator.setUpLocationManager();
                        locator.determineLocation();
                    } else {
                        Toast.makeText(this, "Location permission was denied - cannot determine address", Toast.LENGTH_LONG).show();

                    }
                }
            }
        }

    }
    public void doLocationName() {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = null;
           addresses = geocoder.getFromLocationName(locat, 1);

            if (addresses.size() == 0) {
                Toast.makeText(this, "Please enter proper location", Toast.LENGTH_SHORT).show();

            }
            for (Address address : addresses) {
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    doLocationWork(address.getLatitude(), address.getLongitude());
                }
            }
          // return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void doLocationWork(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            StringBuilder sb = new StringBuilder();
            for (Address ad : addresses) {
                //sb.append("\nAddress\n\n");
                for (int i = 1; i < ad.getMaxAddressLineIndex(); i++)
                    sb.append(ad.getPostalCode());
                //sb.append("\t" + ad.getCountryName() + " (" + ad.getCountryCode() + ")\n");
            }
            zipcode=sb.toString();

        } catch (IOException e) {
            Toast.makeText(this, "Cannot acquire ZIP code from Lat/Lon.\n\nNetwork resources unavailable", Toast.LENGTH_LONG).show();
        }


    }

    public void noLocationAvailable() {
        Toast.makeText(this, "No location providers were available", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onResume() {
       super.onResume();
        if(flag){loadCivicData(zipcode);}
    }
    @Override
    protected void onPause() {
        super.onPause();
        flag=true;

    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return false;
    }
}
