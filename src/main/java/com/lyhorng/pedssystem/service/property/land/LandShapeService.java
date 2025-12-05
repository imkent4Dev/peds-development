package com.lyhorng.pedssystem.service.property.land;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.property.land.LandShape;
import com.lyhorng.pedssystem.repository.property.land.LandShapeRepository;

@Service
public class LandShapeService {
    
    @Autowired
    public LandShapeRepository landShapeRepository;

    public Page<LandShape> getAll(int page, int size) {
      int pageIndex = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(pageIndex, size);
        return landShapeRepository.findAll(pageable);
    }
}
