package com.worldofbooks.exercise.service;

import com.worldofbooks.exercise.payload.request.ListingRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;



@Component
class ValidationTest {
    @Autowired
    Validation validation;

    @Test
    void validateNewListing() {

    }

    }


/*
    "id": "3baa5cc7-9682-49ea-a65b-0c7176f4014a",
    "title": "Ostler's Mousetail",
    "description": "Ivesia shockleyi S. Watson var. ostleri Ertter",
    "location_id": "5c3a4cf8-1ac4-456d-ba85-a782ff475256",
    "listing_price": 427.24,
    "currency": "JPY",
    "quantity": 24,
    "listing_status": 3,
    "marketplace": 2,
    "upload_time": "2/15/2018",
    "owner_email_address": "jgwang@aol.com"
 */