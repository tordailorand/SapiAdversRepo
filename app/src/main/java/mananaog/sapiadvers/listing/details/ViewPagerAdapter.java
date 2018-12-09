package mananaog.sapiadvers.listing.details;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mananaog.sapiadvers.R;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> imageList;

    ViewPagerAdapter(Context context, ArrayList<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_layout, container,
                false);

        ImageView imageView = view.findViewById(R.id.imageViewAdverImage);

        String imageUrl = imageList.get(position);
        Glide.with(context).load(imageUrl).into(imageView);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        (container).removeView((ImageView) object);
    }
}
