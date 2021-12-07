package com.example.pets.data;

import android.content.Context;
import android.database.Cursor;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.pets_database_java.R;


public class PetCursorAdapter extends CursorAdapter {

    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //Create and return new blank list item
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Populate list item view with pet data

        //get the views from list item
        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView summaryView = (TextView) view.findViewById(R.id.summary);

        //Get cursor info
        int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME);
        int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED);
        String name = cursor.getString(nameColumnIndex);
        String breed = cursor.getString(breedColumnIndex);
        if(TextUtils.isEmpty(breed)){
            summaryView.setText("Unknown breed");
        }else{
            summaryView.setText(breed);
        }
        //Set View texts to info
        nameView.setText(name);

    }
}
