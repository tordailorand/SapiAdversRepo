package mananaog.sapiadvers.add;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.FileInputStream;
import java.util.ArrayList;

import mananaog.sapiadvers.R;

public class AddNewAdvertismentImagesAdapter extends RecyclerView.Adapter<AddNewAdvertismentImagesAdapter.ImageViewHolder> {

    private ArrayList<String> imageList;
    private Context context;

    public AddNewAdvertismentImagesAdapter(ArrayList<String> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(mananaog.sapiadvers.R.layout.item_new_advertisment_image_layout, viewGroup,
                        false);
        return new AddNewAdvertismentImagesAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {

        String imagePath = imageList.get(i);
//        if (i % 2 == 0) {
//            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_creator)).into(imageViewHolder.adverImage);
//        } else {
//
//            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_report_black_24dp)).into(imageViewHolder.adverImage);
//        }

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewHolder.adverImage.setImageBitmap(bitmap);
//            Glide.with(context).load(Uri.parse(imagePath)).into(imageViewHolder.adverImage);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void updateList(ArrayList<String> advertismentImages) {
        this.imageList = advertismentImages;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView adverImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            adverImage = itemView.findViewById(R.id.imageViewNewAdverImage);
        }
    }
}
