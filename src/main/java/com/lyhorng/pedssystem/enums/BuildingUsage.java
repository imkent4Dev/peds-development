package com.lyhorng.pedssystem.enums;

public enum BuildingUsage {
    OWNER("Owner"),
    RENT_OUT("Rent out");
    
    private final String value;
    
    BuildingUsage(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}