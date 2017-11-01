package edu.orangecoastcollege.cs273.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PetListActivity extends AppCompatActivity {

    private ImageView petImageView;
    private Uri imageUri;

    private DBHelper db;
    private List<Pet> petsList;
    private PetListAdapter petListAdapter;
    private ListView petsListView;

    // Rerefences to the widgets needed
    private EditText mPetName;
    private EditText mPetDetails;
    private EditText mPetPhone;
    private ImageView mPetImageView;

    // Constants for permissions:
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    private static final int DENIED = PackageManager.PERMISSION_DENIED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        db = new DBHelper(this);

        petImageView = (ImageView) findViewById(R.id.selectPetImageView);

        petImageView.setImageURI(getUriFromResource(this, R.drawable.none));

        // Instantiate the Pet List View
        petsListView = (ListView) findViewById(R.id.petListView);

        // Fill the petsList with all pets from the database
        petsList = db.getAllPets();

        // Connect the list adapter with the list
        petListAdapter = new PetListAdapter(this, R.layout.pet_list_items, petsList);

        // Set the list view to use the list adapter
        petsListView.setAdapter(petListAdapter);

    }

    public void selectPetImage(View v)
    {
        List<String> permslist = new ArrayList<>();

        // Check each permission individually
        int hasCameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (hasCameraPerm == DENIED)
            permslist.add(Manifest.permission.CAMERA);

        int readStoragePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readStoragePerm == DENIED)
            permslist.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        int writeStoragePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeStoragePerm == DENIED)
            permslist.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // Some permissions have not been granted
        if (permslist.size() > 0)
        {
            // Convert the permsList into an array: (the size of the array has to be the same the
            // size of the list
            String[] permsArray = new String[permslist.size()];
            permslist.toArray(permsArray);

            // Ask user for them:
            ActivityCompat.requestPermissions(this, permsArray, 1337 ); // any number. 1337 is a make-up one
        }

        // Let's make sure we have all the permissions, then start up the Image Gallery:
        if (hasCameraPerm == GRANTED && readStoragePerm == GRANTED && writeStoragePerm == GRANTED)
        {
            // Let's open up the image gallery
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            // Start activity for a result (picture)
            startActivityForResult(galleryIntent, 1);  // any number; 1 is a make-up number
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            // data = data from GalleryIntent (the URI of some image)
            imageUri = data.getData();
            petImageView.setImageURI(imageUri);
        }
    }

    public static Uri getUriFromResource(Context context, int resId)
    {
        Resources res = context.getResources();
        // Build a String in the form:
        // android.resource://edu.orangecoastcollege.cs273.petprotector/drawable/none
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + res.getResourcePackageName(resId) + "/"
                + res.getResourceTypeName(resId) + "/"
                + res.getResourceEntryName(resId);

        // Parser the String in order to construct a URI
        return Uri.parse(uri);

    }


}
