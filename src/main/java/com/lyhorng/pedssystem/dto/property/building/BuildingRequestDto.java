package com.lyhorng.pedssystem.dto.property.building;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lyhorng.pedssystem.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildingRequestDto {
    
    private Long sourceTypeId;  // SME, MEGA, Branch, Agency relate with agency id
    private Long agencyId; 
    
    private BuildingEvaluation buildingEvaluation;
    
    @NotNull(message = "Building type is required")
    private Long buildingTypeId;
    
    @Column(name = "building_storey")
    private Integer Storey;
    
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