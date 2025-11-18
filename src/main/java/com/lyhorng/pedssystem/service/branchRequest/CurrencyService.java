package com.lyhorng.pedssystem.service.branchRequest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.branchRequest.Currency;
import com.lyhorng.pedssystem.repository.branchRequest.CurrencyRepository;

@Service
public class CurrencyService {
    
    @Autowired
    public CurrencyRepository currencyRepository;

    public List<Currency> getAllCurrency() {
        return currencyRepository.findAll();
    }
}
