package com.lyhorng.pedssystem.dto.property.building;

import lombok.Data;

@Data
public class AgencySummaryDto {
    private Long id;
    private String name;        // CPL, LUCKY, KEY, etc.
    private String displayName; // "CPL", "Lucky Real Estate", etc.
}