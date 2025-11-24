package com.lyhorng.pedssystem.service.property;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.agency.Agency;
import com.lyhorng.pedssystem.model.property.building.BuildingSourceType;
import com.lyhorng.pedssystem.repository.agency.AgencyRepository;
import com.lyhorng.pedssystem.repository.property.building.SourceTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SourceTypeService {
    
    private final SourceTypeRepository sourceTypeRepository;
    private final AgencyRepository agencyRepository;
    
    public List<BuildingSourceType> getAllSourceTypes() {
        return sourceTypeRepository.findAll();
    }
    
    public List<Agency> getAgenciesBySourceType(Long sourceTypeId) {
        return agencyRepository.findBySourceTypeId(sourceTypeId);
    }
    
    public BuildingSourceType getSourceTypeByName(String name) {
        return sourceTypeRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Source type not found: " + name));
    }
}