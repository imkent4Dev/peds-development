package com.lyhorng.pedssystem.service.branchRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.branchRequest.Currency;
import com.lyhorng.pedssystem.repository.branchRequest.CurrencyRepository;

@Service
public class CurrencyService {
    
    @Autowired
    public CurrencyRepository currencyRepository;

    public Page<Currency> getAllCurrency(int page, int size) {
        int pageIndex = Math.max(0, page -1);
        Pageable pageable = PageRequest.of(pageIndex, size);
        return currencyRepository.findAll(pageable);
    }
}
