package com.lyhorng.pedssystem.controller.branchRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lyhorng.pedssystem.dto.BranchRequest.BranchRequestDTO;
import com.lyhorng.pedssystem.model.branchRequest.BranchRequest;
import com.lyhorng.pedssystem.service.branchRequest.BranchRequestService;
import com.lyhorng.common.response.ApiResponse;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/branchrequest")
public class BranchRequestController {
    
    @Autowired
    private BranchRequestService branchRequestService;
    
    // Create a new branch request
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BranchRequest>> createBranchRequest(
            @Valid @RequestBody BranchRequestDTO branchRequestDTO) {
        BranchRequest createdBranchRequest = branchRequestService.createBranchRequest(branchRequestDTO);
        return ResponseEntity.ok(ApiResponse.success("Branch request created successfully!", createdBranchRequest));
    }
    
    // Get all branch requests
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<BranchRequest>>> getAllBranchRequests() {
        List<BranchRequest> branchRequests = branchRequestService.getAllBranchRequests();
        return ResponseEntity.ok(ApiResponse.success("Branch requests fetched successfully!", branchRequests));
    }
    
    // Get a branch request by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BranchRequest>> getBranchRequestById(@PathVariable Long id) {
        BranchRequest branchRequest = branchRequestService.getBranchRequestById(id);
        return ResponseEntity.ok(ApiResponse.success("Branch request fetched successfully!", branchRequest));
    }
    
    // Update a branch request by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<BranchRequest>> updateBranchRequest(
            @PathVariable Long id, 
            @Valid @RequestBody BranchRequestDTO branchRequestDTO) {
        BranchRequest updatedBranchRequest = branchRequestService.updateBranchRequest(id, branchRequestDTO);
        return ResponseEntity.ok(ApiResponse.success("Branch request updated successfully!", updatedBranchRequest));
    }
    
    // Delete a branch request by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBranchRequest(@PathVariable Long id) {
        branchRequestService.deleteBranchRequest(id);
        return ResponseEntity.ok(ApiResponse.success("Branch request deleted successfully!", "Deleted successfully"));
    }
}