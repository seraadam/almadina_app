package com.example.project1.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.project1.R;
import com.example.project1.adapters.ExploreAdapter;

public class Explore extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView mListView;
    String[] OPTION_NAMES = {"Islamic Landmarks", "Historical Places","Museum", "Event","shopping mall", "Restaurants"};
    String[] OPTION_DESCRIPTION = {"Visit and know more about Islamic places in Almadinah!", "Discover more historical places!", "Find out where to eat!"};
    Integer[] OPTIONS_PICTURES = {R.drawable.pois, R.drawable.tripplanner, R.drawable.multimedia};

    private String mParam1;
    private String mParam2;

    private Home.OnFragmentInteractionListener mListener;

    public Explore() {}

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = view.findViewById(R.id.listview);
        ExploreAdapter exploreAdapter = new ExploreAdapter(getActivity(), OPTION_NAMES, OPTION_DESCRIPTION, OPTIONS_PICTURES);
        mListView.setAdapter(exploreAdapter);
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
