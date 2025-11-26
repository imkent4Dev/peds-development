package com.lyhorng.pedssystem.service.agency;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.agency.Agency;
import com.lyhorng.pedssystem.repository.agency.AgencyRepository;

@Service
public class AgencyService {
    
    @Autowired
    private AgencyRepository agencyRepository;
    
    public Page<Agency> getAllAgencies(int page, int size) {
        // Convert 1-based page to 0-based for Spring Data
        int pageIndex = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(pageIndex, size);
        return agencyRepository.findAll(pageable);
    }
    
    public List<Agency> getAgenciesBySourceType(String sourceTypeCode) {
        return agencyRepository.findBySourceTypeCode(sourceTypeCode);
    }
}