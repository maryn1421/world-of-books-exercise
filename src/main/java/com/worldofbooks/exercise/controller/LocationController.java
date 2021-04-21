package com.worldofbooks.exercise.controller;

import com.worldofbooks.exercise.model.Location;
import com.worldofbooks.exercise.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

    @GetMapping("")
    public List<Location> getAllLocation() {
        return locationRepository.findAll();
    }


    @PostMapping("")
    public ResponseEntity<String> addNewLocation(@RequestBody Location LocationRequest) {
        Object response = locationRepository.save(LocationRequest);
        if (response.getClass().equals(Location.class)) {
            return ResponseEntity.ok("New location added!");
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }





}
