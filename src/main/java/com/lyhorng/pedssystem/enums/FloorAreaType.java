package com.lyhorng.pedssystem.enums;

public enum FloorAreaType {
    MAIN("Main Floor Area"),
    ANCILLARY("Ancillary Floor Area");
    
    private final String value;
    
    FloorAreaType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}