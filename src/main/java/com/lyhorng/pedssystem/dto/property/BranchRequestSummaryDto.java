package com.lyhorng.pedssystem.dto.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchRequestSummaryDto {
    private Long id;
    private String requestCode;
    private String branchName;
}