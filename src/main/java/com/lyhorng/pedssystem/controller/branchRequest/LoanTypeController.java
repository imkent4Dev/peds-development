package com.lyhorng.pedssystem.controller.branchRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.common.response.PageResponse;
import com.lyhorng.pedssystem.model.branchRequest.LoanType;
import com.lyhorng.pedssystem.service.branchRequest.LoanTypeService;

@RestController
@RequestMapping("/api/loantype")
public class LoanTypeController {

    @Autowired
    public LoanTypeService loanTypeService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageResponse<LoanType>>> listLoanType(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<LoanType> loanTypeList = loanTypeService.getAllLoanType(page, size);

        PageResponse<LoanType> pageResponse = PageResponse.of(
                loanTypeList.getContent(),
                loanTypeList.getNumber() + 1,
                loanTypeList.getSize(),
                loanTypeList.getTotalElements());
        return ResponseEntity.ok(ApiResponse.success("Loan Type fetch success", pageResponse));
    }
}
