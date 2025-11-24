package com.lyhorng.pedssystem.repository.property.building;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lyhorng.pedssystem.model.property.building.BuildingSourceType;

public interface SourceTypeRepository extends JpaRepository<BuildingSourceType, Long> {
     Optional<BuildingSourceType> findByName(String name);
}