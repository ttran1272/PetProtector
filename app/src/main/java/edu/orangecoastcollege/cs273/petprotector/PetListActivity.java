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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PetListActivity extends AppCompatActivity {

    private LinearLayout mLayout;

    private DBHelper db;
    private List<Pet> petsList;
    private PetListAdapter petsListAdapter;
    private ListView petsListView;

    private Uri imageUri ;

    // References to the widgets needed
    private ImageView petImageView;
    private EditText mPetName;
    private EditText mPetDetails;
    private EditText mPetPhone;

    // Constants for permissions:
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    private static final int DENIED = PackageManager.PERMISSION_DENIED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        // Initiate pet image
        imageUri = getUriFromResource(this, R.drawable.none);

        // Clear the existing database
        //deleteDatabase(DBHelper.DATABASE_NAME);

        // Instantiate a new DBHelper
        db = new DBHelper(this);

        // Instantiate the image view
        petImageView = (ImageView) findViewById(R.id.selectPetImageView);
        petImageView.setImageURI(getUriFromResource(this, R.drawable.none));

        // Instantiate the Pet List View
        petsListView = (ListView) findViewById(R.id.petListView);

        // Clear out the list and Fill the petsList with all Pets from the database
        petsList = db.getAllPets();

        // Connect the list adapter with the list
        petsListAdapter = new PetListAdapter(this, R.layout.pet_list_item, petsList);

        // Set the list view to use the list adapter
        petsListView.setAdapter(petsListAdapter);
    }

    /**
     * This function return the pet details
     * @param view
     */
    public void viewPetDetails(View view){
        mLayout = (LinearLayout) view;

        Pet selectedPet = (Pet) mLayout.getTag();

        // Implement the view pet details using an Intent
        Intent detailsIntent = new Intent(this, PetDetailsActivity.class);

        detailsIntent.putExtra("Name", selectedPet.getName());
        detailsIntent.putExtra("Details", selectedPet.getDetail());
        detailsIntent.putExtra("Phone", selectedPet.getPhone());
        detailsIntent.putExtra("ImageName", selectedPet.getPetImageName());

        startActivity(detailsIntent);
    }


    /**
     * This method is an onClick method, which allows the user to select an image view from the gallery
     * @param v
     */
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


    /**
     * This function displays the image in the image view
     * @param requestCode
     * @param resultCode
     * @param data
     */
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


    /**
     * This function gets Uri from the resource
     * @param context
     * @param resId
     * @return
     */
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


    /**
     * This function adds a new pet to the database by clicking on the Add button in the layout
     * @param view
     */
    public void addPet(View view){

        mPetName = (EditText) findViewById(R.id.nameEditText);
        mPetDetails = (EditText) findViewById(R.id.detailsEditText);
        mPetPhone = (EditText) findViewById(R.id.phoneEditText);

        String name = mPetName.getText().toString();
        String details = mPetDetails.getText().toString();
        String phone = mPetPhone.getText().toString();
        Uri petImage = imageUri;

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(details) || TextUtils.isEmpty(phone))
            Toast.makeText(this, "All information about the pet must be provided", Toast.LENGTH_LONG).show();
        else
        {
            // Create a new pet object
            Pet pet = new Pet(name, details, phone, petImage.toString());

            // Add pet to the pet list
            petsList.add(pet);

            // Add pet to the database
            db.addPet(pet);

            // Notify the list adapter that it has been changed
            petsListAdapter.notifyDataSetChanged();

            // Clear out the EditTExts
            mPetName.setText("");
            mPetDetails.setText("");
            mPetPhone.setText("");
            imageUri = getUriFromResource(this, R.drawable.none);
            petImageView.setImageURI(imageUri);

        }

    }
}
