package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterActivity extends AppCompatActivity {

    private LinearLayout mainContent;
    private Typeface customFont;
    private static final String PREF_NAME = "SelectedNumbers"; //relates this set of shared preferences

    private SharedPreferences sharedPreferences;

    //types to filter by
    private List<String> selectedColors = new ArrayList<>();
    private List<String> selectedSizes = new ArrayList<>();
    private List<String> selectedTails = new ArrayList<>();
    private List<String> selectedTypes = new ArrayList<>();
    private List<String> selectedActivities = new ArrayList<>();
    private List<String> selectedWings = new ArrayList<>();
    private List<String> selectedHabitats = new ArrayList<>();





//sends the info to main acitivity so it can filter items
    public void openMainActivityWithFilterVariables(List<String> selectedColors, List<String> selectedSizes, List<String> selectedTypes, List<String> selectedTails, List<String> selectedActivities, List<String> selectedWings, List<String> selectedHabitats) {
        // Modify the selectedSizes list
        List<String> modifiedSizes = new ArrayList<>();
        for (String size : selectedSizes) {
            if (size.equals("Mallard")) {
                modifiedSizes.add("About the size of a " + size + " or Herring Gull");
            } else {
                modifiedSizes.add("About the size of a " + size);
            }
        }

        // Pass the modified lists to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putStringArrayListExtra("colors_filter", (ArrayList<String>) selectedColors);
        intent.putStringArrayListExtra("sizes_filter", (ArrayList<String>) modifiedSizes);
        intent.putStringArrayListExtra("types_filter", (ArrayList<String>) selectedTypes);
        intent.putStringArrayListExtra("tails_filter", (ArrayList<String>) selectedTails);
        intent.putStringArrayListExtra("activities_filter", (ArrayList<String>) selectedActivities);
        intent.putStringArrayListExtra("wings_filter", (ArrayList<String>) selectedWings);
        intent.putStringArrayListExtra("habitats_filter", (ArrayList<String>) selectedHabitats);

        startActivity(intent);
    }

    //when you click the button to go back to main acitivity
    public void onBackPressed() {
        // Call your method here
        openMainActivityWithFilterVariables(selectedColors, selectedSizes, selectedTypes, selectedTails, selectedActivities, selectedWings, selectedHabitats);
        super.onBackPressed();
    }



    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red));
        }

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE); //load shared preferences
        loadSelectedNumbersState();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter); //set view to the filter acitivy

        Intent intentExit = new Intent(this, ExitService.class);
        startService(intentExit);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            customFont = getResources().getFont(R.font.pokemon_pixel_font); //pixel font setting
        }


//create scrollbar to select categories
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontalScrollView);
        final LinearLayout sliderContainer = findViewById(R.id.sliderContainer);
        mainContent = findViewById(R.id.mainContent);

        // Add buttons to slider for filkter categories
        final String[] categories = {"Color", "Size", "Type", "Habitat", "Tail Shape", "Wing Shape", "Activity"};
        for (final String category : categories) {
            Button button = new Button(this);
            button.setText(category);
            button.setTextSize(18);
            button.setTextColor(getResources().getColor(R.color.white));
            button.setTypeface(customFont);
            button.setBackgroundResource(R.drawable.slider_button_selector);
            if (category.equals("Color")) {
                button.setSelected(true);
                button.setTextColor(getResources().getColor(R.color.black));
                showOptionsForCategory(category); //Show options for the selected category
            }

            //onlcick for selecting button
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < sliderContainer.getChildCount(); i++) {
                        Button childButton = (Button) sliderContainer.getChildAt(i);
                        childButton.setSelected(false);
                        childButton.setTextColor(getResources().getColor(R.color.white)); // Reset text to white for all the buttons
                    }
                    Button clickedButton = (Button) v;
                    clickedButton.setSelected(true);
                    clickedButton.setTextColor(getResources().getColor(R.color.black)); // Change to blue for button selected
                    showOptionsForCategory(category);
                }
            });
            sliderContainer.addView(button);
        }

    }

