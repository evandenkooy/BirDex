package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.helloworld.R;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.ui.theme.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Objects;


public class YourProfileActivity extends AppCompatActivity implements RecyclerViewInterface {
    private customCharacterView characterView;

    private LinearLayout skinScrollView, eyesScrollView, noseScrollView, mouthScrollView, hatScrollView, bodyScrollView;

    // Arrays holding the drawable resource IDs for each character part
    private int[] skinDrawables = {R.drawable.skin1, R.drawable.skin2, R.drawable.skin3,R. drawable.skin4, R.drawable.skin5, R.drawable.skin6, R.drawable.skin7, R.drawable.skin8, R.drawable.skin9, R.drawable.skin10, R.drawable.skin11, R.drawable.skin13, R.drawable.skin14, R.drawable.skin15};
    private int[] eyesDrawables = {R.drawable.eyes1, R.drawable.eyes2, R.drawable.eyes3};
    private int[] noseDrawables = {R.drawable.nose1, R.drawable.nose2, R.drawable.nose3, R.drawable.nose4, R.drawable.nose5, R.drawable.nose6};
    private int[] mouthDrawables = {R.drawable.mouth1, R.drawable.mouth2, R.drawable.mouth3};
    private int[] hatDrawables = {R.drawable.hat1, R.drawable.hat2, R.drawable.hat3, R.drawable.hat4, R.drawable.hat5, R.drawable.hat6, R.drawable.hat7, R.drawable.hat8};
    private int[] bodyDrawables = {R.drawable.body1, R.drawable.body2, R.drawable.body3, R.drawable.body4, R.drawable.body5, R.drawable.body6, R.drawable.body7, R.drawable.body8};

    private int selectedSkin, selectedEyes, selectedNose, selectedMouth, selectedHat, selectedBody;
    private int tempSkin, tempEyes, tempNose, tempMouth, tempHat, tempBody;

    private SharedPreferencesHelper preferencesHelper;
    private LogAdapter.RecyclerViewClickListener listener;

    private LogAdapter myAdapter;

