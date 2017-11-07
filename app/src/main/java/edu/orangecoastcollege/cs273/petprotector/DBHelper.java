package edu.orangecoastcollege.cs273.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private static final String PHONE = "phone";
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
                + PHONE + " TEXT, "
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
        ArrayList<Pet> petList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_FIELD_ID, NAME, DETAILS, PET_IMAGE_NAME},
                null, null, null, null, null);

        if (cursor.moveToFirst()){
            do {
                Pet pet = new Pet(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(5));
                petList.add(pet);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return petList;

    }
}
