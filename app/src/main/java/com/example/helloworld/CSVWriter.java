package com.example.helloworld;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    //columns setting
    private static final String CSV_HEADER = "logNumber,birdName,date,time,audio,photo,fieldNotes,seen,heard,captured,location\n";

    //creating the csv entirely new
    public static boolean writeCsvFile(Context context, String fileName, List<String[]> data) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(CSV_HEADER);
            for (String[] rowData : data) {
                for (String columnData : rowData) {
                    writer.append(columnData).append(",");
                }
                writer.append("\n");
            }
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //adding a row to the csv
    public static boolean appendCsvFile(Context context, String fileName, String[] rowData) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.append(String.join(",", rowData)).append("\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //checking if the csv exists on device
    public static boolean fileExists(Context context, String fileName) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
        return file.exists();
    }

}

