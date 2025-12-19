package com.lyhorng.pedssystem.service.property.building;

import com.lyhorng.pedssystem.dto.property.building.BuildingRequestDto;
import com.lyhorng.pedssystem.dto.property.building.FloorAreaRequestDto;
import com.lyhorng.pedssystem.model.property.Property;
import com.lyhorng.pedssystem.model.property.building.Building;
import com.lyhorng.pedssystem.model.property.building.FloorArea;
import com.lyhorng.pedssystem.repository.property.building.*;
import com.lyhorng.pedssystem.repository.agency.AgencyRepository;
import com.lyhorng.pedssystem.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final SourceTypeRepository sourceTypeRepository;
    private final BuildingTypeRepository buildingTypeRepository;
    private final AgencyRepository agencyRepository;
    private final FloorAreaRepository floorAreaRepository;
    private final FloorDescriptionRepository floorDescriptionRepository;
    private final BuildingMapper buildingMapper;

    public void createBuildings(List<BuildingRequestDto> dtos, Property property) {
        if (dtos == null || dtos.isEmpty()) return;

        for (BuildingRequestDto dto : dtos) {
            Building building = buildingMapper.requestToEntity(dto);
            building.setProperty(property);

            building.setSourceType(sourceTypeRepository.findById(dto.getSourceTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Source type not found")));

            if (dto.getAgencyId() != null) {
                building.setAgency(agencyRepository.findById(dto.getAgencyId())
                        .orElseThrow(() -> new ResourceNotFoundException("Agency not found")));
            }

            building.setBuildingType(buildingTypeRepository.findById(dto.getBuildingTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Building type not found")));

            Building saved = buildingRepository.save(building);

            if (dto.getFloorAreas() != null) {
                for (FloorAreaRequestDto floorDto : dto.getFloorAreas()) {
                    FloorArea floorArea = buildingMapper.floorAreaRequestToEntity(floorDto);
                    floorArea.setBuilding(saved);

                    floorArea.setFloorDescription(
                        floorDescriptionRepository.findById(floorDto.getFloorDescriptionId())
                                .orElseThrow(() -> new ResourceNotFoundException("Floor description not found"))
                    );

                    floorAreaRepository.save(floorArea);
                }
            }

            saved.calculateTotals();
            buildingRepository.save(saved);
        }
    }

    public void updateBuildings(List<BuildingRequestDto> dtos, Property property) {
        if (property.getBuildings() != null && !property.getBuildings().isEmpty()) {
            buildingRepository.deleteAll(property.getBuildings());
            property.getBuildings().clear();
        }

        createBuildings(dtos, property);
    }
}
