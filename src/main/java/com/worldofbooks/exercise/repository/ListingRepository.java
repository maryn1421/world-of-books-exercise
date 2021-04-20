package com.worldofbooks.exercise.repository;

import com.worldofbooks.exercise.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, UUID> {

}
