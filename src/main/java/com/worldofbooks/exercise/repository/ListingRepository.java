package com.worldofbooks.exercise.repository;

import com.worldofbooks.exercise.model.Listing;
import com.worldofbooks.exercise.model.MarketPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, UUID> {


    @Query(value = "select  min(uploadTime) from Listing ")
    Date getLowestDate();

    @Query(value = "select  max(uploadTime) from Listing ")
    Date getHighestDate();


    @Query(value = "select max(quantity) from Listing ")
    Integer getHighestQuantity();


    Listing findFirstByQuantity(int quantity);

    List<Listing> findAllByMarketplace(MarketPlace marketplace);


    @Query(value = "SELECT owner_email_address  FROM listing GROUP BY owner_email_address, (quantity * listing_price) ORDER BY  (quantity * listing_price) DESC LIMIT 1", nativeQuery=true)
    String getBestSeller();



}
