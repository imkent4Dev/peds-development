package com.lyhorng.pedssystem.service.agency;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.agency.Agency;
import com.lyhorng.pedssystem.repository.agency.AgencyRepository;

@Service
public class AgencyService {
    
    @Autowired
    private AgencyRepository agencyRepository;
    
    public List<Agency> getAgenciesBySourceType(String sourceTypeCode) {
        return agencyRepository.findBySourceTypeCode(sourceTypeCode);
    }
}
