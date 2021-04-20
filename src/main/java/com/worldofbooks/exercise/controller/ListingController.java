package com.worldofbooks.exercise.controller;

import com.worldofbooks.exercise.model.Listing;
import com.worldofbooks.exercise.payload.request.ListingRequest;
import com.worldofbooks.exercise.repository.ListingRepository;
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
        return ResponseEntity.ok("Listing added successfully");
    }


}