//showingh the filter options for each category to filter
    private void showOptionsForCategory(String category) {
        mainContent.removeAllViews();
        if (category.equals("Color")) { //if its the color category
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            LinearLayout colorOptionsLayout = new LinearLayout(this);
            colorOptionsLayout.setLayoutParams(layoutParams);
            colorOptionsLayout.setOrientation(LinearLayout.VERTICAL);

            GridLayout colorOptionsGridLayout = new GridLayout(this);
            colorOptionsGridLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            colorOptionsGridLayout.setColumnCount(2);
            colorOptionsGridLayout.setOrientation(GridLayout.HORIZONTAL);

            //colors it could be
            final String[] colors = {"Black", "Gray", "White", "Tan", "Brown", "Red-Brown", "Red", "Pink", "Orange", "Yellow", "Green", "Olive", "Blue", "Purple"};
            //create buttons for each color that have each color as background
            for (final String color : colors) {
                final Button colorButton = new Button(this);
                colorButton.setText(color);
                if (!color.equals("White")){
                    colorButton.setTextColor(Color.WHITE);

                }else{
                    colorButton.setTextColor(Color.BLACK);

                }
                colorButton.setTypeface(customFont);
                GridLayout.LayoutParams buttonLayoutParams = new GridLayout.LayoutParams();
                buttonLayoutParams.width = 400;
                buttonLayoutParams.height = 150;
                if (color.equals("Red-Brown")) {
                    colorButton.setTextSize(30);
                    buttonLayoutParams.setMargins(50, 38, 50, 50);

                }else{
                    colorButton.setTextSize(50);
                    buttonLayoutParams.setMargins(50, 50, 50, 50);

                }
                colorButton.setPadding(16, 8, 16, 8);
                colorButton.setGravity(Gravity.CENTER);

                colorButton.setLayoutParams(buttonLayoutParams);
                colorButton.setBackgroundColor(getColorForColorName(color)); // Set background color
                if (selectedColors.contains(colorButton.getText().toString())) {
                    colorButton.setBackground(getButtonBackgroundWithOutline(getColorForColorName(color)));
                } else {
                    colorButton.setBackgroundColor(getColorForColorName(color));
                }
                //onclick to select color and update visually
                colorButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isSelected = !colorButton.isSelected(); // Toggle selection state
                        if (selectedColors.contains(colorButton.getText().toString())) {
                            //If number is already in list, remove it
                            selectedColors.remove(colorButton.getText().toString());
                        } else {
                            // Otherwise, add to the list
                            selectedColors.add(colorButton.getText().toString());
                        }
                        //Save state of selected numbers
                        saveSelectedNumbersState();
                        colorButton.setSelected(isSelected);
                        if (selectedColors.contains(colorButton.getText().toString())) {
                            colorButton.setBackground(getButtonBackgroundWithOutline(getColorForColorName(color)));
                        } else {
                            colorButton.setBackgroundColor(getColorForColorName(color));
                        }

                    }
                });
                colorOptionsGridLayout.addView(colorButton);
            }

            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int gridLayoutWidth = measureGridLayoutWidth(colorOptionsGridLayout);
            int leftMargin = (screenWidth - gridLayoutWidth) / 2;
            int rightMargin = (screenWidth - gridLayoutWidth) / 2;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) colorOptionsGridLayout.getLayoutParams();
            marginLayoutParams.setMargins(leftMargin, marginLayoutParams.topMargin, rightMargin, marginLayoutParams.bottomMargin);
            colorOptionsGridLayout.setLayoutParams(marginLayoutParams);

            colorOptionsLayout.addView(colorOptionsGridLayout);
            mainContent.addView(colorOptionsLayout);
        }
        if (category.equals("Size")) { //if the cateogry is size
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            LinearLayout sizeOptionsLayout = new LinearLayout(this);
            sizeOptionsLayout.setLayoutParams(layoutParams);
            sizeOptionsLayout.setOrientation(LinearLayout.VERTICAL);

            // Add buttons for diferent sizes with select images
            int[] buttonDrawables = {R.drawable.sparrow_for_icon, R.drawable.american_robin_icon, R.drawable.american_crow_icon, R.drawable.mallard_for_size, R.drawable.great_blue_heron_icon};
            String[] buttonTexts = {"Sparrow", "Robin", "Crow", "Mallard", "Heron"}; //possible sizes
            for (int i = 0; i < buttonDrawables.length; i++) {
                //layout to contain buttons
                RelativeLayout buttonLayout = new RelativeLayout(this);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonLayoutParams.setMargins(20, 0, 0, 0);
                buttonLayout.setLayoutParams(buttonLayoutParams);

                //Add button to  layout
                final ImageButton sizeButton = new ImageButton(this);
                sizeButton.setId(View.generateViewId()); // Set ID for button
                sizeButton.setImageResource(buttonDrawables[i]);
                sizeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                sizeButton.setBackgroundColor(Color.TRANSPARENT); //Set background color transparent

                //Apply blue color filter to  image if select
                if (selectedSizes.contains(buttonTexts[i])) {
                    sizeButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
                } else {
                    sizeButton.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                }

                //Set size of button
                int buttonSize = 200 + i * 80; //Increase size progressively
                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(buttonSize, buttonSize);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                sizeButton.setLayoutParams(buttonParams);

                buttonLayout.addView(sizeButton);

                //Add text view to layout for size
                TextView textView = new TextView(this);
                textView.setText(buttonTexts[i]);
                textView.setTextSize(50);
                textView.setTypeface(customFont);
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.RIGHT_OF, sizeButton.getId());
                textParams.addRule(RelativeLayout.CENTER_VERTICAL);
                textParams.setMargins(16, 0, 0, 0);
                textView.setLayoutParams(textParams);

                buttonLayout.addView(textView);

                final int buttonIndex = i;

                //onclick to select size and update visually
                buttonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Selected sizes: " + selectedSizes);
                        String selectedSize = buttonTexts[buttonIndex];
                        if (selectedSizes.contains(selectedSize)) {
                            // If the button is already selected, unselect it
                            selectedSizes.remove(selectedSize);
                            sizeButton.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        } else {
                            // If the button is not selected, select it
                            selectedSizes.add(selectedSize);
                            sizeButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
                        }
                        showOptionsForCategory("Size");
                        // Save selected sizes
                        saveSelectedNumbersState();
                        System.out.println("Selected sizes: " + selectedSizes);
                    }
                });

                // Add button layout to the main thing
                sizeOptionsLayout.addView(buttonLayout);
            }

            mainContent.addView(sizeOptionsLayout);
        }
        if (category.equals("Type")) { //if category for filter is type
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            LinearLayout sizeOptionsLayout = new LinearLayout(this);
            sizeOptionsLayout.setLayoutParams(layoutParams);
            sizeOptionsLayout.setOrientation(LinearLayout.VERTICAL);

            // Add image buttons for types
            int[] buttonDrawables = {R.drawable.gull_forfilter, R.drawable.red_tailed_hawk_icon, R.drawable.uprightwater_forfilter, R.drawable.barred_owl_icon, R.drawable.perch_forfilter, R.drawable.great_blue_heron_icon, R.drawable.cling_forfilter, R.drawable.uplandground_forfilter, R.drawable.mallard_for_size, R.drawable.hummingbird_forfilter, R.drawable.sandpiper_forfilter, R.drawable.pigeon_forfilter, R.drawable.chickenmarsh_forfilter, R.drawable.swallow_forfilter};
            String[] buttonTexts = {"Gull-like Birds", "Hawk-like Birds", "Upright-perching Water Birds", "Owls", "Perching Birds", "Long-legged Waders", "Tree-clinging Birds", "Upland Ground Birds", "Duck-like Birds", "Hummingbirds", "Sandpiper-like Birds", "Pigeon-like Birds", "Chicken-like Marsh Birds", "Swallow-like Birds"};
            for (int i = 0; i < buttonDrawables.length; i++) {
                // Create a RelativeLayout to contain buttons
                RelativeLayout buttonLayout = new RelativeLayout(this);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonLayoutParams.setMargins(20, 0, 0, 0);
                buttonLayout.setLayoutParams(buttonLayoutParams);

                // Add type button to layout
                final ImageButton typeButton = new ImageButton(this);
                typeButton.setId(View.generateViewId()); //Set ID for button
                typeButton.setImageResource(buttonDrawables[i]);
                typeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                typeButton.setBackgroundColor(Color.TRANSPARENT); //Set background color transparent

                // Apply blue color filter to image selected
                if (selectedTypes.contains(buttonTexts[i])) {
                    typeButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
                } else {
                    typeButton.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                }

                // Set size of button
                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(120, 120);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                typeButton.setLayoutParams(buttonParams);

                buttonLayout.addView(typeButton);

                // Add text view to the total layout
                TextView textView = new TextView(this);
                textView.setText(buttonTexts[i]);
                textView.setTextSize(20);
                textView.setTypeface(customFont);
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.RIGHT_OF, typeButton.getId());
                textParams.addRule(RelativeLayout.CENTER_VERTICAL);
                textParams.setMargins(16, 0, 0, 0);
                textView.setLayoutParams(textParams);

                buttonLayout.addView(textView);

                // Set click listener to toggle selection update UI
                final int buttonIndex = i;
                buttonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Selected Types: " + selectedTypes);
                        String selectedType = buttonTexts[buttonIndex];
                        if (selectedTypes.contains(selectedType)) {
                            // If  button is already selected, unselect it
                            selectedTypes.remove(selectedType);
                            typeButton.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        } else {
                            // If  button is not selected, select it
                            selectedTypes.add(selectedType);
                            typeButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
                        }
                        // Update the UI t
                        showOptionsForCategory("Type");
                        // Save selected types
                        saveSelectedNumbersState();
                        System.out.println("Selected types: " + selectedTypes);
                    }
                });

                // Add button layout
                sizeOptionsLayout.addView(buttonLayout);
            }

            mainContent.addView(sizeOptionsLayout);
        }
        if (category.equals("Tail Shape")) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            LinearLayout sizeOptionsLayout = new LinearLayout(this);
            sizeOptionsLayout.setLayoutParams(layoutParams);
            sizeOptionsLayout.setOrientation(LinearLayout.VERTICAL);

            int[] buttonDrawables = {R.drawable.long_tail, R.drawable.rounded_tail, R.drawable.square_tail, R.drawable.notched_tail, R.drawable.multi_pointed_tail, R.drawable.wedge_tail, R.drawable.forked_tail, R.drawable.short_tail, R.drawable.pointed_tail};
            String[] buttonTexts = {"Long", "Rounded", "Square-tipped", "Notched", "Multi-pointed", "Wedge-shaped", "Forked", "Short", "Pointed"};
            for (int i = 0; i < buttonDrawables.length; i++) {
                RelativeLayout buttonLayout = new RelativeLayout(this);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonLayoutParams.setMargins(20, 0, 0, 0);
                buttonLayout.setLayoutParams(buttonLayoutParams);

                final ImageButton tailButton = new ImageButton(this);
                tailButton.setId(View.generateViewId());
                tailButton.setImageResource(buttonDrawables[i]);
                tailButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                tailButton.setBackgroundColor(Color.TRANSPARENT);

                if (selectedTails.contains(buttonTexts[i])) {
                    tailButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
                } else {
                    tailButton.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                }

                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(200, 200);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                tailButton.setLayoutParams(buttonParams);

                buttonLayout.addView(tailButton);

                TextView textView = new TextView(this);
                textView.setText(buttonTexts[i]);
                textView.setTextSize(30);
                textView.setTypeface(customFont);
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.RIGHT_OF, tailButton.getId());
                textParams.addRule(RelativeLayout.CENTER_VERTICAL);
                textParams.setMargins(16, 0, 0, 0);
                textView.setLayoutParams(textParams);

                buttonLayout.addView(textView);

                final int buttonIndex = i;
                buttonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Selected Tails: " + selectedTails);
                        String selectedTail = buttonTexts[buttonIndex];
                        if (selectedTails.contains(selectedTail)) {
                            selectedTails.remove(selectedTail);
                            tailButton.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        } else {
                            selectedTails.add(selectedTail);
                            tailButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
                        }
                        showOptionsForCategory("Tail Shape");
                        saveSelectedNumbersState();
                        System.out.println("Selected tails: " + selectedTails);
                    }
                });

                sizeOptionsLayout.addView(buttonLayout);
            }

            mainContent.addView(sizeOptionsLayout);
        }
        if (category.equals("Activity")) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            LinearLayout sizeOptionsLayout = new LinearLayout(this);
            sizeOptionsLayout.setLayoutParams(layoutParams);
            sizeOptionsLayout.setOrientation(LinearLayout.VERTICAL);

            int[] buttonDrawables = {R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square};
            String[] buttonTexts = {"Direct Flight", "Soaring", "Flap/Glide", "Hovering", "Undulating", "Flushes", "Flitter", "Formation", "Erratic", "Swooping", "Running", "Tree-climbing", "Walking", "Hopping", "Swimming"};
            for (int i = 0; i < buttonDrawables.length; i++) {
                RelativeLayout buttonLayout = new RelativeLayout(this);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonLayoutParams.setMargins(20, 0, 0, 0);
                buttonLayout.setLayoutParams(buttonLayoutParams);

                final ImageButton activityButton = new ImageButton(this);
                activityButton.setId(View.generateViewId());
                activityButton.setImageResource(buttonDrawables[i]);
                activityButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                activityButton.setBackgroundColor(Color.TRANSPARENT);



                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(1, 120);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                activityButton.setLayoutParams(buttonParams);

                buttonLayout.addView(activityButton);

                TextView textView = new TextView(this);
                textView.setText(buttonTexts[i]);
                textView.setTextSize(50);
                textView.setTypeface(customFont);
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.RIGHT_OF, activityButton.getId());
                textParams.addRule(RelativeLayout.CENTER_VERTICAL);
                textParams.setMargins(16, 0, 0, 0);
                textView.setLayoutParams(textParams);

                buttonLayout.addView(textView);

                if (selectedActivities.contains(buttonTexts[i])) {
                    textView.setTextColor(Color.BLUE);
                } else {
                    textView.setTextColor(Color.GRAY);

                }

                final int buttonIndex = i;
                buttonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Selected Activities: " + selectedActivities);
                        String selectedActivity = buttonTexts[buttonIndex];
                        if (selectedActivities.contains(selectedActivity)) {
                            selectedActivities.remove(selectedActivity);
                            textView.setTextColor(Color.GRAY);
                        } else {
                            selectedActivities.add(selectedActivity);
                            textView.setTextColor(Color.BLUE);
                        }
                        showOptionsForCategory("Activity");
                        saveSelectedNumbersState();
                        System.out.println("Selected activities: " + selectedActivities);
                    }
                });

                sizeOptionsLayout.addView(buttonLayout);
            }

            mainContent.addView(sizeOptionsLayout);
        }
        if (category.equals("Wing Shape")) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            LinearLayout sizeOptionsLayout = new LinearLayout(this);
            sizeOptionsLayout.setLayoutParams(layoutParams);
            sizeOptionsLayout.setOrientation(LinearLayout.VERTICAL);

            int[] buttonDrawables = {R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square};
            String[] buttonTexts = {"Long", "Narrow", "Pointed", "Broad", "Rounded", "Fingered", "Short"};
            for (int i = 0; i < buttonDrawables.length; i++) {
                RelativeLayout buttonLayout = new RelativeLayout(this);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonLayoutParams.setMargins(20, 0, 0, 0);
                buttonLayout.setLayoutParams(buttonLayoutParams);

                final ImageButton wingButton = new ImageButton(this);
                wingButton.setId(View.generateViewId());
                wingButton.setImageResource(buttonDrawables[i]);
                wingButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                wingButton.setBackgroundColor(Color.TRANSPARENT);



                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(1, 220);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                wingButton.setLayoutParams(buttonParams);

                buttonLayout.addView(wingButton);

                TextView textView = new TextView(this);
                textView.setText(buttonTexts[i]);
                textView.setTextSize(60);
                textView.setTypeface(customFont);
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.RIGHT_OF, wingButton.getId());
                textParams.addRule(RelativeLayout.CENTER_VERTICAL);
                textParams.setMargins(16, 0, 0, 0);
                textView.setLayoutParams(textParams);

                buttonLayout.addView(textView);

                if (selectedWings.contains(buttonTexts[i])) {
                    textView.setTextColor(Color.BLUE);
                } else {
                    textView.setTextColor(Color.GRAY);

                }

                final int buttonIndex = i;
                buttonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Selected Wings: " + selectedWings);
                        String selectedWing = buttonTexts[buttonIndex];
                        if (selectedWings.contains(selectedWing)) {
                            selectedWings.remove(selectedWing);
                            textView.setTextColor(Color.GRAY);
                        } else {
                            selectedWings.add(selectedWing);
                            textView.setTextColor(Color.BLUE);
                        }
                        showOptionsForCategory("Wing Shape");
                        saveSelectedNumbersState();
                        System.out.println("Selected wings: " + selectedWings);
                    }
                });

                sizeOptionsLayout.addView(buttonLayout);
            }

            mainContent.addView(sizeOptionsLayout);
        }
        if (category.equals("Habitat")) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            LinearLayout sizeOptionsLayout = new LinearLayout(this);
            sizeOptionsLayout.setLayoutParams(layoutParams);
            sizeOptionsLayout.setOrientation(LinearLayout.VERTICAL);

            int[] buttonDrawables = {R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square};
            String[] buttonTexts = {"Forests and Woodlands", "Shrublands, Savannas, and Thickets", "Desert and Arid Habitats", "Arroyos and Canyons", "High Mountains", "Landfills and Dumps", "Fields, Meadows, and Grasslands", "Coasts and Shorelines", "Freshwater Wetlands", "Lakes, Ponds, and Rivers", "Saltwater Wetlands", "Urban and Suburban Habitats", "Tundra and Boreal Habitats", "Open Ocean"};
            for (int i = 0; i < buttonDrawables.length; i++) {
                RelativeLayout buttonLayout = new RelativeLayout(this);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonLayoutParams.setMargins(20, 0, 0, 0);
                buttonLayout.setLayoutParams(buttonLayoutParams);

                final ImageButton habitatButton = new ImageButton(this);
                habitatButton.setId(View.generateViewId());
                habitatButton.setImageResource(buttonDrawables[i]);
                habitatButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                habitatButton.setBackgroundColor(Color.TRANSPARENT);



                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(1, 140);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                habitatButton.setLayoutParams(buttonParams);

                buttonLayout.addView(habitatButton);

                TextView textView = new TextView(this);
                textView.setText(buttonTexts[i]);
                textView.setTextSize(30);
                textView.setTypeface(customFont);
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.RIGHT_OF, habitatButton.getId());
                textParams.addRule(RelativeLayout.CENTER_VERTICAL);
                textParams.setMargins(16, 0, 0, 0);
                textView.setLayoutParams(textParams);

                buttonLayout.addView(textView);

                if (selectedHabitats.contains(buttonTexts[i])) {
                    textView.setTextColor(Color.BLUE);
                } else {
                    textView.setTextColor(Color.GRAY);

                }

                final int buttonIndex = i;
                buttonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Selected Habitats: " + selectedHabitats);
                        String selectedHabitat = buttonTexts[buttonIndex];
                        if (selectedHabitats.contains(selectedHabitat)) {
                            selectedHabitats.remove(selectedHabitat);
                            textView.setTextColor(Color.GRAY);
                        } else {
                            selectedHabitats.add(selectedHabitat);
                            textView.setTextColor(Color.BLUE);
                        }
                        showOptionsForCategory("Habitat");
                        saveSelectedNumbersState();
                        System.out.println("Selected habitats: " + selectedHabitats);
                    }
                });

                sizeOptionsLayout.addView(buttonLayout);
            }

            mainContent.addView(sizeOptionsLayout);
        }
    }

    //measure width of grid for color buttons
    private int measureGridLayoutWidth(GridLayout gridLayout) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        gridLayout.measure(widthMeasureSpec, heightMeasureSpec);
        return gridLayout.getMeasuredWidth();
    }

