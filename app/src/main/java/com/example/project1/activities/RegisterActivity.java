package com.example.project1.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.example.project1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.example.project1.activities.LoginActivity.MY_PREFS_ID;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_USERNAME = "Username";
    public static final String KEY_PASSWORD = "Password";
    public static final String key_nationality = "NAtionality";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_ID = "id";
    long mRequestStartTime;

    ConnectivityManager isConnectionExist ;
    Boolean connected;


    String REGISTER_URL = "";
    String userid="";

    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Spinner editTextNationality;

    private Button buttonRegister;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final String IS_LOGGED_USER = "IsLogged";
    public static final String MY_PREFS_EMAIL = "MyPrefsId";
    private String vid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        Boolean setting = sharedPreferences.getBoolean(IS_LOGGED_USER, false);

        if(setting){
            Log.e("existed",setting+"");
            Intent loged = new Intent(getApplicationContext(), MainActivity.class);
            loged.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(loged);

        }

        editTextUsername = (EditText) findViewById(R.id.username_signup);
        editTextEmail = (EditText) findViewById(R.id.email_signup);
        editTextPassword = (EditText) findViewById(R.id.password_signup);
        editTextNationality = (Spinner) findViewById(R.id.nationality_signup);

        buttonRegister = (Button) findViewById(R.id.signup_btn);
        buttonRegister.setOnClickListener(this);

    }

    private void registerUser() throws UnsupportedEncodingException {
        final String username = editTextUsername.getText().toString().trim();
        final String userISSUE_decode = URLEncoder.encode(username, "UTF-8");
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String  nationality = editTextNationality.getSelectedItem().toString().trim();

        String EMAIL_REGEX1 = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


        if (username.isEmpty() || password.isEmpty() || email.isEmpty() ||  nationality.isEmpty()) {
            Toast.makeText(getApplicationContext(), " الرجاء ملء جميع الحقول", Toast.LENGTH_SHORT).show();
        }else {
            if (email.matches(EMAIL_REGEX1) && !email.isEmpty()) {

                    REGISTER_URL = "http://nomow.tech/tiba/api/create.php";

                final JSONObject jsonBodyObj = new JSONObject();
                try {
                    jsonBodyObj.put(KEY_EMAIL, email);
                    jsonBodyObj.put(KEY_USERNAME, username);
                    jsonBodyObj.put(key_nationality, nationality);
                    jsonBodyObj.put(KEY_PASSWORD, password);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String requestBody = jsonBodyObj.toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    getUID(email);


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }){
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {

                                responseString = String.valueOf(response.statusCode);
                                Log.i("LOG_VOLLEY 1", response.allHeaders.toString()+"");

                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }

                    };

                        // Access the RequestQueue through your singleton class.
                           stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);

            } else {
                Toast.makeText(getApplicationContext(), "الرجاء ادخال ايميل صحيح", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onClick(View v) {
        isConnectionExist = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(v == buttonRegister){
            if(isConnectionExist.getActiveNetworkInfo() != null){
                try {
                    registerUser();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getApplicationContext(), "لايوجد اتصال بالانترنت", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void getUID(final String email) {

        mRequestStartTime = System.currentTimeMillis();

        vid = null;

        String url = "http://nomow.tech/tiba/api/visitor/read_one.php?id="+email;
        Log.e("Response: ", url);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    //JSONObject jsonObject = new JSONObject(response);
                    Log.e("Response: ", response.getString("VID"));
                    vid= response.getString("VID");
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = RegisterActivity.this.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putBoolean(IS_LOGGED_USER, true);
                    editor.putString(MY_PREFS_EMAIL, email);
                    editor.putString(MY_PREFS_ID,vid );
                    //Saving values to editor
                    editor.commit();

                    Toast.makeText(RegisterActivity.this, "تم تسجيل بياناتك بنجاح", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Access the RequestQueue through your singleton class.

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(stringRequest);


    }

    public int CheckNameIsLetter(String x) {

        int flag=0;
        //using a StringBuilder instead of concatenate Strings
        for (int i = 0; i < x.length(); i++) {
            if (Character.isLetter(x.charAt(i))) {
                //adding data into the StringBuilder
            }else {
                flag =1;}
        }
        return flag;
    }
}



