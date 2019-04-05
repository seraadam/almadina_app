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
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.activities.ItemDetailsActivity;

public class ExploreDetailsAdapter extends ArrayAdapter<String> {
    private static final String ITEM_DETAILS_KEY = "item_details_key";
    private String[] option_name;
    private String[] option_desc;
    private Integer[] option_pic;
    private Activity mContext;

    public ExploreDetailsAdapter(Activity context, String[] option_name, String[] option_desc, Integer[] option_pic) {
        super(context, R.layout.list_item, option_name);

        this.mContext = context;
        this.option_name = option_name;
        this.option_desc = option_desc;
        this.option_pic = option_pic;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = mContext.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.list_item, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();

        }

        viewHolder.ivw.setImageResource(option_pic[position]);

        viewHolder.tvw1.setText(option_name[position]);
        viewHolder.tvw2.setText(option_desc[position]);
        viewHolder.listItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemDetailsIntent = new Intent(mContext, ItemDetailsActivity.class);
                itemDetailsIntent.putExtra(ITEM_DETAILS_KEY, option_name[position]);
                mContext.startActivity(itemDetailsIntent);
            }
        });

        return r;
    }

    class ViewHolder {
        TextView tvw1;
        TextView tvw2;
        ImageView ivw;
        LinearLayout listItemLayout;

        ViewHolder(View v) {
            tvw1 = v.findViewById(R.id.menu_options);
            tvw2 = v.findViewById(R.id.options_desc);
            ivw = v.findViewById(R.id.imageView);
            listItemLayout = v.findViewById(R.id.list_item_layout);
        }
    }
}