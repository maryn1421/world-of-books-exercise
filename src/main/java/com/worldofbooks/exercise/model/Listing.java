package com.worldofbooks.exercise.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    private Location inventory_item_location_id;

    private Currency listing_price;

    private String currency;

    private Integer quantity;

    @ManyToOne
    private ListingStatus listing_status;

    @ManyToOne
    private MarketPlace marketplace;

    private Date upload_time;

    private String owner_email_address;
}
