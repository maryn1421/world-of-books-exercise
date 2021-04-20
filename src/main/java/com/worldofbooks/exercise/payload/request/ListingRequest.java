package com.worldofbooks.exercise.payload.request;

import com.worldofbooks.exercise.model.ListingStatus;
import com.worldofbooks.exercise.model.Location;
import com.worldofbooks.exercise.model.MarketPlace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;
import java.util.Date;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingRequest {

    private UUID id;

    private String title;

    private String description;

    private Location inventory_item_location_id;

    private Currency listing_price;

    private String currency;

    private Integer quantity;

    private ListingStatus listing_status;

    private MarketPlace marketplace;

    private Date upload_time;

    private String owner_email_address;
}
