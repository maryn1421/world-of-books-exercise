package com.worldofbooks.exercise.controller;

import com.worldofbooks.exercise.model.ListingStatus;
import com.worldofbooks.exercise.repository.ListingStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listingStatus")
public class ListingStatusController {

    @Autowired
    ListingStatusRepository listingStatusRepository;


    @GetMapping("")
    public List<ListingStatus> getAll() {
        return listingStatusRepository.findAll();
    }


    @PostMapping()
    public ResponseEntity<String> addNewListingStatus(@RequestBody ListingStatus listingStatusRequest) {
        listingStatusRepository.save(listingStatusRequest);
        return ResponseEntity.ok("success");
    }




}
