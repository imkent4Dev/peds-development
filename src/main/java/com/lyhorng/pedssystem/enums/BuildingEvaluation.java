package com.lyhorng.pedssystem.enums;

// Building Evaluation enum
public enum BuildingEvaluation {
    YES("Yes"),
    NO("No"),
    REMARK("Remark");
    
    private final String value;
    
    BuildingEvaluation(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
