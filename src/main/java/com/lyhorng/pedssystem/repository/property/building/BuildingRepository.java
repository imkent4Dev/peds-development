package com.lyhorng.pedssystem.repository.property.building;

import com.lyhorng.pedssystem.model.property.building.*;
import com.lyhorng.pedssystem.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// ==================== BUILDING REPOSITORY ====================
@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    List<Building> findByPropertyId(Long propertyId);
    List<Building> findBySourceType(BuildingSourceType sourceType);
    List<Building> findByPropertyIdAndSourceType(Long propertyId, BuildingSourceType sourceType);
}