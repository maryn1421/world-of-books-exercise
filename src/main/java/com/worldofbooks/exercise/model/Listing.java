package com.worldofbooks.exercise.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Listing {


    @Id
    private UUID id;




}
