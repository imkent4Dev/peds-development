package com.lyhorng.pedssystem.service.property.building;

import org.springframework.stereotype.Component;

import com.lyhorng.pedssystem.dto.property.building.BuildingRequestDto;
import com.lyhorng.pedssystem.dto.property.building.FloorAreaRequestDto;
import com.lyhorng.pedssystem.model.property.building.Building;
import com.lyhorng.pedssystem.model.property.building.FloorArea;
import com.lyhorng.pedssystem.model.property.building.BuildingSourceType;
import com.lyhorng.pedssystem.model.property.building.BuildingType;
import com.lyhorng.pedssystem.model.agency.Agency;
import com.lyhorng.pedssystem.repository.property.building.BuildingTypeRepository;
import com.lyhorng.pedssystem.repository.property.building.SourceTypeRepository;
import com.lyhorng.pedssystem.repository.agency.AgencyRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component("buildingDtoMapper")
public class BuildingMapper {

    private final SourceTypeRepository sourceTypeRepository;
    private final AgencyRepository agencyRepository;
    private final BuildingTypeRepository buildingTypeRepository;

    // === Map BuildingRequestDto -> Building entity ===
    public Building requestToEntity(BuildingRequestDto dto) {
        Building building = new Building();

        // Source Type
        if (dto.getSourceTypeId() != null) {
            BuildingSourceType sourceType = sourceTypeRepository.findById(dto.getSourceTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Source type not found with id: " + dto.getSourceTypeId()));
            building.setSourceType(sourceType);
        }

        // Agency (only if provided)
        if (dto.getAgencyId() != null) {
            Agency agency = agencyRepository.findById(dto.getAgencyId())
                    .orElseThrow(() -> new IllegalArgumentException("Agency not found with id: " + dto.getAgencyId()));
            building.setAgency(agency);
        }

        // Building Type
        if (dto.getBuildingTypeId() != null) {
            BuildingType type = buildingTypeRepository.findById(dto.getBuildingTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Building type not found with id: " + dto.getBuildingTypeId()));
            building.setBuildingType(type);
        }

        // Other properties
        building.setBuildingEvaluation(dto.getBuildingEvaluation());
        building.setBuildingUsage(dto.getBuildingUsage());
        // building.setBuildingStructure(dto.getBuildingStructure());
        building.setBuildingStories(dto.getBuildingStories());
        building.setBuildingSizeUnit(dto.getBuildingSizeUnit());
        building.setBuildingYearBuilt(dto.getBuildingYearBuilt());
        building.setRemark(dto.getRemark());

        return building;
    }

    // === Map FloorAreaRequestDto -> FloorArea entity ===
    public FloorArea floorAreaRequestToEntity(FloorAreaRequestDto dto) {
        FloorArea floorArea = new FloorArea();

        floorArea.setFloorType(dto.getFloorType());
        // floorArea.setFloorDescriptionId(dto.getFloorDescriptionId());
        floorArea.setSize(dto.getSize());
        floorArea.setLength(dto.getLength());
        floorArea.setDisplayOrder(dto.getDisplayOrder());

        return floorArea;
    }
}
