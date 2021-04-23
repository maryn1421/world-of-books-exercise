package com.worldofbooks.exercise.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    private Long totalListing;

    private Integer totalEbayListingNumber;

    private Double totalEbayListingPrice;

    private Double averageEbayListingPrice;

    private Integer totalAmazonListingNumber;

    private Double totalAmazonListingPrice;

    private Double averageAmazonListingPrice;

    private String BestListerEmailAddress;

    private List<MonthlyReport> monthlyReports;

}
