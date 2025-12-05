package com.lyhorng.pedssystem.service.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.lyhorng.pedssystem.model.property.PropertyMeasureInfo;
import com.lyhorng.pedssystem.repository.property.MeasureInfoRepository;

@Service
public class MeasureInfoService {
    
    @Autowired
    public MeasureInfoRepository measureInfoRepository;

    public Page<PropertyMeasureInfo> getAllList(int page, int size) {
        int pageIndex = Math.max(0, page -1);
        Pageable pageable = PageRequest.of(pageIndex, size);
        return measureInfoRepository.findAll(pageable);
    }
}
