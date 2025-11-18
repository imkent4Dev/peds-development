package com.lyhorng.pedssystem.repository.branchRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyhorng.pedssystem.model.branchRequest.BranchRequestCoBorrower;

@Repository
public interface BranchRequestCoBorrowerRepository extends JpaRepository<BranchRequestCoBorrower, Long> {
}