package com.example.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PetProvider extends ContentProvider {
    private PetDbHelper mDbHelper;
    //setting up the two path codes
    private static final int PETS = 100;
    private static final int PET_ID = 101;
    //initializing UriMatcher
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    //setting up our two paths
    static{
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS, PETS);
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS + "/#", PET_ID);
    }


    @Override
    public boolean onCreate() {
        //initialize PetDbHelper to get access to the database
        mDbHelper = new PetDbHelper(getContext());
        return false;
    }

    //perform query for the given URI. Use given projection, selection, selectionArg, and sortorder
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //Get database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        //initialize for result
        Cursor cursor = null;
        int match = sUriMatcher.match(uri);
        switch(match){
            case PETS:
                cursor = database.query(PetContract.PetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PET_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = PetContract.PetEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                //perform query
                cursor = database.query(PetContract.PetEntry.TABLE_NAME, projection,selection,selectionArgs,null,null, sortOrder);
                break;
        }
        return cursor;

    }

    //Insert new data into the provider with given Content values
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        //wouldnt make sense to insert a pet to a specific occupied row
        switch(match){
            case PETS:
                return insertPet(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
    private Uri insertPet(Uri uri, ContentValues values){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowID = db.insert(PetContract.PetEntry.TABLE_NAME, null, values);
        if (newRowID == -1) {

            return null;
        }
        return ContentUris.withAppendedId(uri, newRowID);
    }

    //delete data at given selection and selection arguments
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    //Uopdate data at given selection and selection Arg with new Content values
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    //Return the MIME type of data for the content URI
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
