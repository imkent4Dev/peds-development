package com.lyhorng.pedssystem.service.property;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lyhorng.pedssystem.model.property.PropertyMeasureInfo;
import com.lyhorng.pedssystem.repository.property.MeasureInfoRepository;

@Service
public class MeasureInfoService {
    
    @Autowired
    public MeasureInfoRepository measureInfoRepository;

    public List<PropertyMeasureInfo> getAllList() {
        return measureInfoRepository.findAll();
    }
}
