package com.worldofbooks.exercise.repository;

import com.worldofbooks.exercise.model.Listing;
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

}
