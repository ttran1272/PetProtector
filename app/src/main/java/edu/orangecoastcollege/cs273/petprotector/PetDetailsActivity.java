package edu.orangecoastcollege.cs273.petprotector;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static edu.orangecoastcollege.cs273.petprotector.PetListActivity.getUriFromResource;

/**
 * Created by AnhTran on 10/29/2017.
 */

public class PetDetailsActivity extends AppCompatActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        ImageView petDetailsImageView = (ImageView) findViewById(R.id.imageView);
        TextView petDetailsNameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView petDetailsDetailTextView = (TextView) findViewById(R.id.detailTextView);
        TextView petDetailsPhoneTextView = (TextView) findViewById(R.id.phoneTextView);

        Intent detailsIntent = getIntent();
        String name = detailsIntent.getStringExtra("Name");
        String detail = detailsIntent.getStringExtra("Detail");
        String phone = detailsIntent.getStringExtra("Phone");
        String imageName = detailsIntent.getStringExtra("ImageName");

        petDetailsNameTextView.setText(name);
        petDetailsDetailTextView.setText(detail);
        petDetailsPhoneTextView.setText(phone);

        petDetailsImageView.setImageURI(getUriFromResource(this, R.drawable.none));


    }
}
