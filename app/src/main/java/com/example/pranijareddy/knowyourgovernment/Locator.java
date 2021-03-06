package com.example.pranijareddy.knowyourgovernment;

/**
 * Created by Pranijareddy on 4/11/2017.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;


public class Locator  {

    private MainActivity owner;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public Locator(MainActivity activity) {
        owner = activity;
        if (checkPermission()) {
            setUpLocationManager();
            determineLocation();
           // if (loc != null)
             //   owner.doLocationWork(loc.getLatitude(), loc.getLongitude());
        }
    }
    public void setUpLocationManager() {

        if (locationManager != null)
            return;
        if (!checkPermission())
            return;

        locationManager = (LocationManager) owner.getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                owner.doLocationWork(location.getLatitude(), location.getLongitude());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Nothing to do here
            }

            public void onProviderEnabled(String provider) {
                // Nothing to do here
            }

            public void onProviderDisabled(String provider) {
                // Nothing to do here
            }
        };
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 100, 0, locationListener);
    }

    public void shutdown() {
        locationManager.removeUpdates(locationListener);
        locationManager = null;
    }

    // This method chooses the best location provider Network ==> Passive ==> GPS
    public void determineLocation() {

        if (!checkPermission())
            return;

        if (locationManager == null)
            setUpLocationManager();

        if (locationManager != null) {
            Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (loc != null) {
                owner.doLocationWork(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(owner, "Using " + LocationManager.NETWORK_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (locationManager != null) {
            Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (loc != null) {
                owner.doLocationWork(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(owner, "Using " + LocationManager.PASSIVE_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (locationManager != null) {
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                owner.doLocationWork(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(owner, "Using " + LocationManager.GPS_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // If you get here, you got no location at all
        owner.noLocationAvailable();
        return;
    }


    // This method asks the user for Locations permissions (if it does not already have them)
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(owner, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(owner,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
            return false;
        }
        return true;
    }
}
