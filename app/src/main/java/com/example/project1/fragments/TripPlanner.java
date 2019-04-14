package com.example.project1.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.example.project1.activities.LoginActivity;
import com.example.project1.adapters.PlannerAdapter;
import com.example.project1.models.Places;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class TripPlanner extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private List<Places> places;
    ListView mListView;
    PlannerAdapter plannerAdapter;
    public String pday=null;
    public String pmonth=null;
    public String pyear=null;
    public  String selectedDate;

    private OnFragmentInteractionListener mListener;
    private CalendarView customCalendar;
    private Spinner spinner1;
    private Spinner spinner2;
    private Button btnSubmit,start ,end;


    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    public static final int REQUEST_CODE = 11; // Used to identify the result
    public static final int REQUEST_CODE2 = 15; // Used to identify the result


    long mRequestStartTime;
    public TripPlanner() {}

    public static TripPlanner newInstance(String param1, String param2) {
        TripPlanner fragment = new TripPlanner();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_trip_planner, container, false);

       // customCalendar = (CalendarView) v.findViewById(R.id.calendarView);
        spinner1 = (Spinner) v.findViewById(R.id.spinner1);
        spinner2 = (Spinner) v.findViewById(R.id.spinner2);
        btnSubmit= (Button) v.findViewById(R.id.btnSubmit);
        start= (Button) v.findViewById(R.id.start);
        end= (Button) v.findViewById(R.id.end);

       // dateView = (TextView) v.findViewById(R.id.textView3);
        // get fragment manager so we can launch from fragment
        final FragmentManager fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();


       start.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // create the datePickerFragment
               AppCompatDialogFragment startFragment = new DatePickerFragment();
               // set the targetFragment to receive the results, specifying the request code
               startFragment.setTargetFragment(TripPlanner.this, REQUEST_CODE);
               // show the datePicker
               startFragment.show(fm, "datePicker");
           }
       });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create the datePickerFragment
                AppCompatDialogFragment endFragment = new DatePickerFragment();
                // set the targetFragment to receive the results, specifying the request code
                endFragment.setTargetFragment(TripPlanner.this, REQUEST_CODE2);
                // show the datePicker
                endFragment.show(fm, "datePicker");
            }
        });

btnSubmit.setOnClickListener(new View.OnClickListener() {




    @Override
    public void onClick(View v) {
        String text = spinner1.getSelectedItem().toString();


        Log.e("responce spinner", text);

        Log.e("Response: " ,pyear+pmonth+ pday);
        String url = "http://nomow.tech/tiba/api/place/read_dates.php?pdate="+ selectedDate;
        Log.e("url",url);

        mRequestStartTime = System.currentTimeMillis();
        Log.e("Response: " , url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;
                        places.clear();

                        Log.e("time: " , totalRequestTime+"responce");
                        try {
                            JSONArray obj = response.getJSONArray("records");
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject jsonObject = obj.getJSONObject(i);
                                Places p = new Places( jsonObject.getString("Title"),
                                        jsonObject.getString("image_name") ,
                                        jsonObject.getString("lat"),
                                        jsonObject.getString("lang"),
                                        jsonObject.getString("Start"),
                                        jsonObject.getString("End"),
                                        jsonObject.getString("Description"),
                                        jsonObject.getInt("PID") ,
                                        jsonObject.getString("Category"));
                                Log.e("Response: " , p.getCategory());


                                places.add(p);
                            }

                            plannerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // As of f605da3 the following should work
                        NetworkResponse response = error.networkResponse;
                        long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;
                        Log.e("time: " , response+"response");

                        places.clear();
                        plannerAdapter.notifyDataSetChanged();

                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                                Log.i("obj : ", obj.toString());
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }

                    }
                });

// Access the RequestQueue through your singleton class.
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(jsonObjectRequest);

        // Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
});
        places = new ArrayList<>();

        mListView = v.findViewById(R.id.listview);
        plannerAdapter = new PlannerAdapter(getActivity(), places);

        mListView.setAdapter(plannerAdapter);

//        customCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//               // String msg = "Selected date Day: " + dayOfMonth + " Month : " + (month + 1) + " Year " + year;
//                //http://nomow.tech/tiba/api/place/read_dates.php?pdate=2019-09-09
//
//            }
//        });

        plannerAdapter.setListener(new PlannerAdapter.AdapterListener() {
            @Override
            public void onClick(int id) {

               // String url = "http://nomow.tech/tiba/api/plan/create.php";

                mRequestStartTime = System.currentTimeMillis();

                final String vid = "7";
                final String pid = id +"";

                final String date= pyear+"-"+ pmonth +"-"+pday;

                Log.e("Response: " , date);
                Log.e("Response: " , pid);

                final String URL = "http://nomow.tech/tiba/api/plan/create.php";
                Log.e("Response:",URL+"2");


                JSONObject jsonBodyObj = new JSONObject();
                try {
                    jsonBodyObj.put("VID", vid);
                    jsonBodyObj.put("PID", pid);
                    jsonBodyObj.put("Date", date);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String requestBody = jsonBodyObj.toString();
                Log.e("Response:",requestBody);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("LOG_VOLLEY", response);
                        Toast.makeText(getActivity().getApplicationContext(),"Place is added succesfully",Toast.LENGTH_LONG).show();
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

                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };


// Access the RequestQueue through your singleton class.
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                RequestQueue rr = Volley.newRequestQueue(getContext());
                rr.add(stringRequest);
            }


        });

        
        return v;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check for the results
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // get date from string
            selectedDate = data.getStringExtra("selectedDate");

            // set the value of the editText
            Log.e("date",selectedDate);
            start.setText(selectedDate);


        }else if (requestCode == REQUEST_CODE2 && resultCode == Activity.RESULT_OK ) {
            // get date from string
            String selectedDate2 = data.getStringExtra("selectedDate");
            // set the value of the editText
            Log.e("date",selectedDate2);

            end.setText(selectedDate2);
        }
    }

    public static boolean isDateAfter(String startDate,String endDate)
    {
        try
        {
            String myFormatString = "yyyy-M-dd"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate))
                return true;
            else
                return false;
        }
        catch (Exception e)
        {

            return false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
