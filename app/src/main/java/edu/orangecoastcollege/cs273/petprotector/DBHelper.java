package edu.orangecoastcollege.cs273.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by ttran1272 on 10/26/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "Pet Protector";
    static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;

    private static final String KEY_FIELD_ID = "id";
    private static final String NAME = "name";
    public static final String DETAILS = "details";
    public static final String PET_IMAGE_NAME = "image_name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate (SQLiteDatabase db)
    {
        String createTable = "CREATE TABLE " + DATABASE_NAME + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, "
                + DETAILS + " TEXT, "
                + PET_IMAGE_NAME + " TEXT )";

        db.execSQL(createTable);

    }

    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXIST " + db);
        onCreate(db);
    }

    public void addPet (Pet pet){

        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_FIELD_ID, pet.getId());
        values.put(NAME, pet.getName());
        values.put(DETAILS, pet.getDetail());
        values.put(PET_IMAGE_NAME, pet.getPetImageName());

        long id = db.insert(DATABASE_TABLE, null, values);
        pet.setId(id);
        db.close();
    }

    public ArrayList<Pet> getAllPets() {

    }
}
