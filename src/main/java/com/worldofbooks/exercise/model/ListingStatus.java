package com.worldofbooks.exercise.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class ListingStatus {

    @Id
    @GeneratedValue
    private Long id;

    private String status_name;
}
