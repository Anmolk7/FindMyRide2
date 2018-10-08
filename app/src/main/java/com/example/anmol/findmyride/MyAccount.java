package com.example.anmol.findmyride;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MyAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Toast.makeText(MyAccount.this, "Please dial 17122 to activate balance transfer service", Toast.LENGTH_SHORT).show();
    }


    public void ncell(View view)
    {
        EditText amount = (EditText) findViewById(R.id.amount);
        String amountText=amount.getText().toString();
       // Double a = Double.parseDouble(String.valueOf(amount.getText()));
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        Databasehelper databasehelper = new Databasehelper(this);
        String no = databasehelper.display().toString();
        if (amountText.equals("")||Double.parseDouble(String.valueOf(amount.getText()))<10 ||Double.parseDouble(String.valueOf(amount.getText()))>200)
        {
            Toast.makeText(MyAccount.this, "Amount should be in the range of 10-200", Toast.LENGTH_SHORT).show();
        }
        else {
            //databasehelper.insertBalance(Float.parseFloat(amountText));
            float bal=databasehelper.getBalance();
            //float newbal= (float) (bal - 10.2);
            databasehelper.updateBalance(Float.parseFloat(amountText),bal);
            Toast.makeText(getApplicationContext(),String.valueOf(databasehelper.getBalance()),Toast.LENGTH_SHORT).show();
          //  Toast.makeText(getApplicationContext(),String.valueOf(bal),Toast.LENGTH_SHORT).show();
            callIntent.setData(Uri.parse("tel:" + "*17122*" + no + "*" + amount.getText() + "#"));    //this is the phone number calling

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                //request permission from user if the app hasn't got the required permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CALL_PHONE},   //request specific permission from user
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
}
