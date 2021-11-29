package com.example.pets.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

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
        return null;
    }

    //Insert new data into the provider with given Content values
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
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