//creating the backgroudn for color buttons
    private GradientDrawable getButtonBackgroundWithOutline(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setStroke(8, Color.BLUE); // Set blue stroke
        return drawable;
    }





//relating names to colors
    private int getColorForColorName(String colorName) {
        switch (colorName.toLowerCase()) {
            case "red":
                return getResources().getColor(R.color.red);
            case "blue":
                return getResources().getColor(R.color.blue);
            case "green":
                return getResources().getColor(R.color.green);
            case "yellow":
                return getResources().getColor(R.color.yellow);
            case "black":
                return getResources().getColor(R.color.black);
            case "white":
                return getResources().getColor(R.color.white);
            case "orange":
                return getResources().getColor(R.color.orange);
            case "purple":
                return getResources().getColor(R.color.purple);
            case "pink":
                return getResources().getColor(R.color.pink);
            case "brown":
                return getResources().getColor(R.color.brown);
            case "gray":
                return getResources().getColor(R.color.gray);
            case "red-brown":
                return getResources().getColor(R.color.reddish_brown);
            case "olive":
                return getResources().getColor(R.color.olive);
            case "tan":
                return getResources().getColor(R.color.tan); // Lime color
            default:
                return Color.TRANSPARENT;
        }
    }

    //saving the selected filter to hashmap
    private void saveSelectedNumbersState() {
        // Save the state of selected numbers to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("selectedNumbers", new HashSet<>(selectedColors));
        editor.putStringSet("selectedSizes", new HashSet<>(selectedSizes));
        editor.putStringSet("selectedTypes", new HashSet<>(selectedTypes));
        editor.putStringSet("selectedTails", new HashSet<>(selectedTails));
        editor.putStringSet("selectedActivities", new HashSet<>(selectedActivities));
        editor.putStringSet("selectedWings", new HashSet<>(selectedWings));
        editor.putStringSet("selectedHabitats", new HashSet<>(selectedHabitats));





        editor.apply();
    }

    //laoding the filter
    private void loadSelectedNumbersState() {
        Set<String> savedNumbers = sharedPreferences.getStringSet("selectedNumbers", new HashSet<String>());
        Set<String> savedSizes = sharedPreferences.getStringSet("selectedSizes", new HashSet<String>());
        Set<String> savedTypes = sharedPreferences.getStringSet("selectedTypes", new HashSet<String>());
        Set<String> savedTails = sharedPreferences.getStringSet("selectedTails", new HashSet<String>());
        Set<String> savedActivities = sharedPreferences.getStringSet("selectedActivities", new HashSet<String>());
        Set<String> savedWings = sharedPreferences.getStringSet("selectedWings", new HashSet<String>());
        Set<String> savedHabitats = sharedPreferences.getStringSet("selectedHabitats", new HashSet<String>());




        selectedColors = new ArrayList<>(savedNumbers);
        selectedSizes = new ArrayList<>(savedSizes);
        selectedTypes = new ArrayList<>(savedTypes);
        selectedTails = new ArrayList<>(savedTails);
        selectedActivities = new ArrayList<>(savedActivities);
        selectedWings = new ArrayList<>(savedWings);
        selectedHabitats = new ArrayList<>(savedHabitats);




    }








}