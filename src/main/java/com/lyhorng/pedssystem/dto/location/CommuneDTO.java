package com.lyhorng.pedssystem.dto.location;

import com.lyhorng.pedssystem.model.property.location.Commune;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommuneDTO {
    
    private Long id;
    private String type;
    private String code;
    private String khmerName;
    private String name;
    private Long distictId;
    private Long provinceId;

    public CommuneDTO(Commune commune) {
        this.id = commune.getId();
        this.type =commune.getType();
        this.code = commune.getCode();
        this.khmerName = commune.getKhmerName();
        this.name = commune.getName();
        this.distictId = commune.getDistrictId();
        this.provinceId = commune.getProvinceId();
    }
}
