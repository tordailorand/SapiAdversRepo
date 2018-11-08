package com.example.lorand.sapiadvers.listing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lorand.sapiadvers.R;

import java.util.ArrayList;

public class ListingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        setupList();

    }

    private void setupList() {

        ArrayList<AdverItem> advertList = new ArrayList<>();
        advertList.add(new AdverItem("KUTYA", "ELADO NEM LOPOTT", 32, "", ""));
        advertList.add(new AdverItem("KUTYA", "ELADO NEM LOPOTT", 32, "", ""));
        advertList.add(new AdverItem("KUTYA", "ELADO NEM LOPOTT", 32, "", ""));

        ListingAdapter listingAdapter = new ListingAdapter(advertList);

        RecyclerView recyclerViewList = findViewById(R.id.recycleViewListing);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewList.setAdapter(listingAdapter);

    }
}
