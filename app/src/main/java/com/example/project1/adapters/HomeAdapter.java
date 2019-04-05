package com.example.project1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.activities.ItemDetailsActivity;
import com.example.project1.fragments.ContactUs;
import com.example.project1.fragments.Explore;
import com.example.project1.fragments.Home;
import com.example.project1.fragments.Map;
import com.example.project1.fragments.Multimedia;
import com.example.project1.fragments.Setting;
import com.example.project1.fragments.TripPlanner;
import com.example.project1.models.Item;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    Context mContext;
    List<Item> mData;
    FragmentManager mFragmentManager;

    public HomeAdapter(Context mContext, List<Item> mData, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.mData = mData;
        this.mFragmentManager = fragmentManager;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.card_item,parent,false);

        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final HomeViewHolder holder, final int position) {
        holder.background_img.setImageResource(mData.get(position).getBackground());
        holder.fragment_photo.setImageResource(mData.get(position).getProfilePhoto());
        holder.tv_title.setText(mData.get(position).getProfileName());
        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = null;
                Class fragmentClass;
                switch (mData.get(position).getBackground()) {
                    case R.drawable.pois:
                        fragmentClass = Explore.class;
                        break;
                    case R.drawable.tripplanner:
                        fragmentClass = TripPlanner.class;
                        break;
                    case R.drawable.multimedia:
                        fragmentClass = Multimedia.class;
                        break;
                    default:
                        fragmentClass = Home.class;
                }
                try {
                    myFragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mFragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder{
        ImageView fragment_photo,background_img;
        TextView tv_title;
        Button btn_view;
        public HomeViewHolder(View itemView) {
            super(itemView);
            fragment_photo = itemView.findViewById(R.id.frg_img);
            background_img = itemView.findViewById(R.id.card_background);
            tv_title = itemView.findViewById(R.id.card_title);
            btn_view = itemView.findViewById(R.id.btn_view);
        }
    }
}

