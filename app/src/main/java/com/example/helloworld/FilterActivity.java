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
    private static final String PREF_NAME = "SelectedNumbers";

    private SharedPreferences sharedPreferences;
    private List<String> selectedColors = new ArrayList<>();
    private List<String> selectedSizes = new ArrayList<>();
    private List<String> selectedTails = new ArrayList<>();
    private List<String> selectedTypes = new ArrayList<>();
    private List<String> selectedActivities = new ArrayList<>();
    private List<String> selectedWings = new ArrayList<>();
    private List<String> selectedHabitats = new ArrayList<>();






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

        // Pass the modified selectedSizes list to MainActivity
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

        // Corrected initialization of sharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Load the state of selected numbers from SharedPreferences
        loadSelectedNumbersState();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Intent intentExit = new Intent(this, ExitService.class);
        startService(intentExit);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            customFont = getResources().getFont(R.font.pokemon_pixel_font);
        }



        // Get references to views
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontalScrollView);
        final LinearLayout sliderContainer = findViewById(R.id.sliderContainer);
        mainContent = findViewById(R.id.mainContent);

        // Add slider options dynamically
        final String[] categories = {"Color", "Size", "Type", "Habitat", "Tail Shape", "Wing Shape", "Activity"};
        for (final String category : categories) {
            Button button = new Button(this);
            button.setText(category);
            button.setTextSize(18);
            button.setTextColor(getResources().getColor(R.color.white));
            button.setTypeface(customFont); // Apply custom font
            button.setBackgroundResource(R.drawable.slider_button_selector);
            if (category.equals("Color")) {
                button.setSelected(true);
                button.setTextColor(getResources().getColor(R.color.black));
                showOptionsForCategory(category); // Show options for the selected category
            }


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < sliderContainer.getChildCount(); i++) {
                        Button childButton = (Button) sliderContainer.getChildAt(i);
                        childButton.setSelected(false);
                        childButton.setTextColor(getResources().getColor(R.color.white)); // Reset text color to white for all buttons
                    }
                    Button clickedButton = (Button) v;
                    clickedButton.setSelected(true);
                    clickedButton.setTextColor(getResources().getColor(R.color.black)); // Change text color to blue for the selected button
                    showOptionsForCategory(category);
                }
            });
            sliderContainer.addView(button);
        }

    }


    private void showOptionsForCategory(String category) {
        mainContent.removeAllViews();
        if (category.equals("Color")) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            LinearLayout colorOptionsLayout = new LinearLayout(this);
            colorOptionsLayout.setLayoutParams(layoutParams);
            colorOptionsLayout.setOrientation(LinearLayout.VERTICAL);

            GridLayout colorOptionsGridLayout = new GridLayout(this);
            colorOptionsGridLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            colorOptionsGridLayout.setColumnCount(2);
            colorOptionsGridLayout.setOrientation(GridLayout.HORIZONTAL);

            final String[] colors = {"Black", "Gray", "White", "Tan", "Brown", "Red-Brown", "Red", "Pink", "Orange", "Yellow", "Green", "Olive", "Blue", "Purple"};
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
                    buttonLayoutParams.setMargins(50, 38, 50, 50); // Add margins to create spacing between buttons

                }else{
                    colorButton.setTextSize(50);
                    buttonLayoutParams.setMargins(50, 50, 50, 50); // Add margins to create spacing between buttons

                }
                colorButton.setPadding(16, 8, 16, 8);
                colorButton.setGravity(Gravity.CENTER); // Center text horizontally and vertically within the button

                colorButton.setLayoutParams(buttonLayoutParams);
                colorButton.setBackgroundColor(getColorForColorName(color)); // Set background color programmatically
                if (selectedColors.contains(colorButton.getText().toString())) {
                    colorButton.setBackground(getButtonBackgroundWithOutline(getColorForColorName(color)));
                } else {
                    colorButton.setBackgroundColor(getColorForColorName(color));
                }
                colorButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isSelected = !colorButton.isSelected(); // Toggle selection state
                        if (selectedColors.contains(colorButton.getText().toString())) {
                            // If number is already in the list, remove it
                            selectedColors.remove(colorButton.getText().toString());
                        } else {
                            // Otherwise, add it to the list
                            selectedColors.add(colorButton.getText().toString());
                        }
                        // Save the state of selected numbers
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

            // Center the GridLayout horizontally by adjusting the left margin
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
        if (category.equals("Size")) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            LinearLayout sizeOptionsLayout = new LinearLayout(this);
            sizeOptionsLayout.setLayoutParams(layoutParams);
            sizeOptionsLayout.setOrientation(LinearLayout.VERTICAL);

            // Add image buttons for size options
            int[] buttonDrawables = {R.drawable.sparrow_for_icon, R.drawable.american_robin_icon, R.drawable.american_crow_icon, R.drawable.mallard_for_size, R.drawable.great_blue_heron_icon};
            String[] buttonTexts = {"Sparrow", "Robin", "Crow", "Mallard", "Heron"};
            for (int i = 0; i < buttonDrawables.length; i++) {
                // Create a RelativeLayout to contain both the image button and the text view
                RelativeLayout buttonLayout = new RelativeLayout(this);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonLayoutParams.setMargins(20, 0, 0, 0); // Shift buttons slightly to the left
                buttonLayout.setLayoutParams(buttonLayoutParams);

                // Add image button to the layout
                final ImageButton sizeButton = new ImageButton(this);
                sizeButton.setId(View.generateViewId()); // Set an ID for the button
                sizeButton.setImageResource(buttonDrawables[i]);
                sizeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                sizeButton.setBackgroundColor(Color.TRANSPARENT); // Set background color to transparent

                // Apply blue color filter to the image if it's selected
                if (selectedSizes.contains(buttonTexts[i])) {
                    sizeButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
                } else {
                    sizeButton.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                }

                // Set the size of the button
                int buttonSize = 200 + i * 80; // Increase size progressively
                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(buttonSize, buttonSize);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                sizeButton.setLayoutParams(buttonParams);

                buttonLayout.addView(sizeButton);

                // Add text view to the layout
                TextView textView = new TextView(this);
                textView.setText(buttonTexts[i]);
                textView.setTextSize(50); // Set text size
                textView.setTypeface(customFont); // Apply custom font
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.RIGHT_OF, sizeButton.getId()); // Align text to the right of the button
                textParams.addRule(RelativeLayout.CENTER_VERTICAL); // Center text vertically
                textParams.setMargins(16, 0, 0, 0); // Set margin to the left of the text
                textView.setLayoutParams(textParams);

                buttonLayout.addView(textView);

                // Set click listener to toggle selection and update UI accordingly
                final int buttonIndex = i;
                buttonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Selected sizes: " + selectedSizes);
                        String selectedSize = buttonTexts[buttonIndex];
                        if (selectedSizes.contains(selectedSize)) {
                            // If the button is already selected, unselect it
                            selectedSizes.remove(selectedSize);
                            sizeButton.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP); // Update color filter
                        } else {
                            // If the button is not selected, select it
                            selectedSizes.add(selectedSize);
                            sizeButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP); // Update color filter
                        }
                        // Update the UI to reflect the new selection state
                        showOptionsForCategory("Size");
                        // Save selected sizes to SharedPreferences
                        saveSelectedNumbersState();
                        // Print the selected sizes list for debugging
                        System.out.println("Selected sizes: " + selectedSizes);
                    }
                });

                // Add button layout to the main layout
                sizeOptionsLayout.addView(buttonLayout);
            }

            mainContent.addView(sizeOptionsLayout);
        }
        if (category.equals("Type")) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            LinearLayout sizeOptionsLayout = new LinearLayout(this);
            sizeOptionsLayout.setLayoutParams(layoutParams);
            sizeOptionsLayout.setOrientation(LinearLayout.VERTICAL);

            // Add image buttons for size options
            int[] buttonDrawables = {R.drawable.gull_forfilter, R.drawable.red_tailed_hawk_icon, R.drawable.uprightwater_forfilter, R.drawable.barred_owl_icon, R.drawable.perch_forfilter, R.drawable.great_blue_heron_icon, R.drawable.cling_forfilter, R.drawable.uplandground_forfilter, R.drawable.mallard_for_size, R.drawable.hummingbird_forfilter, R.drawable.sandpiper_forfilter, R.drawable.pigeon_forfilter, R.drawable.chickenmarsh_forfilter, R.drawable.swallow_forfilter};
            String[] buttonTexts = {"Gull-like Birds", "Hawk-like Birds", "Upright-perching Water Birds", "Owls", "Perching Birds", "Long-legged Waders", "Tree-clinging Birds", "Upland Ground Birds", "Duck-like Birds", "Hummingbirds", "Sandpiper-like Birds", "Pigeon-like Birds", "Chicken-like Marsh Birds", "Swallow-like Birds"};
            for (int i = 0; i < buttonDrawables.length; i++) {
                // Create a RelativeLayout to contain both the image button and the text view
                RelativeLayout buttonLayout = new RelativeLayout(this);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonLayoutParams.setMargins(20, 0, 0, 0); // Shift buttons slightly to the left
                buttonLayout.setLayoutParams(buttonLayoutParams);

                // Add image button to the layout
                final ImageButton typeButton = new ImageButton(this);
                typeButton.setId(View.generateViewId()); // Set an ID for the button
                typeButton.setImageResource(buttonDrawables[i]);
                typeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                typeButton.setBackgroundColor(Color.TRANSPARENT); // Set background color to transparent

                // Apply blue color filter to the image if it's selected
                if (selectedTypes.contains(buttonTexts[i])) {
                    typeButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
                } else {
                    typeButton.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                }

                // Set the size of the button
                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(120, 120);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                typeButton.setLayoutParams(buttonParams);

                buttonLayout.addView(typeButton);

                // Add text view to the layout
                TextView textView = new TextView(this);
                textView.setText(buttonTexts[i]);
                textView.setTextSize(20); // Set text size
                textView.setTypeface(customFont); // Apply custom font
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.RIGHT_OF, typeButton.getId()); // Align text to the right of the button
                textParams.addRule(RelativeLayout.CENTER_VERTICAL); // Center text vertically
                textParams.setMargins(16, 0, 0, 0); // Set margin to the left of the text
                textView.setLayoutParams(textParams);

                buttonLayout.addView(textView);

                // Set click listener to toggle selection and update UI accordingly
                final int buttonIndex = i;
                buttonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Selected Types: " + selectedTypes);
                        String selectedType = buttonTexts[buttonIndex];
                        if (selectedTypes.contains(selectedType)) {
                            // If the button is already selected, unselect it
                            selectedTypes.remove(selectedType);
                            typeButton.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP); // Update color filter
                        } else {
                            // If the button is not selected, select it
                            selectedTypes.add(selectedType);
                            typeButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP); // Update color filter
                        }
                        // Update the UI to reflect the new selection state
                        showOptionsForCategory("Type");
                        // Save selected sizes to SharedPreferences
                        saveSelectedNumbersState();
                        // Print the selected sizes list for debugging
                        System.out.println("Selected types: " + selectedTypes);
                    }
                });

                // Add button layout to the main layout
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

            // Add image buttons for size options
            int[] buttonDrawables = {R.drawable.long_tail, R.drawable.rounded_tail, R.drawable.square_tail, R.drawable.notched_tail, R.drawable.multi_pointed_tail, R.drawable.wedge_tail, R.drawable.forked_tail, R.drawable.short_tail, R.drawable.pointed_tail};
            String[] buttonTexts = {"Long", "Rounded", "Square-tipped", "Notched", "Multi-pointed", "Wedge-shaped", "Forked", "Short", "Pointed"};
            for (int i = 0; i < buttonDrawables.length; i++) {
                // Create a RelativeLayout to contain both the image button and the text view
                RelativeLayout buttonLayout = new RelativeLayout(this);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonLayoutParams.setMargins(20, 0, 0, 0); // Shift buttons slightly to the left
                buttonLayout.setLayoutParams(buttonLayoutParams);

                // Add image button to the layout
                final ImageButton tailButton = new ImageButton(this);
                tailButton.setId(View.generateViewId()); // Set an ID for the button
                tailButton.setImageResource(buttonDrawables[i]);
                tailButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                tailButton.setBackgroundColor(Color.TRANSPARENT); // Set background color to transparent

                // Apply blue color filter to the image if it's selected
                if (selectedTails.contains(buttonTexts[i])) {
                    tailButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
                } else {
                    tailButton.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                }

                // Set the size of the button
                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(200, 200);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                tailButton.setLayoutParams(buttonParams);

                buttonLayout.addView(tailButton);

                // Add text view to the layout
                TextView textView = new TextView(this);
                textView.setText(buttonTexts[i]);
                textView.setTextSize(30); // Set text size
                textView.setTypeface(customFont); // Apply custom font
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.RIGHT_OF, tailButton.getId()); // Align text to the right of the button
                textParams.addRule(RelativeLayout.CENTER_VERTICAL); // Center text vertically
                textParams.setMargins(16, 0, 0, 0); // Set margin to the left of the text
                textView.setLayoutParams(textParams);

                buttonLayout.addView(textView);

                // Set click listener to toggle selection and update UI accordingly
                final int buttonIndex = i;
                buttonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Selected Tails: " + selectedTails);
                        String selectedTail = buttonTexts[buttonIndex];
                        if (selectedTails.contains(selectedTail)) {
                            // If the button is already selected, unselect it
                            selectedTails.remove(selectedTail);
                            tailButton.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP); // Update color filter
                        } else {
                            // If the button is not selected, select it
                            selectedTails.add(selectedTail);
                            tailButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP); // Update color filter
                        }
                        // Update the UI to reflect the new selection state
                        showOptionsForCategory("Tail Shape");
                        // Save selected sizes to SharedPreferences
                        saveSelectedNumbersState();
                        // Print the selected sizes list for debugging
                        System.out.println("Selected tails: " + selectedTails);
                    }
                });

                // Add button layout to the main layout
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

            // Add image buttons for size options
            int[] buttonDrawables = {R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square};
            String[] buttonTexts = {"Direct Flight", "Soaring", "Flap/Glide", "Hovering", "Undulating", "Flushes", "Flitter", "Formation", "Erratic", "Swooping", "Running", "Tree-climbing", "Walking", "Hopping", "Swimming"};
            for (int i = 0; i < buttonDrawables.length; i++) {
                // Create a RelativeLayout to contain both the image button and the text view
                RelativeLayout buttonLayout = new RelativeLayout(this);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonLayoutParams.setMargins(20, 0, 0, 0); // Shift buttons slightly to the left
                buttonLayout.setLayoutParams(buttonLayoutParams);

                // Add image button to the layout
                final ImageButton activityButton = new ImageButton(this);
                activityButton.setId(View.generateViewId()); // Set an ID for the button
                activityButton.setImageResource(buttonDrawables[i]);
                activityButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                activityButton.setBackgroundColor(Color.TRANSPARENT); // Set background color to transparent

                // Apply blue color filter to the image if it's selected


                // Set the size of the button
                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(1, 120);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                activityButton.setLayoutParams(buttonParams);

                buttonLayout.addView(activityButton);

                // Add text view to the layout
                TextView textView = new TextView(this);
                textView.setText(buttonTexts[i]);
                textView.setTextSize(50); // Set text size
                textView.setTypeface(customFont); // Apply custom font
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.RIGHT_OF, activityButton.getId()); // Align text to the right of the button
                textParams.addRule(RelativeLayout.CENTER_VERTICAL); // Center text vertically
                textParams.setMargins(16, 0, 0, 0); // Set margin to the left of the text
                textView.setLayoutParams(textParams);

                buttonLayout.addView(textView);

                if (selectedActivities.contains(buttonTexts[i])) {
                    textView.setTextColor(Color.BLUE);
                } else {
                    textView.setTextColor(Color.GRAY);

                }

                // Set click listener to toggle selection and update UI accordingly
                final int buttonIndex = i;
                buttonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Selected Activities: " + selectedActivities);
                        String selectedActivity = buttonTexts[buttonIndex];
                        if (selectedActivities.contains(selectedActivity)) {
                            // If the button is already selected, unselect it
                            selectedActivities.remove(selectedActivity);
                            textView.setTextColor(Color.GRAY); // Update color filter
                        } else {
                            // If the button is not selected, select it
                            selectedActivities.add(selectedActivity);
                            textView.setTextColor(Color.BLUE); // Update color filter
                        }
                        // Update the UI to reflect the new selection state
                        showOptionsForCategory("Activity");
                        // Save selected sizes to SharedPreferences
                        saveSelectedNumbersState();
                        // Print the selected sizes list for debugging
                        System.out.println("Selected activities: " + selectedActivities);
                    }
                });

                // Add button layout to the main layout
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

            // Add image buttons for size options
            int[] buttonDrawables = {R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square};
            String[] buttonTexts = {"Long", "Narrow", "Pointed", "Broad", "Rounded", "Fingered", "Short"};
            for (int i = 0; i < buttonDrawables.length; i++) {
                // Create a RelativeLayout to contain both the image button and the text view
                RelativeLayout buttonLayout = new RelativeLayout(this);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonLayoutParams.setMargins(20, 0, 0, 0); // Shift buttons slightly to the left
                buttonLayout.setLayoutParams(buttonLayoutParams);

                // Add image button to the layout
                final ImageButton wingButton = new ImageButton(this);
                wingButton.setId(View.generateViewId()); // Set an ID for the button
                wingButton.setImageResource(buttonDrawables[i]);
                wingButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                wingButton.setBackgroundColor(Color.TRANSPARENT); // Set background color to transparent

                // Apply blue color filter to the image if it's selected


                // Set the size of the button
                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(1, 220);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                wingButton.setLayoutParams(buttonParams);

                buttonLayout.addView(wingButton);

                // Add text view to the layout
                TextView textView = new TextView(this);
                textView.setText(buttonTexts[i]);
                textView.setTextSize(60); // Set text size
                textView.setTypeface(customFont); // Apply custom font
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.RIGHT_OF, wingButton.getId()); // Align text to the right of the button
                textParams.addRule(RelativeLayout.CENTER_VERTICAL); // Center text vertically
                textParams.setMargins(16, 0, 0, 0); // Set margin to the left of the text
                textView.setLayoutParams(textParams);

                buttonLayout.addView(textView);

                if (selectedWings.contains(buttonTexts[i])) {
                    textView.setTextColor(Color.BLUE);
                } else {
                    textView.setTextColor(Color.GRAY);

                }

                // Set click listener to toggle selection and update UI accordingly
                final int buttonIndex = i;
                buttonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Selected Wings: " + selectedWings);
                        String selectedWing = buttonTexts[buttonIndex];
                        if (selectedWings.contains(selectedWing)) {
                            // If the button is already selected, unselect it
                            selectedWings.remove(selectedWing);
                            textView.setTextColor(Color.GRAY); // Update color filter
                        } else {
                            // If the button is not selected, select it
                            selectedWings.add(selectedWing);
                            textView.setTextColor(Color.BLUE); // Update color filter
                        }
                        // Update the UI to reflect the new selection state
                        showOptionsForCategory("Wing Shape");
                        // Save selected sizes to SharedPreferences
                        saveSelectedNumbersState();
                        // Print the selected sizes list for debugging
                        System.out.println("Selected wings: " + selectedWings);
                    }
                });

                // Add button layout to the main layout
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

            // Add image buttons for size options
            int[] buttonDrawables = {R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square, R.drawable.blank_square};
            String[] buttonTexts = {"Forests and Woodlands", "Shrublands, Savannas, and Thickets", "Desert and Arid Habitats", "Arroyos and Canyons", "High Mountains", "Landfills and Dumps", "Fields, Meadows, and Grasslands", "Coasts and Shorelines", "Freshwater Wetlands", "Lakes, Ponds, and Rivers", "Saltwater Wetlands", "Urban and Suburban Habitats", "Tundra and Boreal Habitats", "Open Ocean"};
            for (int i = 0; i < buttonDrawables.length; i++) {
                // Create a RelativeLayout to contain both the image button and the text view
                RelativeLayout buttonLayout = new RelativeLayout(this);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonLayoutParams.setMargins(20, 0, 0, 0); // Shift buttons slightly to the left
                buttonLayout.setLayoutParams(buttonLayoutParams);

                // Add image button to the layout
                final ImageButton habitatButton = new ImageButton(this);
                habitatButton.setId(View.generateViewId()); // Set an ID for the button
                habitatButton.setImageResource(buttonDrawables[i]);
                habitatButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                habitatButton.setBackgroundColor(Color.TRANSPARENT); // Set background color to transparent

                // Apply blue color filter to the image if it's selected


                // Set the size of the button
                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(1, 140);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                habitatButton.setLayoutParams(buttonParams);

                buttonLayout.addView(habitatButton);

                // Add text view to the layout
                TextView textView = new TextView(this);
                textView.setText(buttonTexts[i]);
                textView.setTextSize(30); // Set text size
                textView.setTypeface(customFont); // Apply custom font
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.addRule(RelativeLayout.RIGHT_OF, habitatButton.getId()); // Align text to the right of the button
                textParams.addRule(RelativeLayout.CENTER_VERTICAL); // Center text vertically
                textParams.setMargins(16, 0, 0, 0); // Set margin to the left of the text
                textView.setLayoutParams(textParams);

                buttonLayout.addView(textView);

                if (selectedHabitats.contains(buttonTexts[i])) {
                    textView.setTextColor(Color.BLUE);
                } else {
                    textView.setTextColor(Color.GRAY);

                }

                // Set click listener to toggle selection and update UI accordingly
                final int buttonIndex = i;
                buttonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Selected Habitats: " + selectedHabitats);
                        String selectedHabitat = buttonTexts[buttonIndex];
                        if (selectedHabitats.contains(selectedHabitat)) {
                            // If the button is already selected, unselect it
                            selectedHabitats.remove(selectedHabitat);
                            textView.setTextColor(Color.GRAY); // Update color filter
                        } else {
                            // If the button is not selected, select it
                            selectedHabitats.add(selectedHabitat);
                            textView.setTextColor(Color.BLUE); // Update color filter
                        }
                        // Update the UI to reflect the new selection state
                        showOptionsForCategory("Habitat");
                        // Save selected sizes to SharedPreferences
                        saveSelectedNumbersState();
                        // Print the selected sizes list for debugging
                        System.out.println("Selected habitats: " + selectedHabitats);
                    }
                });

                // Add button layout to the main layout
                sizeOptionsLayout.addView(buttonLayout);
            }

            mainContent.addView(sizeOptionsLayout);
        }
    }

    private int measureGridLayoutWidth(GridLayout gridLayout) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        gridLayout.measure(widthMeasureSpec, heightMeasureSpec);
        return gridLayout.getMeasuredWidth();
    }


    private GradientDrawable getButtonBackgroundWithOutline(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setStroke(8, Color.BLUE); // Set blue stroke
        return drawable;
    }






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

    private void loadSelectedNumbersState() {
        // Load the state of selected numbers from SharedPreferences
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