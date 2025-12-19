package com.lyhorng.pedssystem.dto.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Google Map information of a property.
 * All fields are nullable/optional.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyMapInfoDto {

    private Long id;
    private String drawLine;
    private String style;
    private Double latitude;
    private Double longitude;
    private String documentPath;
}


