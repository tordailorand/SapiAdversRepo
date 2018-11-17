package mananaog.sapiadvers.listing;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mananaog.sapiadvers.R;

import java.util.ArrayList;

public class ListingFragment extends Fragment {

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
        setupList(view);

        return view;
    }

    private void setupList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycleViewListing);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<AdverItem> advertList = new ArrayList<>();
        advertList.add(new AdverItem("KUTYA", "ELADO NEM LOPOTT", 32, "", ""));
        advertList.add(new AdverItem("KUTYA", "ELADO NEM LOPOTT", 32, "", ""));
        advertList.add(new AdverItem("KUTYA", "ELADO NEM LOPOTT", 32, "", ""));

        ListingAdapter listingAdapter = new ListingAdapter(new clickListener(), advertList);
        recyclerView.setAdapter(listingAdapter);
    }


    public class clickListener implements IClickListingListener {

        public void onClick(AdverItem item) {
            //TODO on click
            Intent intent = new Intent(getActivity(), ListingDetailsActivity.class);
            startActivity(intent);
        }
    }
}

