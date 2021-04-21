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

    private UUID inventory_item_location_id;

    private Double listing_price;

    private String currency;

    private Integer quantity;

    private Long listing_status;

    private Long marketplace;

    private Date upload_time;

    private String owner_email_address;
}
