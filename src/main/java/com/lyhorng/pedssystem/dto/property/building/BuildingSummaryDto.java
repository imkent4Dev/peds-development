package com.lyhorng.pedssystem.dto.property.building;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingSummaryDto {
    private BigDecimal totalMFA;           // Sum of Main Floor Areas
    private BigDecimal totalAFA;           // Sum of Ancillary Floor Areas
    private BigDecimal accommodationUnit;  // MFA + AFA
    private BigDecimal totalApproximately; // Same as accommodationUnit
}