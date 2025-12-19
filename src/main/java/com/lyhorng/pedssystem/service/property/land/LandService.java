package com.lyhorng.pedssystem.service.property.land;

import com.lyhorng.pedssystem.model.property.Property;
import com.lyhorng.pedssystem.model.property.land.Land;
import com.lyhorng.pedssystem.repository.property.land.LandRepository;
import com.lyhorng.pedssystem.repository.property.land.LandShapeRepository;
import com.lyhorng.pedssystem.repository.property.land.TypeOfLotRepository;
import com.lyhorng.pedssystem.service.property.mapper.LandMapper;
import com.lyhorng.pedssystem.repository.property.land.FlatUnitTypeRepository;
import com.lyhorng.pedssystem.dto.property.LandRequestDto;
import com.lyhorng.pedssystem.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandService {

    private final LandRepository landRepository;
    private final LandShapeRepository landShapeRepository;
    private final TypeOfLotRepository typeOfLotRepository;
    private final FlatUnitTypeRepository flatUnitTypeRepository;
    private final LandMapper landMapper;

    public Land createLand(LandRequestDto dto, Property property) {
        Land land = landMapper.requestToEntity(dto);
        land.setProperty(property);

        land.setShape(landShapeRepository.findById(dto.getShapeId())
                .orElseThrow(() -> new ResourceNotFoundException("Land shape not found")));

        land.setTypeOfLot(typeOfLotRepository.findById(dto.getTypeOfLotId())
                .orElseThrow(() -> new ResourceNotFoundException("Type of lot not found")));

        if (dto.getFlatUnitTypeId() != null) {
            land.setFlatUnitType(flatUnitTypeRepository.findById(dto.getFlatUnitTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Flat unit type not found")));
        }

        return landRepository.save(land);
    }

    public void updateLand(LandRequestDto dto, Property property) {
        if (dto == null) return;

        if (property.getLand() == null) {
            createLand(dto, property);
            return;
        }

        Land land = property.getLand();
        landMapper.updateEntityFromRequest(dto, land);

        land.setShape(landShapeRepository.findById(dto.getShapeId())
                .orElseThrow(() -> new ResourceNotFoundException("Land shape not found")));

        land.setTypeOfLot(typeOfLotRepository.findById(dto.getTypeOfLotId())
                .orElseThrow(() -> new ResourceNotFoundException("Type of lot not found")));

        if (dto.getFlatUnitTypeId() != null) {
            land.setFlatUnitType(flatUnitTypeRepository.findById(dto.getFlatUnitTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Flat unit type not found")));
        } else {
            land.setFlatUnitType(null);
        }

        landRepository.save(land);
    }
}
