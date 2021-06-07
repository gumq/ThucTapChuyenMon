package com.tranlequyen.appdubaothoitiet;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class GPStracker implements LocationListener {

    Context context;

    public GPStracker (Context c) {
        context = c;
    }

    public Location getLocation(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, R.string.notGrantedLocation, Toast.LENGTH_LONG).show();
                return null;
            }
        }

        LocationManager locationManager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE);
        boolean isGPSenabled = locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER);
        if (isGPSenabled) {
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,5000,0,this);
            Location location = locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER);
            return location;
        } else {
            Toast.makeText(context, R.string.enableGPS, Toast.LENGTH_LONG).show();
            return null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