    private List<LoggedItem> items;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            //sets theming to red
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_window);

        //initialize preferences helper
        preferencesHelper = new SharedPreferencesHelper(this);

        //grab items from birdataimporter
        items = CSVReader.getItems(this, "data.csv");

        //load the users body parts
        selectedBody = preferencesHelper.loadBody();
        selectedSkin = preferencesHelper.loadSkin();
        selectedEyes = preferencesHelper.loadEyes();
        selectedHat = preferencesHelper.loadHat();
        selectedMouth = preferencesHelper.loadMouth();
        selectedNose = preferencesHelper.loadNose();

        //bottom navigation to switch activities
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem itemMenu) {
                switch (itemMenu.getItemId()) {
                    case R.id.navigation_dex:
                        startActivity(new Intent(YourProfileActivity.this, MainActivity.class));
                        return true;
                    case R.id.navigation_profile:
                        return true;
                    case R.id.navigation_map:
                        startActivity(new Intent(YourProfileActivity.this, GPSActivity.class));
                        return true;
                }
                return false;
            }
        });

        //set characterview ui to varuiable
        characterView = findViewById(R.id.characterView);

        // Customize character in general
        if (selectedBody != 0){ characterView.setBody(selectedBody);}else{characterView.setBody(R.drawable.body1);}
        if (selectedSkin != 0){ characterView.setHead(selectedSkin);}else{characterView.setHead(R.drawable.skin1);}
        if (selectedEyes != 0){ characterView.setEyes(selectedEyes);}else{characterView.setEyes(R.drawable.eyes1);}
        if (selectedNose != 0){ characterView.setNose(selectedNose);}else{characterView.setNose(R.drawable.nose1);}
        if (selectedMouth != 0){ characterView.setMouth(selectedMouth);}else{characterView.setMouth(R.drawable.mouth1);}
        if (selectedHat != 0){ characterView.setHat(selectedHat);}else{characterView.setHat(R.drawable.hat1);}

        //setting characterview ui for an editable version
        customCharacterView editableCharacterView = findViewById(R.id.editableCharacterView);

        // Customize character in editor
        if (selectedBody != 0){ editableCharacterView.setBody(selectedBody);}else{editableCharacterView.setBody(R.drawable.body1);}
        if (selectedSkin != 0){ editableCharacterView.setHead(selectedSkin);}else{editableCharacterView.setHead(R.drawable.skin1);}
        if (selectedEyes != 0){ editableCharacterView.setEyes(selectedEyes);}else{editableCharacterView.setEyes(R.drawable.eyes1);}
        if (selectedNose != 0){ editableCharacterView.setNose(selectedNose);}else{editableCharacterView.setNose(R.drawable.nose1);}
        if (selectedMouth != 0){ editableCharacterView.setMouth(selectedMouth);}else{editableCharacterView.setMouth(R.drawable.mouth1);}
        if (selectedHat != 0){ editableCharacterView.setHat(selectedHat);}else{editableCharacterView.setHat(R.drawable.hat1);}

        skinScrollView = findViewById(R.id.skin_scrollview);
        eyesScrollView = findViewById(R.id.eyes_scrollview);
        noseScrollView = findViewById(R.id.nose_scrollview);
        mouthScrollView = findViewById(R.id.mouth_scrollview);
        hatScrollView = findViewById(R.id.hat_scrollview);
        bodyScrollView = findViewById(R.id.body_scrollview);

        //add character piece options to scrollview
        populateScrollView(skinScrollView, skinDrawables, "skin");
        populateScrollView(eyesScrollView, eyesDrawables, "eyes");
        populateScrollView(noseScrollView, noseDrawables, "nose");
        populateScrollView(mouthScrollView, mouthDrawables, "mouth");
        populateScrollView(hatScrollView, hatDrawables, "hat");
        populateScrollView(bodyScrollView, bodyDrawables, "body");

        //ui for changing outfit
        ImageButton hangerButton = findViewById(R.id.change_outfit_button);
        FrameLayout changeOutfitFrame = findViewById(R.id.changeOutfitFrame);

        //hangerbutton to open outfit changer
        hangerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOutfitFrame.setVisibility(View.VISIBLE);
                tempNose = 0;
                tempBody = 0;
                tempEyes = 0;
                tempSkin = 0;
                tempHat = 0;
                tempMouth = 0;

            }
        });

        //button to confirm outfit
        ImageButton confirmOutfitButton = findViewById(R.id.confirm_outfit_button);

        //onclick to set new outfit and store data
        confirmOutfitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOutfitFrame.setVisibility(View.INVISIBLE);
                characterView = findViewById(R.id.characterView);

                if(tempBody != 0){selectedBody = tempBody; preferencesHelper.saveBody(tempBody);}
                if(tempSkin != 0){selectedSkin = tempSkin; preferencesHelper.saveSkin(tempSkin);}
                if(tempEyes != 0){selectedEyes = tempEyes; preferencesHelper.saveEyes(tempEyes);}
                if(tempMouth != 0){selectedMouth = tempMouth; preferencesHelper.saveMouth(tempMouth);}
                if(tempNose != 0){selectedNose = tempNose; preferencesHelper.saveNose(tempNose);}
                if(tempHat != 0){selectedHat = tempHat; preferencesHelper.saveHat(tempHat);}

                if (selectedBody != 0){ characterView.setBody(selectedBody);}else{characterView.setBody(R.drawable.body1);}
                if (selectedSkin != 0){ characterView.setHead(selectedSkin);}else{characterView.setHead(R.drawable.skin1);}
                if (selectedEyes != 0){ characterView.setEyes(selectedEyes);}else{characterView.setEyes(R.drawable.eyes1);}
                if (selectedNose != 0){ characterView.setNose(selectedNose);}else{characterView.setNose(R.drawable.nose1);}
                if (selectedMouth != 0){ characterView.setMouth(selectedMouth);}else{characterView.setMouth(R.drawable.mouth1);}
                if (selectedHat != 0){ characterView.setHat(selectedHat);}else{characterView.setHat(R.drawable.hat1);}

            }
        });


        //recyclerview to hold logs
        RecyclerView recyclerView = findViewById(R.id.recyclerviewlogs);

        //adapter for recyclerview
        myAdapter = new LogAdapter(getApplicationContext(),items, this, listener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

    }

    //add imagebuttons to select body and outfit in outfit selector
    private void populateScrollView(LinearLayout scrollView, int[] drawables, String part) {
        int buttonSize = 100;
        if (Objects.equals(part, "eyes") | Objects.equals(part, "nose") | Objects.equals(part, "mouth")){
            buttonSize = 100;
        }
        for (int drawable : drawables) {
            FrameLayout frameLayout = new FrameLayout(this);
            ImageButton imageButton = new ImageButton(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonSize, buttonSize);
            if (Objects.equals(part, "eyes") | Objects.equals(part, "nose") | Objects.equals(part, "mouth")){
                params.gravity= Gravity.TOP;
            }
            imageButton.setLayoutParams(params);
            imageButton.setBackgroundResource(drawable);
            imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButton.setOnClickListener(v -> onPartSelected(part, drawable, imageButton));

            frameLayout.addView(imageButton);
            scrollView.addView(frameLayout);
        }
    }

    //when part is selected update the editable character and store data temporarily
    private void onPartSelected(String part, int drawable, ImageButton clickedButton) {
        // Deselect all buttons in scrollView
        LinearLayout parentScrollView = (LinearLayout) clickedButton.getParent().getParent();
        for (int i = 0; i < parentScrollView.getChildCount(); i++) {
            FrameLayout frameLayout = (FrameLayout) parentScrollView.getChildAt(i);
            ImageButton imageButton = (ImageButton) frameLayout.getChildAt(0);
            imageButton.setSelected(false);
        }

        // Select the clicked button
        clickedButton.setSelected(true);

        // Update the character looks
        updateCharacterAppearance(part, drawable);
     }

     //updates the appearance of the character in the outfit editor
    private void updateCharacterAppearance(String part, int drawable) {
        customCharacterView editableCharacterView = findViewById(R.id.editableCharacterView);

        if (part == "skin"){
            editableCharacterView.setHead(drawable);
            tempSkin = drawable;
        } else if (part == "eyes"){
            editableCharacterView.setEyes(drawable);
            tempEyes = drawable;
        } else if (part == "nose"){
            editableCharacterView.setNose(drawable);
            tempNose = drawable;
        } else if (part == "mouth"){
            editableCharacterView.setMouth(drawable);
            tempMouth = drawable;
        } else if (part == "hat"){
            editableCharacterView.setHat(drawable);
            tempHat = drawable;
        } else if (part == "body"){
            editableCharacterView.setBody(drawable);
            tempBody = drawable;
        }

    }

    @Override
    public void onItemClick(int position) {

    }


}
