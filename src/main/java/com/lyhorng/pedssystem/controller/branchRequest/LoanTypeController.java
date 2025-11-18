package com.lyhorng.pedssystem.controller.branchRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.pedssystem.model.branchRequest.LoanType;
import com.lyhorng.pedssystem.service.branchRequest.LoanTypeService;

@RestController
@RequestMapping("/api/loantype")
public class LoanTypeController {
    
    @Autowired
    public LoanTypeService loanTypeService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<LoanType>>> listLoanType() {
        List<LoanType> loanTypeList = loanTypeService.getAllLoanType();
        return ResponseEntity.ok(ApiResponse.success("Loan Type fetch success", loanTypeList));
    }
}
