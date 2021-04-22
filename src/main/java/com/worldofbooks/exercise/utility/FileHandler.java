package com.worldofbooks.exercise.utility;

import com.opencsv.CSVWriter;
import com.worldofbooks.exercise.model.ImportError;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@Service
public class FileHandler {



    public void writeToFile(File file, List<ImportError> errors){
        try {
            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile);


            String[] header = { "ListingId", "MarketplaceName", "InvalidField" };
            writer.writeNext(header);


            for (ImportError error : errors) {
                String[] data1 = { error.getListingId().toString(), error.getMarketplaceName(), error.getInvalidField() };
                writer.writeNext(data1);
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



}
