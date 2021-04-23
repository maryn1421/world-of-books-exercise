package com.worldofbooks.exercise.service;

import com.worldofbooks.exercise.model.Listing;
import com.worldofbooks.exercise.model.MonthlyReport;
import com.worldofbooks.exercise.model.Report;
import com.worldofbooks.exercise.repository.ListingRepository;
import com.worldofbooks.exercise.repository.ListingStatusRepository;
import com.worldofbooks.exercise.repository.LocationRepository;
import com.worldofbooks.exercise.repository.MarketplaceRepository;
import com.worldofbooks.exercise.utility.ErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReportProvider {

    @Autowired
    MarketplaceRepository marketplaceRepository;

    @Autowired
    ListingStatusRepository listingStatusRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ListingRepository listingRepository;


    public List<Listing> createReport() {
        List<Listing> allListing = listingRepository.findAll();
        getMonthlyReports();


        return allListing;


    }


    private Date getMinimumDate() {
        return listingRepository.getLowestDate();

    }

    private Date getMaximumDate() {
        return listingRepository.getHighestDate();
    }


    public List<String> getMonths() {
        List<String> results = new ArrayList<>();

        String date11 = getMinimumDate().toString().split(" ")[0];
        String date22 = getMaximumDate().toString().split(" ")[0];

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();

        try {
            beginCalendar.setTime(formatter.parse(date11));
            finishCalendar.setTime(formatter.parse(date22));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        while (beginCalendar.before(finishCalendar)) {
            String date = formatter.format(beginCalendar.getTime()).toUpperCase();
            results.add(date);
            beginCalendar.add(Calendar.MONTH, 1);
        }
        return results;
    }


    public List<MonthlyReport> getMonthlyReports() {
        List<MonthlyReport> results = new ArrayList<>();
        List<String> months = getMonths();
        months.forEach(month ->{
            System.out.println(getMonthlyListingsByDate(month));
        });
        return results;

    }


    private List<Listing> getMonthlyListingsByDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        Month month = localDate.getMonth();
        int year = localDate.getYear();


        List<Listing> listings = listingRepository.findAll();
        List<Listing> results = new ArrayList<>();

        listings.forEach(listing -> {
            LocalDate currentDate = LocalDate.parse(listing.getUploadTime().toString().split(" ")[0]);
            Month currentMonth = currentDate.getMonth();
            int currentYear = currentDate.getYear();
            if (month == currentMonth && year == currentYear) {
                results.add(listing);
            }
        });

        return results;


    }


}

