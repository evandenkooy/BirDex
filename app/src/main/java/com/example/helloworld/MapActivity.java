package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    //variables for map
    private GoogleMap mMap;
    private LatLng selectedLocation;
    private Marker locationMarker;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mappy);

        Bundle extras = getIntent().getExtras();

        // Initialize SDK
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyB-3PIDrBNKsJMFrKu3HwLPA5cVyM8Uc8s");
        }

        //initialize map fragmenmt
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //button to confirm lcoation
        ImageButton confirmButton = findViewById(R.id.btn_confirm_location);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLocation != null) {
                    returnLocation();
                } else {
                    Toast.makeText(MapActivity.this, "No location selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //button to open frame to search locaiton
        ImageButton searchLocationButton = findViewById(R.id.btn_search_location);
        searchLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the fields to specify which types of place data to return.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(MapActivity.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });


    }

    //open the map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set a marker on tap
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (locationMarker != null) {
                    locationMarker.remove();
                }
                selectedLocation = latLng;
                setLocationMarker(selectedLocation);
            }
        });
    }

    //send the selected location to logger when finished
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    selectedLocation = latLng;
                    setLocationMarker(selectedLocation);
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("MapActivity", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                //user canceled the operation.
            }
        }
    }

    //sets icon of user character to the location
    private void setLocationMarker(LatLng latLng) {
        if (locationMarker != null) {
            locationMarker.remove();
        }
        locationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    //returns current lcoationm
    private void returnLocation() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("location", selectedLocation.latitude + "," + selectedLocation.longitude);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}