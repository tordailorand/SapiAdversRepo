package mananaog.sapiadvers.add;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import mananaog.sapiadvers.MainActivity;
import mananaog.sapiadvers.R;

import mananaog.sapiadvers.listing.AdverItem;


public class AddNewAdvertisementFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 0;
    static final int REQUEST_GALLERY_CAPTURE = 1;


    private EditText editTextTitle;
    private EditText editTextShortDescription;
    private EditText editTextLongDescription;
    private EditText editTextPhoneNumber;
    private EditText editTextLocation;

    private TextView textViewAddImage;

    private ImageView imageViewSlideLeft;
    private ImageView imageViewSlideRight;

    private Button buttonSave;

    private ContextWrapper cw;
    private File directory;
    private ArrayList<String> advertismentImages = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();

    private AddNewAdvertismentImagesAdapter imagesListingAdapter;
    private RecyclerView recycleViewAdverPicturesList;

    LinearLayoutManager linearLayoutManager;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    public static Fragment newInstance() {
        AddNewAdvertisementFragment fragment = new AddNewAdvertisementFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_advertisment, container, false);

        cw = new ContextWrapper(getContext());
        directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

        initViews(view);
        setupClickListeners();
        setupAdvertismentImagesList();

        return view;
    }

    private void setupAdvertismentImagesList() {
        imagesListingAdapter = new AddNewAdvertismentImagesAdapter(advertismentImages, getContext());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleViewAdverPicturesList.setLayoutManager(linearLayoutManager);

        recycleViewAdverPicturesList.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.HORIZONTAL));

        recycleViewAdverPicturesList.setAdapter(imagesListingAdapter);
    }

    private void setupClickListeners() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (advertismentImages.size() > 0) {
                    uploadImages();
                } else {
                    saveItem();
                }
            }
        });
        imageViewSlideLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideLeft();
            }
        });

        imageViewSlideRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideRight();
            }
        });

        textViewAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage();
            }
        });
    }

    private void slideRight() {
        recycleViewAdverPicturesList.scrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
    }

    private void slideLeft() {
        recycleViewAdverPicturesList.scrollToPosition(linearLayoutManager.findFirstVisibleItemPosition() - 1);
    }

    private void uploadImages() {
        for (String path : advertismentImages) {
            storeImageInFirebase(path);
        }
    }

    private void saveItem() {
        String id = String.valueOf(System.currentTimeMillis());
        String title = editTextTitle.getText().toString();
        String shortDescription = editTextShortDescription.getText().toString();
        String longDescription = editTextLongDescription.getText().toString();
        int visitors = 0;
        String phone = editTextPhoneNumber.getText().toString();
        String location = editTextLocation.getText().toString();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        String userId = currentUser.getPhoneNumber();

        AdverItem adver = new AdverItem(id, title, shortDescription, longDescription, visitors, phone, location, mImages, userId);
        DatabaseReference adversRef = dbRef.child("advers").child(id);

        adversRef.setValue(adver);

        startActivity(new Intent(getContext(), MainActivity.class));
    }

    private void initViews(View view) {

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextShortDescription = view.findViewById(R.id.editTextShortDescription);
        editTextLongDescription = view.findViewById(R.id.editTextLongDescription);
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber);
        editTextLocation = view.findViewById(R.id.editTextLocation);

        textViewAddImage = view.findViewById(R.id.textViewAddImage);

        imageViewSlideLeft = view.findViewById(R.id.imageViewSlideLeft);
        imageViewSlideRight = view.findViewById(R.id.imageViewSlideRight);

        buttonSave = view.findViewById(R.id.buttonSave);

        recycleViewAdverPicturesList = view.findViewById(R.id.recycleViewAdverPicturesList);
    }

    private void showImageChooseDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.text_choose_image)
                .setItems(R.array.image_chooser_options,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        openCamera();
                                        break;
                                    case 1:
                                        openGallery();
                                        break;
                                }
                                ;
                            }
                        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY_CAPTURE);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void addImage() {

        showImageChooseDialog();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap imageBitmap = null;

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
        }

        if (requestCode == REQUEST_GALLERY_CAPTURE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        if (imageBitmap != null) {
            String cameraImagePath = saveImageToInternalStorage(imageBitmap);
            advertismentImages.add(cameraImagePath);
            updateImagesList();
        }
    }

    private void updateImagesList() {
        imagesListingAdapter.updateList(advertismentImages);
    }

    private String saveImageToInternalStorage(Bitmap imageBitmap) {
        File mypath = new File(directory, System.currentTimeMillis() + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mypath.getAbsolutePath();
    }

    private void storeImageInFirebase(String path) {
        mStorageRef = FirebaseStorage.getInstance().getReference();

        Uri file = Uri.fromFile(new File(path));
        final StorageReference imagesRef = mStorageRef.child("images/advertisments/" + System.currentTimeMillis() + ".jpg");

        imagesRef.putFile(file).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return imagesRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    String downloadUrl = task.getResult().toString();
                    mImages.add(downloadUrl);

                    if (mImages.size() == advertismentImages.size()) {
                        saveItem();
                    }
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
}
