package com.example.locationtrailapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {
    private Button btn_requestloc;
    private TextView tlat , tlong , tcountry,tlaytfromarray;
    //int intArray[];
    private ArrayList<String> latlongList = new ArrayList<String>();
    int j=0;
    //private LocationManager locationManager;
   // private LocationListener listener;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tlat =  findViewById(R.id.textv1);
        tlong= findViewById(R.id.textv2);
        tcountry= findViewById(R.id.textv3);
        tlaytfromarray= findViewById(R.id.textv4);
        btn_requestloc = findViewById(R.id.b1);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        btn_requestloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this
                ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    getLocatn();
                }
                else{
                    ActivityCompat.requestPermissions(MainActivity.this
                            ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }

        });


    }
    private  void  getLocatn(){

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location= task.getResult();
                if (location!=null){
                    try {
                        Geocoder geocoder=new Geocoder(MainActivity.this
                                , Locale.getDefault());
                        List<Address> address= geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                     //   intArray[j]= (int) address.get(0).getLatitude();
                        String lat=String.valueOf( address.get(0).getLatitude());
                        String lon=String.valueOf( address.get(0).getLongitude());
                        String latlong=lat +" . " +lon;
                        latlongList.add(latlong);
                        Log.d("TAGVALUEs","latlon"+latlong);

                        tlat.setText(String.valueOf( address.get(0).getLatitude()));
                        tlong.setText(String.valueOf( address.get(0).getLongitude()));
                        tcountry.setText(String.valueOf( address.get(0).getCountryName()));
                        //String.valueOf(address.get(0).);


                    }
                    catch (IOException excptn){
                        excptn.printStackTrace();
                    }
                }

            }
        });
    }
}
