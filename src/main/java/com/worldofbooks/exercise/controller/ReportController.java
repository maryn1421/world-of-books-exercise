package com.worldofbooks.exercise.controller;



import com.worldofbooks.exercise.model.Report;
import com.worldofbooks.exercise.service.ReportProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    ReportProvider reportProvider;

    @GetMapping("")
    public Report getAll() {
        return reportProvider.createReport();
    }



}
