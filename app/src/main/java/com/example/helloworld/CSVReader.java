package com.example.helloworld;
import android.content.Context;
import android.net.wifi.aware.AwareResources;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    //reading the whole csv
    public static List<String[]> readCsvFile(Context context, String fileName) {
        List<String[]> data = new ArrayList<>();
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                data.add(rowData);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    //getting each individual item for display of logged items
    public static List<LoggedItem> getItems(Context context, String fileName) {
        List<LoggedItem> data = new ArrayList<>();
        String logNumber = "",birdName= "",date= "",time= "",audio= "",photo= "",fieldNotes= "",location= "";
        boolean seen = false,heard = false,captured = false;
        List<String[]> csvData =readCsvFile(context, fileName);
        for (int index = 0; index < csvData.size(); index++) {
            if (index != 0){
                String[] row = csvData.get(index);
                logNumber = row[0];
                birdName = row[1];
                date = row[2];
                time = row[3];
                audio = row[4];
                photo = row[5];
                fieldNotes = row[6];
                location = row[9];
                seen = Boolean.parseBoolean(row[7]);
                heard = Boolean.parseBoolean(row[8]);
                captured = Boolean.parseBoolean(row[9]);
                LoggedItem currentRowItem = new LoggedItem(logNumber, birdName, date, time, audio, photo, fieldNotes, location, seen, heard, captured);
                data.add(currentRowItem);
            }
        }
        return data;
    }



}