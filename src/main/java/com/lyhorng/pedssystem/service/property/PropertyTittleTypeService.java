package com.lyhorng.pedssystem.service.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.lyhorng.pedssystem.model.property.PropertyTitleType;
import com.lyhorng.pedssystem.repository.property.PropertyTitleTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyTittleTypeService {

    @Autowired
    private PropertyTitleTypeRepository propertyTittleTypeRepository;

    // Get all Property Title Types
    public List<PropertyTitleType> getAllPropertyTitleTypes() {
        return propertyTittleTypeRepository.findAll();
    }

    // Create a new Property Title Type
    public PropertyTitleType createPropertyTittleType(String titleType) {
        PropertyTitleType propertyTittleType = new PropertyTitleType();
        propertyTittleType.setTitleType(titleType);
        return propertyTittleTypeRepository.save(propertyTittleType);
    }

    // Update an existing Property Title Type
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

    // Delete a Property Title Type
    public void deletePropertyTittleType(Long id) {
        Optional<PropertyTitleType> propertyTittleType = propertyTittleTypeRepository.findById(id);
        if (propertyTittleType.isPresent()) {
            propertyTittleTypeRepository.delete(propertyTittleType.get());
        } else {
            throw new RuntimeException("Property Title Type not found with ID: " + id);
        }
    }

    // Get a specific Property Title Type by ID
    public PropertyTitleType getPropertyTittleTypeById(Long id) {
        return propertyTittleTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property Title Type not found with ID: " + id));
    }
}
