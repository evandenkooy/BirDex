package com.example.helloworld;

import java.util.List;

public class Item {
    String name;
    String scientificName;
    String birdNumber;
    int birdIcon;

    String atGlance;
    String habitatExtended;
    String behavior;
    String conservation;
    String population;
    String description;
    String size;
    String color;
    String wingShape;
    String tailShape;
    String callPattern;
    String callType;
    String eggs;
    String diet;
    String young;
    String feeding;
    String nesting;

    String category;
    String webname;

    List<String> extractedActivities;
    List<String> extractedColors;
    List<String> extractedCallTypes;
    List<String> extractedWingShapes;
    List<String> extractedTailShapes;
    List<String> extractedSizes;
    List<String> extractedTypes;


    List<String> extractedHabitats;



    public Item(String birdNumber, String name, String scientificName, int birdIcon, List<String> extractedHabitats, List<String> extractedActivities, List<String> extractedColors, List<String> extractedCallTypes, List<String> extractedWingShapes, List<String> extractedTailShapes, List<String> extractedSizes, List<String> extractedTypes, String habitatExtended, String behavior, String conservation, String population, String description, String size, String color, String wingShape, String tailShape, String callPattern, String callType, String eggs, String diet, String young, String feeding, String nesting, String atGlance, String category, String webname) {
        this.birdNumber = birdNumber;
        this.name = name;
        this.birdIcon = birdIcon;
        this.scientificName = scientificName;
        this.extractedActivities = extractedActivities;
        this.extractedSizes = extractedSizes;
        this.extractedColors = extractedColors;
        this.extractedCallTypes = extractedCallTypes;
        this.extractedWingShapes = extractedWingShapes;
        this.extractedHabitats = extractedHabitats;
        this.extractedTailShapes = extractedTailShapes;
        this.extractedTypes = extractedTypes;
        this.habitatExtended = habitatExtended;
        this.behavior = behavior;
        this.conservation = conservation;
        this.population = population;
        this.description = description;
        this.size = size;
        this.color = color;
        this.wingShape = wingShape;
        this.tailShape = tailShape;
        this.callPattern = callPattern;
        this.callType = callType;
        this.eggs = eggs;
        this.young = young;
        this.diet = diet;
        this.feeding = feeding;
        this.nesting = nesting;
        this.atGlance = atGlance;
        this.category = category;
        this.webname = webname;

    }

    public String getWebname() {
        return webname;
    }

    public String getHabitatExtended() {
        return habitatExtended;
    }

    public String getBehavior() {
        return behavior;
    }

    public String getConservation() {
        return conservation;
    }

    public String getCategory() {
        return category;
    }

    public String getPopulation() {
        return population;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getWingShape() {
        return wingShape;
    }

    public String getTailShape() {
        return tailShape;
    }

    public String getCallPattern() {
        return callPattern;
    }

    public String getCallType() {
        return callType;
    }

    public String getEggs() {
        return eggs;
    }

    public String getDiet() {
        return diet;
    }

    public String getYoung() {
        return young;
    }

    public String getFeeding() {
        return feeding;
    }

    public String getNesting() {
        return nesting;
    }

    public List<String> getExtractedActivities() {
        return extractedActivities;
    }

    public void setExtractedActivities(List<String> extractedActivities) {
        this.extractedActivities = extractedActivities;
    }

    public List<String> getExtractedColors() {
        return extractedColors;
    }

    public void setExtractedColors(List<String> extractedColors) {
        this.extractedColors = extractedColors;
    }

    public List<String> getExtractedCallTypes() {
        return extractedCallTypes;
    }

    public void setExtractedCallTypes(List<String> extractedCallTypes) {
        this.extractedCallTypes = extractedCallTypes;
    }

    public List<String> getExtractedWingShapes() {
        return extractedWingShapes;
    }

    public void setExtractedWingShapes(List<String> extractedWingShapes) {
        this.extractedWingShapes = extractedWingShapes;
    }

    public List<String> getExtractedTailShapes() {
        return extractedTailShapes;
    }

    public void setExtractedTailShapes(List<String> extractedTailShapes) {
        this.extractedTailShapes = extractedTailShapes;
    }

    public List<String> getExtractedSizes() {
        return extractedSizes;
    }

    public void setExtractedSizes(List<String> extractedSizes) {
        this.extractedSizes = extractedSizes;
    }

    public List<String> getExtractedHabitats() {
        return extractedHabitats;
    }

    public void setExtractedHabitats(List<String> extractedHabitats) {
        this.extractedHabitats = extractedHabitats;
    }

    public List<String> getExtractedTypes() {
        return extractedTypes;
    }

    public void setExtractedTypes(List<String> extractedTypes) {
        this.extractedTypes = extractedTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getBirdNumber() {
        return birdNumber;
    }

    public void setBirdNumber(String birdNumber) {
        this.birdNumber = birdNumber;
    }

    public int getBirdIcon() {
        return birdIcon;
    }

    public void setBirdIcon(int birdIcon) {
        this.birdIcon = birdIcon;
    }

    public String getAtGlance() {
        return atGlance;
    }
}
