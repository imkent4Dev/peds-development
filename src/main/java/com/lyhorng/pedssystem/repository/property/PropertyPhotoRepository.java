package com.lyhorng.pedssystem.repository.property;

import com.lyhorng.pedssystem.model.property.PropertyPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyPhotoRepository extends JpaRepository<PropertyPhoto, Long> {

    List<PropertyPhoto> findByPropertyId(Long propertyId);
}


