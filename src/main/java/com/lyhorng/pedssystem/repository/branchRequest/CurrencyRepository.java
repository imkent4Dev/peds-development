package com.lyhorng.pedssystem.repository.branchRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lyhorng.pedssystem.model.branchRequest.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long>{
    
}
