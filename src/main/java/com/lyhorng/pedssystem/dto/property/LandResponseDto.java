package com.lyhorng.pedssystem.dto.property;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ==================== LAND RESPONSE DTO ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandResponseDto {
    
    private Long id;
    private LandShapeDto shape;
    private TypeOfLotDto typeOfLot;
    private BigDecimal landSize;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal front;
    private BigDecimal back;
    private String dimensionLand; // Auto calculated: Width x Length
    private FlatUnitTypeDto flatUnitType;
    private Integer numberOfLot;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
