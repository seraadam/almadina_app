package com.example.project1.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.project1.R;
import com.example.project1.activities.LoginActivity;

public class Multimedia extends Fragment {

    private CardView cardTop,cardRight,cardLeft,cardLeft2;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Multimedia() {}

    public static Multimedia newInstance(String param1, String param2) {
        Multimedia fragment = new Multimedia();
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

        View v = inflater.inflate(R.layout.fragment_multimedia, container, false);

        // card ini
        cardTop = v.findViewById(R.id.cardTop);
        cardRight = v.findViewById(R.id.cardRight);
        cardLeft = v.findViewById(R.id.cardLeft);
        cardLeft2 = v.findViewById(R.id.cardLeft2);
        // ini Animations

        cardTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putInt("param1",1);
                Fragment fragment = new ImagesFragment();
                fragment.setArguments(data);
                Log.e("cardtop","almadina meuseum 1");
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flcontent,fragment);
                ft.commit();

            }
        });
        cardRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putInt("param1",2);
                Fragment fragment = new ImagesFragment();
                fragment.setArguments(data);
                Log.e("cardright","islamic2");
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flcontent,fragment);
                ft.commit();

            }
        });
        cardLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putInt("param1",3);
                Fragment fragment = new ImagesFragment();
                fragment.setArguments(data);
                Log.e("cardLeft","dress3");
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.flcontent,fragment);
                ft.commit();

            }
        });
        cardLeft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putInt("param1",4);
                Fragment fragment = new ImagesFragment();
                fragment.setArguments(data);
                Log.e("cardLeft2","food4");
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flcontent,fragment);
                ft.commit();

            }
        });

        Animation animationBottomToTop = AnimationUtils.loadAnimation(getActivity(),R.anim.anime_bottom_to_top);
        Animation animationTopToBottom = AnimationUtils.loadAnimation(getActivity(),R.anim.anime_top_to_bottom);
        Animation animationRightToLeft = AnimationUtils.loadAnimation(getActivity(),R.anim.anime_right_to_left);
        Animation animationLeftToRight = AnimationUtils.loadAnimation(getActivity(),R.anim.anime_left_to_right);

        // setup Animation
        cardLeft2.setAnimation(animationBottomToTop);
        cardTop.setAnimation(animationTopToBottom);
        cardRight.setAnimation(animationRightToLeft);
        cardLeft.setAnimation(animationLeftToRight);

        // Inflate the layout for this fragment
        return v ;
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
