package com.faroukelkhayat.gurugoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Farouk on 3/4/2018.
 */

public class GuruMapsActivity extends AppCompatActivity {

    private TextInputEditText sourceInputEditText;
    private TextInputEditText destinationInputEditText;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_maps);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Guru Maps");


        sourceInputEditText = findViewById(R.id.sourceInputEditText);
        destinationInputEditText =  findViewById(R.id.destinationInputEditText);

    }

    //Method tied to route button for getting directions
    public void getRoute(View view){
        String sourceAddress = sourceInputEditText.getText().toString();
        String destinationAddress = destinationInputEditText.getText().toString();

        //Navigation query for source and destination
        String navQuery = "https://www.google.com/maps/dir/?api=1&origin=" + sourceAddress + "&destination=" + destinationAddress;
        Uri gmmIntentUri = Uri.parse(navQuery);

        //Opens google maps with the route query
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
