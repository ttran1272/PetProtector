package edu.orangecoastcollege.cs273.petprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ttran1272 on 10/31/2017.
 */

public class PetDetailsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        ImageView petDetailsImageView = (ImageView) findViewById(R.id.imageView);
        TextView petDetailsNameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView petDetailsDetailTextView = (TextView) findViewById(R.id.detailTextView);
        TextView petDetailsPhoneTextView = (TextView) findViewById(R.id.phoneTextView);

        Intent detailsIntent = getIntent();
        String name = detailsIntent.getStringExtra("Name");
        String phone = detailsIntent.getStringExtra("Phone");
        String detail = detailsIntent.getStringExtra("Detail");

    }
}
