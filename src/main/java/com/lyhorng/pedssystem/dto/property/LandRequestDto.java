package com.lyhorng.pedssystem.dto.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

// ==================== LAND REQUEST DTO ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandRequestDto {
    
    @NotNull(message = "Shape is required")
    private Long shapeId;
    
    @NotNull(message = "Type of lot is required")
    private Long typeOfLotId;
    
    @NotNull(message = "Land size is required")
    @DecimalMin(value = "0.01", message = "Land size must be greater than 0")
    private BigDecimal landSize;
    
    @NotNull(message = "Length is required")
    @DecimalMin(value = "0.01", message = "Length must be greater than 0")
    private BigDecimal length;
    
    @NotNull(message = "Width is required")
    @DecimalMin(value = "0.01", message = "Width must be greater than 0")
    private BigDecimal width;
    
    @DecimalMin(value = "0", message = "Front must be greater than or equal to 0")
    private BigDecimal front;
    
    @DecimalMin(value = "0", message = "Back must be greater than or equal to 0")
    private BigDecimal back;
    
    private Long flatUnitTypeId; // Optional, default blank
    
    @Min(value = 1, message = "Number of lot must be at least 1")
    private Integer numberOfLot = 1;
}
