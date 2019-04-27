package com.newplacessdkclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int AUTOCOMPLETE_REQUEST_CODE = 99;
    // Define a Place ID.
    String placeId = "AIzaSyDVrsPrQDaF10E7TmXCVxm1AIaUSsbEpoU";
    private TextView selectedAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Places.
        Places.initialize(getApplicationContext(), placeId);

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Set the fields to specify which types of place data to return.
        final List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS);

        selectedAddress = findViewById(R.id.tvSelectedAddress);
        findViewById(R.id.btnAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(MainActivity.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.e(TAG, "onActivityResult: Place: " + place.getName());
                Log.e(TAG, "onActivityResult: Place: " + place.getAddressComponents().asList().get(0).getName());
                Log.e(TAG, "onActivityResult: Place: " + place.getAddressComponents().asList().get(1).getName());
                Log.e(TAG, "onActivityResult: Place: " + place.getAddressComponents().asList().get(2).getName());
                Log.e(TAG, "onActivityResult: Place: " + place.getAddressComponents().asList().get(3).getName());
                Log.e(TAG, "onActivityResult: LatLang: " + place.getLatLng().latitude + ", " + place.getLatLng().longitude);
                selectedAddress.setText(place.getName());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e(TAG, "onActivityResult: Status : " + status.getStatusMessage());
                selectedAddress.setText("Status : " + status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                Log.e(TAG, "onActivityResult: RESULT_CANCELED");
                selectedAddress.setText("RESULT_CANCELED");
            }
        }
    }
}