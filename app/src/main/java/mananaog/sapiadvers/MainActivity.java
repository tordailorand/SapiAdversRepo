package mananaog.sapiadvers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mananaog.sapiadvers.add.AddNewAdvertisementFragment;
import mananaog.sapiadvers.auth.SignupActivity;
import mananaog.sapiadvers.listing.ListingFragment;
import mananaog.sapiadvers.listing.details.ListingDetailsFragment;
import mananaog.sapiadvers.auth.ProfileFragment;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, setupListingFragment(ListingFragment.LISTING_MODE_ALL_USER));
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add:
                    selectedFragment = AddNewAdvertisementFragment.newInstance();
                    break;
                case R.id.navigation_home:
                    selectedFragment = setupListingFragment(ListingFragment.LISTING_MODE_ALL_USER);
                    break;
                case R.id.navigation_profile:
                    selectedFragment = ProfileFragment.newInstance();
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    private ListingFragment setupListingFragment(String listingMode){
        ListingFragment listingFragment = ListingFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putString(ListingFragment.KEY_LISTING_MODE, listingMode);

        listingFragment.setArguments(bundle);

        return listingFragment;
    }

    private void startListingDetailsFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, setupListingDetailsFragment(ListingDetailsFragment.SHOW_VISITOR_MODE));
        transaction.commit();
    }

    private Fragment setupListingDetailsFragment(String showingMode) {
        ListingDetailsFragment listingDetailsFragment = ListingDetailsFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putString(ListingDetailsFragment.KEY_SHOWING_MODE, showingMode);

        listingDetailsFragment.setArguments(bundle);

        return listingDetailsFragment;
    }


}
