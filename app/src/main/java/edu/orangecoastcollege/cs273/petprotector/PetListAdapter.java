package edu.orangecoastcollege.cs273.petprotector;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttran1272 on 10/31/2017.
 */

public class PetListAdapter extends ArrayAdapter<Pet> {

    private Context mContext;
    private List<Pet> mPetsList = new ArrayList<>();
    private int mResourceId;

    private LinearLayout mLayout;

    public PetListAdapter(Context context, int rId, List<Pet> pets){
        super(context, rId, pets);

        mContext = context;
        mPetsList = pets;
        mResourceId = rId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(mResourceId, null);

        mLayout = (LinearLayout) view.findViewById(R.id.petListLinearLayout);

        ImageView listItemImageView = (ImageView) view.findViewById(R.id.petListImageView);
        TextView listItemPetNameTextView = (TextView) view.findViewById(R.id.petListNameTextView);
        TextView listItemPetDetailsTextView = (TextView) view.findViewById(R.id.petListDetailTextView);

        Pet aPet = mPetsList.get(position);

        // Retrieve the pet name and details
        listItemPetNameTextView.setText(aPet.getName());
        listItemPetDetailsTextView.setText(aPet.getDetail());

        // TODO: retrieve the pet image


        mLayout.setTag(aPet);

        return view;


    }
}
