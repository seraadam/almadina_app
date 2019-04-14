package com.example.project1.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.project1.R;
import com.squareup.picasso.Picasso;

public class PhotoImageAdapter extends BaseAdapter {
    private Context mContext;
    private int[] mThumbIds;


    public PhotoImageAdapter(Context c ,int[] mThumbIds) {
        this.mContext = c;
        this.mThumbIds = mThumbIds;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
           // imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }


        if(mThumbIds[position]==0){

           // Picasso.with(mContext).load(R.drawable.ppp).into(imageView);
            imageView.setImageResource(R.drawable.ppp);

        }
        else{
            Log.e("img",mThumbIds[position]+"");
            imageView.setImageResource(mThumbIds[position]);
            //Picasso.with(mContext).load(mThumbIds[position]).into(imageView);

        }

        return imageView;
    }


}