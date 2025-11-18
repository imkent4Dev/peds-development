package com.lyhorng.pedssystem.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class BranchRequestResponseDTO {
    private Long id;
    private BranchDTO branch;
    private LocalDateTime submitDate;
    private CustomerDTO mainBorrower;
    private List<CoBorrowerResponseDTO> coBorrowers;
    private LoanTypeDTO loanType;
    private RequestForDTO requestFor;
    private CurrencyDTO currency;
    private BigDecimal requestLimit;
    private String remark;
}

@Data
class BranchDTO {
    private Long id;
    private String name;
}

@Data
class CustomerDTO {
    private Long id;
    private String name;
    private String phone;
}

@Data
class CoBorrowerResponseDTO {
    private Long id;
    private String name;
    private String phone;
    private RelationshipTypeDTO relationshipType;
}

@Data
class RelationshipTypeDTO {
    private Long id;
    private String name;
    private String description;
}

@Data
class LoanTypeDTO {
    private Long id;
    private String name;
}

@Data
class RequestForDTO {
    private Long id;
    private String name;
}

@Data
class CurrencyDTO {
    private Long id;
    private String code;
}