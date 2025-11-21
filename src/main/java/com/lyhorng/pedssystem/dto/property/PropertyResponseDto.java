package com.lyhorng.pedssystem.dto.property;

import java.time.LocalDateTime;

import com.lyhorng.pedssystem.dto.property.building.BuildingTotalsDto;
import com.lyhorng.pedssystem.dto.property.building.BuildingsBySourceDto;
import com.lyhorng.pedssystem.enums.EvaStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ==================== RESPONSE DTO ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyResponseDto {

    private Long id;
    private BranchRequestSummaryDto branchRequest;
    private EvaStatus evaStatus;
    private String existApplicationCode;
    private String applicationCode;
    private Integer evaCycle;
    private Boolean isOwnershipTitle;

    private CustomerSummaryDto owner;
    private CustomerSummaryDto coOwner;

    private PropertyTitleTypeSummaryDto propertyTitleType;
    private String titleNumber;
    private CategorySummaryDto category;
    private PropertySpecificSummaryDto propertySpecific;

    private LocationDto location;
    private PropertyMeasureInfoSummaryDto measureInfo;
    private Boolean isKeepRecordEvaluation;
    private String remark;

    private LandResponseDto land;

    private BuildingsBySourceDto buildingsBySource;
    private BuildingTotalsDto buildingTotals;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
