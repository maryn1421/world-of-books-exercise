package com.worldofbooks.exercise.repository;

import com.worldofbooks.exercise.model.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingStatusRepository extends JpaRepository<ListingStatus, Long> {
}
