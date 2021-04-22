package com.worldofbooks.exercise.service;

import com.worldofbooks.exercise.payload.request.ListingRequest;
import com.worldofbooks.exercise.repository.ListingRepository;
import com.worldofbooks.exercise.repository.ListingStatusRepository;
import com.worldofbooks.exercise.repository.LocationRepository;
import com.worldofbooks.exercise.repository.MarketplaceRepository;
import com.worldofbooks.exercise.utility.ErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Validation {



    @Autowired
    ErrorHandler errorHandler;

    @Autowired
    MarketplaceRepository marketplaceRepository;

    @Autowired
    ListingStatusRepository listingStatusRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ListingRepository listingRepository;


    public Boolean validateNewListing(ListingRequest listingRequest) {


        if (!isUUIDValid(listingRequest.getId()) || listingRequest.getId() == null) {
            handleError(listingRequest,"id");
            return false;
        }
        if (listingRequest.getCurrency().length() != 3 || listingRequest.getCurrency() == null) {
            handleError(listingRequest,"currency");
            return false;
        }

        if (listingRequest.getQuantity() < 0) {
            handleError(listingRequest,"quantity");
            return false;
        }
        if (!hasDouble2Decimals(listingRequest.getListing_price()) || listingRequest.getListing_price() < 0) {
            handleError(listingRequest,"listing price");
            return false;
        }

        if (!isMarketplaceValid(listingRequest.getMarketplace()) || listingRequest.getMarketplace() == null) {
            handleError(listingRequest,"marketplace");
            return false;
        }
        if (!isListingStatusValid(listingRequest.getListing_status()) || listingRequest.getListing_status() == null) {
            handleError(listingRequest,"listing status");
            return false;
        }
        if (!isLocationValid(listingRequest.getLocation_id()) || listingRequest.getLocation_id() == null) {
            handleError(listingRequest,"location");
            return false;
        }

        if (!isDateValid(listingRequest.getUpload_time()) || listingRequest.getUpload_time() == null)
        if (!isEmailFormatValid(listingRequest.getOwner_email_address()) || listingRequest.getOwner_email_address() == null) {
            handleError(listingRequest,"email");
            return false;
        }


        return true;
    }


    private boolean isEmailFormatValid(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private boolean isMarketplaceValid(Long marketPlaceId) {
        return marketplaceRepository.findById(marketPlaceId).isPresent();
    }

    private boolean isLocationValid(UUID locationId) {
        return locationRepository.findById(locationId).isPresent();
    }

    private boolean isListingStatusValid(Long listingStatusId) {
        return listingStatusRepository.findById(listingStatusId).isPresent();
    }

    private boolean isUUIDValid(String uuid) {
        try {
            UUID check =  UUID.fromString(uuid);
            return true;
        } catch (Exception ex) {
            return false;
        }



    }

    private boolean isDateValid(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dateCheck = format.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }

    }



    private boolean hasDouble2Decimals(Double number) {
        if (number != null) {

            String[] splitter = number.toString().split("\\.");
            return  splitter[1].length() == 2;
        }
        else {
            return false;
        }
    }


    private void handleError(ListingRequest listingRequest ,String type) {
       errorHandler.addANewError(listingRequest, type);
    }

}
