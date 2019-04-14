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

import java.util.List;

public class PhotoImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mThumbIds;


    public PhotoImageAdapter(Context c ,List<String> mThumbIds) {
        this.mContext = c;
        this.mThumbIds = mThumbIds;
    }

    @Override
    public int getCount() {
        return mThumbIds.size();
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


        if(mThumbIds.get(position).isEmpty()|| mThumbIds.get(position).equals(null)){

            Picasso.with(mContext).load(R.drawable.ppp).into(imageView);
            Picasso.with(mContext).setLoggingEnabled(true);
            //imageView.setImageResource(R.drawable.ppp);

        }
        else{
            Log.e("img",mThumbIds.get(position)+"");
          //  imageView.setImageResource(mThumbIds[position]);
            Picasso.with(mContext).load(mThumbIds.get(position)).into(imageView);
            Picasso.with(mContext).setLoggingEnabled(true);

        }

        return imageView;
    }


}