package com.worldofbooks.exercise.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Currency;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Listing {


    @Id
    private UUID id;

    private String title;

    private String description;


    private UUID inventory_item_location_id;

    private Currency listing_price;

    private String currency;

    private Integer quantity;

    private Long listing_status;

    private Long marketplace;

    private Date upload_time;

    private String owner_email_address;

}
