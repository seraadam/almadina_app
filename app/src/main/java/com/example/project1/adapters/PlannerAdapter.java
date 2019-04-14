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
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.example.project1.R;
        import com.example.project1.activities.ExploreDetailsActivity;
        import com.example.project1.models.PlaceCategory;
        import com.example.project1.models.Places;
        import com.squareup.picasso.Picasso;

        import java.util.List;

public class PlannerAdapter extends ArrayAdapter<Places> {

    private static final String EXPLORE_CATEGORY_KEY = "explore_category_key";

    private List<Places> gplaces;
    private Activity mContext;
    private AdapterListener mListener;

    public static class ViewHolder  {
        public TextView tvw1;
        public TextView tvw2;
        public ImageView ivw;
        public LinearLayout listItemLayout;
        public Button serve;
        public TextView reserved;

        public ViewHolder(View v) {

            tvw1 = v.findViewById(R.id.menu_options);
            tvw2 = v.findViewById(R.id.options_desc);
            reserved = v.findViewById(R.id.reserved);
            ivw = v.findViewById(R.id.imageView);
            listItemLayout = v.findViewById(R.id.list_item_layout);
            serve = v.findViewById(R.id.reserve);
        }
    }

    public PlannerAdapter(Activity context, List<Places> gplaces) {
        super(context, R.layout.list_item, gplaces);

        this.mContext = context;
        this.gplaces= gplaces;
    }
    // define listener
    public interface AdapterListener {
        void onClick(int id);
    }

    // set the listener. Must be called from the fragment
    public void setListener(AdapterListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        assert convertView != null;
        final ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater = mContext.getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.list_item_button, null, true);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        final Places pcat = gplaces.get(position);

       
        if(pcat.getImage_name().equals(null)||pcat.getImage_name().isEmpty()){

            Picasso.with(mContext).load(R.drawable.ppp).into(viewHolder.ivw);

        }
        else{
            // loading movie cover using Glide library
            Log.e("image",pcat.getImage_name());
            Picasso.with(mContext).load(pcat.getImage_name()).into(viewHolder.ivw);

        }
        viewHolder.tvw1.setText(pcat.getTitle());
        viewHolder.tvw2.setText(pcat.getDescription());

        viewHolder.serve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(pcat.getId());
                viewHolder.serve.setVisibility(View.GONE);
                viewHolder.reserved.setVisibility(View.VISIBLE);
            }
        });

        return convertView;
    }

    public int getCount () {
        return gplaces.size();
    }


}
