package com.example.project1.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.project1.R;
import com.riontech.calendar.CustomCalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TripPlanner extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CalendarView customCalendar;

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

        customCalendar = (CalendarView) v.findViewById(R.id.calendarView);

        Calendar mCalendar = Calendar.getInstance();
        int daysInMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        ArrayList<String> allDays = new ArrayList<String>();
        SimpleDateFormat mFormat = new SimpleDateFormat("EEE, dd MMM", Locale.US);
        for(int i = 0; i < daysInMonth; i++){
            // Add day to list
            allDays.add(mFormat.format(mCalendar.getTime()));
            // Move next day
            mCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        customCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String msg = "Selected date Day: " + dayOfMonth + " Month : " + (month + 1) + " Year " + year;
                //http://nomow.tech/tiba/api/place/read_dates.php?pdate=2019-09-09

                Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });


        return v;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
