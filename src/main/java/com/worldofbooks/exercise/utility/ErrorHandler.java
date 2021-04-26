package com.worldofbooks.exercise.utility;


import com.worldofbooks.exercise.model.ImportError;
import com.worldofbooks.exercise.model.MarketPlace;
import com.worldofbooks.exercise.payload.request.ListingRequest;
import com.worldofbooks.exercise.repository.MarketplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ErrorHandler {

    List<ImportError> badRequests = new ArrayList<>();


    File errorFile = new File("src/main/resources/logs/importLog" + getCurrentTime() + ".csv");

    @Autowired
    FileHandler fileHandler;

    @Autowired
    MarketplaceRepository marketplaceRepository;


    public void addANewError(ListingRequest listingRequest, String fieldError) {
        String marketPlaceName = "not found";
        Optional<MarketPlace> byId = marketplaceRepository.findById(listingRequest.getMarketplace());

        if (byId.isPresent()) {
            marketPlaceName = byId.get().getMarketplaceName();
        }


        ImportError importError = new ImportError(
                UUID.fromString(listingRequest.getId()),
                marketPlaceName,
                fieldError
        );
        badRequests.add(importError);
    }


    String getCurrentTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String result =  dateTime.format(formatter).replaceAll(" ", "");
        result = result.replace(":", "");
        result = result.replace("-", "");
        return result;

    }


    public void submitErrorToFile() {
        fileHandler.writeToFile(errorFile, badRequests);
    }


}
