package com.lyhorng.pedssystem.dto.BranchRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class BranchRequestDTO {
    private Long branchId;
    private Long mainBorrowerId;
    private List<CoBorrowerDTO> coBorrowers; // List of co-borrowers with relationships
    private Long loanTypeId;
    private Long requestForId;
    private Long currencyId;
    private BigDecimal requestLimit;
    private String remark;
    private LocalDateTime submitDate;
}