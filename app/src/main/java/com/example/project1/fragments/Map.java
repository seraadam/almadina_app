package com.example.project1.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.example.project1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map extends Fragment implements GoogleMap.OnInfoWindowClickListener , OnMapReadyCallback {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    GoogleMap map;
    private static View view;
    HashMap<String, HashMap> extraMarkerInfo;
    final List<Marker> PlacesMarkers = new ArrayList<Marker>();
    List<Marker> pinslist = new ArrayList<Marker>();

    Marker placesMarkers ;

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
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng camlocation = new LatLng(24.470901, 39.612236);
        map.moveCamera(CameraUpdateFactory.newLatLng(camlocation));
        map.animateCamera(CameraUpdateFactory.zoomTo(11), 2000, null);

        extraMarkerInfo = new HashMap<String, HashMap>();

        double lat, log;
        String lat1;// = p.getLatitude();
        String log1; //= p.getLongitude();

//        lat = new Double(lat1);
//        log = new Double(log1);
//        LatLng location = new LatLng(lat, log);
//
//        Marker startupsMaeker = map.addMarker(new MarkerOptions().position(location).title(p.getTitle())
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.startuppins))
//                .snippet(p.getDescription()));
//
//        // When you created the marker you store the extra data from your JSON in another variable (HashMap for example)
//        HashMap<String, String> data = new HashMap<String, String>();
//
//        data.put("title", p.getTitle());
//        data.put("city", p.getAdress());
//        data.put("email", p.getEmail());
//        data.put("phone", p.getPhone());
//        data.put("category", p.getCategory());
//        data.put("description", p.getDescription());
//        data.put("founded", p.getFounded());
//        data.put("website", p.getWebsite());
//        data.put("employee", p.getemployee());
//
//        // Save this marker data in your previously made HashMap mapped to the marker ID. So you can get it back based on the marker ID
//        extraMarkerInfo.put(startupsMaeker.getId(), data);
//        StartupsMaekers.add(startupsMaeker);
//        pinslist.add(startupsMaeker);


    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
