package com.lyhorng.pedssystem.service.branchRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.branchRequest.Branch;
import com.lyhorng.pedssystem.repository.branchRequest.BranchRepository;

@Service
public class BranchService {
    
    @Autowired
    public BranchRepository branchRepository;

    public List<Branch> getAllBranch() {
        return branchRepository.findAll();
    }
}
