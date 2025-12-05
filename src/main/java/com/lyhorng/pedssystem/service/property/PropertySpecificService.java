package com.lyhorng.pedssystem.service.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lyhorng.pedssystem.model.property.PropertySpecific;
import com.lyhorng.pedssystem.repository.property.PropertySpecificRepository;

@Service
public class PropertySpecificService {

    @Autowired
    public PropertySpecificRepository propertySpecificRepository;

    @Transactional(readOnly = true)
    public Page<PropertySpecific> getAllList(int page, int size) {
        int pageIndex = Math.max(0, page -1);
        Pageable pageable = PageRequest.of(pageIndex, size);
       return propertySpecificRepository.findAll(pageable);
    }
}
