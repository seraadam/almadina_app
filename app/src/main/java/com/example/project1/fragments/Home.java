package com.example.project1.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project1.R;
import com.example.project1.adapters.HomeAdapter;
import com.example.project1.models.Item;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<Item> mList = new ArrayList<>();
    RecyclerView mRecyclerView;
    HomeAdapter mHomeAdapter;
    private String mParam1;
    private String mParam2;

    private Home.OnFragmentInteractionListener mListener;

    public Home() {}

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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList.add(new Item(R.drawable.pois,"Points of Interest",R.drawable.explore_icon));
        mList.add(new Item(R.drawable.tripplanner,"Trip planner",R.drawable.plan_icon));
        mList.add(new Item(R.drawable.multimedia,"Multimedia",R.drawable.multimedia_icon));
        mRecyclerView = view.findViewById(R.id.rv_list);
        mHomeAdapter = new HomeAdapter(getActivity(), mList, getFragmentManager());

        mRecyclerView.setAdapter(mHomeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
