package com.lyhorng.pedssystem.repository.property.building;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyhorng.pedssystem.enums.FloorAreaType;
import com.lyhorng.pedssystem.model.property.building.FloorArea;

@Repository
public interface FloorAreaRepository extends JpaRepository<FloorArea, Long> {
    List<FloorArea> findByBuildingId(Long buildingId);
    List<FloorArea> findByBuildingIdAndFloorType(Long buildingId, FloorAreaType floorType);
}