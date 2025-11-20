package com.lyhorng.pedssystem.dto.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyMeasureInfoSummaryDto {
    private Long id;
    private String status;
    private String color;
}