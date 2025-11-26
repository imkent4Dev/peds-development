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
import com.lyhorng.pedssystem.model.branchRequest.Branch;
import com.lyhorng.pedssystem.service.branchRequest.BranchService;

@RestController
@RequestMapping("/api/branch")
public class BranchController {

    @Autowired
    public BranchService branchService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageResponse<Branch>>> listAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {

        Page<Branch> branchListPage = branchService.getAllBranch(page, size);

        PageResponse<Branch> pageResponse = PageResponse.of(
            branchListPage.getContent(),
            branchListPage.getNumber() + 1,
            branchListPage.getSize(),
            branchListPage.getTotalElements());

        return ResponseEntity.ok(ApiResponse.success("Branch fetch success!", pageResponse));
    }

}
