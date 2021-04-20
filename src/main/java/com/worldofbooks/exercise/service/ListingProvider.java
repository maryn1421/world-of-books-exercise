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

    public Boolean addNewListing(ListingRequest listingRequest) {


        Location location = locationRepository.findById(listingRequest.getInventory_item_location_id()).get();

        ListingStatus listingStatus = listingStatusRepository.findById(listingRequest.getListing_status()).get();

        MarketPlace marketPlace = marketplaceRepository.findById(listingRequest.getMarketplace()).get();

        Listing listing = new Listing (
                listingRequest.getId(),
                listingRequest.getTitle(),
                listingRequest.getDescription(),
                location,
                listingRequest.getListing_price(),
                listingRequest.getCurrency(),
                listingRequest.getQuantity(),
                listingStatus,
                marketPlace,
                listingRequest.getUpload_time(),
                listingRequest.getOwner_email_address());

        listingRepository.save(listing);
        return true;
    }

}
