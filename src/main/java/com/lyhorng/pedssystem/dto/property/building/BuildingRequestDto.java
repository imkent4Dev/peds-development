package com.lyhorng.pedssystem.dto.property.building;

import com.lyhorng.pedssystem.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingRequestDto {
    
    @NotNull(message = "Source type is required")
    private BuildingSourceType sourceType;
    
    private BuildingEvaluation buildingEvaluation;
    
    @NotNull(message = "Building type is required")
    private Long buildingTypeId;
    
    private BuildingUsage buildingUsage;
    
    private String buildingStructure;
    
    @Min(value = 1, message = "Building stories must be at least 1")
    private Integer buildingStories;
    
    @DecimalMin(value = "0", message = "Building size unit must be greater than or equal to 0")
    private BigDecimal buildingSizeUnit;
    
    @Min(value = 1900, message = "Building year must be at least 1900")
    @Max(value = 2100, message = "Building year must be before 2100")
    private Integer buildingYearBuilt;
    
    @Size(max = 5000, message = "Remark must not exceed 5000 characters")
    private String remark;
    
    // Floor areas list (must have at least one)
    @NotEmpty(message = "At least one floor area is required")
    @Valid
    private List<FloorAreaRequestDto> floorAreas = new ArrayList<>();
}