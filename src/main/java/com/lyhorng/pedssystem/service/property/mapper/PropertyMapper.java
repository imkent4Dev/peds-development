package com.lyhorng.pedssystem.service.property.mapper;

import com.lyhorng.pedssystem.dto.property.PropertyRequestDto;
import com.lyhorng.pedssystem.dto.property.PropertyResponseDto;
import com.lyhorng.pedssystem.dto.property.BranchRequestSummaryDto;
import com.lyhorng.pedssystem.dto.property.CustomerSummaryDto;
import com.lyhorng.pedssystem.model.property.Property;
import org.springframework.stereotype.Component;

@Component
public class PropertyMapper {

    public Property requestToEntity(PropertyRequestDto dto) {
        Property p = new Property();
        p.setEvaStatus(dto.getEvaStatus());
        p.setExistApplicationCode(dto.getExistApplicationCode());
        p.setEvaCycle(dto.getEvaCycle());
        p.setIsOwnershipTitle(dto.getIsOwnershipTitle());
        p.setTitleNumber(dto.getTitleNumber());
        p.setIsKeepRecordEvaluation(dto.getIsKeepRecordEvaluation());
        p.setRemark(dto.getRemark());
        return p;
    }

    public void updateEntityFromRequest(PropertyRequestDto dto, Property property) {
        property.setEvaStatus(dto.getEvaStatus());
        property.setExistApplicationCode(dto.getExistApplicationCode());
        property.setEvaCycle(dto.getEvaCycle());
        property.setIsOwnershipTitle(dto.getIsOwnershipTitle());
        property.setTitleNumber(dto.getTitleNumber());
        property.setIsKeepRecordEvaluation(dto.getIsKeepRecordEvaluation());
        property.setRemark(dto.getRemark());
    }

    public PropertyResponseDto entityToResponse(Property property) {
        PropertyResponseDto dto = new PropertyResponseDto();
        dto.setId(property.getId());
        dto.setApplicationCode(property.getApplicationCode());
        dto.setEvaStatus(property.getEvaStatus());
        dto.setEvaCycle(property.getEvaCycle());
        dto.setIsOwnershipTitle(property.getIsOwnershipTitle());
        dto.setTitleNumber(property.getTitleNumber());
        dto.setIsKeepRecordEvaluation(property.getIsKeepRecordEvaluation());
        dto.setRemark(property.getRemark());

        if (property.getBranchRequest() != null) {
            BranchRequestSummaryDto b = new BranchRequestSummaryDto();
            b.setId(property.getBranchRequest().getId());
            dto.setBranchRequest(b);
        }

        if (property.getOwner() != null) {
            CustomerSummaryDto c = new CustomerSummaryDto();
            c.setId(property.getOwner().getId());
            dto.setOwner(c);
        }

        return dto;
    }
}
