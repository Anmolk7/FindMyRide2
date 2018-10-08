package com.example.anmol.findmyride;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String c="";
    String cordi;
    Databasehelper db= new Databasehelper(this);
    private Button b;
    Intent intent = null;
    private TextView t;
    private TextView tt;
    private LocationManager locationManager;

    private LocationListener listener;
    private RequestQueue requestQueue;

    IntentFilter intentFilter;
    private BroadcastReceiver intentReceiver= new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            TextView inTxt=(TextView)findViewById(R.id.textView);
            inTxt.setText(intent.getExtras().getString("message"));

        }
    };
    public String getText()
    {
        t=(TextView) findViewById(R.id.textView);
        t.setTextIsSelectable(true);
        String text=t.getText().toString();
        return text;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cordi= db.cordinates().toString();
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Databasehelper db= new Databasehelper(this);
        String co=db.cordinates();
        t = (TextView) findViewById(R.id.textView);
        t.setTextIsSelectable(true);
        t.setText(co);


        //final String text=t.getText().toString();
        requestQueue = Volley.newRequestQueue(this);


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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                t = (TextView) findViewById(R.id.textView);
                t.setTextIsSelectable(true);
                String text = t.getText().toString();
                if (getText().equals("")) {
                    Toast.makeText(MainActivity.this, "Please request your location", Toast.LENGTH_LONG).show();
                }
                else
                {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("google.navigation:q="+text));
                    startActivity(intent);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override

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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}

                        , 10);

            }

            return;

        }



    }


   /* public void call(View view) {
        /*Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:014492737"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //   ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
        String number = "014492737";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", number, null)));


    }*/
   public void predict(View view) {

       Databasehelper db = new Databasehelper(this);
       cordi = db.pCordinates().toString();
       if (cordi.equals("")) {
           Toast.makeText(MainActivity.this, "No coordinates recieved", Toast.LENGTH_SHORT).show();
       } else {
           TextView prr = (TextView) findViewById(R.id.textView);
           double dt = 1;


           try {
               String[] parts = cordi.split(",");
               String part1 = parts[0]; // 004
               String part2 = parts[1]; // 034556


               double xPos = Double.parseDouble(part1);
               //27.65656565;
               double yPos = Double.parseDouble(part2);
               /// /85.26565656;
               double xSpd = 0.0000013;
               double ySpd = 0.0;
               SimpleMatrix x = new SimpleMatrix(new double[][]{{xPos}, {yPos}, {xSpd}, {ySpd}});

               double devPos = 2.5;
               double devSpd = 1.5;
               Measurement[] rec = Measurement.create(dt, dt * 10, 2, xPos, yPos, devPos, xSpd, ySpd, devSpd);

               double maxAcc = 0.5;

               // process matrix
               SimpleMatrix A = null;

               // observation matrix
               SimpleMatrix H = new SimpleMatrix(new double[][]{
                       {1, 0, 0, 0},
                       {0, 1, 0, 0},
                       {0, 0, 1, 0},
                       {0, 0, 0, 1}});

               // identity matrix
               SimpleMatrix I = SimpleMatrix.identity(4);

               // kalman gain
               SimpleMatrix K = new SimpleMatrix(4, 4);

               // initial state covariance estimate
               double xp = 1.0;
               SimpleMatrix P = SimpleMatrix.diag(xp, xp, xp, xp);

               // process noise (disturbance)
               SimpleMatrix Q = null;

               // observation noise (uncertainties) - sensors only, to be estimated in advance in real life
               SimpleMatrix R = SimpleMatrix.diag(devPos * devPos, devPos * devPos, devSpd * devSpd, devSpd * devSpd);

               // observation (aka measurement)
               SimpleMatrix z = null;

               // just for storing the results
               ArrayList<Estimate> store = new ArrayList<Estimate>();

               // kalman filter implementation
               for (int i = 1; i < rec.length; i++) {

                   // PREDICTION STEP

                   // process
                   A = A(rec[i].dt);
                   // project state ahead
                   x = A.mult(x);
                   // process noise
                   Q = Q(rec[i].dt, maxAcc);
                   // project error covariance ahead
                   P = (A.mult(P).mult(A.transpose())).plus(Q);

                   // measurement
                   z = rec[i].toMeasurement();

                   // CORRECTION STEP

                   if (rec[i].update) {
                       // calculate kalman gain
                       K = (P.mult(H.transpose())).mult((H.mult(P).mult(H.transpose()).plus(R)).invert());
                       // update estimate
                       x = x.plus(K.mult(z.minus(H.mult(x))));
                       // update error covariance
                       P = (I.minus(K.mult(H))).mult(P);
                   }

                   // store estimates for "later on"-check
                   Estimate e = new Estimate(rec[i].t, x, z, K);

                   c = e.co().toString();

                   //store.add(new Estimate(rec[i].t, x, z, K));

               }
               //return c;
               String[] splitCordinates = c.split(",");

               String split1 = parts[0]; // 004
               String split2 = parts[1]; // 034556
               String lat = split1.substring(0, 9);
               String lng = split2.substring(0, 9);
               String latlng = lat + "," + lng;
               db.insertPCordinates(c);
               prr.setText(latlng);

           }
           catch (Exception e)
           {
               Toast.makeText(MainActivity.this,"Invalid Coordinates",Toast.LENGTH_SHORT).show();
           }
       }
   }

    // process matrix
    public static SimpleMatrix A(double t) {
        return new SimpleMatrix(new double[][] {
                { 1, 0, t, 0 },
                { 0, 1, 0, t },
                { 0, 0, 1, 0 },
                { 0, 0, 0, 1 }
        });
    }

    // process noise variance matrix
    public static SimpleMatrix Q(double t, double maxA) {
        double xy = 0.5*(t*t);
        SimpleMatrix _Q = new SimpleMatrix(new double[][] { {xy}, {xy}, {t}, {t} });
        return _Q.mult(_Q.transpose()).scale(maxA*maxA);
    }

    public void call(View view) {
       Intent callIntent = new Intent(Intent.ACTION_CALL);
       Databasehelper db= new Databasehelper(this);
       String no=db.display().toString();
       if(no.equals(""))
       {

           startActivity(new Intent(MainActivity.this,LoginActivity.class));
           Toast.makeText(MainActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
       }

       else
       {

           callIntent.setData(Uri.parse("tel:" +no));    //this is the phone number calling
           //check permission
           //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
           //the system asks the user to grant approval.
           if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
               //request permission from user if the app hasn't got the required permission
               ActivityCompat.requestPermissions(this,
                       new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                       10);
               return;
           } else {     //have got permission
               try {
                   startActivity(callIntent);  //call activity and make phone call
               } catch (android.content.ActivityNotFoundException ex) {
                   Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
               }
           }
       }
   }
    /*public void location(View view)
    {
         tt=(TextView) findViewById(R.id.locationtext);
        JsonObjectRequest request=new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng="+getText()+"&key= AIzaSyB7rMYgvm_PxzgJCyk7NzfHZIriOLbYvqg", new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String address= response.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                    tt.setText(address);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)

            {

            }
        });
        requestQueue.add(request);
    }*/private void share() {
        if (getText().equals("")) {
            Toast.makeText(MainActivity.this, "Please request your location", Toast.LENGTH_LONG).show();
        } else {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "my location is"+" " + Uri.parse("Latitude/Longitude:"+getText()+" "+"come get me"));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

    private void sendSMS() {
        if (getText().equals(""))
        {
            Toast.makeText(MainActivity.this,
                    "Please request your location", Toast.LENGTH_LONG).show();
        }
        else
        {


            String defaultSmsPackageName = null; // Need to change the build to API 19
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this);
            }

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, getText());

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);
        }
    }
   /*public void smsControl()
    {
        String[] phoneNumber = new String[] { "+9779861008249" };
        //String edtmessagebody;
        TextView edtmessagebody=(TextView) findViewById(R.id.sms);


        Cursor cursor1 = getContentResolver().query(Uri.parse("content://sms/inbox"), new String[] { "_id", "thread_id", "address", "person", "date","body", "type" }, "address=?", phoneNumber, null);
        StringBuffer msgData = new StringBuffer();
        if (cursor1.moveToFirst()) {
            do
            {


                for(int idx=0;idx<cursor1.getColumnCount();idx++)
                {
                    msgData.append(" " + cursor1.getColumnName(idx) + ":" + cursor1.getString(idx));
                }

            } while (cursor1.moveToNext());
        } else {

            edtmessagebody.setText("no message from this contact"+phoneNumber);
        }*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
        else if (id == R.id.nav_gallery)
        {

            startActivity(new Intent(MainActivity.this,MyLocation.class));


        }
        else if(id==R.id.nav_share)
        {
            share();
        }
        else if (id == R.id.nav_send) {

            sendSMS();
        }
        else if (id == R.id.nav_account) {

            startActivity(new Intent(MainActivity.this,MyAccount.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onResume()
    {
        registerReceiver(intentReceiver,intentFilter);
        super.onResume();
    }
    @Override
    protected void onPause()
    {
        unregisterReceiver(intentReceiver);
        super.onPause();
    }
}
