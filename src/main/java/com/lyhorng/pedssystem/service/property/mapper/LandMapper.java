package com.lyhorng.pedssystem.service.property.mapper;

import com.lyhorng.pedssystem.dto.property.LandRequestDto;
import com.lyhorng.pedssystem.dto.property.LandResponseDto;
import com.lyhorng.pedssystem.model.property.land.Land;
import org.springframework.stereotype.Component;

@Component
public class LandMapper {

    public Land requestToEntity(LandRequestDto dto) {
        Land land = new Land();
        land.setLandSize(dto.getLandSize());
        land.setLength(dto.getLength());
        land.setWidth(dto.getWidth());
        land.setFront(dto.getFront());
        land.setBack(dto.getBack());
        land.setNumberOfLot(dto.getNumberOfLot());
        return land;
    }

    public void updateEntityFromRequest(LandRequestDto dto, Land land) {
        land.setLandSize(dto.getLandSize());
        land.setLength(dto.getLength());
        land.setWidth(dto.getWidth());
        land.setFront(dto.getFront());
        land.setBack(dto.getBack());
        land.setNumberOfLot(dto.getNumberOfLot());
    }

    public LandResponseDto entityToResponse(Land land) {
        LandResponseDto dto = new LandResponseDto();
        dto.setId(land.getId());
        dto.setLandSize(land.getLandSize());
        dto.setLength(land.getLength());
        dto.setWidth(land.getWidth());
        dto.setNumberOfLot(land.getNumberOfLot());
        return dto;
    }
}
