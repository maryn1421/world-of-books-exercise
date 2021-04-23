package com.worldofbooks.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyReport {

    private Long totalEbayListing;

    private Double totalEbayListingPrice;

    private Double averageEbayListingPrice;

    private Long totalAmazonListing;

    private Double totalAmazonListingPrice;

    private Double averageAmazonListingPrice;

    private String BestListerEmailAddress;


}
