package com.lyhorng.pedssystem.repository.branchRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lyhorng.pedssystem.model.branchRequest.LoanType;

public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {

    
}