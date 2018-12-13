package mananaog.sapiadvers.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mananaog.sapiadvers.R;
import mananaog.sapiadvers.listing.ListingFragment;


public class ProfileFragment extends Fragment {


    private ImageView imageViewQuit;
    private ImageView imageViewProfilePicture;
    private ImageView imageViewMyAdverts;

    private TextView textViewFirstName;
    private TextView textViewLastName;
    private TextView textViewEmail;
    private TextView textViewPhoneNumber;
    private TextView textViewAddress;


    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {

        imageViewQuit = view.findViewById(R.id.imageViewQuit);
        imageViewProfilePicture = view.findViewById(R.id.imageViewProfilePicture);
        imageViewMyAdverts = view.findViewById(R.id.imageViewMyAdverts);

        textViewFirstName = view.findViewById(R.id.textViewFirstName);
        textViewLastName = view.findViewById(R.id.textViewLastName);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber);
        textViewAddress = view.findViewById(R.id.textViewAddress);

        imageViewQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit();
            }

        });

        imageViewMyAdverts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUserAdverts();
            }
        });
    }

    private void currentUserAdverts() {
        ListingFragment listingFragment = ListingFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putString(ListingFragment.KEY_LISTING_MODE, ListingFragment.LISTING_CURRENT_USER);
        listingFragment.setArguments(bundle);


        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, listingFragment);
        transaction.commit();
    }

    private void quit() {

        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }

}
