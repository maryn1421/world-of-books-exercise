package com.worldofbooks.exercise.database;


import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class DbInitializer {
    @Autowired
    Seeder seeder;

    @PostConstruct
    public void postConstruct() throws IOException, ParseException {
        seeder.getListingStatusData();
        seeder.getMarketplaceData();
        seeder.getLocationData();
        seeder.getListingData();
    }


}
