package mananaog.sapiadvers.listing;


import android.content.Context;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import mananaog.sapiadvers.R;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ListingItemViewHolder> {

    private IClickListingListener clickListingListener;
    private ArrayList<AdverItem> advertsList;
    private Context context;

    public ListingAdapter(IClickListingListener clickListingListener, ArrayList<AdverItem> advertsList) {
        this.clickListingListener = clickListingListener;
        this.advertsList = advertsList;
    }

    @NonNull
    @Override
    public ListingItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_listing_layout, viewGroup,
                        false);
        return new ListingItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListingItemViewHolder listingItemViewHolder, final int i) {
        final AdverItem adver = advertsList.get(i);
        listingItemViewHolder.txtTitle.setText(adver.getTitle());
        listingItemViewHolder.txtDescription.setText(adver.getShortDescription());
        listingItemViewHolder.txtVisitors.setText(Integer.toString(adver.getVisitors()));

        listingItemViewHolder.imgProfilePicture.setImageResource(R.drawable.chicken_logo);


        if (adver.getImages().size() > 0) {
            Glide.with(context).load(adver.getImages().get(0)).into(listingItemViewHolder.imgBackground);
        } else {
            Glide.with(context).load(R.drawable.chicken_logo).into(listingItemViewHolder.imgBackground);
        }

        setupClickListener(listingItemViewHolder.imgBackground, i);
    }

    private void setupClickListener(View view, final int itemPosition) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListingListener != null) {
                    clickListingListener.onClick(advertsList.get(itemPosition));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return advertsList.size();
    }

    public class ListingItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgBackground;
        private ImageView imgProfilePicture;
        private TextView txtTitle;
        private TextView txtDescription;
        private TextView txtVisitors;


        public ListingItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBackground = itemView.findViewById(R.id.imageViewMainImage);
            imgProfilePicture = itemView.findViewById(R.id.imageViewProfilePicture);
            txtTitle = itemView.findViewById(R.id.textViewTitle);
            txtDescription = itemView.findViewById(R.id.textViewDescription);
            txtVisitors = itemView.findViewById(R.id.textViewVisitors);
        }
    }


}
