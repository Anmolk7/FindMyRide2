package com.example.anmol.findmyride;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

public class MyLocation2 extends AppCompatActivity {
    private Button b;
    Intent intent = null;
    private TextView t;
    private TextView tt;
    private LocationManager locationManager;

    private LocationListener listener;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location2);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        t = (TextView) findViewById(R.id.textView);

        b = (Button) findViewById(R.id.button);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {

            @Override

            public void onLocationChanged(Location location) {
                TextView cleartext;
                cleartext = ((TextView) findViewById(R.id.textView));
                cleartext.setText("");

                t.append("\n " + location.getLatitude() + "N" + "," + +location.getLongitude() + "E");

            }

            @Override

            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override

            public void onProviderEnabled(String s) {

            }

            @Override

            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                startActivity(i);

            }

        };

        configure_button();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                t = (TextView) findViewById(R.id.textView);
//                t.setTextIsSelectable(true);
//                String text = t.getText().toString();
//                if (text.equals("")) {
//                    Toast.makeText(MyLocation2.this, "Please request your location", Toast.LENGTH_LONG).show();
//                }
//                else
//                {
//                    intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("google.navigation:q="+text));
//                    startActivity(intent);
//                }
//            }
//        });
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case 10:

                configure_button();

                break;

            default:

                break;

        }

    }

    void configure_button()
    {

        // first check for permissions

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}

                        , 10);

            }

            return;

        }

        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.

        b.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                // t=(TextView) findViewById(R.id.textView);
                //noinspection MissingPermission

                locationManager.requestLocationUpdates("gps", 5000, 0, listener);


            }

        });

    }

}
