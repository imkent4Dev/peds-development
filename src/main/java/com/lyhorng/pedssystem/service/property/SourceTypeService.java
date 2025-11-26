package com.lyhorng.pedssystem.service.property;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    
    public Page<BuildingSourceType> getAllSourceTypes(int page, int size) {
        int pageIndex = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(pageIndex, size);
        return sourceTypeRepository.findAll(pageable);
    }
    
    public List<Agency> getAgenciesBySourceType(Long sourceTypeId) {
        return agencyRepository.findBySourceTypeId(sourceTypeId);
    }
    
    public BuildingSourceType getSourceTypeByName(String name) {
        return sourceTypeRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Source type not found: " + name));
    }
}