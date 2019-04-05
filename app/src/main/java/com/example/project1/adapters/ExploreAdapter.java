package com.example.project1.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.activities.ExploreDetailsActivity;
import com.example.project1.models.PlaceCategory;

import java.util.List;

public class ExploreAdapter extends ArrayAdapter<PlaceCategory> {

    private static final String EXPLORE_CATEGORY_KEY = "explore_category_key";

    private List<PlaceCategory> pcategory;
    private Activity mContext;


    public static class ViewHolder  {
        public TextView tvw1;
        public TextView tvw2;
        public ImageView ivw;
        public LinearLayout listItemLayout;

        public ViewHolder(View v) {

            tvw1 = v.findViewById(R.id.menu_options);
            tvw2 = v.findViewById(R.id.options_desc);
            ivw = v.findViewById(R.id.imageView);
            listItemLayout = v.findViewById(R.id.list_item_layout);
        }
    }

    public ExploreAdapter(Activity context, List<PlaceCategory> pcategory) {
        super(context, R.layout.list_item, pcategory);

        this.mContext = context;
        this.pcategory= pcategory;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        assert convertView != null;
        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater = mContext.getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.list_item, null, true);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        final PlaceCategory pcat = pcategory.get(position);

        viewHolder.ivw.setImageResource(pcat.getPic());

        viewHolder.tvw1.setText(pcat.getName());
        viewHolder.tvw2.setText(pcat.getDesc());

        viewHolder.listItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemDetailsIntent = new Intent(mContext, ExploreDetailsActivity.class);
                itemDetailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                itemDetailsIntent.putExtra(EXPLORE_CATEGORY_KEY, pcat.getName());
                mContext.startActivity(itemDetailsIntent);
            }
        });

        return convertView;
    }

    public int getCount () {
        return pcategory.size();
    }


}
