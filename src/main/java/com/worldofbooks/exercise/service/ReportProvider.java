package com.worldofbooks.exercise.service;


import com.worldofbooks.exercise.model.*;
import com.worldofbooks.exercise.repository.ListingRepository;
import com.worldofbooks.exercise.repository.ListingStatusRepository;
import com.worldofbooks.exercise.repository.LocationRepository;
import com.worldofbooks.exercise.repository.MarketplaceRepository;
import com.worldofbooks.exercise.utility.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

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

    @Autowired
    FileHandler fileHandler;


    public void createReport() {
        List<MonthlyReport> monthlyReports = getMonthlyReports();
        Report report = buildReport(monthlyReports);
        fileHandler.createJsonFile(report);
    }


    private Report buildReport(List<MonthlyReport> monthlyReports) {
        Long TotalListings = listingRepository.count();
        return Report.builder()
                .totalAmazonListingNumber(getTotalListingNumbersByMarketplace("AMAZON"))
                .totalEbayListingNumber(getTotalListingNumbersByMarketplace("EBAY"))
                .totalListing(TotalListings)
                .totalAmazonListingPrice(getTotalListingPriceByMarketplace("AMAZON"))
                .totalEbayListingPrice(getTotalListingPriceByMarketplace("EBAY"))
                .averageAmazonListingPrice(getAverageListingPriceByMarketplace("AMAZON"))
                .averageEbayListingPrice(getAverageListingPriceByMarketplace("EBAY"))
                .bestListerEmailAddress(getBestListerOfAllListings()).monthlyReports(monthlyReports).build();
    }


    private String getBestListerOfAllListings() {
        return listingRepository.getBestSeller();
    }


    private Date getMinimumDate() {
        return listingRepository.getLowestDate();

    }

    private Date getMaximumDate() {
        return listingRepository.getHighestDate();
    }


    public List<String> getMonths() {
        List<String> results = new ArrayList<>();

        String startDate = getMinimumDate().toString().split(" ")[0];
        String endDate = getMaximumDate().toString().split(" ")[0];

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();

        try {
            beginCalendar.setTime(formatter.parse(startDate));
            finishCalendar.setTime(formatter.parse(endDate));
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
        return listings.stream().filter(listing -> listing.getMarketplace().getMarketplaceName().equals(marketPlaceName)).count();
    }


    private double getTotalListingPriceByMarketplace(List<Listing> listings, String marketplaceName) {
        double result = 0;
        for (Listing listing : listings) {
            if (listing.getMarketplace().getMarketplaceName().equals(marketplaceName)) {
                result += listing.getListing_price();
            }
        }
        return result;
    }


    private double getAverageListingPriceByMarketPlace(String marketplace, List<Listing> listings) {
        double result = 0;
        int size = 0;
        for (Listing listing : listings) {
            if (listing.getMarketplace().getMarketplaceName().equals(marketplace)) {
                size++;
                result += listing.getListing_price();
            }
        }
        return result / size;
    }

    private String getBestListerByMonthlyListing(List<Listing> monthlyListings) {
        Map<String, Double> listers = new HashMap<>();

        for (Listing listing : monthlyListings) {
            if (!listers.containsKey(listing.getOwner_email_address())) {
                listers.put(listing.getOwner_email_address(), (listing.getQuantity() * listing.getListing_price()));
            } else {
                listers.put(listing.getOwner_email_address(), listers.get(listing.getOwner_email_address()) + (listing.getQuantity() * listing.getListing_price()));
            }
        }
        Map.Entry<String, Double> maxEntry = Collections.max(listers.entrySet(), Map.Entry.comparingByValue());

        return maxEntry.getKey();
    }

    private int getTotalListingNumbersByMarketplace(String marketplaceName) {
        try {
            MarketPlace marketPlace = marketplaceRepository.findByMarketplaceName(marketplaceName);
            return listingRepository.findAllByMarketplace(marketPlace).size();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }

    private double getTotalListingPriceByMarketplace(String marketplaceName) {
        try {
            MarketPlace marketPlace = marketplaceRepository.findByMarketplaceName(marketplaceName);
            double result;
            List<Listing> allListing = listingRepository.findAllByMarketplace(marketPlace);

            result = allListing.stream().mapToDouble(Listing::getListing_price).sum();
            return result;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }

    private double getAverageListingPriceByMarketplace(String marketplaceName) {
        try {
            MarketPlace marketPlace = marketplaceRepository.findByMarketplaceName(marketplaceName);
            double result;
            List<Listing> allListing = listingRepository.findAllByMarketplace(marketPlace);

            result = allListing.stream().mapToDouble(Listing::getListing_price).sum();
            return result / allListing.size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}

