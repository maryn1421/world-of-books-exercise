package com.worldofbooks.exercise.repository;

import com.worldofbooks.exercise.model.MarketPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketplaceRepository extends JpaRepository<MarketPlace, Long> {
}
