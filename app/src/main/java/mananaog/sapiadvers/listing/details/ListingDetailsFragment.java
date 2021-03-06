package mananaog.sapiadvers.listing.details;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;

import mananaog.sapiadvers.R;
import mananaog.sapiadvers.auth.User;
import mananaog.sapiadvers.listing.AdverItem;
import mananaog.sapiadvers.listing.ListingAdapter;
import mananaog.sapiadvers.listing.ListingFragment;

public class ListingDetailsFragment extends Fragment {

    public static String KEY_SHOWING_MODE = "showing_mode";
    public static String SHOW_EDITOR_MODE = "editor_mode";
    public static String SHOW_VISITOR_MODE = "visitor_mode";


    private ConstraintLayout layoutEditorMode;
    private ConstraintLayout layoutVisitorMode;

    private ViewPager viewPagerAdPictures;
    private ImageView imageViewReport;
    private ImageView imageViewShare;
    private ImageView imageViewCreator;
    private ImageView imageViewLeftArrow;
    private ImageView imageViewRightArrow;

    private TextView textViewLongDescription;
    private TextView textViewPhoneNumber;
    private TextView textViewEmail;
    private TextView textViewLocation;
    private TextView textViewCreator;
    private TextView textViewTitle;

    private ArrayList<String> imageList;

    private String showingMode;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference dbRef = database.getReference();
    private final DatabaseReference adversRef = dbRef.child("advers");
    private final DatabaseReference usersRef = dbRef.child("users");


    public static ListingDetailsFragment newInstance() {
        ListingDetailsFragment fragment = new ListingDetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listing_details, container, false);

        getShowingMode();

        initViews(view);

        getAdverById();

        setupData();

        return view;
    }

    private void getShowingMode() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            showingMode = bundle.getString(KEY_SHOWING_MODE);
        } else {
            showingMode = SHOW_VISITOR_MODE;
        }
    }

    private void getAdverById() {
        Bundle bundle = getArguments();
        final String id;

        if (bundle != null) {
            id = bundle.getString("id");
        } else {
            id = "-1";
        }

        adversRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(id)) {
                    DataSnapshot adverRef = snapshot.child(id);

                    final AdverItem advertisment = adverRef.getValue(AdverItem.class);

                    usersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            DataSnapshot userRef = snapshot.child(advertisment.getUserId());

                            User user = userRef.getValue(User.class);

                            fillInputs(advertisment, user);
                            setupViewPager(advertisment);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    //TODO nincs ilyen hirdetes
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fillInputs(AdverItem advertisment, User user) {
        textViewTitle.setText(advertisment.getTitle());
        textViewLongDescription.setText(advertisment.getLongDescription());
        textViewPhoneNumber.setText(advertisment.getPhone());
//        textViewEmail.setText(advertisment.getEmail());
        textViewLocation.setText(advertisment.getLocation());

        textViewCreator.setText(user.getFirstName() + " " + user.getLastName());
    }

    private void setupViewPager(AdverItem advertisment) {
        imageList = new ArrayList<>();

        if (advertisment.getImages().size() > 0) {
            for (String image : advertisment.getImages()) {
                imageList.add(image);
            }
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext(), imageList);
        viewPagerAdPictures.setAdapter(viewPagerAdapter);
    }

    public void initViews(View view) {
        layoutVisitorMode = view.findViewById(R.id.layoutVisitorMode);
        layoutEditorMode = view.findViewById(R.id.layoutEditorMode);

        viewPagerAdPictures = view.findViewById(R.id.viewPagerAdPictures);
        imageViewReport = view.findViewById(R.id.imageViewReport);
        imageViewShare = view.findViewById(R.id.imageViewShare);
        imageViewCreator = view.findViewById(R.id.imageViewCreator);
        imageViewLeftArrow = view.findViewById(R.id.imageViewLeftArrow);
        imageViewRightArrow = view.findViewById(R.id.imageViewRightArrow);

        textViewLongDescription = view.findViewById(R.id.textViewLongDescription);
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewLocation = view.findViewById(R.id.textViewLocation);
        textViewCreator = view.findViewById(R.id.textViewCreator);
        textViewTitle = view.findViewById(R.id.textViewTitle);

        imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareAd();
            }
        });

        imageViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportAd();
            }
        });

        imageViewLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideLeft();
            }
        });

        imageViewRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideRight();
            }
        });


        if (showingMode.equals(SHOW_EDITOR_MODE)) {
            layoutEditorMode.setVisibility(View.VISIBLE);
            layoutVisitorMode.setVisibility(View.INVISIBLE);
        } else {
            layoutEditorMode.setVisibility(View.INVISIBLE);
            layoutVisitorMode.setVisibility(View.VISIBLE);
        }
    }

    private void slideRight() {
        int currentItemPosition = viewPagerAdPictures.getCurrentItem();

        if (imageList != null && currentItemPosition < imageList.size())
            viewPagerAdPictures.setCurrentItem(currentItemPosition + 1);

    }

    private void slideLeft() {
        int currentItemPosition = viewPagerAdPictures.getCurrentItem();
        if (currentItemPosition > 0)
            viewPagerAdPictures.setCurrentItem(currentItemPosition - 1);
    }

    private void reportAd() {
        // TODO share
        Toast.makeText(getContext(), "REPORTED", Toast.LENGTH_SHORT).show();
    }

    private void shareAd() {
        // TODO share
        Toast.makeText(getContext(), "SHARED", Toast.LENGTH_SHORT).show();
    }

    public void setupData() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_creator);
        Glide.with(getContext())
                .load(drawable)
                .apply(RequestOptions.circleCropTransform())
                .into(imageViewCreator);
    }

}
