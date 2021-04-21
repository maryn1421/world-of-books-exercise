package com.worldofbooks.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    @Id
    private UUID id;

    private String manager_name;

    private String phone;

    private String address_primary;

    private String address_secondary;

    private String country;

    private String town;

    private String postal_code;

}
