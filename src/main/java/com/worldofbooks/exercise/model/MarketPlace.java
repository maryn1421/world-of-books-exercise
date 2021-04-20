package com.worldofbooks.exercise.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class MarketPlace {

    @Id
    @GeneratedValue
    private Long id;

    private String marketplace_name;
}
