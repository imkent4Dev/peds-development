package com.lyhorng.pedssystem.service.property.mapper;

import com.lyhorng.pedssystem.dto.property.building.BuildingRequestDto;
import com.lyhorng.pedssystem.dto.property.building.FloorAreaRequestDto;
import com.lyhorng.pedssystem.model.property.building.Building;
import com.lyhorng.pedssystem.model.property.building.FloorArea;
import org.springframework.stereotype.Component;

@Component
public class BuildingMapper {

    public Building requestToEntity(BuildingRequestDto dto) {
        Building b = new Building();
        b.setBuildingEvaluation(dto.getBuildingEvaluation());
        b.setBuildingUsage(dto.getBuildingUsage());
        b.setBuildingStories(dto.getBuildingStories());
        b.setBuildingSizeUnit(dto.getBuildingSizeUnit());
        b.setBuildingYearBuilt(dto.getBuildingYearBuilt());
        b.setRemark(dto.getRemark());
        return b;
    }

    public FloorArea floorAreaRequestToEntity(FloorAreaRequestDto dto) {
        FloorArea f = new FloorArea();
        f.setFloorType(dto.getFloorType());
        f.setSize(dto.getSize());
        f.setLength(dto.getLength());
        f.setDisplayOrder(dto.getDisplayOrder());
        return f;
    }
}
