package com.lyhorng.pedssystem.controller.branchRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.pedssystem.model.branchRequest.Branch;
import com.lyhorng.pedssystem.service.branchRequest.BranchService;

@RestController
@RequestMapping("/api/branch")
public class BranchController {
    
    @Autowired
    public BranchService branchService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Branch>>> listAll() {
        List<Branch> branchList = branchService.getAllBranch();
        return ResponseEntity.ok(ApiResponse.success("Branch fetch success!", branchList));
    }

}
