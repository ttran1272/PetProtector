package edu.orangecoastcollege.cs273.petprotector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnhTran on 11/6/2017.
 */

public class PetListAdapter extends ArrayAdapter<Pet>{

    private Context mContext;
    private List<Pet> mPetsList = new ArrayList<>();
    private int mResourceId;

    private LinearLayout mLayout;


    public PetListAdapter(Context c, int rId, List<Pet> pets){
        super(c, rId, pets);
        mContext = c;
        mPetsList = pets;
        mResourceId = rId;
    }

    public View getView(int pos, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        mLayout = (LinearLayout) view.findViewById(R.id.petListLinearLayout);
        ImageView petItemImageView = (ImageView) view.findViewById(R.id.selectPetImageView);
        TextView listItemPetNameTextView = (TextView) view.findViewById(R.id.petListNameTextView);
        TextView listItemPetDetailsTextView = (TextView) view.findViewById(R.id.petListDetailTextView);

        Pet aPet = mPetsList.get(pos);

        // Retrieve the pet name
        listItemPetNameTextView.setText(aPet.getName());

        // Retrieve the pet details
        listItemPetDetailsTextView.setText(aPet.getDetail());

        // TODO: Retrieve the pet image


        mLayout.setTag(aPet);

        return view;
    }


}
