package edu.ksu.cs.benign;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    Location location;
    protected LocationManager locationManager;

    
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; 

    
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10; 

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                showSettingsAlert();
            } else {
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                } else if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }
        } catch (RuntimeException e) {
            Log.d("BenignGPSTracker", "Exception occured while accessing location");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return location;
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            try {
                locationManager.removeUpdates(GPSTracker.this);
            } catch (IllegalArgumentException e) {
                Log.d("BenignGPSTracker", "Security Exception when stopping gps use");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    @JavascriptInterface
    public double getLatitude() {
        if (location != null) {
            return location.getLatitude();
        } else {
            
            return 42.42;
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("GPS Settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location new_location) {
        this.location = new_location;
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopUsingGPS();
        super.onDestroy();
    }
}