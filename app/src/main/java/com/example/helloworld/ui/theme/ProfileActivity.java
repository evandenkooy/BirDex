package com.example.helloworld.ui.theme;

import static androidx.compose.ui.semantics.SemanticsPropertiesKt.dismiss;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.helloworld.CSVReader;

import android.Manifest;

import com.example.helloworld.CSVWriter;
import com.example.helloworld.MapActivity;
import com.example.helloworld.R;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class ProfileActivity extends AppCompatActivity {
    //Variables to hold paragraph info
    private Map<String, String[]> categoryMap;
    private Map<String, String[]> mainInfoTypesMap;


    private TextView profileTypeTxt;

    private Typeface customFont; //pixel art font

    private MediaPlayer mediaPlayer; //media player for bird sounds

    private MediaPlayer mediaPlayerLogger; //media player for uploaded sound

    private boolean isPlaying = false; //is any audio playing

    private ImageButton currentPlayButton = null; //the currently selected bird sound button

    //Unique codes for app to relate to device
    private static final int REQUEST_CODE_IMAGE_PICKER = 100;
    private static final int REQUEST_AUDIO_REQUEST_CODE = 101;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 102;

    private int currentLogNumber; //number of logs + 1
    private String birdNameLog;
    private String dateLog;
    private String timeLog;
    private String locationLog;
    private String audioIdLog; //uri in form of string for file location
    private String photoIdLog; //uri in form of string for file location
    private String fieldNoteLog;
    private boolean seenLog;
    private boolean heardLog;
    private boolean capturedLog;

    private boolean isImageSelected = false; //is there an image uploaded

    //UI elements for the logger
    private ImageButton photoEntryButton;
    private TextView photoEntryText;

    private Drawable photoEntryButtonUnpaintedDrawable;
    private Drawable photoEntryButtonPaintedDrawable;

    private ImageButton audioEntryButton;
    private TextView audioEntryText;
    private Drawable audioEntryButtonNormalDrawable;
    private Drawable audioEntryButtonSelectedDrawable;
    private boolean isAudioSelected;

    private ImageButton audioDeleteButton;
    private ImageButton imageDeleteButton;

    private ImageButton playYourAudioButton;

    private ImageButton seeYourImageButton;

    private ImageButton setLocationButton;

    private Drawable setLocationButtonNormalDrawable;
    private Drawable setLocationButtonSelectedDrawable;

    private ImageButton deleteLocationButton;

    private FrameLayout askLocationDeleteFrame;

    boolean isLocationSelected;

    //UI for time setter
    private ImageButton buttonAM, buttonPM, buttonSubmit;
    private LinearLayout monthScrollView, dateScrollView, hourScrollView, minuteScrollView, yearScrollView;

    private String selectedAMPM = "AM";
    private String selectedMonth, selectedDate, selectedHour, selectedMinute, selectedYear;

//Variables for time setter
    private static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static final String[] DAYS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private static final String[] HOURS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    private static String[] MINUTES;

    //Just setting up minute variables
    static {
        MINUTES = new String[60];
        for (int i = 0; i < 60; i++) {
            MINUTES[i] = String.format("%02d", i); // Format to always have two digits
        }
    }

    //Years I'm letting users log
    private static final String[] YEARS = {"2020","2021","2022", "2023", "2024", "2025", "2026","2027","2028","2029","2030"}; // Adjust the range of years as necessary


    private static final int MAP_ACTIVITY_REQUEST_CODE = 1; //another code for app to relate to device

    //additional ui variables for time setter
    private Drawable unselectedTimeButtonDrawable;
    private Drawable selectedTimeButtonDrawable;
    private Drawable selectedAmPmButtonDrawable, unselectedAmPmButtonDrawable;
    private Drawable selectedYearButtonDrawable, unselectedYearButtonDrawable;

    private Drawable selectedDateTimeButtonDrawable;
    private Drawable unselectedDateTimeButtonDrawable;
    private ImageButton dateTimeEntryButton;

    private FrameLayout dateTimeEntryFrame;
    private TextView dateTimeLoggerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            //set the UI to a red theme
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bird_profile); //setting the UI layout

        //relating variables to UI elements
        TextView profileBirdNameTxt = findViewById(R.id.profileNameTextView);
        TextView profileBirdSciNameTxt = findViewById(R.id.profileSciNameTextView);
        ImageView profileIcon = findViewById(R.id.profileIconImageView);
        TextView profileNumberTxt = findViewById(R.id.profileNumberTextView);
        TextView profileCoolDescTxt = findViewById(R.id.profileCoolNameTextView);
        profileTypeTxt = findViewById(R.id.profileTypeTextView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            customFont = getResources().getFont(R.font.pokemon_pixel_font); //setting font to pixel one
        }

        //Initiallizing variables in case empty for information
        String birdId = "000";
        String name = "Common Name not Set";
        String scientificName = "Scientific Name not set";
        int icon = R.drawable.pixelated_question_icon;
        String habitatExpanded = "Habitat not set";
        String behavior = "Behavior not set";
        String conservation = "conservation not set";
        String population = "population not set";
        int rangeImage = R.drawable.pixelated_question_icon;
        String description = "description not set";
        String size = "size not set";
        String color = "color not set";
        String wingShape = "wing shape not set";
        String tailShape = "tail shape not set";
        String callPattern = "call pattern not set";
        String callType = "call type not set";
        String eggs = "eggs not set";
        String diet = "diet not set";
        String young = "young not set";
        String feeding = "feeding not set";
        String nesting = "nesting not set";
        String atGlance = "at glance not set";
        String category = "category not set";
        List<Integer> listImages = new ArrayList<Integer>();
        String webname = "webname not set";

        //Getting information from main activity from birdimporter to add to each variable
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("birdsName");
            scientificName = extras.getString("birdsSciName");
            icon = extras.getInt("birdsIcon");
            habitatExpanded = extras.getString("birdsHabitatExtended");
            behavior = extras.getString("birdsBehavior");
            conservation = extras.getString("birdsConservation");
            population = extras.getString("birdsPopulation");
            description = extras.getString("birdsDescription");
            size = extras.getString("birdsSize");
            color = extras.getString("birdsColor");
            wingShape = extras.getString("birdsWingShape");
            tailShape = extras.getString("birdsTailShape");
            callType = extras.getString("birdsCallType");
            callPattern = extras.getString("birdsCallPattern");
            eggs = extras.getString("birdsEggs");
            diet = extras.getString("birdsDiet");
            young = extras.getString("birdsYoung");
            feeding = extras.getString("birdsFeeding");
            nesting = extras.getString("birdsNesting");
            birdId = extras.getString("birdId");
            atGlance = extras.getString("atGlance");
            category = extras.getString("category");
            webname = extras.getString("webName");


        }

        //setting basic ui elemnts to info
        profileBirdNameTxt.setText(name);
        profileBirdSciNameTxt.setText(scientificName);
        profileIcon.setImageResource(icon);
        profileNumberTxt.setText(birdId);

        //changing size of font based on length of name
        if (name.length() > 19) {
            ViewGroup.MarginLayoutParams nameLayoutParams = (ViewGroup.MarginLayoutParams) profileBirdNameTxt.getLayoutParams();
            int marginTopInDp = 24;
            float density = getResources().getDisplayMetrics().density;
            int marginTopInPx = (int) (marginTopInDp * density);
            nameLayoutParams.topMargin = marginTopInPx;
            profileBirdNameTxt.setTextSize(40);
            profileBirdNameTxt.setLayoutParams(nameLayoutParams);
        }

        if (scientificName.length() > 30) {
            ViewGroup.MarginLayoutParams nameLayoutParams = (ViewGroup.MarginLayoutParams) profileBirdSciNameTxt.getLayoutParams();
            int marginTopInDp = 83;
            float density = getResources().getDisplayMetrics().density;
            int marginTopInPx = (int) (marginTopInDp * density);
            nameLayoutParams.topMargin = marginTopInPx;
            profileBirdSciNameTxt.setTextSize(22);
            profileBirdSciNameTxt.setLayoutParams(nameLayoutParams);
        }

        //Creating a map of bird "Types" along with colors to relate them to
        categoryMap = new HashMap<>();
        categoryMap.put("Cardinals", new String[]{"Flamebirds", "#FF0000"});
        categoryMap.put("Perching Birds", new String[]{"Tweeties", "#7FFF00"});
        categoryMap.put("Owls", new String[]{"Nightwings", "#2F4F4F"});
        categoryMap.put("Crows", new String[]{"Darkcaws", "#000000"});
        categoryMap.put("Magpies", new String[]{"Shimmerbeaks", "#1E90FF"});
        categoryMap.put("Jays", new String[]{"Skyflits", "#87CEEB"});
        categoryMap.put("Hawk-like Birds", new String[]{"Swiftalons", "#8B0000"});
        categoryMap.put("Hawks and Eagles", new String[]{"Skytalons", "#CD853F"});
        categoryMap.put("Finches", new String[]{"Sunflits", "#FFD700"});
        categoryMap.put("Mockingbirds and Thrashers", new String[]{"Mimics", "#808080"});
        categoryMap.put("Pigeons and Doves", new String[]{"Coocooz", "#D3D3D3"});
        categoryMap.put("Thrushes", new String[]{"Whistlers", "#A52A2A"});
        categoryMap.put("Herons", new String[]{"Tallstalks", "#C0C0C0"});
        categoryMap.put("Egrets", new String[]{"Mistwaders", "#D9CFFF"});
        categoryMap.put("Bitterns", new String[]{"Reedwhispers", "#808000"});
        categoryMap.put("Long-legged Waders", new String[]{"Stiltsweepers", "#654321"});
        categoryMap.put("Pigeon-like Birds", new String[]{"Fluffyflits", "#D3D3D3"});


        //Setting said category to that indicated from the input data
        String[] partsCategory = category.split(",");
        String resultCategory = partsCategory[0].trim();
        setCategoryInfo(resultCategory);


        //Creating a bitmap for the bird's icon to be analyzed
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), icon);

        //Downscaling the image to increase analyzation speed
        int scaledWidth = originalBitmap.getWidth() / 16; // Adjust as needed
        int scaledHeight = originalBitmap.getHeight() / 16; // Adjust as needed
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, scaledWidth, scaledHeight, true);

        // Calculate the average color of the icon
        int averageColor = calculateAverageColor(scaledBitmap);

        //Define possible colors for the icon to be most similar to
        int[] rainbowColors = {
                Color.RED,
                Color.YELLOW,
                Color.GREEN,
                Color.BLUE,
        };

        //Finding the closest color to teh average of the icon
        int closestColor = findClosestColor(averageColor, rainbowColors);
        int red = Color.red(closestColor);
        int green = Color.green(closestColor);
        int blue = Color.blue(closestColor);
        String hexColor = rgbToHex(red, green, blue);
        //Setting the text and color for the partially false descriptionm
        String[] parts = atGlance.split(":");
        String coolName = parts[0].trim();
        String coolDescription = parts[1].trim();
        String textForCool = "<font color=" + hexColor + "><b>" + coolName + ":</b> </font> <font color=#000000>" + coolDescription + "</font>";
        profileCoolDescTxt.setText(Html.fromHtml(textForCool));

        originalBitmap.recycle();
        scaledBitmap.recycle();

    //Setting the font size based on text length
        if (atGlance.length() < 130) {
            ViewGroup.MarginLayoutParams nameCoolLayoutParams = (ViewGroup.MarginLayoutParams) profileCoolDescTxt.getLayoutParams();
            if (atGlance.length() < 130) {
                int marginTopInDp = 42;
                float density = getResources().getDisplayMetrics().density;
                int marginTopInPx = (int) (marginTopInDp * density);
                nameCoolLayoutParams.topMargin = marginTopInPx;
                profileCoolDescTxt.setTextSize(50);
                profileCoolDescTxt.setLayoutParams(nameCoolLayoutParams);
            } else if (atGlance.length() < 125) {
                int marginTopInDp = 40;
                float density = getResources().getDisplayMetrics().density;
                int marginTopInPx = (int) (marginTopInDp * density);
                nameCoolLayoutParams.topMargin = marginTopInPx;
                profileCoolDescTxt.setTextSize(54);
                profileCoolDescTxt.setLayoutParams(nameCoolLayoutParams);
            }
        }


        //Layout for all information in paragraphs
        final LinearLayout theBig = findViewById(R.id.theBig);

        //converting the bird name traditionally used for webscraping to one that can be used to relate it to files in drawable
        String scoredName = webname.replace("-", "_");
        String prefix = scoredName + "_image";

        //Creating a scrollview to hold bird images
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        ViewGroup container = findViewById(R.id.image_container);

        //Reading in image Ids
        List<Drawable> allDrawables = new ArrayList<>();
        Resources resources = getApplicationContext().getResources();
        String packageName = getApplicationContext().getPackageName();

        //Getting a list of all the Ids and adding them to a list of images specific ot the selected bird if they match the name
        Field[] drawableFields = R.drawable.class.getFields();
        for (Field field : drawableFields) {
            String resourceName = field.getName();
            if (resourceName.startsWith(prefix)) { // Or any other criteria you want to apply
                int resourceId = resources.getIdentifier(resourceName, "drawable", packageName);
                if (resourceId != 0) {
                    Drawable drawable = resources.getDrawable(resourceId, null);
                    if (drawable != null) {
                        allDrawables.add(drawable);
                    }
                }
            }
        }

        //Adding bird photos to the scrollview
        for (Drawable drawable : allDrawables) {
            ImageView imageView = new ImageView(this);
            imageView.setImageDrawable(drawable);
            imageView.setPadding(0, 0, 0, 0); // Add some padding between images
            container.addView(imageView);
        }


        //setting the proper prefix to find bird audio files
        String audioPrefix = scoredName + "_audio";

        //Creating a layout to hold audio buttons and decriptions
        LinearLayout containerAudios = findViewById(R.id.audio_container);

        //Creating a list of audio ids
        List<Integer> allAudioIds = new ArrayList<>();

        //finding ids for the selected bird based on name
        Field[] rawAudioFields = R.raw.class.getFields();
        for (Field field : rawAudioFields) {
            String resourceName = field.getName();
            if (resourceName.startsWith(audioPrefix)) { // Or any other criteria you want to apply
                int resourceId = resources.getIdentifier(resourceName, "raw", packageName);
                if (resourceId != 0) {
                    allAudioIds.add(resourceId);
                    Log.d("ID", allAudioIds.toString());
                }
            }
        }

        for (int audioId : allAudioIds) {
            // Get the title of the audio from its resource name
            String audioResourceName = resources.getResourceEntryName(audioId);
            String audioResourceNameAfterAudio = audioResourceName.split("audio")[1];
            String[] partsAudio = audioResourceNameAfterAudio.split("_");

            String audioTitle = null;
            String formattedTitle = "";


//Getting the name of the specific audio
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < partsAudio.length; i++) {
                sb.append(partsAudio[i]);
                if (i < partsAudio.length - 1) {
                    sb.append("_"); // Add underscore if not the last part
                }
            }
            audioTitle = sb.toString();

