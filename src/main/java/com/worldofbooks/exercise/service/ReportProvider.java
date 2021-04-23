package com.worldofbooks.exercise.service;

import com.worldofbooks.exercise.model.Listing;
import com.worldofbooks.exercise.model.MonthlyData;
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


    public Report createReport() {
        List<Listing> allListing = listingRepository.findAll();
        List<MonthlyReport> monthlyReports = getMonthlyReports();

        return buildReport(monthlyReports);
    }

    private Report buildReport(List<MonthlyReport> monthlyReports) {
        Long TotalListings = listingRepository.count();
    return Report.builder()
            .totalListing(TotalListings)
            .BestListerEmailAddress(getBestListerOfAllListings()).monthlyReports(monthlyReports).build();
    }

    private String getBestListerOfAllListings() {
        int highestQuantity = listingRepository.getHighestQuantity();
        Listing highestQuantityListing = listingRepository.findFirstByQuantity(highestQuantity);
        return highestQuantityListing.getOwner_email_address();
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
        List<String> months = getMonths();
        List<MonthlyReport> monthlyReports = new ArrayList<>();
        months.forEach(month -> {
            List<Listing> monthlyListings = getMonthlyListingsByDate(month);

            monthlyReports.add(getMReportFromMonthlyListings(monthlyListings));
        });


        return monthlyReports;

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

    private MonthlyReport getMReportFromMonthlyListings(List<Listing> monthlyListings) {
        Long totalEbayListings = getTotalListingsByMarketplaceName(monthlyListings, "EBAY");
        double averageEbayListings = getAverageListingPriceByMarketPlace("EBAY", monthlyListings);
        double averageAmazonListings = getAverageListingPriceByMarketPlace("AMAZON", monthlyListings);
        Long totalAmazonListings = getTotalListingsByMarketplaceName(monthlyListings, "AMAZON");
        double totalAmazonListingPrice = getTotalListingPriceByMarketplace(monthlyListings, "AMAZON");
        double totalEbayListingPrice = getTotalListingPriceByMarketplace(monthlyListings, "EBAY");

        MonthlyData data = new MonthlyData(
                totalEbayListings, totalEbayListingPrice, averageEbayListings, totalAmazonListings, totalAmazonListingPrice, averageAmazonListings, getBestListerByMonthlyListing(monthlyListings)
        );

        return MonthlyReport.builder()
                .date(monthlyListings.get(0).getUploadTime().toString().split(" ")[0]).monthlyData(data).build();
    }


    private Long getTotalListingsByMarketplaceName(List<Listing> listings, String marketPlaceName) {
        return listings.stream().filter(listing -> listing.getMarketplace().getMarketplace_name().equals(marketPlaceName)).count();
    }


    private double getTotalListingPriceByMarketplace(List<Listing> listings, String marketplaceName) {
        double result = 0;
        for (Listing listing : listings) {
            if (listing.getMarketplace().getMarketplace_name().equals(marketplaceName)) {
                result += listing.getListing_price();
            }
        }
        return result;
    }


    private double getAverageListingPriceByMarketPlace(String marketplace, List<Listing> listings) {
        double result = 0;
        int size = 0;
        for (Listing listing : listings) {
            if (listing.getMarketplace().getMarketplace_name().equals(marketplace)) {
                size++;
                result += listing.getListing_price();
            }
        }
        return result / size;
    }

    private String getBestListerByMonthlyListing(List<Listing> monthlyListings) {
        Listing bestLister = monthlyListings.get(0);

        for (Listing listing : monthlyListings) {
            if (listing.getQuantity() > bestLister.getQuantity()) {
                bestLister = listing;
            }
        }
        return bestLister.getOwner_email_address();


    }
}

