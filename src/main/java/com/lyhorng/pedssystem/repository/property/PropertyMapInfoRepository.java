package com.lyhorng.pedssystem.repository.property;

import com.lyhorng.pedssystem.model.property.PropertyMapInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyMapInfoRepository extends JpaRepository<PropertyMapInfo, Long> {

    Optional<PropertyMapInfo> findByPropertyId(Long propertyId);
}


