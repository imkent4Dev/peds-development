package com.lyhorng.pedssystem.enums;

public enum BuildingSourceType {
    BRANCH("By Branch"),
    SME("By SME"),
    MEGA("By Mega"),
    AGENCY_CPL("By Agency - CPL"),
    AGENCY_LUCKY("By Agency - Lucky Real estate"),
    AGENCY_KEY("By Agency - Key Real Estate"),
    AGENCY_VTRUST("By Agency - V-Trust"),
    AGENCY_KNIGHT_FRANK("By Agency - Knight Frank");
    
    private final String displayName;
    
    BuildingSourceType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}