package com.worldofbooks.exercise.utility;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import com.worldofbooks.exercise.model.ImportError;
import com.worldofbooks.exercise.model.Report;
import com.worldofbooks.exercise.service.ftp.FtpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@Service
public class FileHandler {
    @Autowired
    ErrorHandler errorHandler;


    public void writeToFile(File file, List<ImportError> errors) {
        try {
            FileWriter outputFile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputFile);


            String[] header = {"ListingId", "MarketplaceName", "InvalidField"};
            writer.writeNext(header);


            for (ImportError error : errors) {
                String[] data1 = {error.getListingId().toString(), error.getMarketplaceName(), error.getInvalidField()};
                writer.writeNext(data1);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadToFTP(String fileName) throws IOException {
        try {
            FtpClient ftpClient = new FtpClient("localhost", 21, "maryn", "Zolnai123");
            ftpClient.open();
            ftpClient.putFileToPath(new File("src/main/resources/files/" + fileName), "/" + fileName);

        } catch (IIOException exception) {
            exception.printStackTrace();
        }

    }


    public void createJsonFile(Report report) {
        try {
            String jsonInString = new Gson().toJson(report);
            JSONParser parser = new JSONParser();

            JSONObject mJSONObject = (JSONObject) parser.parse(jsonInString);
            String fileName = "report" + errorHandler.getCurrentTime() + ".json";

            FileWriter file = new FileWriter("src/main/resources/files/" + fileName);
            file.write(mJSONObject.toJSONString());
            file.close();

            uploadToFTP(fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
