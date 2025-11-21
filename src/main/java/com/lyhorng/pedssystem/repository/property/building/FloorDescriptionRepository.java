package com.lyhorng.pedssystem.repository.property.building;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyhorng.pedssystem.enums.FloorAreaType;
import com.lyhorng.pedssystem.model.property.building.FloorDescription;

@Repository
public interface FloorDescriptionRepository extends JpaRepository<FloorDescription, Long> {
    List<FloorDescription> findByFloorType(FloorAreaType floorType);
    Optional<FloorDescription> findByDescriptionNameAndFloorType(String descriptionName, FloorAreaType floorType);
}
