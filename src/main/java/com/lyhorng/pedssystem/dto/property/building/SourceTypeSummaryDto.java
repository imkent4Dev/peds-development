package com.lyhorng.pedssystem.dto.property.building;

import lombok.Data;

@Data
public class SourceTypeSummaryDto {
    private Long id;
    private String name;        // BRANCH, SME, MEGA, AGENCY
    private String displayName; // "By Branch", "By SME", etc.
}