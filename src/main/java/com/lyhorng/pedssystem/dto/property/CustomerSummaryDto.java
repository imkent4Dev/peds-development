package com.lyhorng.pedssystem.dto.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSummaryDto {
    private Long id;
    private String fullName;
    private String phoneNumber;
}