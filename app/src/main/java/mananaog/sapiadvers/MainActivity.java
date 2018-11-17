package mananaog.sapiadvers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import mananaog.sapiadvers.add.AddNewAdvertisementFragment;
import mananaog.sapiadvers.listing.ListingFragment;
import mananaog.sapiadvers.listing.details.ListingDetailsFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, ListingFragment.newInstance());
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_add:
                    selectedFragment = AddNewAdvertisementFragment.newInstance();
                    break;
                case R.id.navigation_home:
                    selectedFragment = ListingFragment.newInstance();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = HomeFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    private void startListingDetailsFragment (){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, ListingDetailsFragment.newInstance());
        transaction.commit();
    }


}
