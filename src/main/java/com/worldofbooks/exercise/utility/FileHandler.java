package com.worldofbooks.exercise.utility;

import com.opencsv.CSVWriter;
import com.worldofbooks.exercise.model.ImportError;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {



    public void writeToFile(File file, ImportError error){
        try {
            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile);

            String[] header = { "Name", "Class", "Marks" };
            writer.writeNext(header);

            // add data to csv
            /*
            String[] data1 = { "Aman", "10", "620" };
            writer.writeNext(data1);
            String[] data2 = { "Suraj", "10", "630" };
            writer.writeNext(data2);

             */
            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



}
