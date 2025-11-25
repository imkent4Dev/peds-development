package com.lyhorng.pedssystem.dto.property.building;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingsBySourceDto {
    private List<BuildingResponseDto> branch = new ArrayList<>();
    private List<BuildingResponseDto> sme = new ArrayList<>();
    private List<BuildingResponseDto> mega = new ArrayList<>();
    private List<BuildingResponseDto> agency = new ArrayList<>();
}