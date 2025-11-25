package com.lyhorng.pedssystem.dto.property;

import java.util.ArrayList;
import java.util.List;

import com.lyhorng.pedssystem.dto.property.building.BuildingRequestDto;
import com.lyhorng.pedssystem.enums.EvaStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

// ==================== REQUEST DTO ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequestDto {

    @NotNull(message = "Branch request is required")
    private Long branchRequestId;

    @NotNull(message = "Evaluation status is required")
    private EvaStatus evaStatus;

    private String existApplicationCode; // Only for RENEW status

    private Integer evaCycle = 1;

    private Boolean isOwnershipTitle = false;

    @NotNull(message = "Owner is required")
    private Long ownerId;

    private Long coOwnerId;

    @NotNull(message = "Property title type is required")
    private Long propertyTitleTypeId;

    @Size(max = 100, message = "Title number must not exceed 100 characters")
    private String titleNumber;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "Property specific is required")
    private Long propertySpecificId;

    @NotNull(message = "Province is required")
    private Long provinceId;

    @NotNull(message = "District is required")
    private Long districtId;

    @NotNull(message = "Commune is required")
    private Long communeId;

    private Long villageId;

    @NotNull(message = "Measure info is required")
    private Long measureInfoId;

    private Boolean isKeepRecordEvaluation = true;

    @Size(max = 5000, message = "Remark must not exceed 5000 characters")
    private String remark;

    // Land information (1-to-1)
    @NotNull(message = "Land information is required")
    private LandRequestDto land;

    // Buildings (1-to-Many) - Optional, can be empty list
    @Valid
    private List<BuildingRequestDto> buildings = new ArrayList<>();
}
