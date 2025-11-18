package com.lyhorng.pedssystem.service.branchRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.branchRequest.LoanType;
import com.lyhorng.pedssystem.repository.branchRequest.LoanTypeRepository;

@Service
public class LoanTypeService {
    
    @Autowired
    public LoanTypeRepository loanTypeRepository;

    public List<LoanType> getAllLoanType() {
        return loanTypeRepository.findAll();
    }
}
