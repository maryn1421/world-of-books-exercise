package com.worldofbooks.exercise.controller;


import com.worldofbooks.exercise.model.Listing;
import com.worldofbooks.exercise.service.ReportProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    ReportProvider reportProvider;

    @GetMapping("")
    public List<Listing> getAll() {
        return reportProvider.createReport();
    }



}
