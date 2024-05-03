package com.example.helloworld;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class BirdDataImporter {

    public static List<String> extractCategories(String inputString, String[] allCategories) {
        List<String> extractedCategories = new ArrayList<>();

        // Iterate over each habitat in the allPossibleHabitats array
        for (String category : allCategories) {
            // Check if the habitat exists in the habitatString
            if (inputString.contains(category)) {
                extractedCategories.add(category);
            }
        }

        return extractedCategories;
    }
    public static List<Item> importBirdData(Context context) {
        List<Item> items = new ArrayList<>();

        try {
            // Open the CSV file as an input stream
            InputStream inputStream = context.getResources().openRawResource(R.raw.test4birds);

            // Wrap the input stream in a reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            reader.readLine();

            String line;
            // Read each line of the CSV file
            while ((line = reader.readLine()) != null) {
                // Split the line by comma (assuming CSV format)
                String[] birdData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Remove leading and trailing quotes if they exist
                for (int i = 0; i < birdData.length; i++) {
                    birdData[i] = birdData[i].replaceAll("^\"|\"$", "");
                }

                // Access each field of the bird data
                int birdIdNum = Integer.parseInt(birdData[0]) + 1; // Assuming the first field is ID
                String birdId = Integer.toString(birdIdNum);
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





                String birdIconIdentifier = (birdData[3].replace("-", "_")) + "_icon";
                int birdIconImage = context.getResources().getIdentifier(birdIconIdentifier, "drawable", context.getPackageName());

                //Category Info:
                String[] allPossibleHabitats = {"Forests and Woodlands", "Shrublands, Savannas, and Thickets", "Desert and Arid Habitats", "Arroyos and Canyons", "High Mountains", "Landfills and Dumps", "Fields, Meadows, and Grasslands", "Coasts and Shorelines", "Freshwater Wetlands", "Lakes, Ponds, and Rivers", "Saltwater Wetlands", "Urban and Suburban Habitats", "Tundra and Boreal Habitats", "Open Ocean"};
                String[] allPossibleActivities = {"Direct Flight", "Soaring", "Flap/Glide", "Hovering", "Undulating", "Flushes", "Flitter", "Formation", "Erratic", "Swooping", "Running", "Tree-climbing", "Walking", "Hopping", "Swimming"};
                String[] allPossibleColors = {"Black", "Gray", "White", "Tan", "Brown", "Reddish-Brown", "Red", "Pink", "Orange", "Yellow", "Green", "Olive", "Blue", "Purple"};
                String[] allPossibleCallTypes = {"High", "Flute", "Whistle", "Buzz", "Trill", "Chirp/Chip", "Chatter", "Yodel", "Scream", "Croak/Quack", "Hoot", "Raucous", "Drum", "Rattle", "Odd", "Twitter", "Warble", "Nasal", "Harsh", "Honk", "Complex"};
                String[] allPossibleWingShapes = {"Long", "Narrow", "Pointed", "Broad", "Rounded", "Fingered", "Short"};
                String[] allPossibleTailShapes = {"Long", "Rounded", "Square-tipped", "Notched", "Multi-pointed", "Wedge-shaped", "Forked", "Short", "Pointed"};
                String[] allPossibleTypes = {"Gull-like Birds", "Hawk-like Birds", "Upright-perching Water Birds", "Owls", "Perching Birds", "Long-legged Waders", "Tree-clinging Birds", "Upland Ground Birds", "Duck-like Birds", "Hummingbirds", "Sandpiper-like Birds", "Pigeon-like Birds", "Chicken-like Marsh Birds", "Swallow-like Birds"};
                String[] allPossibleSizes = {"About the size of a Sparrow", "About the size of a Robin", "About the size of a Crow", "About the size of a Mallard or Herring Gull", "About the size of a Heron"};
                List<String> extractedHabitats = extractCategories(birdData[7], allPossibleHabitats);
                List<String> extractedActivities = extractCategories(birdData[9], allPossibleActivities);
                List<String> extractedColors = extractCategories(birdData[14], allPossibleColors);
                List<String> extractedCallTypes = extractCategories(birdData[18], allPossibleCallTypes);
                List<String> extractedWingShapes = extractCategories(birdData[15], allPossibleWingShapes);
                List<String> extractedTailShapes = extractCategories(birdData[16], allPossibleTailShapes);
                List<String> extractedSizes = extractCategories(birdData[13], allPossibleSizes);
                List<String> extractedTypes = extractCategories(birdData[5], allPossibleTypes);








                // Create Item object and add to the list
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


}