// If audioTitle is not null, format it
            if (audioTitle != null) {
                StringBuilder sb2 = new StringBuilder();
                // Replace underscores with spaces and capitalize the first letter of each word
                String[] words = audioTitle.split("_|\\("); // Split by underscore or parenthesis
                for (String word : words) {
                    if (!word.isEmpty()) { // Skip empty strings
                        sb2.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
                    }
                }
                formattedTitle = sb2.toString().trim();
            }

            //Creating a linear layout for each audio play button and audio name
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            FrameLayout nameButtonFrame = new FrameLayout(this);
            LinearLayout.LayoutParams nameButtonFrameParams = new LinearLayout.LayoutParams(
                    700, // Set width
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            nameButtonFrame.setLayoutParams(nameButtonFrameParams); // Apply layout parameters

            //CReating the button for the nam,e
            ImageButton nameButton = new ImageButton(this);
            nameButton.setImageResource(R.drawable.audio_name_back); // Set image for name button
            nameButton.setBackgroundColor(Color.TRANSPARENT); // Set background color to transparent
            nameButton.setAdjustViewBounds(true); // Ensure the button adjusts its bounds to fit the image
            nameButton.setScaleType(ImageButton.ScaleType.FIT_CENTER); // Scale image to fit the button
            FrameLayout.LayoutParams nameButtonParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            nameButton.setLayoutParams(nameButtonParams); // Apply layout parameters

            //Creating textview for name
            TextView nameText = new TextView(this);
            nameText.setText(formattedTitle);
            nameText.setTextColor(Color.BLACK);
            nameText.setTypeface(customFont);
            nameText.setTextSize(16); // Set text size
            nameText.setGravity(Gravity.CENTER); // Center text
            FrameLayout.LayoutParams textParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            textParams.topMargin = 5;
            nameText.setLayoutParams(textParams); // Apply layout parameters

            //Adding namebuttons to the linear layout
            nameButtonFrame.addView(nameButton);
            nameButtonFrame.addView(nameText);

            String finalFormattedTitle = formattedTitle;

            //setting the textsize of the namebuttons
            nameButtonFrame.post(new Runnable() {
                @Override
                public void run() {
                    int availableWidth = nameButtonFrame.getWidth();
                    int availableHeight = nameButtonFrame.getHeight();

                    TextPaint textPaint = nameText.getPaint();
                    float textWidth = textPaint.measureText(finalFormattedTitle);
                    float textHeight = textPaint.getTextSize();

                    float maxWidth = availableWidth * 0.7f; // Adjust as needed
                    float maxHeight = availableHeight * 0.7f; // Adjust as needed

                    float newTextSize = Math.min(maxWidth / textWidth, maxHeight / textHeight) * 16; // Initial text size

                    nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, newTextSize);
                }
            });

            //creating the button for the frame
            FrameLayout playButtonFrame = new FrameLayout(this);
            LinearLayout.LayoutParams playButtonFrameParams = new LinearLayout.LayoutParams(
                    260,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            playButtonFrame.setLayoutParams(playButtonFrameParams); // Apply layout parameters

//Create ImageButton for the play button
            ImageButton playButton = new ImageButton(this);
            playButton.setImageResource(R.drawable.audio_button_back_red); // Set image for play button
            playButton.setBackgroundColor(Color.TRANSPARENT); // Set background color to transparent
            playButton.setAdjustViewBounds(true); // Ensure the button adjusts its bounds to fit the image
            playButton.setScaleType(ImageButton.ScaleType.FIT_CENTER); // Scale image to fit the button
            FrameLayout.LayoutParams playButtonParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            playButton.setLayoutParams(playButtonParams); // Apply layout parameters

// Create TextView for the play button
            TextView playButtonText = new TextView(this);
            playButtonText.setText("PLAY");
            playButtonText.setTextColor(Color.BLACK);
            playButtonText.setTypeface(customFont);
            playButtonText.setTextSize(20); // Set text size
            playButtonText.setGravity(Gravity.CENTER); // Center text
            FrameLayout.LayoutParams textParamsPlay = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            playButtonText.setLayoutParams(textParamsPlay); // Apply layout parameters

// Adding playbutton elements to its frame
            playButtonFrame.addView(playButton);
            playButtonFrame.addView(playButtonText);

            // adding both buttons to the row for that sound
            rowLayout.addView(nameButtonFrame);
            rowLayout.addView(playButtonFrame);

            //Set OnClickListener for the play button
            playButton.setOnClickListener(v -> {
                if (!isPlaying) {
                    //Start playing audio
                    mediaPlayer = MediaPlayer.create(this, audioId);
                    mediaPlayer.start();
                    isPlaying = true;
                    currentPlayButton = playButton;
                    //Change image of the play button to "playing" image
                    playButton.setImageResource(R.drawable.audio_button_back_white);

                } else {
                    if (currentPlayButton == playButton) {
                        mediaPlayer.pause();
                        isPlaying = false;
                        currentPlayButton.setImageResource(R.drawable.audio_button_back_red);
                        //Change image of the play button to "not playing" image
                        playButton.setImageResource(R.drawable.audio_button_back_red);
                    } else {
                        mediaPlayer.pause();
                        isPlaying = false;
                        currentPlayButton.setImageResource(R.drawable.audio_button_back_red);
                        //Change image of the play button to "not playing" image
                        playButton.setImageResource(R.drawable.audio_button_back_red);

                        mediaPlayer = MediaPlayer.create(this, audioId);
                        mediaPlayer.start();
                        isPlaying = true;
                        currentPlayButton = playButton;
                        //Change image of the play button to "playing" image
                        playButton.setImageResource(R.drawable.audio_button_back_white);
                    }
                }
            });

            // Add the row to the layout of all audios
            containerAudios.addView(rowLayout);
        }


        String rangeImageName = scoredName + "_range"; //finding rangemap file name
        int resourceRangeId = resources.getIdentifier(rangeImageName, "drawable", packageName); //go through drawable and find it
        ImageView rangeMapImage = findViewById(R.id.range_image_imageview); //find the ranegmap ui element
        rangeMapImage.setImageResource(resourceRangeId); //set the rangemap ui to the map

        //creating info button to show what range map means
        ImageButton rangeInfoButton = findViewById(R.id.range_info_button);
        ImageView rangeInfoImage = findViewById(R.id.range_info_imageview);
        rangeInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rangeInfoImage.getVisibility() == View.VISIBLE) {
                    rangeInfoImage.setVisibility(View.INVISIBLE); // Make the info invisible
                } else {
                    rangeInfoImage.setVisibility(View.VISIBLE); // Make the info visible
                }
            }
        });

        //creating a list of categories for paragraph info
        final String[] mainInfoTypes = {"description", "habitatExpanded", "size", "color", "wingShape", "tailShape", "callType", "callPattern", "population", "conservation", "behavior", "eggs", "diet", "feeding", "nesting"};

        //making hashmap to relate categories to information
        mainInfoTypesMap = new HashMap<>();
        mainInfoTypesMap.put("description", new String[]{"<b>Description: </b>", description, "0.9", "0"});
        mainInfoTypesMap.put("habitatExpanded", new String[]{"<b>Habitat: </b>", habitatExpanded, "0.4", "0"});
        mainInfoTypesMap.put("size", new String[]{"<b>Size: </b>", size, "0.2", "120"});
        mainInfoTypesMap.put("color", new String[]{"<b>Color: </b>", color, "0.2", "120"});
        mainInfoTypesMap.put("wingShape", new String[]{"<b>Wing Shape: </b>", wingShape, "0.2", "120"});
        mainInfoTypesMap.put("tailShape", new String[]{"<b>Tail Shape: </b>", tailShape, "0.2", "120"});
        mainInfoTypesMap.put("callType", new String[]{"<b>Call Type: </b>", callType, "0.2", "120"});
        mainInfoTypesMap.put("callPattern", new String[]{"<b>Call Pattern: </b>", callPattern, "0.2", "120"});
        mainInfoTypesMap.put("population", new String[]{"<b>Population: </b>", population, "0.2", "120"});
        mainInfoTypesMap.put("conservation", new String[]{"<b>Conservation: </b>", conservation, "0.2", "120"});
        mainInfoTypesMap.put("behavior", new String[]{"<b>Behavior: </b>", behavior, "0.2", "120"});
        mainInfoTypesMap.put("eggs", new String[]{"<b>Eggs: </b>", eggs, "0.7", "60"});
        mainInfoTypesMap.put("diet", new String[]{"<b>Diet: </b>", diet, "0.7", "60"});
        mainInfoTypesMap.put("feeding", new String[]{"<b>Feeding: </b>", feeding, "0.7", "60"});
        mainInfoTypesMap.put("nesting", new String[]{"<b>Nesting: </b>", nesting, "0.7", "60"});

        //creating paragraph boxes
        for (String i : mainInfoTypes) {
            String[] info = mainInfoTypesMap.get(i);
            //Initializing Text
            String paraText = "";
            String introText = "";
            //Setting the colors for the outside of the box
            float sat = 1;
            float redRotate = 0;
            float greenRotate = 0;
            float blueRotate = 0;
            //Setting info
            if (info != null && info.length == 4) {
                introText = info[0];
                paraText = info[1];
                sat = Float.parseFloat(info[2]);
                greenRotate = Float.parseFloat(info[3]);
            }
            //Applying color for box settings
            if (paraText != null) {
                ColorMatrix hueMatrix = new ColorMatrix();
                hueMatrix.setRotate(1, greenRotate);
                ColorMatrix saturationMatrix = new ColorMatrix();
                saturationMatrix.setSaturation(sat);
                ColorMatrix combinedMatrix = new ColorMatrix();
                combinedMatrix.postConcat(hueMatrix);
                combinedMatrix.postConcat(saturationMatrix);
                ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(combinedMatrix);

                //PARAGRAPH BOXES
                FrameLayout frame = new FrameLayout(this);
                FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );
                frameLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                int marginTopInDp = 20;
                float density = getResources().getDisplayMetrics().density;
                int marginTopInPx = (int) (marginTopInDp * density);
                frameLayoutParams.topMargin = marginTopInPx;
                frame.setLayoutParams(frameLayoutParams);

                //Frame Top
                ImageView topImage = new ImageView(this);
                topImage.setImageResource(R.drawable.info_frame_end);
                topImage.setColorFilter(colorFilter);
                int topImageBackWidth = getResources().getDisplayMetrics().widthPixels - 30;
                int topImageBackHeight = (int) (topImageBackWidth * 0.177966);
                FrameLayout.LayoutParams topImageLayoutParams = new FrameLayout.LayoutParams(
                        topImageBackWidth,
                        topImageBackHeight
                );
                topImageLayoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                topImage.setLayoutParams(topImageLayoutParams);
                frame.addView(topImage);

                //Frame Bottom
                ImageView bottomImage = new ImageView(this);
                bottomImage.setImageResource(R.drawable.info_frame_end);
                bottomImage.setColorFilter(colorFilter);
                FrameLayout.LayoutParams bottomImageLayoutParams = new FrameLayout.LayoutParams(
                        topImageBackWidth,
                        topImageBackHeight
                );
                bottomImageLayoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                bottomImage.setLayoutParams(bottomImageLayoutParams);
                frame.addView(bottomImage);

                //Back of Text
                ImageView image = new ImageView(this);
                int imageBackWidth = getResources().getDisplayMetrics().widthPixels - 50;
                FrameLayout.LayoutParams imageLayoutParams = new FrameLayout.LayoutParams(
                        imageBackWidth,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );
                imageLayoutParams.gravity = Gravity.CENTER;
                imageLayoutParams.topMargin = 50;
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                image.setImageResource(R.drawable.back_for_all_text);
                frame.addView(image);

                //Setting Text
                TextView text = new TextView(this);
                FrameLayout.LayoutParams textParams = new FrameLayout.LayoutParams(
                        imageBackWidth - 210, // Set the width directly
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );
                textParams.leftMargin = 145; // Set the margin directly
                textParams.topMargin = 145;
                text.setLayoutParams(textParams);
                text.setText(Html.fromHtml(introText + paraText));
                text.setTextSize(45); // Set the text size directly
                text.setTextColor(getResources().getColor(R.color.black));
                text.setTypeface(customFont);
                frame.addView(text);

                //Left of frame
                ImageView segmentImage = new ImageView(this);
                segmentImage.setImageResource(R.drawable.info_frame_segment);
                segmentImage.setColorFilter(colorFilter);
                int segmentImageBackWidth = 115;
                int segmentImageBackHeight = 100;
                FrameLayout.LayoutParams segmentImageLayoutParams = new FrameLayout.LayoutParams(
                        segmentImageBackWidth,
                        segmentImageBackHeight
                );
                segmentImageLayoutParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
                segmentImageLayoutParams.leftMargin = 15;
                segmentImage.setLayoutParams(segmentImageLayoutParams);
                segmentImage.setScaleType(ImageView.ScaleType.FIT_XY);
                frame.addView(segmentImage);

                //Right of Frame
                ImageView segmentImage2 = new ImageView(this);
                segmentImage2.setImageResource(R.drawable.info_frame_segment);
                segmentImage2.setColorFilter(colorFilter);
                FrameLayout.LayoutParams segmentImageLayoutParams2 = new FrameLayout.LayoutParams(
                        segmentImageBackWidth,
                        segmentImageBackHeight
                );
                segmentImageLayoutParams2.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
                segmentImageLayoutParams2.rightMargin = 15;
                segmentImage2.setLayoutParams(segmentImageLayoutParams2);
                segmentImage2.setScaleType(ImageView.ScaleType.FIT_XY);
                frame.addView(segmentImage2);


                topImage.bringToFront();
                bottomImage.bringToFront();


                // Add frame for the paragraph to the total page
                theBig.addView(frame);

                //altering the size of box to fit the text
                ViewTreeObserver viewTreeObserver = text.getViewTreeObserver();
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        text.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int textViewHeight = text.getHeight();
                        image.getLayoutParams().height = textViewHeight + 120;
                        image.getLayoutParams().width = imageBackWidth;
                        segmentImage.getLayoutParams().height = textViewHeight + 120;
                        segmentImage2.getLayoutParams().height = textViewHeight + 120;

                        FrameLayout.LayoutParams paramSegment = (FrameLayout.LayoutParams) segmentImage.getLayoutParams();
                        paramSegment.topMargin = 90;
                        paramSegment.bottomMargin = 90;
                        paramSegment.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
                        segmentImage.setLayoutParams(paramSegment);

                        FrameLayout.LayoutParams paramSegment2 = (FrameLayout.LayoutParams) segmentImage2.getLayoutParams();
                        paramSegment2.topMargin = 90;
                        paramSegment2.bottomMargin = 90;
                        paramSegment2.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
                        segmentImage2.setLayoutParams(paramSegment2);

                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) image.getLayoutParams();
                        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER;
                        image.setLayoutParams(params);

                        FrameLayout.LayoutParams paramsBottom = (FrameLayout.LayoutParams) bottomImage.getLayoutParams();
                        bottomImage.setScaleY(-1);
                        paramsBottom.height = topImageBackHeight;
                        paramsBottom.width = topImageBackWidth;
                        paramsBottom.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                        bottomImage.setLayoutParams(paramsBottom);

                        image.requestLayout();

                    }
                });


            }

            //Adding separating borders between sections
            if (i == "habitatExpanded" || i == "behavior" || i == "nesting") { // these are the last categories of each section
                int imageBorderBackWidth = getResources().getDisplayMetrics().widthPixels;
                int newWidth = imageBorderBackWidth - 100;
                int newHeight = (int) (newWidth * 0.106);
                ImageView imageBorder = new ImageView(this);
                imageBorder.setImageResource(R.drawable.border_line_profile);
                LinearLayout.LayoutParams imageBorderLayoutParams = new LinearLayout.LayoutParams(
                        newWidth,
                        newHeight
                );
                imageBorderLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                imageBorderLayoutParams.topMargin = 24;
                imageBorder.setLayoutParams(imageBorderLayoutParams);
                theBig.addView(imageBorder);

            }

        }

        //initializing the csv where logs are stored locally in device storage
        List<String[]> dataLogs = CSVReader.readCsvFile(getApplicationContext(), "data.csv");
        if (dataLogs != null && !dataLogs.isEmpty()) {
            int lastIndex = dataLogs.size() - 1;
            String[] lastRow = dataLogs.get(lastIndex);
            if (lastRow.length > 0) {
                currentLogNumber = Integer.parseInt(lastRow[0]);
            } else {
                currentLogNumber = 1;
            }
        }
        birdNameLog = name; //logs for this page will be for this bird


        //BRING UP LOGGER
        ImageButton eyeconImageButton = findViewById(R.id.eyeconEntry);
        FrameLayout entryFrame = findViewById(R.id.enterEntryFrame);

        ///LOGGER

        //Exit the logger
        ImageButton exitLoggerButton = findViewById(R.id.exitEntryButton);
        FrameLayout exitMaybeMenu = findViewById(R.id.exitQuestionFrame);
        ImageButton exitLoggerConfirmButton = findViewById(R.id.definetlyDontSubmitButton);
        ImageButton exitLoggerDenyButton = findViewById(R.id.definetlyKeepSubmitButton);

        //onclick listener for exit button
        exitLoggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitMaybeMenu.setVisibility(View.VISIBLE);
            }
        });

        //onclick listener to confirm exit
        exitLoggerConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitMaybeMenu.setVisibility(View.INVISIBLE);
                entryFrame.setVisibility(View.INVISIBLE);
            }
        });

        //oncklick listener to stay in logger
        exitLoggerDenyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitMaybeMenu.setVisibility(View.INVISIBLE);
            }
        });


        //Submit Button
        ImageButton submitLoggerButton = findViewById(R.id.greenSubmitEntry);
        FrameLayout submitAskAboutFrame = findViewById(R.id.askAboutSubmitFrame);
        ImageButton submitConfirmButton = findViewById(R.id.submitLogConfirmButton);
        ImageButton submitDenyButton = findViewById(R.id.submitLogDenyButton);

        //onclick for submit button
        submitLoggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAskAboutFrame.setVisibility(View.VISIBLE);
            }
        });

        //onclick for staying in logger
        submitDenyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAskAboutFrame.setVisibility(View.INVISIBLE);
            }
        });


        //Set Common and Sci Name for Logger
        TextView nameForLogger = findViewById(R.id.commonScientificForLogger);
        nameForLogger.setText(name + " - " + scientificName);

        //Seen/heard/captured buttons
        ImageButton seenLogImageButton = findViewById(R.id.seenLogButton);
        ImageButton heardLogImageButton = findViewById(R.id.heardLogButton);
        ImageButton capturedLogImageButton = findViewById(R.id.capturedLogButton);

        //Selected/Unselected
        Drawable typeLogButtonNormalDrawable = seenLogImageButton.getBackground();
        capturedLogImageButton.setBackground(typeLogButtonNormalDrawable);
        heardLogImageButton.setBackground(typeLogButtonNormalDrawable);
        seenLogImageButton.setBackground(typeLogButtonNormalDrawable);


        Drawable typeLogButtonSelectedDrawable = typeLogButtonNormalDrawable.getConstantState().newDrawable().mutate();
        // Create a ColorMatrixColorFilter to adjust color of buttons when selected
        ColorMatrix colorMatrixLogButtonType = new ColorMatrix();
        colorMatrixLogButtonType.setRotate(0, 50); // Adjust hueValue as needed
        ColorMatrixColorFilter colorFilterForLogButtonType = new ColorMatrixColorFilter(colorMatrixLogButtonType);
        // Apply the color filter to the new button drawable
        typeLogButtonSelectedDrawable.setColorFilter(colorFilterForLogButtonType);

        //onclick listener for seen boolean button
        seenLogImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seenLogImageButton.getBackground() == typeLogButtonNormalDrawable) {
                    seenLogImageButton.setBackground(typeLogButtonSelectedDrawable);
                    seenLog = true;
                } else {
                    seenLogImageButton.setBackground(typeLogButtonNormalDrawable);
                    capturedLogImageButton.setBackground(typeLogButtonNormalDrawable);
                    seenLog = false;
                    capturedLog = false;
                }
            }
        });

        //onclick listener for heard button boolean
        heardLogImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (heardLogImageButton.getBackground() == typeLogButtonNormalDrawable) {
                    heardLogImageButton.setBackground(typeLogButtonSelectedDrawable);
                    heardLog = true;
                } else {
                    heardLogImageButton.setBackground(typeLogButtonNormalDrawable);
                    heardLog = false;
                }
            }
        });

        //onlick for captured button boolean
        capturedLogImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (capturedLogImageButton.getBackground() == typeLogButtonNormalDrawable) {
                    capturedLogImageButton.setBackground(typeLogButtonSelectedDrawable);
                    seenLogImageButton.setBackground(typeLogButtonSelectedDrawable);
                    seenLog = true;
                    capturedLog = true;
                } else {
                    capturedLogImageButton.setBackground(typeLogButtonNormalDrawable);
                    capturedLog = true;
                }
            }
        });

        //Field Notes
        ImageButton fieldNoteEntryButton = findViewById(R.id.enterFieldNoteButton);
        TextView fieldNoteEntryText = findViewById(R.id.textForFieldNoteButton);
        FrameLayout fieldNoteEntryFrame = findViewById(R.id.fieldNoteEntryFrame);
        EditText fieldNoteEntryEditText = findViewById(R.id.editFieldNoteInLogger);
        ImageButton saveandCloseFieldButton = findViewById(R.id.saveAndCloseTextButton);

        ////Selected/Unselected
        Drawable fieldNoteEntryButtonNormalDrawable = fieldNoteEntryButton.getBackground();
        fieldNoteEntryButton.setBackground(fieldNoteEntryButtonNormalDrawable);


        Drawable fieldNoteEntryButtonSelectedDrawable = fieldNoteEntryButtonNormalDrawable.getConstantState().newDrawable().mutate();
        // Create a ColorMatrixColorFilter to adjust color of entered in field notes
        ColorMatrix colorMatrixFieldNoteEntryButtonType = new ColorMatrix();
        colorMatrixFieldNoteEntryButtonType.setRotate(0, 30); // Adjust hueValue as needed
        ColorMatrixColorFilter colorFilterForFieldNoteEntryButtonType = new ColorMatrixColorFilter(colorMatrixFieldNoteEntryButtonType);
        // Apply the color filter to the new drawable for the entered field notes button
        fieldNoteEntryButtonSelectedDrawable.setColorFilter(colorFilterForFieldNoteEntryButtonType);

        //onclick listener for field note button to bring up field note editor
        fieldNoteEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldNoteEntryFrame.setVisibility(View.VISIBLE);
                fieldNoteEntryEditText.setText(fieldNoteLog, TextView.BufferType.EDITABLE);
            }
        });

        //onclick to save and close the notes
        saveandCloseFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldNoteLog = fieldNoteEntryEditText.getText().toString();
                fieldNoteEntryFrame.setVisibility(View.INVISIBLE);
                if (fieldNoteLog.isBlank()) {
                    fieldNoteEntryButton.setBackground(fieldNoteEntryButtonNormalDrawable);
                    fieldNoteEntryText.setText("Add Field Notes");
                } else {
                    fieldNoteEntryButton.setBackground(fieldNoteEntryButtonSelectedDrawable);
                    fieldNoteEntryText.setText("Edit Field Notes");
                }
            }
        });


        //initializing ui to add photos
        photoEntryButton = findViewById(R.id.enterPhotoLogButton);
        photoEntryText = findViewById(R.id.textForAddImageButton);
        photoEntryButtonUnpaintedDrawable = photoEntryButton.getBackground();
        photoEntryButtonPaintedDrawable = getResources().getDrawable(R.drawable.finished_eisel);
        photoEntryButton.setBackground(photoEntryButtonUnpaintedDrawable);

        //delete the image
        imageDeleteButton = findViewById(R.id.deleteImageButton);
        FrameLayout imageDeletionQuestion = findViewById(R.id.askRemoveImageFrame);
        ImageButton confirmImageDeleteButton = findViewById(R.id.removeImageConfirmButton);
        ImageButton denyImageDeleteButton = findViewById(R.id.dontRemoveImageButton);

        //view the image
        seeYourImageButton = findViewById(R.id.seeYourImageInLoggerButton);
        FrameLayout seeYourImageFrame = findViewById(R.id.seeLoggerImageFrame);
        ImageButton backOutOfSeeYourImageFrameButton = findViewById(R.id.backToLoggerFromImageButton);
        ImageView ownImageLogger = findViewById(R.id.ownImageLogger);

        //onclick that brings up image selection activity
        photoEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        //onclick that brings up window asking to delete image
        imageDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDeletionQuestion.setVisibility(View.VISIBLE);
            }
        });

        //confirm delete the image
        confirmImageDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoEntryButton.setBackground(photoEntryButtonUnpaintedDrawable);
                isImageSelected = false;
                photoEntryText.setText("Add Photo");
                photoIdLog = "";
                imageDeletionQuestion.setVisibility(View.INVISIBLE);
                imageDeleteButton.setVisibility(View.INVISIBLE);
                seeYourImageButton.setVisibility(View.INVISIBLE);

            }
        });

        //dont delete the image
        denyImageDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDeletionQuestion.setVisibility(View.INVISIBLE);
            }
        });

        //bring up window that loads the image
        seeYourImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri seeLogImageUri = Uri.parse(photoIdLog);
                Log.d("PROPERs", seeLogImageUri.toString());
                Picasso.get().setLoggingEnabled(true);

                Picasso.get().load(seeLogImageUri).into(new Target() {
                    //image itself
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        ownImageLogger.setImageBitmap(bitmap);
                    }

                    //if the image fails to load
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        ownImageLogger.setImageResource(R.drawable.error_paper);
                    }

                    //while the image loads
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        ownImageLogger.setImageResource(R.drawable.loading_sand);
                    }
                });
                seeYourImageFrame.setVisibility(View.VISIBLE);
            }
        });

        //leave the image viewer
        backOutOfSeeYourImageFrameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeYourImageFrame.setVisibility(View.INVISIBLE);
            }
        });

        //initializing ui buttons for adding audio
        audioEntryButton = findViewById(R.id.enterAudioLogButton);
        audioEntryText = findViewById(R.id.textForAddAudioButton);

        //delete your audio button
        audioDeleteButton = findViewById(R.id.deleteAudioButton);
        ImageButton confirmAudioDeleteButton = findViewById(R.id.removeAudioConfirmButton);
        ImageButton denyAudioDeleteButton = findViewById(R.id.dontRemoveAudioButton);
        FrameLayout askAudioDeleteFrame = findViewById(R.id.askRemoveAudioFrame);

        audioEntryButtonNormalDrawable = audioEntryButton.getBackground();
        audioEntryButton.setBackground(audioEntryButtonNormalDrawable);


        audioEntryButtonSelectedDrawable = audioEntryButtonNormalDrawable.getConstantState().newDrawable().mutate();
        //// Create a ColorMatrixColorFilter for selected button darwable
        ColorMatrix colorMatrixAudioEntryButtonType = new ColorMatrix();
        colorMatrixAudioEntryButtonType.setRotate(1, 100); // Adjust hueValue as needed
        ColorMatrixColorFilter colorFilterForAudioEntryButtonType = new ColorMatrixColorFilter(colorMatrixAudioEntryButtonType);
        //// Apply the color filter to the button for when its selected
        audioEntryButtonSelectedDrawable.setColorFilter(colorFilterForAudioEntryButtonType);

        //onclick to bring up audio file selection
        audioEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAudioFile();

            }
        });

        //onclick to ask user to delete audio
        audioDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askAudioDeleteFrame.setVisibility(View.VISIBLE);
            }
        });

        //delete the audio
        confirmAudioDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioEntryButton.setBackground(audioEntryButtonNormalDrawable);
                isAudioSelected = false;
                audioEntryText.setText("Add Audio");
                audioIdLog = "";
                askAudioDeleteFrame.setVisibility(View.INVISIBLE);
                audioDeleteButton.setVisibility(View.INVISIBLE);
            }
        });

        //dont delete the audio
        denyAudioDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askAudioDeleteFrame.setVisibility(View.INVISIBLE);
            }
        });

        //button to play audio initialize
        playYourAudioButton = findViewById(R.id.playYourAudioInLoggerButton);

        Drawable playYourAudioButtonNormalDrawable = playYourAudioButton.getBackground();
        playYourAudioButton.setBackground(playYourAudioButtonNormalDrawable);


        Drawable playYourAudioButtonSelectedDrawable = playYourAudioButtonNormalDrawable.getConstantState().newDrawable().mutate();
        final float[] NEGATIVE = {
                0.5f, 0, 0, 0, 150, // red
                0, 0.5f, 0, 0, 150, // green
                100, 50, -1.0f, 100, 255, // blue
                0, 0, 0, 1.0f, 0  // alpha
        };
        //// Create a ColorMatrixColorFilter for when audio is playing in logger
        ColorMatrix colorMatrixPlayYourAudioButtonType = new ColorMatrix(NEGATIVE);
        ColorMatrixColorFilter colorFilterForPlayYourAudioButtonType = new ColorMatrixColorFilter(colorMatrixPlayYourAudioButtonType);
        //// Apply the color filter to the drawable for when playing
        playYourAudioButtonSelectedDrawable.setColorFilter(colorFilterForPlayYourAudioButtonType);
        Uri audioToPlayInLogger;

        //onclick that plays user inputted audio
        playYourAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidUri(audioIdLog)) {
                    //audioIdLog is a valid URI
                    Uri myUriAudio = Uri.parse(audioIdLog);
                    mediaPlayerLogger = new MediaPlayer();
                    mediaPlayerLogger.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // Change button back normal thing when audio is done playing
                            playYourAudioButton.setBackground(playYourAudioButtonNormalDrawable);
                        }
                    });
                    try {
                        mediaPlayerLogger.setDataSource(getApplicationContext(), myUriAudio);
                        mediaPlayerLogger.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                //play the audio
                                mediaPlayerLogger.start();
                            }
                        });
                        mediaPlayerLogger.prepareAsync(); // Prepare asynchronously
                        playYourAudioButton.setBackground(playYourAudioButtonSelectedDrawable);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // audioIdLog is not a valid URI
                }
            }
        });

        //creating buttons for location selection
        setLocationButton = findViewById(R.id.locationEntryButton);
        setLocationButtonNormalDrawable = setLocationButton.getBackground();
        setLocationButton.setBackground(setLocationButtonNormalDrawable);

        setLocationButtonSelectedDrawable = setLocationButtonNormalDrawable.getConstantState().newDrawable().mutate();
        //// Create a ColorMatrixColorFilter for the location button when selected
        ColorMatrix colorMatrixLocationEntryButtonType = new ColorMatrix();
        colorMatrixLocationEntryButtonType.setRotate(1, 100);
        ColorMatrixColorFilter colorFilterForLocationEntryButtonType = new ColorMatrixColorFilter(colorMatrixLocationEntryButtonType);
        //// Apply the color filter to the drawbel for a selected location
        setLocationButtonSelectedDrawable.setColorFilter(colorFilterForLocationEntryButtonType);

        //button delete locaiton
        deleteLocationButton = findViewById(R.id.locationDeleteButton);

        askLocationDeleteFrame = findViewById(R.id.askRemoveLocationFrame);

        ImageButton confirmLocationDeleteButton = findViewById(R.id.removeLocationConfirmButton);
        ImageButton denyLocationDeleteButton = findViewById(R.id.dontRemoveLocationButton);

        //onclick to bring up the lcoation selector
        setLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MapActivity.class);
                intent.putExtra("initialLocation", locationLog);
                startActivityForResult(intent, MAP_ACTIVITY_REQUEST_CODE);
            }
        });

        //onclick to delete location
        deleteLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askLocationDeleteFrame.setVisibility(View.VISIBLE);
            }
        });

        //onclick confirmlocation deletion
        confirmLocationDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocationButton.setBackground(setLocationButtonNormalDrawable);
                isLocationSelected = false;
                locationLog = "";
                askLocationDeleteFrame.setVisibility(View.INVISIBLE);
                deleteLocationButton.setVisibility(View.INVISIBLE);
            }
        });

        //onclick deny location deletion
        denyLocationDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askLocationDeleteFrame.setVisibility(View.INVISIBLE);
            }
        });

        //initializing buttons for setting date and time
        dateTimeLoggerText = findViewById(R.id.dateTextLogger);
        dateTimeEntryFrame = findViewById(R.id.timeSetterFrame);
        dateTimeEntryButton = findViewById(R.id.timeDateEntryButton);
        unselectedDateTimeButtonDrawable = dateTimeEntryButton.getBackground();
        selectedDateTimeButtonDrawable = unselectedDateTimeButtonDrawable.getConstantState().newDrawable().mutate();
        //// Create a ColorMatrixColorFilter for selected time entry button
        ColorMatrix colorMatrixDateTimeEntryButtonType = new ColorMatrix();
        colorMatrixDateTimeEntryButtonType.setRotate(1, 180); // Adjust hueValue as needed
        ColorMatrixColorFilter colorFilterForDateTimeEntryButtonType = new ColorMatrixColorFilter(colorMatrixDateTimeEntryButtonType);
        //// Apply the color filter to the selected time entry button
        selectedDateTimeButtonDrawable.setColorFilter(colorFilterForDateTimeEntryButtonType);


        unselectedTimeButtonDrawable = getResources().getDrawable(R.drawable.small_gray_time_box);
        selectedTimeButtonDrawable = unselectedTimeButtonDrawable.getConstantState().newDrawable().mutate();
        //// Create a ColorMatrixColorFilter for selected buttons
        ColorMatrix colorMatrixTimeEntryButtonType = new ColorMatrix();
        colorMatrixTimeEntryButtonType.setRotate(1, 180); // Adjust hueValue as needed
        ColorMatrixColorFilter colorFilterForTimeEntryButtonType = new ColorMatrixColorFilter(colorMatrixTimeEntryButtonType);
        //// Apply the color filter to the selected date/time buttons drawable
        selectedTimeButtonDrawable.setColorFilter(colorFilterForTimeEntryButtonType);


        buttonAM = findViewById(R.id.button_am);
        unselectedAmPmButtonDrawable = buttonAM.getBackground();
        buttonPM = findViewById(R.id.button_pm);
        selectedAmPmButtonDrawable = unselectedAmPmButtonDrawable.getConstantState().newDrawable().mutate();
        //// Create a ColorMatrixColorFilter for am/pm buttons
        ColorMatrix colorMatrixAmPmEntryButtonType = new ColorMatrix();
        colorMatrixAmPmEntryButtonType.setRotate(1, 180); // Adjust hueValue as needed
        ColorMatrixColorFilter colorFilterForAmPmEntryButtonType = new ColorMatrixColorFilter(colorMatrixAmPmEntryButtonType);
        //// Apply the color filter to the new drawables for am/pm buttons
        selectedAmPmButtonDrawable.setColorFilter(colorFilterForAmPmEntryButtonType);
        buttonSubmit = findViewById(R.id.button_submit_time);

        //scrollviews for time categories
        monthScrollView = findViewById(R.id.month_scrollview);
        dateScrollView = findViewById(R.id.date_scrollview);
        hourScrollView = findViewById(R.id.hour_scrollview);
        minuteScrollView = findViewById(R.id.minute_scrollview);

        //this one is horizontal
        yearScrollView = findViewById(R.id.year_scrollview);

        //onclick listeners to set am and pm
        buttonAM.setOnClickListener(v -> selectAMPM("AM"));
        buttonPM.setOnClickListener(v -> selectAMPM("PM"));
        buttonSubmit.setOnClickListener(v -> submitDateTime());

        //adding the time numbers to the scrollviews
        populateScrollViews();
        setCurrentDateTime();


        //bring up the date/time entry onlclick
        dateTimeEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeEntryFrame.setVisibility(View.VISIBLE);
            }
        });

     birdNameLog = name;



        //Resetting the information for a log whenever a new log is started
        eyeconImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                entryFrame.setVisibility(View.VISIBLE); //open the logger frame
                if (dataLogs != null && !dataLogs.isEmpty()) {
                    int lastIndex = dataLogs.size() - 1;
                    String[] lastRow = dataLogs.get(lastIndex);
                    if (lastRow.length > 0) {
                        currentLogNumber = Integer.parseInt(lastRow[0]);
                    } else {
                        currentLogNumber = 1;
                    }
                } else {
                    currentLogNumber = 1;
                }

                //resetting all variables and updating default time
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                boolean isAM = calendar.get(Calendar.AM_PM) == Calendar.AM;
                selectAMPM(isAM ? "AM" : "PM");
                dateLog = month + "-" + day + "-" + year;
                int hour = (calendar.get(Calendar.HOUR) == 0 ? 12 : calendar.get(Calendar.HOUR));
                String minute = String.format("%02d", calendar.get(Calendar.MINUTE));
                timeLog = hour + ":" + minute + " " + selectedAMPM;
                dateTimeLoggerText.setText(dateLog + ", " + timeLog);
                audioIdLog = "";
                photoIdLog = "";
                fieldNoteLog = "";
                locationLog = "";
                seenLog = false;
                heardLog = false;
                capturedLog = false;
                isImageSelected = false;

                //resetting buttons
                audioEntryButton.setBackground(audioEntryButtonNormalDrawable);
                isAudioSelected = false;
                audioEntryText.setText("Add Audio");
                askAudioDeleteFrame.setVisibility(View.INVISIBLE);
                audioDeleteButton.setVisibility(View.INVISIBLE);

                photoEntryButton.setBackground(photoEntryButtonUnpaintedDrawable);
                isImageSelected = false;
                photoEntryText.setText("Add Photo");
                photoIdLog = "";
                imageDeletionQuestion.setVisibility(View.INVISIBLE);
                imageDeleteButton.setVisibility(View.INVISIBLE);
                seeYourImageButton.setVisibility(View.INVISIBLE);

                capturedLogImageButton.setBackground(typeLogButtonNormalDrawable);
                seenLogImageButton.setBackground(typeLogButtonNormalDrawable);
                heardLogImageButton.setBackground(typeLogButtonNormalDrawable);

                fieldNoteEntryButton.setBackground(fieldNoteEntryButtonNormalDrawable);

            }
        });

        //adding a new log to the locally stored csv on submission
        submitConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CSVWriter.fileExists(getApplicationContext(), "data.csv")) {
                    List<String[]> data = new ArrayList<>();
                    data.add(new String[]{String.valueOf(currentLogNumber), birdNameLog, dateLog, timeLog, audioIdLog, photoIdLog, fieldNoteLog, String.valueOf(seenLog), String.valueOf(heardLog), String.valueOf(capturedLog), locationLog});
                    boolean success = CSVWriter.writeCsvFile(getApplicationContext(), "data.csv", data);
                    if (success) {
                        Log.d("File Write", "good");
                    } else {
                        Log.d("File Write", "bad");
                    }                } else {
                    String[] newRow = {String.valueOf(currentLogNumber), birdNameLog, dateLog, timeLog, audioIdLog, photoIdLog, fieldNoteLog, String.valueOf(seenLog), String.valueOf(heardLog), String.valueOf(capturedLog), locationLog};
                    boolean success = CSVWriter.appendCsvFile(getApplicationContext(), "data.csv", newRow);
                    if (success) {
                        Log.d("File Append", "good");

                    } else {
                        Log.d("File append", "bad");
                    }
                }
                //closing the logger
                submitAskAboutFrame.setVisibility(View.INVISIBLE);
                entryFrame.setVisibility(View.INVISIBLE);
                Toast.makeText(ProfileActivity.this, "Log Submitted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //calculating the average color of the icon for use in theming
    private int calculateAverageColor(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int totalPixelCount = width * height;
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        int validPixelCount = 0; // Track the number of valid non-black pixels
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = bitmap.getPixel(x, y);
                int alpha = Color.alpha(pixel);
                if (alpha != 0) { // Exclude  transparent pixels
                    int red = Color.red(pixel);
                    int green = Color.green(pixel);
                    int blue = Color.blue(pixel);
                    // Exclude very dark colors (close to black)
                    if (red > 40 || green > 40 || blue > 40) {
                        redSum += red;
                        greenSum += green;
                        blueSum += blue;
                        validPixelCount++;
                    }
                }
            }
        }
        // Calculate the average color
        int averageRed = redSum / validPixelCount;
        int averageGreen = greenSum / validPixelCount;
        int averageBlue = blueSum / validPixelCount;
        return Color.rgb(averageRed, averageGreen, averageBlue);
    }

    //find the closest color of selected colors to the average color of the icon
    private int findClosestColor(int targetColor, int[] colors) {
        int minDistance = Integer.MAX_VALUE;
        int closestColor = Color.BLACK;
        for (int color : colors) {
            int distance = calculateColorDistance(targetColor, color);
            if (distance < minDistance) {
                minDistance = distance;
                closestColor = color;
            }
        }
        //make yellow darker for theming
        if (closestColor == Color.YELLOW) {
            return darkerShadeOfYellow(closestColor);
        } else {
            return closestColor;
        }
    }

    //darker yellow creation
    private int darkerShadeOfYellow(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        // Decrease the brightness by reducing each component
        red = (int) (red * 0.7);
        green = (int) (green * 0.5);
        blue = (int) (blue * 0.7);

        return Color.rgb(red, green, blue);
    }

    //calculating the distance between colors
    private int calculateColorDistance(int color1, int color2) {
        int redDiff = Color.red(color1) - Color.red(color2);
        int greenDiff = Color.green(color1) - Color.green(color2);
        int blueDiff = Color.blue(color1) - Color.blue(color2);
        return redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
    }

    //convert rgb to hex for colors
    public static String rgbToHex(int red, int green, int blue) {
        return String.format("#%02X%02X%02X", red, green, blue);
    }

    //settinf the information for category information
    private void setCategoryInfo(String category) {
        String[] info = categoryMap.get(category);
        if (info != null && info.length == 2) {
            String altCategory = info[0];
            String typeColor = info[1];
            profileTypeTxt.setText(altCategory);
            profileTypeTxt.setTextColor(Color.parseColor(typeColor));
        } else {
            profileTypeTxt.setText("Category not found");
        }
    }

    //flipping imageviews vertically
    private void flipVertical(ImageView imageView) {
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f); // Flip vertically
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setImageMatrix(matrix);
    }

    //retrieving drawables of specific names form drawable folder
    public static Drawable getDrawable(Context context, String name) {
        int resourceId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return context.getResources().getDrawable(resourceId);
    }

    //when any activity is closed, do these things depending on what it is
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //reset media player
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (mediaPlayerLogger != null) {
            mediaPlayerLogger.release();
            mediaPlayerLogger = null;
        }
    }

    //request permissionf for storage
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    //open image selector
    private void selectImage() {
        Intent imageIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(imageIntent, REQUEST_CODE_IMAGE_PICKER);
    }

    //open audio selector
    private void selectAudioFile() {
        Intent audioIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(audioIntent, REQUEST_AUDIO_REQUEST_CODE);
    }

    //diffrentiating what is done based on which activity closed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //storing image or audio  or locationfor image picker or audiopicker or location picker
        if (requestCode == REQUEST_CODE_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            photoIdLog = selectedImageUri.toString();
            isImageSelected = true;
        } else if (requestCode == REQUEST_AUDIO_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedAudioUri = data.getData();
            // Use the selectedAudioUri to do further processing
            // For example, store it in a CSV file
            isAudioSelected = true;
            audioIdLog = selectedAudioUri.toString();
        } else if (requestCode == MAP_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                locationLog = data.getStringExtra("location");
                isLocationSelected = true;
            }
        }

        //reset buttons
        if (isImageSelected) {
            photoEntryButton.setBackground(photoEntryButtonPaintedDrawable);
            photoEntryText.setText("Change Photo");
            imageDeleteButton.setVisibility(View.VISIBLE);
            seeYourImageButton.setVisibility(View.VISIBLE);
        } else {
            photoEntryButton.setBackground(photoEntryButtonUnpaintedDrawable);
            photoEntryText.setText("Add Photo");
            imageDeleteButton.setVisibility(View.INVISIBLE);
            seeYourImageButton.setVisibility(View.INVISIBLE);
        }

        if (isAudioSelected) {
            audioEntryButton.setBackground(audioEntryButtonSelectedDrawable);
            audioEntryText.setText("Change Audio");
            audioDeleteButton.setVisibility(View.VISIBLE);
            playYourAudioButton.setVisibility(View.VISIBLE);
        } else {
            audioEntryButton.setBackground(audioEntryButtonNormalDrawable);
            audioEntryText.setText("Add Audio");
            audioDeleteButton.setVisibility(View.INVISIBLE);
            playYourAudioButton.setVisibility(View.INVISIBLE);
        }

        if (isLocationSelected) {
            setLocationButton.setBackground(setLocationButtonSelectedDrawable);
            deleteLocationButton.setVisibility(View.VISIBLE);
        } else {
            setLocationButton.setBackground(setLocationButtonNormalDrawable);
            deleteLocationButton.setVisibility(View.INVISIBLE);
        }
    }

    //check if a uri is valid
    private boolean isValidUri(String uriString) {
        try {
            Uri uri = Uri.parse(uriString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //select am/pm buttons and set visually
    private void selectAMPM(String ampm) {
        selectedAMPM = ampm;
        buttonAM.setBackground(ampm.equals("AM") ? selectedAmPmButtonDrawable : unselectedAmPmButtonDrawable);
        buttonPM.setBackground(ampm.equals("PM") ? selectedAmPmButtonDrawable : unselectedAmPmButtonDrawable);
    }

    //submit the date time and update visually
    private void submitDateTime() {
        Log.d("DateTimePicker", "Selected DateTime: " + selectedYear + "-" + selectedMonth + "-" + selectedDate +
                " " + selectedHour + ":" + selectedMinute + " " + selectedAMPM);
        dateLog = selectedMonth + "-" + selectedDate + "-" + selectedYear;
        timeLog = selectedHour + ":" + selectedMinute + " " + selectedAMPM;
        dateTimeEntryFrame.setVisibility(View.INVISIBLE);
        dateTimeEntryButton.setBackground(selectedDateTimeButtonDrawable);
        dateTimeLoggerText.setText(dateLog + ", " + timeLog);

    }

    //populating scrollviews for date/time logger
    private void populateScrollViews() {
        populateMonthScrollView();
        populateDateScrollView();
        populateHourScrollView();
        populateMinuteScrollView();
        populateYearScrollView();
    }

    //individual populate functions
    private void populateMonthScrollView() {
        for (String month : MONTHS) {
            addButtonToScrollView(monthScrollView, month, month, 140, 84); // Example size
        }
    }

    private void populateDateScrollView() {
        for (String day : DAYS) {
            addButtonToScrollView(dateScrollView, day, day, 140, 84); // Example size
        }
    }

    private void populateHourScrollView() {
        for (String hour : HOURS) {
            addButtonToScrollView(hourScrollView, hour, hour, 140, 84); // Example size
        }
    }

    private void populateMinuteScrollView() {
        for (String minute : MINUTES) {
            addButtonToScrollView(minuteScrollView, minute, minute, 140, 84); // Example size
        }
    }

    private void populateYearScrollView() {
        for (String year : YEARS) {
            addButtonToScrollView(yearScrollView, year, year, 200, 120); // Example size
        }
    }

    //adding individual number buttons to time/date scrollviews
    private void addButtonToScrollView(LinearLayout scrollView, String text, String value, int width, int height) {
        FrameLayout frameLayout = new FrameLayout(this);

        ImageButton imageButton = new ImageButton(this, null, 0, R.style.ScrollViewButtonStyle);
        imageButton.setBackground(unselectedTimeButtonDrawable);
        if (scrollView == yearScrollView){
            unselectedYearButtonDrawable = imageButton.getBackground();
            selectedYearButtonDrawable = unselectedYearButtonDrawable.getConstantState().newDrawable().mutate();
            //// Create a ColorMatrixColorFilter to adjust hue of selected button drawable
            ColorMatrix colorMatrixYearEntryButtonType = new ColorMatrix();
            colorMatrixYearEntryButtonType.setRotate(1, 180); // Adjust hueValue as needed
            ColorMatrixColorFilter colorFilterForYearEntryButtonType = new ColorMatrixColorFilter(colorMatrixYearEntryButtonType);
            //// Apply the color filter to the selected date/time button drawble
            selectedYearButtonDrawable.setColorFilter(colorFilterForYearEntryButtonType);
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.gravity = Gravity.CENTER;
        imageButton.setLayoutParams(params);
        imageButton.setOnClickListener(v -> onButtonClicked(scrollView, value));

        TextView textView = new TextView(this);
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(customFont);

        textView.setTextSize(40);

        textView.setClickable(false);
        textView.setGravity(Gravity.CENTER);

        frameLayout.addView(imageButton);

        frameLayout.addView(textView);

        scrollView.addView(frameLayout);
    }

    //when the date/time button is clicked, update logger info and deselect all other buttons in scrollview
    private void onButtonClicked(LinearLayout scrollView, String value) {
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            FrameLayout frameLayout = (FrameLayout) scrollView.getChildAt(i);
            ImageButton imageButton = (ImageButton) frameLayout.getChildAt(0);
            if(scrollView == yearScrollView){
                imageButton.setBackground(unselectedYearButtonDrawable);
            } else{
                imageButton.setBackground(unselectedTimeButtonDrawable);
            }
            imageButton.setSelected(false);
        }

        // Select the clicked button
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            FrameLayout frameLayout = (FrameLayout) scrollView.getChildAt(i);
            TextView textView = (TextView) frameLayout.getChildAt(1);
            if (textView.getText().toString().equals(value)) {
                ImageButton selectedButton = (ImageButton) frameLayout.getChildAt(0);
                if(scrollView == yearScrollView){
                    selectedButton.setBackground(selectedYearButtonDrawable);
                } else{
                    selectedButton.setBackground(selectedTimeButtonDrawable);
                }
                selectedButton.setSelected(true);
                break;
            }
        }

        // Store the selected value based on the scrollView
        if (scrollView == monthScrollView) {
            selectedMonth = value;
        } else if (scrollView == dateScrollView) {
            selectedDate = value;
        } else if (scrollView == hourScrollView) {
            selectedHour = value;
        } else if (scrollView == minuteScrollView) {
            selectedMinute = value;
        } else if (scrollView == yearScrollView) {
            selectedYear = value;
        }
    }

    //setting the default time to the current date/time
    private void setCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();

        // Get current date, time, and year
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentHour = calendar.get(Calendar.HOUR);
        int currentMinute = calendar.get(Calendar.MINUTE);
        boolean isAM = calendar.get(Calendar.AM_PM) == Calendar.AM;

        // Set AM or PM
        selectAMPM(isAM ? "AM" : "PM");

        // Select the current month
        String currentMonthStr = MONTHS[currentMonth];
        selectButtonInScrollView(monthScrollView, currentMonthStr);

        // Select the current date
        String currentDateStr = String.valueOf(currentDay);
        selectButtonInScrollView(dateScrollView, currentDateStr);

        // Select the current hour
        String currentHourStr = String.valueOf(currentHour == 0 ? 12 : currentHour); // Handle 12 AM/PM
        selectButtonInScrollView(hourScrollView, currentHourStr);

        // Select the current minute
        String currentMinuteStr = String.format("%02d", currentMinute);
        selectButtonInScrollView(minuteScrollView, currentMinuteStr);

        // Select the current year
        String currentYearStr = String.valueOf(currentYear);
        selectButtonInScrollView(yearScrollView, currentYearStr);
    }

    //selectinf the button for date/time within each scrollview
    private void selectButtonInScrollView(LinearLayout scrollView, String value) {
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            FrameLayout frameLayout = (FrameLayout) scrollView.getChildAt(i);
            TextView textView = (TextView) frameLayout.getChildAt(1);
            if (textView.getText().toString().equals(value)) {
                ImageButton selectedButton = (ImageButton) frameLayout.getChildAt(0);
                if(scrollView == yearScrollView){
                    selectedButton.setBackground(selectedYearButtonDrawable);
                }else{
                    selectedButton.setBackground(selectedTimeButtonDrawable);
                }
                selectedButton.setSelected(true);
                // Store the selected value
                if (scrollView == monthScrollView) {
                    selectedMonth = value;
                } else if (scrollView == dateScrollView) {
                    selectedDate = value;
                } else if (scrollView == hourScrollView) {
                    selectedHour = value;
                } else if (scrollView == minuteScrollView) {
                    selectedMinute = value;
                } else if (scrollView == yearScrollView) {
                    selectedYear = value;
                }
                break;
            }
            String forThisYear = "2020";
            if(scrollView == yearScrollView){
                forThisYear = value;
            }
            dateLog = selectedMonth + "-" + selectedDate + "-" + forThisYear;
            timeLog = selectedHour + ":" + selectedMinute + " " + selectedAMPM;
            dateTimeLoggerText.setText(dateLog + ", " + timeLog);
        }
    }

}