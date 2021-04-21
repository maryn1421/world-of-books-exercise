package com.worldofbooks.exercise.controller;

import com.worldofbooks.exercise.model.Listing;
import com.worldofbooks.exercise.payload.request.ListingRequest;
import com.worldofbooks.exercise.repository.ListingRepository;
import com.worldofbooks.exercise.service.ListingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/listing")
public class ListingController {

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    ListingProvider listingProvider;


    @GetMapping()
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteListingById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok("Listing deletion successfully");

    }

    @PostMapping
    public ResponseEntity<String> addListing(@RequestBody ListingRequest listingRequest) {
        boolean successful = listingProvider.addNewListing(listingRequest);
        if (successful) {
            return ResponseEntity.ok("Listing added successfully");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
