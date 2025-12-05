package com.lyhorng.pedssystem.service.property.validator;

import com.lyhorng.pedssystem.dto.property.PropertyRequestDto;
import com.lyhorng.pedssystem.enums.EvaStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PropertyValidator {

    public void validateCreate(PropertyRequestDto dto) {
        if (dto == null) throw new IllegalArgumentException("Property request cannot be null");

        if (dto.getEvaStatus() == EvaStatus.RENEW &&
                !StringUtils.hasText(dto.getExistApplicationCode())) {
            throw new IllegalArgumentException("Existing application code required for RENEW");
        }
    }

    public void validateUpdate(PropertyRequestDto dto) {
        validateCreate(dto);
    }
}
