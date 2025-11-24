package com.lyhorng.pedssystem.dto;

import lombok.Data;

@Data
public class AgencyDTO {
    private Long id;
    private String name;
    private String displayName;
    private Long sourceTypeId;
}