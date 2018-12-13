package mananaog.sapiadvers.listing;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mananaog.sapiadvers.R;
import mananaog.sapiadvers.listing.details.ListingDetailsFragment;

import java.util.ArrayList;


public class ListingFragment extends Fragment {

    public static String KEY_LISTING_MODE = "listing_mode";
    public static String LISTING_MODE_ALL_USER = "all_user";
    public static String LISTING_CURRENT_USER = "current_user";

    private String listingMode;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();
    private final DatabaseReference adversRef = dbRef.child("advers");
    private FirebaseAuth mAuth;

    public static ListingFragment newInstance() {
        ListingFragment fragment = new ListingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listing, container, false);

        mAuth = FirebaseAuth.getInstance();

        getListingMode();
        setupList(view);

        return view;
    }

    private void getListingMode() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            listingMode = bundle.getString(KEY_LISTING_MODE);
        } else {
            listingMode = LISTING_MODE_ALL_USER;
        }
    }

    private void setupList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycleViewListing);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fillAdvertList(recyclerView);
    }

    private void fillAdvertList(final RecyclerView recyclerView) {
        adversRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<AdverItem> advertList = new ArrayList<>();

                for (DataSnapshot adverRef : snapshot.getChildren()) {
                    AdverItem advertisment = adverRef.getValue(AdverItem.class);

                    if (listingMode.equals(LISTING_MODE_ALL_USER)) {
                        advertList.add(advertisment);
                    } else {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String userId = currentUser.getPhoneNumber();

                        if (userId.equals(advertisment.getUserId())) {
                            advertList.add(advertisment);
                        }
                    }
                }

                ListingAdapter listingAdapter = new ListingAdapter(new clickListener(), advertList);
                recyclerView.setAdapter(listingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public class clickListener implements IClickListingListener {
        public void onClick(AdverItem advertisment) {
            //TODO on click
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, setupListingDetailsFragment(advertisment.getId()));


                advertisment.setVisitors(advertisment.getVisitors() + 1);
                adversRef.child(advertisment.getId()).setValue(advertisment);


                transaction.commit();
            }

        }

    }

    private Fragment setupListingDetailsFragment(String id) {
        String showingMode;

        ListingDetailsFragment listingDetailsFragment = ListingDetailsFragment.newInstance();

        Bundle bundle = new Bundle();

        if (listingMode.equals(LISTING_MODE_ALL_USER)) {
            showingMode = ListingDetailsFragment.SHOW_VISITOR_MODE;
        } else {
            showingMode = ListingDetailsFragment.SHOW_EDITOR_MODE;
        }
        bundle.putString(ListingDetailsFragment.KEY_SHOWING_MODE, showingMode);
        bundle.putString("id", id);

        listingDetailsFragment.setArguments(bundle);

        return listingDetailsFragment;
    }
}

