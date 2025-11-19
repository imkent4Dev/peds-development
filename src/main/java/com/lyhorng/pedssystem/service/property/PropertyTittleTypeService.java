package com.lyhorng.pedssystem.service.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.lyhorng.pedssystem.model.property.PropertyTittleType;
import com.lyhorng.pedssystem.repository.property.PropertyTittleTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyTittleTypeService {

    @Autowired
    private PropertyTittleTypeRepository propertyTittleTypeRepository;

    // Get all Property Title Types
    public List<PropertyTittleType> getAllPropertyTitleTypes() {
        return propertyTittleTypeRepository.findAll();
    }

    // Create a new Property Title Type
    public PropertyTittleType createPropertyTittleType(String titleType) {
        PropertyTittleType propertyTittleType = new PropertyTittleType();
        propertyTittleType.setTitleType(titleType);
        return propertyTittleTypeRepository.save(propertyTittleType);
    }

    // Update an existing Property Title Type
    public PropertyTittleType updatePropertyTittleType(Long id, String titleType, MultipartFile file) {
        Optional<PropertyTittleType> existingPropertyTittleType = propertyTittleTypeRepository.findById(id);
        if (existingPropertyTittleType.isPresent()) {
            PropertyTittleType propertyTittleType = existingPropertyTittleType.get();
            propertyTittleType.setTitleType(titleType);
            return propertyTittleTypeRepository.save(propertyTittleType);
        } else {
            throw new RuntimeException("Property Title Type not found with ID: " + id);
        }
    }

    // Delete a Property Title Type
    public void deletePropertyTittleType(Long id) {
        Optional<PropertyTittleType> propertyTittleType = propertyTittleTypeRepository.findById(id);
        if (propertyTittleType.isPresent()) {
            propertyTittleTypeRepository.delete(propertyTittleType.get());
        } else {
            throw new RuntimeException("Property Title Type not found with ID: " + id);
        }
    }

    // Get a specific Property Title Type by ID
    public PropertyTittleType getPropertyTittleTypeById(Long id) {
        return propertyTittleTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property Title Type not found with ID: " + id));
    }
}
