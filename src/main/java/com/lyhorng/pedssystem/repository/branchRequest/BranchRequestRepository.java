package com.lyhorng.pedssystem.repository.branchRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyhorng.pedssystem.model.branchRequest.BranchRequest;

@Repository
public interface BranchRequestRepository extends JpaRepository<BranchRequest, Long> {
    
    @EntityGraph(attributePaths = {"branch", "mainBorrower", "coBorrowers", "loanType", "requestFor", "currency"})
    List<BranchRequest> findAll();
    
    @EntityGraph(attributePaths = {"branch", "mainBorrower", "coBorrowers", "loanType", "requestFor", "currency"})
    Optional<BranchRequest> findById(Long id);
    
    void deleteById(Long id);
}