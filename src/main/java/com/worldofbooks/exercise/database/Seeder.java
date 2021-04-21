package com.worldofbooks.exercise.database;

import com.worldofbooks.exercise.model.ListingStatus;
import com.worldofbooks.exercise.model.Location;
import com.worldofbooks.exercise.model.MarketPlace;
import com.worldofbooks.exercise.payload.request.ListingRequest;
import com.worldofbooks.exercise.repository.ListingRepository;
import com.worldofbooks.exercise.repository.ListingStatusRepository;
import com.worldofbooks.exercise.repository.LocationRepository;
import com.worldofbooks.exercise.repository.MarketplaceRepository;
import com.worldofbooks.exercise.service.ListingProvider;
import com.worldofbooks.exercise.service.Validation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

@Service
public class Seeder {
    @Autowired
    Validation validation;

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ListingStatusRepository listingStatusRepository;

    @Autowired
    MarketplaceRepository marketplaceRepository;

    @Autowired
    ListingProvider listingProvider;


    private static JSONArray fetchByURl(String Url) throws IOException, ParseException {
        StringBuilder inline = new StringBuilder();

        URL url = new URL(Url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();


        if (responsecode != 200)
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        else {
            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                inline.append(sc.nextLine());
            }

            sc.close();
        }

        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(inline.toString());

        conn.disconnect();
        return array;

    }


    public void getListingData() throws IOException, ParseException {
        {
            JSONArray array = fetchByURl("https://my.api.mockaroo.com/listing?key=63304c70");

            array.forEach(item -> {
                Location location = new Location();
                MarketPlace marketPlace = new MarketPlace();
                ListingStatus listingStatus = new ListingStatus();






                JSONParser parse = new JSONParser();
                try {
                    JSONObject json = (JSONObject) parse.parse(item.toString());




                           ListingRequest request =  ListingRequest.builder()
                                    .id((String) json.get("id"))
                                    .title((String) json.get("title"))
                                    .description((String) json.get("description"))
                                    .location_id((UUID.fromString((String) json.get("location_id"))))
                                    .listing_price(Double.parseDouble(json.get("listing_price").toString()))
                                    .listing_status(Long.parseLong(json.get("listing_status").toString()))
                                    .currency((String) json.get("currency"))
                                    .marketplace(Long.parseLong( json.get("marketplace").toString()))
                                    .owner_email_address((String) json.get("owner_email_address"))
                                    .quantity(Integer.parseInt(json.get("quantity").toString()))
                                    .upload_time((String) json.get("upload_time"))
                                    .build();


                           if (validation.validateNewListing(request)) {
                               listingProvider.addNewListing(request);
                           };



                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }

    }


    public void getLocationData() throws IOException, ParseException {
        JSONArray array = fetchByURl("https://my.api.mockaroo.com/location?key=63304c70");

        array.forEach(item -> {
            JSONParser parse = new JSONParser();
            try {
                JSONObject json = (JSONObject) parse.parse(item.toString());

                locationRepository.save(
                        Location.builder()
                                .id(UUID.fromString((String) json.get("id")))
                                .manager_name((String) json.get("manager_name"))
                                .phone((String) json.get("phone"))
                                .address_primary((String) json.get("address_primary"))
                                .address_secondary((String) json.get("address_secondary"))
                                .country((String) json.get("country"))
                                .postal_code((String) json.get("postal_code"))
                                .town((String) json.get("town"))
                                .build()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }


    public void getMarketplaceData() throws IOException, ParseException {
        JSONArray array = fetchByURl("https://my.api.mockaroo.com/marketplace?key=63304c70");

        array.forEach(marketplace -> {
            JSONParser parser = new JSONParser();

            try {
                JSONObject json = (JSONObject) parser.parse(marketplace.toString());

                marketplaceRepository.save(
                        MarketPlace.builder()
                                .id(Long.parseLong(json.get("id").toString()))
                                .marketplace_name((String) json.get("marketplace_name"))
                                .build()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void getListingStatusData() throws IOException, ParseException {
        JSONArray array = fetchByURl("https://my.api.mockaroo.com/listingStatus?key=63304c70");

        array.forEach(listingStatus -> {
            JSONParser jsonParser = new JSONParser();

            try {
                JSONObject json = (JSONObject) jsonParser.parse(listingStatus.toString());

                listingStatusRepository.save(
                        ListingStatus.builder()
                                .id(Long.parseLong(json.get("id").toString()))
                                .status_name((String) json.get("status_name"))
                                .build()
                );

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }


}