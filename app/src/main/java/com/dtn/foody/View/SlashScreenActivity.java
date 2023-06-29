package com.dtn.foody.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.dtn.foody.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class SlashScreenActivity extends AppCompatActivity {
    private TextView loading, version, created;
    private FusedLocationProviderClient fusedLocationClient;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slashscreen);
        sharedPreferences = getSharedPreferences("toado", MODE_PRIVATE);
        init();
// Chú ý là khi máy mới thì chưa có lastlocation để lấy nên có 2 cách
        // 1: Vào Maps cho hiện địa chỉ
        // 2: thay getLastLocation thành getCurrentLocation
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//
//
//        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                // Got last known location. In some rare situations this can be null.
//                if (location != null) {
//                    // Logic to handle location object
//                    Log.e("kiemtra", location.getLatitude() + " - " + location.getLatitude());
//                }
//            }
//        });
//        Location location = null;
//        location.setLatitude(20.9862426);
//        location.setLongitude(105.8111081);


        Log.e("kiemtra", "20.9862426");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Latitude", String.valueOf(20.9862426));
        editor.putString("Longtitude", String.valueOf(105.8111081));
        editor.commit();

        try{
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version.setText("Version " + packageInfo.versionName);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SlashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    // Khi back ve thi khong quay lai trang SlashScreen nua thi cho cham dut trang SlashScreen luon
                    finish();
                }
            },2000);
        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

    }
    public void init()
    {
        loading = (TextView) findViewById(R.id.loading);
        version = (TextView) findViewById(R.id.version);
        created = (TextView) findViewById(R.id.created);
    }

}