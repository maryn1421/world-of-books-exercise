package com.worldofbooks.exercise.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyData {

    private Long totalEbayListing;

    private Double totalEbayListingPrice;

    private Double averageEbayListingPrice;

    private Long totalAmazonListing;

    private Double totalAmazonListingPrice;

    private Double averageAmazonListingPrice;

    private String BestListerEmailAddress;

}
