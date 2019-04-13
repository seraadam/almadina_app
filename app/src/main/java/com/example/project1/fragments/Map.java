package com.example.project1.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project1.activities.ItemDetailsActivity;
import com.example.project1.models.Places;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


import com.example.project1.R;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map extends Fragment implements GoogleMap.OnInfoWindowClickListener , OnMapReadyCallback , LocationListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int DEFAULT_ZOOM = 15;
    private static final String ITEM_DETAILS_KEY = "item_details_key";

    private static View view;
    final List<Marker> PlacesMarkers = new ArrayList<Marker>();
    private final LatLng mDefaultLocation = new LatLng(24.470901, 39.612236);
    public String bestProvider;
    public Criteria criteria;
    GoogleMap map;
    HashMap<String, Places> extraMarkerInfo;
    Places p ;
    List<Marker> pinslist = new ArrayList<Marker>();
    LocationManager locationManager;
    Location l;
    long mRequestStartTime;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private boolean mLocationPermissionGranted;
    private LatLng mLastKnownLocation;

    public Map() {}

    public static Map newInstance(String param1, String param2) {

        Map fragment = new Map();
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


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            final LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.e("location 6",isGPSEnabled+"" );

            if (!isGPSEnabled) {
                mLocationPermissionGranted = true;
                Log.e("location 7",mLastKnownLocation+"" );
                buildAlertMessageNoGps();
        }else{
                Log.e("location 10","" );
                getLocation();

            }


        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    //esraa: ask the user to enable the location
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();}


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION  :
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    Log.e("location 8",permissions+"" );
                }
        }
        updateLocationUI();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

            if (mapFragment != null)
                mapFragment.getMapAsync(this);

        } catch (InflateException e) {
            e.printStackTrace();
        }

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
       Places marker_data = extraMarkerInfo.get(marker.getId());

        Intent itemDetailsIntent = new Intent(getContext(), ItemDetailsActivity.class);
        itemDetailsIntent.putExtra(ITEM_DETAILS_KEY, marker_data.getId());
        getContext().startActivity(itemDetailsIntent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

       // LatLng camlocation = new LatLng(24.470901, 39.612236);

        map.moveCamera(CameraUpdateFactory.newLatLng(mDefaultLocation));
        map.animateCamera(CameraUpdateFactory.zoomTo(11), 2000, null);
       // updateLocationUI();
       // getLocation();
        extraMarkerInfo = new HashMap<String, Places>();
        Log.e("location 9", "ready" );
        double lat, log;

        String url = "http://nomow.tech/tiba/api/place/read.php";
        Log.e("url",url);

         mRequestStartTime = System.currentTimeMillis();
        Log.e("Response: " , url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;

                        Log.e("time: " , totalRequestTime+"responce");
                        try {

                            JSONArray obj = response.getJSONArray("records");
                            for (int i = 0; i < obj.length(); i++) {



                                JSONObject jsonObject = obj.getJSONObject(i);

                                double lat, log;
                                String lat1 = jsonObject.getString("lat");
                                String log1=jsonObject.getString("lang");
                                lat = new Double(lat1);
                                log = new Double(log1);

                                LatLng location = new LatLng(lat, log);

                                Marker placesmarkers = map.addMarker(new MarkerOptions().position(location).title(jsonObject.getString("Title"))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.copin
                                        ))
                                        .snippet( jsonObject.getString("Description")));


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
                                // When you created the marker you store the extra data from your JSON in another variable (HashMap for example)

                                extraMarkerInfo.put(placesmarkers.getId(), p);
                                PlacesMarkers.add(placesmarkers);
                                pinslist.add(placesmarkers);
                                placesmarkers.showInfoWindow();

                            }

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

        map.setOnInfoWindowClickListener(this);
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                Log.e("location 4",mLocationPermissionGranted+"" );
            } else {

                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {

                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                criteria = new Criteria();
                bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

                //You can still do this if you like, you might get lucky:
                Location location = locationManager.getLastKnownLocation(bestProvider);
                if(location.getLatitude()!=0)
                Log.e("location 2",location.getLatitude()+"" );
                if (location != null) {
                    Log.e("TAG", "GPS is on");
                    mLastKnownLocation =  new LatLng(location.getLatitude(),location.getLongitude());
                    Log.e("location 3",location.getLatitude()+"" );
                   // Toast.makeText(MainActivity.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
                    //searchNearestPlace(voice2text);
                }
                else{
                    //This is what you need:
                    locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
                }
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //remove location callback:

        locationManager.removeUpdates(this);
        mLastKnownLocation =  new LatLng(location.getLatitude(),location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLng(mLastKnownLocation));
        Log.e("location 1",location.getLatitude()+"" );
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
