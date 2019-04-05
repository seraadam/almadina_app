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
import com.example.project1.models.PlaceCategory;

import java.util.ArrayList;
import java.util.List;

public class Explore extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView mListView;
    private List<PlaceCategory> categories;


    String[] OPTION_NAMES ;
    String[] OPTION_DESCRIPTION ;
    int[] OPTIONS_PICTURES;

    private String mParam1;
    private String mParam2;

    private Home.OnFragmentInteractionListener mListener;



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

        OPTION_NAMES = new String[]{"Islamic Landmarks", "Historical Places","Museum", "Event","shopping mall", "Restaurants"};
        OPTION_DESCRIPTION =new String[] {"Visit and know more about Islamic places in Almadinah!", "Discover more historical places!", "Visit and know more about Islamic places in Almadinah!",
                "Visit and know more about Islamic places in Almadinah!","Visit and know more about Islamic places in Almadinah!","Find out where to eat!"};
         OPTIONS_PICTURES = new int[]{R.drawable.pois, R.drawable.tripplanner, R.drawable.multimedia,R.drawable.pois, R.drawable.tripplanner, R.drawable.multimedia};


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        categories = new ArrayList<>();
        mListView = view.findViewById(R.id.listview);

        filllist();
        ExploreAdapter exploreAdapter = new ExploreAdapter(getActivity(), categories);
        mListView.setAdapter(exploreAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public  void filllist(){
        for (int i = 0; i < 6 ; i++) {

            PlaceCategory c = new PlaceCategory(OPTION_NAMES[i],OPTION_DESCRIPTION[i],OPTIONS_PICTURES[i]);
            categories.add(c);
        }
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
