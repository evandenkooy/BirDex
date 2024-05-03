package com.example.helloworld.ui.theme;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.BirdDataImporter;
import com.example.helloworld.Item;
import com.example.helloworld.MyAdapter;
import com.example.helloworld.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ProfileActivity extends AppCompatActivity {
    private Map<String, String[]> categoryMap;
    private Map<String, String[]> mainInfoTypesMap;

    private TextView profileTypeTxt;

    private Typeface customFont;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bird_profile);
        TextView profileBirdNameTxt = findViewById(R.id.profileNameTextView);
        TextView profileBirdSciNameTxt = findViewById(R.id.profileSciNameTextView);
        ImageView profileIcon = findViewById(R.id.profileIconImageView);
        TextView profileNumberTxt = findViewById(R.id.profileNumberTextView);
        TextView profileCoolDescTxt = findViewById(R.id.profileCoolNameTextView);
        profileTypeTxt = findViewById(R.id.profileTypeTextView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            customFont = getResources().getFont(R.font.pokemon_pixel_font);
        }



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




        Bundle extras = getIntent().getExtras();
        if (extras != null){
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



        profileBirdNameTxt.setText(name);
        profileBirdSciNameTxt.setText(scientificName);
        profileIcon.setImageResource(icon);
        profileNumberTxt.setText(birdId);

        if(name.length() > 19){
            ViewGroup.MarginLayoutParams nameLayoutParams = (ViewGroup.MarginLayoutParams) profileBirdNameTxt.getLayoutParams();
            int marginTopInDp = 24;
            float density = getResources().getDisplayMetrics().density;
            int marginTopInPx = (int) (marginTopInDp * density);
            nameLayoutParams.topMargin = marginTopInPx;
            profileBirdNameTxt.setTextSize(40);
            profileBirdNameTxt.setLayoutParams(nameLayoutParams);
        }

        if(scientificName.length() > 30){
            ViewGroup.MarginLayoutParams nameLayoutParams = (ViewGroup.MarginLayoutParams) profileBirdSciNameTxt.getLayoutParams();
            int marginTopInDp = 83;
            float density = getResources().getDisplayMetrics().density;
            int marginTopInPx = (int) (marginTopInDp * density);
            nameLayoutParams.topMargin = marginTopInPx;
            profileBirdSciNameTxt.setTextSize(22);
            profileBirdSciNameTxt.setLayoutParams(nameLayoutParams);
        }

        //TYPE STUFF
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



        // Add more categories as needed

        // TYPE STUFF
        String[] partsCategory = category.split(",");
        String resultCategory = partsCategory[0].trim();
        setCategoryInfo(resultCategory);



        //ATGLANCESTUFF
        //
        //

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), icon);

        // Downscale the image (e.g., by half)
        int scaledWidth = originalBitmap.getWidth() / 16; // Adjust as needed
        int scaledHeight = originalBitmap.getHeight() / 16; // Adjust as needed
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, scaledWidth, scaledHeight, true);

        // Calculate the average color of the image
        int averageColor = calculateAverageColor(scaledBitmap);

        // Define the rainbow spectrum colors
        int[] rainbowColors = {
                Color.RED,
                Color.YELLOW,
                Color.GREEN,
                Color.BLUE,
        };

        // Find the closest rainbow color to the average color of the image
        int closestColor = findClosestColor(averageColor, rainbowColors);
        int red = Color.red(closestColor);
        int green = Color.green(closestColor);
        int blue = Color.blue(closestColor);
        String hexColor = rgbToHex(red, green, blue);

        String[] parts = atGlance.split(":");
        String coolName = parts[0].trim();
        String coolDescription = parts[1].trim();
        String textForCool = "<font color=" + hexColor + "><b>" + coolName + ":</b> </font> <font color=#000000>" + coolDescription + "</font>";
        profileCoolDescTxt.setText(Html.fromHtml(textForCool));

        originalBitmap.recycle();
        scaledBitmap.recycle();


        if(atGlance.length() < 130){
            ViewGroup.MarginLayoutParams nameCoolLayoutParams = (ViewGroup.MarginLayoutParams) profileCoolDescTxt.getLayoutParams();
            if (atGlance.length() < 130) {
                int marginTopInDp = 42;
                float density = getResources().getDisplayMetrics().density;
                int marginTopInPx = (int) (marginTopInDp * density);
                nameCoolLayoutParams.topMargin = marginTopInPx;
                profileCoolDescTxt.setTextSize(50);
                profileCoolDescTxt.setLayoutParams(nameCoolLayoutParams);
            }else if (atGlance.length() < 125) {
                int marginTopInDp = 40;
                float density = getResources().getDisplayMetrics().density;
                int marginTopInPx = (int) (marginTopInDp * density);
                nameCoolLayoutParams.topMargin = marginTopInPx;
                profileCoolDescTxt.setTextSize(54);
                profileCoolDescTxt.setLayoutParams(nameCoolLayoutParams);
            }
            //AT GLANCE STUFF END
        }



        //For Rest
        final LinearLayout theBig = findViewById(R.id.theBig);

        //IMAGE SLIDER BEGGINING

        String scoredName = webname.replace("-", "_");
        String prefix = scoredName + "_image";

        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        ViewGroup container = findViewById(R.id.image_container);

        List<Drawable> allDrawables = new ArrayList<>();
        Resources resources = getApplicationContext().getResources();
        String packageName = getApplicationContext().getPackageName();

        // Get the list of all resource IDs for drawables
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

        for (Drawable drawable : allDrawables) {
            ImageView imageView = new ImageView(this);
            imageView.setImageDrawable(drawable);
            imageView.setPadding(10, 10, 10, 10); // Add some padding between images
            container.addView(imageView);
        }

        //IMAGE SLIDER END

        final String[] mainInfoTypes = {"description", "habitatExpanded", "size", "color", "wingShape", "tailShape", "callType", "callPattern", "population", "conservation", "behavior", "eggs", "diet", "feeding", "nesting"};

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












        for(String i : mainInfoTypes) {
            String[] info = mainInfoTypesMap.get(i);
            String paraText = "";
            String introText = "";
            float sat = 1;
            float redRotate = 0;
            float greenRotate = 0;
            float blueRotate = 0;
            if (info != null && info.length == 4) {
                introText = info[0];
                paraText = info[1];
                sat = Float.parseFloat(info[2]);
                greenRotate = Float.parseFloat(info[3]);
            }
            if (paraText != null){
                ColorMatrix hueMatrix = new ColorMatrix();
                hueMatrix.setRotate(1, greenRotate);
                ColorMatrix saturationMatrix = new ColorMatrix();
                saturationMatrix.setSaturation(sat);
                ColorMatrix combinedMatrix = new ColorMatrix();
                combinedMatrix.postConcat(hueMatrix);
                combinedMatrix.postConcat(saturationMatrix);
                ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(combinedMatrix);

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

                ImageView topImage = new ImageView(this);
                topImage.setImageResource(R.drawable.info_frame_end);
                topImage.setColorFilter(colorFilter);
                int topImageBackWidth = getResources().getDisplayMetrics().widthPixels - 30;
                int topImageBackHeight = (int)(topImageBackWidth * 0.177966);
                FrameLayout.LayoutParams topImageLayoutParams = new FrameLayout.LayoutParams(
                        topImageBackWidth,
                        topImageBackHeight
                );
                topImageLayoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                topImage.setLayoutParams(topImageLayoutParams);
                frame.addView(topImage);

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


                // Add FrameLayout to the parent layout
                theBig.addView(frame);


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

            if (i == "habitatExpanded" || i == "behavior" || i == "nesting"){
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









    }

    private int calculateAverageColor(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int totalPixelCount = width * height;
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        int validPixelCount = 0; // Track the number of valid (non-black) pixels
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = bitmap.getPixel(x, y);
                int alpha = Color.alpha(pixel);
                if (alpha != 0) { // Exclude fully transparent pixels
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
        if (closestColor == Color.YELLOW) {
            // Return a darker shade of yellow
            return darkerShadeOfYellow(closestColor);
        } else {
            return closestColor;
        }
    }

    private int darkerShadeOfYellow(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        // Decrease the brightness by reducing each component
        red = (int) (red * 0.7); // 70% of the original red value
        green = (int) (green * 0.5); // 70% of the original green value
        blue = (int) (blue * 0.7); // 70% of the original blue value

        return Color.rgb(red, green, blue);
    }

    private int calculateColorDistance(int color1, int color2) {
        int redDiff = Color.red(color1) - Color.red(color2);
        int greenDiff = Color.green(color1) - Color.green(color2);
        int blueDiff = Color.blue(color1) - Color.blue(color2);
        return redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
    }

    public static String rgbToHex(int red, int green, int blue) {
        return String.format("#%02X%02X%02X", red, green, blue);
    }

    private void setCategoryInfo(String category) {
        String[] info = categoryMap.get(category);
        if (info != null && info.length == 2) {
            String altCategory = info[0];
            String typeColor = info[1];
            // Do whatever you need with altCategory and typeColor
            profileTypeTxt.setText(altCategory);
            // Example of setting text color based on category color
            profileTypeTxt.setTextColor(Color.parseColor(typeColor));
        } else {
            // Handle category not found
            profileTypeTxt.setText("Category not found");
        }
    }

    private void flipVertical(ImageView imageView) {
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f); // Flip vertically
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setImageMatrix(matrix);
    }

    public static Drawable getDrawable(Context context, String name) {
        int resourceId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return context.getResources().getDrawable(resourceId);
    }
}
