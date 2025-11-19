package com.lyhorng.pedssystem.service.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lyhorng.pedssystem.model.property.PropertySpecific;
import com.lyhorng.pedssystem.repository.property.PropertySpecificRepository;

@Service
public class PropertySpecificService {

    @Autowired
    public PropertySpecificRepository propertySpecificRepository;

    @Transactional(readOnly = true)
    public List<PropertySpecific> getAllList() {
       return propertySpecificRepository.findAll();
    }
}
