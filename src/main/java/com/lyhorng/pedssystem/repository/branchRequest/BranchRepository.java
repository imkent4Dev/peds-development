package com.lyhorng.pedssystem.repository.branchRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lyhorng.pedssystem.model.branchRequest.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    
}