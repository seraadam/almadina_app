package com.example.project1.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.activities.ItemDetailsActivity;
import com.example.project1.models.Places;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.List;

public class ExploreDetailsAdapter extends ArrayAdapter<Places> {
    private static final String ITEM_DETAILS_KEY = "item_details_key";

    private Activity mContext;
    private List<Places> gplaces;

    private static class ViewHolder {
        private TextView tvw1;
        private TextView tvw2;
        private ImageView ivw;
        private LinearLayout listItemLayout;

        private ViewHolder(View v) {
            tvw1 = v.findViewById(R.id.menu_options);
            tvw2 = v.findViewById(R.id.options_desc);
            ivw = v.findViewById(R.id.imageView);
            listItemLayout = v.findViewById(R.id.list_item_layout);
        }
    }

    public ExploreDetailsAdapter(Activity context,  List<Places>  gplaces) {
        super(context, R.layout.list_item, gplaces);

        this.mContext = context;
        this.gplaces =gplaces;
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

        final Places pcat = gplaces.get(position);


       // viewHolder.ivw.setImageDrawable(getDrawable("R.drawable."+ pcat.getImage_name()));

         viewHolder.tvw1.setText(pcat.getTitle());
        viewHolder.tvw2.setText(pcat.getDescription());
        viewHolder.listItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemDetailsIntent = new Intent(mContext, ItemDetailsActivity.class);
                itemDetailsIntent.putExtra(ITEM_DETAILS_KEY, gplaces.get(position).getId());
                mContext.startActivity(itemDetailsIntent);
            }
        });

        if(pcat.getImage_name()== null || pcat.getImage_name().isEmpty()){
            Picasso.with(mContext).load(R.drawable.ppp).into(viewHolder.ivw);

        }
        else{
            // loading movie cover using Glide library
            Log.e("image",pcat.getImage_name());
            Picasso.with(mContext).load(pcat.getImage_name()).into(viewHolder.ivw);

        }
        return convertView;
    }


    public int getItemCount() {
        return gplaces.size();
    }

}