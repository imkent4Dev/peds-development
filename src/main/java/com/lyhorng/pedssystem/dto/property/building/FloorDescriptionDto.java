package com.lyhorng.pedssystem.dto.property.building;

import com.lyhorng.pedssystem.enums.FloorAreaType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorDescriptionDto {
    private Long id;
    private String descriptionName;
    private FloorAreaType floorType;
}