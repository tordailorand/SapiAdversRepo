package mananaog.sapiadvers.listing;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mananaog.sapiadvers.R;

import java.util.ArrayList;

public class ListingAdapter extends RecyclerView .Adapter<ListingAdapter.ListingItemViewHolder>{
    private ArrayList<AdverItem> advertsList ;

    public ListingAdapter(ArrayList<AdverItem> advertsList) {
        this.advertsList = advertsList;
    }

    @NonNull
    @Override
    public ListingItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_listing_layout, viewGroup,
                        false);
        return new ListingItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingItemViewHolder listingItemViewHolder, final int i) {
        AdverItem adver = advertsList.get(i);
        listingItemViewHolder.txtTitle.setText(adver.getTitle());
        listingItemViewHolder.txtDescription.setText(adver.getDescription());
        listingItemViewHolder.txtVisitors.setText(Integer.toString(adver.getVisitors()));

        listingItemViewHolder.imgBackground.setImageResource(R.drawable.chicken_logo);
        listingItemViewHolder.imgProfilePicture.setImageResource(R.drawable.chicken_logo);

        listingItemViewHolder.imgBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Item clicked " + i, Toast.LENGTH_SHORT).show();
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
