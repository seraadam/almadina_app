package com.example.project1.activities;

  import android.content.Context;
  import android.content.Intent;
  import android.content.SharedPreferences;
  import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
  import android.util.Log;
  import android.view.View;
        import android.widget.Button;
        import android.widget.ProgressBar;
  import android.widget.TextView;
  import android.widget.Toast;

  import com.android.volley.AuthFailureError;
  import com.android.volley.DefaultRetryPolicy;
  import com.android.volley.NetworkResponse;
  import com.android.volley.Request;
  import com.android.volley.RequestQueue;
  import com.android.volley.Response;
  import com.android.volley.ServerError;
  import com.android.volley.VolleyError;
  import com.android.volley.VolleyLog;
  import com.android.volley.toolbox.HttpHeaderParser;
  import com.android.volley.toolbox.JsonObjectRequest;
  import com.android.volley.toolbox.StringRequest;
  import com.android.volley.toolbox.Volley;
  import com.example.project1.R;
  import com.example.project1.models.Places;

  import org.json.JSONArray;
  import org.json.JSONException;
  import org.json.JSONObject;

  import java.io.UnsupportedEncodingException;


public class LoginActivity extends AppCompatActivity {

    Button loginButton,registerButton;
    ProgressBar loginProgress;
    TextView email, password;

    public static final String IS_LOGGED_USER = "IsLogged";
    public static final String MY_PREFS_EMAIL = "MyPrefsId";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.reg_button);
        loginProgress = findViewById(R.id.login_progress);
        email = findViewById(R.id.login_mail);
        password =findViewById(R.id.login_password);
        loginProgress.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        Boolean setting = sharedPreferences.getBoolean(IS_LOGGED_USER, false);


        if(setting){
            Log.e("existed",setting+"");
            Intent loged = new Intent(getApplicationContext(), MainActivity.class);
            loged.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(loged);

        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               

                login( email.getText().toString(),password.getText().toString());


            }
        });



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                register.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(register);
            }
        });




    }

    public void login(final String email, String password){
        long mRequestStartTime = System.currentTimeMillis();

        final String URL = "http://nomow.tech/tiba/api/login.php";
        Log.e("Response:",URL+"2");

        final JSONObject jsonBodyObj = new JSONObject();
        try {
            jsonBodyObj.put("Email", email);
            jsonBodyObj.put("Password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonBodyObj.toString();
        Log.e("Response:",requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                //Creating a shared preference
                SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putBoolean(IS_LOGGED_USER, true);
                editor.putString(MY_PREFS_EMAIL, email);
                //Saving values to editor
                editor.commit();

                Intent register = new Intent(getApplicationContext(), MainActivity.class);
                register.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(register);

                // MY_PREFS_NAME - a static String variable like:


                Log.i("LOG_VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
            }
        }) {
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

        RequestQueue rr = Volley.newRequestQueue(this);
        rr.add(stringRequest);

    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
      //  Toast.makeText(MainActivity.this,"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }


}
