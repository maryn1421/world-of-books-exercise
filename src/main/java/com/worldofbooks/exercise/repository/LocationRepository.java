package com.worldofbooks.exercise.repository;

import com.worldofbooks.exercise.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public interface LocationRepository extends JpaRepository<Location, UUID> {
}
