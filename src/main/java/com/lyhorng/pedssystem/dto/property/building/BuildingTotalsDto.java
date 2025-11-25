package com.lyhorng.pedssystem.dto.property.building;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingTotalsDto {
    private Integer totalBuildings;
    private BigDecimal totalMFA;
    private BigDecimal totalAFA;
    private BigDecimal grandTotal;
}