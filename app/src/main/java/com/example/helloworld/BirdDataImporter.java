package com.example.helloworld;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BirdDataImporter {

    //creating list of categories needed for filtering
    public static List<String> extractCategories(String inputString, String[] allCategories) {
        List<String> extractedCategories = new ArrayList<>();

        for (String category : allCategories) {
            if (inputString.contains(category)) {
                extractedCategories.add(category);
            }
        }

        return extractedCategories;
    }

    //general method for importing bird information
    public static List<Item> importBirdData(Context context) {
        List<Item> items = new ArrayList<>();

        try {
            // Open the CSV file as an input stream
            InputStream inputStream = context.getResources().openRawResource(R.raw.test4birds);

            // Wrap stream with a reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            reader.readLine();

            String line;
            // Read each line of the csv
            while ((line = reader.readLine()) != null) {
                // Split line by comma
                String[] birdData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Remove quotes
                for (int i = 0; i < birdData.length; i++) {
                    birdData[i] = birdData[i].replaceAll("^\"|\"$", "");
                }

                //access individual columns and set variables to info in column
                int birdIdNum = Integer.parseInt(birdData[0]) + 1;
                String birdId = Integer.toString(birdIdNum);
                //bird number setting
                if (birdId.length() < 3){
                    if (birdId.length() < 2) {
                        birdId = "00" + birdId;
                    }
                    else {
                        birdId = "0" + birdId;
                    }
                }
                String birdName = birdData[1];
                String scientificName = birdData[2];
                String habitatExtended = birdData[20];
                String behavior = birdData[9];
                String conservation = birdData[6];
                String population = birdData[10];
                String description = birdData[12];
                String size = birdData[13];
                String color = birdData[14];
                String wingShape = birdData[15];
                String tailShape = birdData[16];
                String callPattern = birdData[17];
                String callType = birdData[18];
                String eggs =birdData[21];
                String diet = birdData[24];
                String young = birdData[22];
                String feeding = birdData[23];
                String nesting = birdData[25];
                String atGlance = birdData[4];
                String category = birdData[5];
                String webname = birdData[3];




                //retrieving bird icon for drawble by modifying webname to be used in file search
                String birdIconIdentifier = (birdData[3].replace("-", "_")) + "_icon";
                int birdIconImage = context.getResources().getIdentifier(birdIconIdentifier, "drawable", context.getPackageName());

                //All possible categories (List used when fitering):
                String[] allPossibleHabitats = {"Forests and Woodlands", "Shrublands, Savannas, and Thickets", "Desert and Arid Habitats", "Arroyos and Canyons", "High Mountains", "Landfills and Dumps", "Fields, Meadows, and Grasslands", "Coasts and Shorelines", "Freshwater Wetlands", "Lakes, Ponds, and Rivers", "Saltwater Wetlands", "Urban and Suburban Habitats", "Tundra and Boreal Habitats", "Open Ocean"};
                String[] allPossibleActivities = {"Direct Flight", "Soaring", "Flap/Glide", "Hovering", "Undulating", "Flushes", "Flitter", "Formation", "Erratic", "Swooping", "Running", "Tree-climbing", "Walking", "Hopping", "Swimming"};
                String[] allPossibleColors = {"Black", "Gray", "White", "Tan", "Brown", "Reddish-Brown", "Red", "Pink", "Orange", "Yellow", "Green", "Olive", "Blue", "Purple"};
                String[] allPossibleCallTypes = {"High", "Flute", "Whistle", "Buzz", "Trill", "Chirp/Chip", "Chatter", "Yodel", "Scream", "Croak/Quack", "Hoot", "Raucous", "Drum", "Rattle", "Odd", "Twitter", "Warble", "Nasal", "Harsh", "Honk", "Complex"};
                String[] allPossibleWingShapes = {"Long", "Narrow", "Pointed", "Broad", "Rounded", "Fingered", "Short"};
                String[] allPossibleTailShapes = {"Long", "Rounded", "Square-tipped", "Notched", "Multi-pointed", "Wedge-shaped", "Forked", "Short", "Pointed"};
                String[] allPossibleTypes = {"Gull-like Birds", "Hawk-like Birds", "Upright-perching Water Birds", "Owls", "Perching Birds", "Long-legged Waders", "Tree-clinging Birds", "Upland Ground Birds", "Duck-like Birds", "Hummingbirds", "Sandpiper-like Birds", "Pigeon-like Birds", "Chicken-like Marsh Birds", "Swallow-like Birds"};
                String[] allPossibleSizes = {"About the size of a Sparrow", "About the size of a Robin", "About the size of a Crow", "About the size of a Mallard or Herring Gull", "About the size of a Heron"};
                //The categories that this bird applies to for filtering:
                List<String> extractedHabitats = extractCategories(birdData[7], allPossibleHabitats);
                List<String> extractedActivities = extractCategories(birdData[9], allPossibleActivities);
                List<String> extractedColors = extractCategories(birdData[14], allPossibleColors);
                List<String> extractedCallTypes = extractCategories(birdData[18], allPossibleCallTypes);
                List<String> extractedWingShapes = extractCategories(birdData[15], allPossibleWingShapes);
                List<String> extractedTailShapes = extractCategories(birdData[16], allPossibleTailShapes);
                List<String> extractedSizes = extractCategories(birdData[13], allPossibleSizes);
                List<String> extractedTypes = extractCategories(birdData[5], allPossibleTypes);








                // Create Item object and add to the list of all items (birds)
                Item item = new Item(birdId, birdName, scientificName, birdIconImage, extractedHabitats, extractedActivities, extractedColors, extractedCallTypes, extractedWingShapes, extractedTailShapes, extractedSizes, extractedTypes, habitatExtended, behavior, conservation, population, description, size, color, wingShape, tailShape, callPattern, callType, eggs, diet, young, feeding, nesting, atGlance, category, webname);
                items.add(item);
            }

            // Close the reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    //method for just getting icon
    public static int fetchIcon(Context context, String birdNameIn) {
        int birdIconImage = 0;
        try {
            // Open the csv as stream
            InputStream inputStream = context.getResources().openRawResource(R.raw.test4birds);

            // wrap stream as reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            reader.readLine();

            String line;
            // Read each line of csv
            while ((line = reader.readLine()) != null) {
                // Split the line by comma
                String[] birdData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Remove quotes
                for (int i = 0; i < birdData.length; i++) {
                    birdData[i] = birdData[i].replaceAll("^\"|\"$", "");

                }

                //get the drawable for the icon
                if (Objects.equals(birdData[1], birdNameIn)){
                    String birdIconIdentifier = (birdData[3].replace("-", "_")) + "_icon";
                    birdIconImage = context.getResources().getIdentifier(birdIconIdentifier, "drawable", context.getPackageName());

                }

            }

            // Close the reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return birdIconImage;
    }


    }