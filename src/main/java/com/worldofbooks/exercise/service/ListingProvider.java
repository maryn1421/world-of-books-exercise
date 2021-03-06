package com.worldofbooks.exercise.service;

import com.worldofbooks.exercise.model.Listing;
import com.worldofbooks.exercise.model.ListingStatus;
import com.worldofbooks.exercise.model.Location;
import com.worldofbooks.exercise.model.MarketPlace;
import com.worldofbooks.exercise.payload.request.ListingRequest;
import com.worldofbooks.exercise.repository.ListingRepository;
import com.worldofbooks.exercise.repository.ListingStatusRepository;
import com.worldofbooks.exercise.repository.LocationRepository;
import com.worldofbooks.exercise.repository.MarketplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class ListingProvider {
    @Autowired
    ListingRepository listingRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ListingStatusRepository listingStatusRepository;

    @Autowired
    MarketplaceRepository marketplaceRepository;

    @Autowired
    Validation validation;

    public void addNewListing(ListingRequest listingRequest) throws ParseException {
        Location location = locationRepository.findById(listingRequest.getLocation_id()).get();

        ListingStatus listingStatus = listingStatusRepository.findById(listingRequest.getListing_status()).get();

        MarketPlace marketPlace = marketplaceRepository.findById(listingRequest.getMarketplace()).get();


        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse(listingRequest.getUpload_time());


        Listing listing = new Listing(
                UUID.fromString(listingRequest.getId()),
                listingRequest.getTitle(),
                listingRequest.getDescription(),
                location,
                listingRequest.getListing_price(),
                listingRequest.getCurrency(),
                listingRequest.getQuantity(),
                listingStatus,
                marketPlace,
                date,
                listingRequest.getOwner_email_address());

        listingRepository.save(listing);
    }
}
