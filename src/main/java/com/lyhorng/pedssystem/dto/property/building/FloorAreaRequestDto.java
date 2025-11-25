package com.lyhorng.pedssystem.dto.property.building;

import com.lyhorng.pedssystem.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

// ==================== FLOOR AREA REQUEST DTO ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorAreaRequestDto {
    
    @NotNull(message = "Floor type is required")
    private FloorAreaType floorType;
    
    @NotNull(message = "Floor description is required")
    private Long floorDescriptionId;
    
    @NotNull(message = "Size is required")
    @DecimalMin(value = "0.01", message = "Size must be greater than 0")
    private BigDecimal size;
    
    @DecimalMin(value = "0", message = "Length must be greater than or equal to 0")
    private BigDecimal length;
    
    private Integer displayOrder;
}