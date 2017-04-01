package com.example.anmol.findmyride;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anmol on 11/13/2016.
 */
public class RegisterRequest extends StringRequest
{

    private static final String REGISTER_REQUEST_URL="https://dizziest-reams.000webhostapp.com/Register.php";
    private Map<String ,String> params;

    public RegisterRequest(String name, String username, String phone, String password, Response.Listener<String> listener)
    {
        super(Method.POST,REGISTER_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("phone",phone);
        params.put("password",password);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

