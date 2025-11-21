package com.lyhorng.pedssystem.dto.property.building;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lyhorng.pedssystem.enums.BuildingEvaluation;
import com.lyhorng.pedssystem.enums.BuildingSourceType;
import com.lyhorng.pedssystem.enums.BuildingUsage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingResponseDto {
    
    private Long id;
    private BuildingSourceType sourceType;
    private BuildingEvaluation buildingEvaluation;
    private BuildingTypeDto buildingType;
    private BuildingUsage buildingUsage;
    private String buildingStructure;
    private Integer buildingStories;
    private BigDecimal buildingSizeUnit;
    private Integer buildingYearBuilt;
    private String remark;
    
    // Separate lists for MAIN and ANCILLARY floor areas
    private List<FloorAreaResponseDto> mainFloorAreas = new ArrayList<>();
    private List<FloorAreaResponseDto> ancillaryFloorAreas = new ArrayList<>();
    
    // Auto-calculated summary
    private BuildingSummaryDto summary;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
