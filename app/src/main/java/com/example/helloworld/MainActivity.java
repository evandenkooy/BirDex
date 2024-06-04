package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;



import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.helloworld.ui.theme.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private MyAdapter.RecyclerViewClickListener listener;


    private SearchView searchView;

    //variables for scroillview
    private ImageButton button;

    private MyAdapter myAdapter;

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            //set theme to red
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        List<String> colorsFilter = intent.getStringArrayListExtra("list_key");

        //button to open image filter
        button = findViewById(R.id.filterImageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilterActivity();
            }
        });


        //recyclerview for items
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        //importing items for each bird button
        items = BirdDataImporter.importBirdData(this);


        //creating searchview to earch by scientific or common name
        SearchView searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });





        //sending data to bird profile when opened
        listener = new MyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                List<Item> filteredItems = myAdapter.getItems();
                if (filteredItems != null && position >= 0 && position < filteredItems.size()) {
                    Item clickedItem = filteredItems.get(position);
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    intent.putExtra("birdsName", clickedItem.getName());
                    intent.putExtra("birdsSciName", clickedItem.getScientificName());
                    intent.putExtra("birdsIcon", clickedItem.getBirdIcon());
                    intent.putExtra("birdsHabitatExtended", clickedItem.getHabitatExtended());
                    intent.putExtra("birdsBehavior", clickedItem.getBehavior());
                    intent.putExtra("birdsConservation", clickedItem.getConservation());
                    intent.putExtra("birdsPopulation", clickedItem.getPopulation());
                    intent.putExtra("birdsDescription", clickedItem.getDescription());
                    intent.putExtra("birdsSize", clickedItem.getSize());
                    intent.putExtra("birdsColor", clickedItem.getColor());
                    intent.putExtra("birdsWingShape", clickedItem.getWingShape());
                    intent.putExtra("birdsTailShape", clickedItem.getTailShape());
                    intent.putExtra("birdsCallPattern", clickedItem.getCallPattern());
                    intent.putExtra("birdsCallType", clickedItem.getCallType());
                    intent.putExtra("birdsEggs", clickedItem.getEggs());
                    intent.putExtra("birdsDiet", clickedItem.getDiet());
                    intent.putExtra("birdsYoung", clickedItem.getYoung());
                    intent.putExtra("birdsFeeding", clickedItem.getFeeding());
                    intent.putExtra("birdsNesting", clickedItem.getNesting());
                    intent.putExtra("birdId", clickedItem.getBirdNumber());
                    intent.putExtra("atGlance", clickedItem.getAtGlance());
                    intent.putExtra("category", clickedItem.getCategory());
                    intent.putExtra("webName", clickedItem.getWebname());

                    startActivity(intent);
                } else {
                    Log.e("MainActivity", "Invalid position: " + position);
                }
            }
        };












        //INITIALIZE ADAPER FOR RECYCLERVIEW
        myAdapter = new MyAdapter(getApplicationContext(),items, this, listener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        filterList("");

        //bottom bar to swicth activities
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dex);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem itemMenu) {
                switch (itemMenu.getItemId()) {
                    case R.id.navigation_dex:
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(MainActivity.this, YourProfileActivity.class));
                        return true;
                    case R.id.navigation_map:
                        startActivity(new Intent(MainActivity.this, GPSActivity.class));
                        return true;
                }
                return false;
            }
        });




    }

    //filters the list of items to be diplayed
    private void filterList(String text) {
        Intent intent = getIntent();
        ArrayList<String> colorsFilter = getIntent().getStringArrayListExtra("colors_filter");
        ArrayList<String> sizesFilter = getIntent().getStringArrayListExtra("sizes_filter");
        ArrayList<String> typesFilter = getIntent().getStringArrayListExtra("types_filter");
        ArrayList<String> tailsFilter = getIntent().getStringArrayListExtra("tails_filter");
        ArrayList<String> wingsFilter = getIntent().getStringArrayListExtra("wings_filter");
        ArrayList<String> habitatsFilter = getIntent().getStringArrayListExtra("habitats_filter");
        ArrayList<String> activitiesFilter = getIntent().getStringArrayListExtra("activities_filter");


        List<Item> filteredList = new ArrayList<>();

        for (Item item : items) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) || item.getScientificName().toLowerCase().contains(text.toLowerCase())) {
                boolean passColorFilter = colorsFilter == null || colorsFilter.isEmpty() || itemContainsAllColors(item, colorsFilter);
                boolean passSizeFilter = sizesFilter == null || sizesFilter.isEmpty() || itemContainsAllSizes(item, sizesFilter);
                boolean passTypeFilter = typesFilter == null || typesFilter.isEmpty() || itemContainsAllTypes(item, typesFilter);
                boolean passWingFilter = wingsFilter == null || wingsFilter.isEmpty() || itemContainsAllWingShapes(item, wingsFilter);
                boolean passTailFilter = tailsFilter == null || tailsFilter.isEmpty() || itemContainsAllTailShapes(item, tailsFilter);
                boolean passHabitatFilter = habitatsFilter == null || habitatsFilter.isEmpty() || itemContainsAllHabitats(item, habitatsFilter);
                boolean passActivityFilter = activitiesFilter == null || activitiesFilter.isEmpty() || itemContainsAllActivities(item, activitiesFilter);


                if (passColorFilter && passSizeFilter && passTypeFilter&& passTailFilter&& passWingFilter&& passActivityFilter&& passHabitatFilter) {
                    filteredList.add(item);
                }
            }
        }

        if (filteredList.isEmpty()) {
            List<Item> emptyList = new ArrayList<>();
            myAdapter.setFilteredList(emptyList);
        } else {
            myAdapter.setFilteredList(filteredList);
            myAdapter.notifyDataSetChanged(); // Notify RecyclerView of dataset change
        }
    }

    //checks if items contain filtered colors
    private boolean itemContainsAllColors(Item item, List<String> colorsFilter) {
        for (String color : colorsFilter) {
            if (!item.getExtractedColors().contains(color)) {
                return false;
            }
        }
        return true;
    }
    //checks if items contain filtered sizes
    private boolean itemContainsAllSizes(Item item, List<String> sizesFilter) {
        for (String size : sizesFilter) {
            if (!item.getExtractedSizes().contains(size)) {
                return false;
            }
        }
        return true;
    }

    //checks if items contain filtered types
    private boolean itemContainsAllTypes(Item item, List<String> typesFilter) {
        for (String type : typesFilter) {
            if (!item.getExtractedTypes().contains(type)) {
                return false;
            }
        }
        return true;
    }

    //checks if items contain filtered acitvities
    private boolean itemContainsAllActivities(Item item, List<String> activitiesFilter) {
        for (String activity : activitiesFilter) {
            if (!item.getExtractedActivities().contains(activity)) {
                return false;
            }
        }
        return true;
    }

    //checks if items contain filtered tail shapes
    private boolean itemContainsAllTailShapes(Item item, List<String> tailsFilter) {
        for (String tail : tailsFilter) {
            if (!item.getExtractedTailShapes().contains(tail)) {
                return false;
            }
        }
        return true;
    }

    //checks if items contain filtered wking shapes
    private boolean itemContainsAllWingShapes(Item item, List<String> wingsFilter) {
        for (String wing : wingsFilter) {
            if (!item.getExtractedWingShapes().contains(wing)) {
                return false;
            }
        }
        return true;
    }

    //checks if items contain filtered habitats
    private boolean itemContainsAllHabitats(Item item, List<String> habitatsFilter) {
        for (String habitat : habitatsFilter) {
            if (!item.getExtractedHabitats().contains(habitat)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void onItemClick(int position) {

    }

    //opens filter actiovity
    public void openFilterActivity(){
        Intent intent2 = new Intent(this, FilterActivity.class);
        startActivity(intent2);
    }
}
