package com.lyhorng.pedssystem.repository.property.land;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyhorng.pedssystem.model.property.land.FlatUnitType;

@Repository
public interface FlatUnitTypeRepository extends JpaRepository<FlatUnitType, Long> {
    // Method name must match entity field name exactly
    Optional<FlatUnitType> findByUnitTypeName(String unitTypeName);
}