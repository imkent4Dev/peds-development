package com.lyhorng.pedssystem.service.branchRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.branchRequest.Branch;
import com.lyhorng.pedssystem.repository.branchRequest.BranchRepository;

@Service
public class BranchService {
    
    @Autowired
    public BranchRepository branchRepository;

    public Page<Branch> getAllBranch(int page, int size) {
        int pageIndex = Math.max(0, page -1);
        Pageable pageable = PageRequest.of(pageIndex, size);
        return branchRepository.findAll(pageable);
    }
}
