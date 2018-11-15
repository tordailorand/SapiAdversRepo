package mananaog.sapiadvers.listing;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import mananaog.sapiadvers.MainActivity;
import mananaog.sapiadvers.R;
import mananaog.sapiadvers.SplashActivity;
import mananaog.sapiadvers.listing.IClickListingListener;




import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

public class ListingAdapter extends RecyclerView .Adapter<ListingAdapter.ListingItemViewHolder>{

    private IClickListingListener clickListingListener;
    private ArrayList<AdverItem> advertsList ;
    private Context context;

    public ListingAdapter(IClickListingListener clickListingListener,ArrayList<AdverItem> advertsList) {
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
        AdverItem adver = advertsList.get(i);
        listingItemViewHolder.txtTitle.setText(adver.getTitle());
        listingItemViewHolder.txtDescription.setText(adver.getDescription());
        listingItemViewHolder.txtVisitors.setText(Integer.toString(adver.getVisitors()));

        listingItemViewHolder.imgBackground.setImageResource(R.drawable.chicken_logo);
        listingItemViewHolder.imgProfilePicture.setImageResource(R.drawable.chicken_logo);

        listingItemViewHolder.imgBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(clickListingListener!=null){
    clickListingListener.onClick(advertsList.get(listingItemViewHolder.getAdapterPosition()));
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
