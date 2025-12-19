package com.lyhorng.pedssystem.service.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.lyhorng.pedssystem.model.property.PropertyTitleType;
import com.lyhorng.pedssystem.repository.property.PropertyTitleTypeRepository;

import java.util.Optional;

@Service
public class PropertyTittleTypeService {

    @Autowired
    private PropertyTitleTypeRepository propertyTittleTypeRepository;

    public Page<PropertyTitleType> getAllPropertyTitleTypes(int page, int size) {
        int pageIndex = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(pageIndex, size);
        return propertyTittleTypeRepository.findAll(pageable);
    }

    public PropertyTitleType createPropertyTittleType(String titleType) {
        PropertyTitleType propertyTittleType = new PropertyTitleType();
        propertyTittleType.setTitleType(titleType);
        return propertyTittleTypeRepository.save(propertyTittleType);
    }

    public PropertyTitleType updatePropertyTittleType(Long id, String titleType, MultipartFile file) {
        Optional<PropertyTitleType> existingPropertyTittleType = propertyTittleTypeRepository.findById(id);
        if (existingPropertyTittleType.isPresent()) {
            PropertyTitleType propertyTittleType = existingPropertyTittleType.get();
            propertyTittleType.setTitleType(titleType);
            return propertyTittleTypeRepository.save(propertyTittleType);
        } else {
            throw new RuntimeException("Property Title Type not found with ID: " + id);
        }
    }

    public void deletePropertyTittleType(Long id) {
        Optional<PropertyTitleType> propertyTittleType = propertyTittleTypeRepository.findById(id);
        if (propertyTittleType.isPresent()) {
            propertyTittleTypeRepository.delete(propertyTittleType.get());
        } else {
            throw new RuntimeException("Property Title Type not found with ID: " + id);
        }
    }

    public PropertyTitleType getPropertyTittleTypeById(Long id) {
        return propertyTittleTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property Title Type not found with ID: " + id));
    }
}
