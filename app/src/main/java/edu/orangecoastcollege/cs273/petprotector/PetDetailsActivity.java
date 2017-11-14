package edu.orangecoastcollege.cs273.petprotector;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.InterpolatorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URI;


/**
 * Created by AnhTran on 10/29/2017.
 */

public class PetDetailsActivity extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        TextView petDetailsNameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView petDetailsDetailTextView = (TextView) findViewById(R.id.detailTextView);
        TextView petDetailsPhoneTextView = (TextView) findViewById(R.id.phoneTextView);

        Intent detailsIntent = getIntent();
        String name = detailsIntent.getStringExtra("Name");
        String detail = detailsIntent.getStringExtra("Details");
        String phone = detailsIntent.getStringExtra("Phone");

        petDetailsNameTextView.setText(name);
        petDetailsDetailTextView.setText(detail);
        petDetailsPhoneTextView.setText(phone);

        ImageView petDetailsImageView = (ImageView) findViewById(R.id.imageView);

        Uri selectedImage = Uri.parse(detailsIntent.getStringExtra("ImageName"));

        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        try {
            // If there is a pet image
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            petDetailsImageView.setImageBitmap(bmp);

        } catch (NullPointerException e) {
            // if no pet image was selected, using the default image
            Log.e("PetDetailsActivity", "NullPointerException because of having no pet image");
            petDetailsImageView.setImageURI(selectedImage);
        }
    }


    /**
     * This function is used to get the Bitmap image from the Uri
     * @param uri
     * @return
     * @throws IOException
     */
    private Bitmap getBitmapFromUri(Uri uri) throws IOException{
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        assert parcelFileDescriptor != null;
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


    /**
     * This function is used to get the Uri from the resource
     * @param context
     * @param resId
     * @return the Uri
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
}
