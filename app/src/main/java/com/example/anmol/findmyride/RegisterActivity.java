package com.example.anmol.findmyride;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity
{
    SharedPreferences sf;
    //ConnectivityManager cManager;
    //NetworkInfo nInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       /* cManager=(ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        nInfo=cManager.getActiveNetworkInfo();
        if(nInfo!=null && nInfo.isConnected())
        {
            Toast.makeText(RegisterActivity.this,"Network Available",Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(RegisterActivity.this,"Internet Access required to Register",Toast.LENGTH_LONG).show();
        }*/

        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        final Button bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager cManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
                        if (nInfo != null && nInfo.isConnected()) {
                            //Toast.makeText(RegisterActivity.this, "Saving...", Toast.LENGTH_SHORT).show();


                            final String name = etName.getText().toString();
                            final String username = etUsername.getText().toString();
                            final String phone = etAge.getText().toString();
                            final String password = etPassword.getText().toString();
                            final String confirmPass = etConfirmPassword.getText().toString();
                            if (name.equals("") || password.equals("") || username.equals("") || phone.equals("")) {
                                Toast.makeText(RegisterActivity.this, "Fill in all the details", Toast.LENGTH_SHORT).show();
                            } else {
                                if (password.length() < 6) {
                                    Toast.makeText(RegisterActivity.this, "Password should have atleast 6 characters", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (!password.equals(confirmPass)) {
                                    Toast.makeText(RegisterActivity.this,"Passwords don't match",Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (phone.length() != 10) {
                                            Toast.makeText(RegisterActivity.this, "Please enter a valid number " + "\n" + "(Do not include country code)", Toast.LENGTH_LONG).show();
                                        } else {
                                            Response.Listener<String> responseListener =
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            try {
                                                                JSONObject jsonResponse = new JSONObject(response);
                                                                String success = jsonResponse.getString("success");
                                                                if (success.equals("success")) {
                                                                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                                    RegisterActivity.this.startActivity(intent);
                                                                } else if (success.equals("username")) {
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                                                    builder.setMessage("Username already exists").setNegativeButton("Retry", null).create().show();
                                                                } else if (success.equals("phone")) {
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                                                    builder.setMessage("Phone number already Registered").setNegativeButton("Retry", null).create().show();
                                                                } else {
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                                                    builder.setMessage("Registration failed please try again later").setNegativeButton("Retry", null).create().show();
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    };
                                            RegisterRequest registerRequest = new RegisterRequest(name, username, phone, password, responseListener);
                                            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                                            queue.add(registerRequest);

                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this,"Internet Access required to Register",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
