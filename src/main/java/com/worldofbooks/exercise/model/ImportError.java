package com.worldofbooks.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImportError {

    private UUID listingId;

    private String marketplaceName;

    private String invalidField;

}
